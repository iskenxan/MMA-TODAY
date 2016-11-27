package space.samatov.mmatoday.model;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public  class FighterReader extends JsonReader {
    public static final String UFC_FIGHTERS_KEY="ufc_fighters";
    public static final String ALL_TIME_FIGHTERS_KEY="all_time_fighters";

    private static String mFightersUrl="http://ufc-data-api.ufc.com/api/v3/iphone/fighters";
    public static final String mFightMatrixSearchUrl="http://www.fightmatrix.com/fighter-search/?fName=";
    public static final String mMartialArtsSearchUrl="http://www.mixedmartialarts.com/fighter/search?search=";
    public static final String mFightMetricsSearchUrl="http://www.fightmetric.com/statistics/fighters/search?query=";
    public static final String mAllTimeRankingsUrl="http://www.fightmatrix.com/all-time-mma-rankings";


    public ArrayList<Fighter> mUFCfighters;
    public ArrayList<Fighter> mAllTimeFighters;

    public Fighter mCurrentDetailsFighter=new Fighter();

    private ArrayList<DataListener> mListeners=new ArrayList<DataListener>();
    private ArrayList<StatsDataListener> mStatsListener=new ArrayList<>();
    private ArrayList<AllTimeDataListener> mAllTimeDataListeners=new ArrayList<>();


    public void addListener(DataListener listener){
        mListeners.add(listener);
    }
    public void addStatsListener(StatsDataListener listener){mStatsListener.add(listener);}
    public void addAllTimeRankListener(AllTimeDataListener listener){mAllTimeDataListeners.add(listener);}

    public void getFightersData(){
        getData(mFightersUrl,true);
    }



    public  void readFighterStatsHtml(Fighter fighter){
        StatsJsoupReader reader=new StatsJsoupReader();
        reader.execute(fighter);
    }

    private  class StatsJsoupReader extends AsyncTask<Fighter,Void,Bundle>{


        @Override
        protected Bundle doInBackground(Fighter... fighters) {
            Fighter fighter=fighters[0];
            mCurrentDetailsFighter=fighter;
            String fightMatrixUrl;
            String martialArtsUrl="";
            String fightmeetricsUrl=mFightMetricsSearchUrl+fighter.getLastName();
            if(fighter.ismIsUFC()) {
                fightMatrixUrl = mFightMatrixSearchUrl + fighter.getmUrlSearchName();
                martialArtsUrl=mMartialArtsSearchUrl+fighter.getmUrlSearchName();
            }
            else {
                fightMatrixUrl = mFightMatrixSearchUrl + fighter.getFirstName() + "+" + fighter.getLastName();
                martialArtsUrl = mMartialArtsSearchUrl + fighter.getFirstName() + "+" + fighter.getLastName();
            }
            Bundle results=new Bundle();
            Bundle basicInfo=new Bundle();
            Bundle ranks=new Bundle();
            Bundle stats=new Bundle();
            try {
                 basicInfo= getUFCAboutDetailsData(fighter.getmFighterDetailsPageUrl(),martialArtsUrl);
                basicInfo.putString("first",fighter.getFirstName());
                basicInfo.putString("last",fighter.getLastName());

                if(basicInfo.getString("record").equals("--"))
                    ranks= getFightMatrixData(fightMatrixUrl,true,true);
                else
                    ranks= getFightMatrixData(fightMatrixUrl,true,false);

                stats= getFightMetricsData(fightmeetricsUrl,true);

            } catch (IOException e) {
                e.printStackTrace();
            }
            results.putBundle("basic_info",basicInfo);
            results.putBundle("ranks",ranks);
            results.putBundle("stats",stats);
            return results;
        }

        public Bundle getMartialArtsData(String fightMatrixUrl,ArrayList<String> failedToGet) throws IOException{
            Document  document;
            if(mCurrentDetailsFighter.getFirstName().equals("Don")&&mCurrentDetailsFighter.getLastName().equals("Frye"))
                document =Jsoup.connect("http://www.mixedmartialarts.com/fighter/Don-Frye:1BA546076E84FC66").get();
            else
            document =Jsoup.connect(fightMatrixUrl).get();
            Bundle stats=new Bundle();
            Elements infoTable=document.select("table.fighter-info");
            try {
                if(failedToGet.contains("Age")) {
                    Elements age = infoTable.select("th:containsOwn(Age:)+td");
                    stats.putString("age",age.text());
                }
                else
                    stats.putString("age","--");
                if(failedToGet.contains("Height")) {
                    Elements height = infoTable.select("th:containsOwn(Height:)+td");
                    stats.putString("height",height.text());
                }
                else
                    stats.putString("height","--");
                if(failedToGet.contains("Weight")) {
                    Elements weight = infoTable.select("th:containsOwn(Weight:)+td");
                    stats.putString("height",weight.text());
                }
                else
                    stats.putString("weight","--");
                if(failedToGet.contains("Out")) {
                    Elements outOf = infoTable.select("th:containsOwn(Out of:)+td");
                    stats.putString("out",outOf.text());
                }
                else
                    stats.putString("out","--");
                if(failedToGet.contains("Record")){
                    Elements record= infoTable.select("th:containsOwn(Pro Record:)+td");
                    stats.putString("record",record.text());
                }
                else
                    stats.putString("record","--");
            }
            catch (Exception e){
                stats.putString("age","--");
                stats.putString("height","--");
                stats.putString("weight","--");
                stats.putString("out","--");
                stats.putString("record","--");
            }
            return stats;
        }

        public Bundle getUFCAboutDetailsData(String ufcUrl,String martialArtsUrl) throws IOException{
            Bundle stats=new Bundle();
            ArrayList<String> failedToGet = new ArrayList<>();
            if(mCurrentDetailsFighter.ismIsUFC()) {
                Document document = Jsoup.connect(ufcUrl).get();


                Elements outOf = document.select("li:contains(From:)");
                Elements age = document.select("li:contains(Age:)");
                Elements height = document.select("li:contains(Height:)");
                Elements weight = document.select("li:contains(Weight:)");
                Elements record=document.select("li:contains(Record:)");
                String ageStr = age.text().replaceAll("Age:", "");
                if (ageStr.equals(""))
                    failedToGet.add("Age");
                String outStr = outOf.text().replaceAll("From:", "");
                if (outStr.equals(""))
                    failedToGet.add("Out");
                String heightStr = height.text().replaceAll("Height:", "");
                if (heightStr.equals(""))
                    failedToGet.add("Height");
                String weightStr = weight.text().replaceAll("Weight:", "");
                if (weightStr.equals(""))
                    failedToGet.add("Weight");
                Bundle result = new Bundle();
                if (failedToGet.size() > 0) {

                    result = getMartialArtsData(martialArtsUrl, failedToGet);
                    if (!result.getString("age").equals("--"))
                        stats.putString("age", result.getString("age"));
                    else
                        stats.putString("age", ageStr);
                    if (!result.getString("height").equals("--"))
                        stats.putString("height", result.getString("height"));
                    else
                        stats.putString("height", heightStr);
                    if (!result.getString("weight").equals("--"))
                        stats.putString("weight", result.getString("weight"));
                    else
                        stats.putString("weight", weightStr);
                    if (!result.getString("out").equals("--"))
                        stats.putString("out", result.getString("out"));
                    else
                        stats.putString("out", outStr);
                    if (!result.getString("record").equals("--"))
                        stats.putString("record", result.getString("record"));
                    else
                        stats.putString("record", "n/a");
                }
                else {
                    stats.putString("age", ageStr);
                    stats.putString("height", heightStr);
                    stats.putString("weight", weightStr);
                    stats.putString("out", outStr);
                    stats.putString("record", "n/a");
                }
            }
            else {
                failedToGet.add("Age");
                failedToGet.add("Out");
                failedToGet.add("Height");
                failedToGet.add("Weight");
                failedToGet.add("Record");
                stats= getMartialArtsData(martialArtsUrl,failedToGet);
            }
            return stats;
        }


        public Bundle getFightMatrixData(String url,boolean isFirstTime,boolean getRecord) throws IOException {
            Document document=Jsoup.connect(url).get();
            Bundle stats=new Bundle();
            String fighterUrl;

            Elements table=document.select("table.tblRank");
            Elements links=table.select("a[href]");
            try {
            fighterUrl=links.get(0).attr("abs:href");
            Document fighterPage=Jsoup.connect(fighterUrl).get();

                Elements proDebut=fighterPage.select("td:containsOwn(Pro Debut Date:)+td");
                  stats.putString("pro_debut",proDebut.text());

                Elements currentrank=fighterPage.select("td:contains(Current Rank:) a");
                if(currentrank.size()==0){
                    Elements lastRank=fighterPage.select("td:contains(Last Ranked:) a");
                    stats.putString("rank",lastRank.get(0).text());
                }
                else {
                    stats.putString("rank",currentrank.get(0).text());
                }
                Elements team=fighterPage.select("td:containsOwn(Association)+td");
                stats.putString("team",team.text());
                if(getRecord) {
                    Elements record = fighterPage.select("td:containsOwn(Pro Record:)+td");
                    stats.putString("record",record.text());
                }
            }
            catch (Exception e){
                if(isFirstTime) {
                    try {
                        stats = getFightMatrixData(mFightMatrixSearchUrl + mCurrentDetailsFighter.getFirstName() + "+" + mCurrentDetailsFighter.getLastName(), false,getRecord);
                    } catch (Exception e2) {

                    }
                    if(stats.size()==0){
                        stats.putString("pro_debut","n/a");
                        stats.putString("rank","n/a");
                        stats.putString("team","n/a");
                    }
                }

            }
            return stats;
        }


        public Bundle getFightMetricsData(String url,boolean isFirstTime) throws IOException {
             Bundle stats=new Bundle();
            String userAgent = System.getProperty("http.agent");
              Document  document= (Document) Jsoup.connect(url).userAgent(userAgent).timeout(10*1000).get();
            Elements link=new Elements();
            if(!mCurrentDetailsFighter.getNickName().equals(""))
                link=document.select("a:matchesOwn(^"+mCurrentDetailsFighter.getNickName()+"$)");
            else
                 link=document.select("a:matchesOwn(^"+mCurrentDetailsFighter.getFirstName()+"$)");
            if(link.size()==0)
                link=document.select("a:containsOwn("+mCurrentDetailsFighter.getFirstName()+")");
            if(link.size()==0)
                link=document.select("a:containsOwn("+mCurrentDetailsFighter.getFirstName().split(" ")[0]+")");
            try {
                if(link.size()==0&&isFirstTime)
                {
                    String[] last=mCurrentDetailsFighter.getLastName().split(" ");
                    getFightMetricsData(mFightMetricsSearchUrl+last[1],false);
                }
                String fighterUrl = link.get(0).attr("abs:href");

                Document fighterPage = Jsoup.connect(fighterUrl).userAgent(userAgent).timeout(10*1000).get();

                Elements slpm = fighterPage.select("li:contains(SLpM:)");
                stats.putString("slpm",slpm.text().replace("SLpM:", ""));
                Elements sAccuracy = fighterPage.select("li:contains(Str. Acc.:)");
                stats.putString("saccuracy",sAccuracy.text().replace("Str. Acc.:", ""));
                Elements sapm = fighterPage.select("li:contains(SApM:)");
                stats.putString("sapm",sapm.text().replace("SApM:", ""));
                Elements sDefence = fighterPage.select("li:contains(Str. Def:)");
                stats.putString("sdefence",sDefence.text().replace("Str. Def:", ""));
                Elements tAverage = fighterPage.select("li:contains(TD Avg.:)");
                stats.putString("taverage",tAverage.text().replace("TD Avg.:", ""));
                Elements tAccuracy = fighterPage.select("li:contains(TD Acc.:)");
                stats.putString("taccuracy",tAccuracy.text().replace("TD Acc.:", ""));
                Elements tDefence = fighterPage.select("li:contains(TD Def.:)");
                stats.putString("tdefence",tDefence.text().replace("TD Def.:", ""));
                Elements submissions = fighterPage.select("li:contains(Sub. Avg.:)");
                stats.putString("submission",submissions.text().replace("Sub. Avg.:", ""));
            }
            catch (Exception e){
                e.getMessage();
            }
            return stats;
        }
        @Override
        protected void onPostExecute(Bundle bundle) {
            Bundle basicinfo=bundle.getBundle("basic_info");
            Bundle ranks=bundle.getBundle("ranks");
            Bundle stats=bundle.getBundle("stats");

            FighterStats fighterStats=new FighterStats();
            fighterStats.setmFirst(basicinfo.getString("first"));
            fighterStats.setmLast(basicinfo.getString("last"));
            fighterStats.setmAge(basicinfo.getString("age"));
            fighterStats.setmHeight(basicinfo.getString("height"));
            fighterStats.setmWeight(basicinfo.getString("weight"));
            fighterStats.setmFightsOutOf(basicinfo.getString("out"));
            fighterStats.setmProfRecord(basicinfo.getString("record"));

            fighterStats.setmRank(ranks.getString("rank"));
            fighterStats.setmTeam(ranks.getString("team"));
            fighterStats.setmProfDebut(ranks.getString("pro_debut"));

            fighterStats.setmSigStrikesLanded(stats.getString("slpm"));
            fighterStats.setmSigStrikesAccuracy(stats.getString("saccuracy"));
            fighterStats.setmStrikesAbsorbed(stats.getString("sapm"));
            fighterStats.setmSigStrikesDef(stats.getString("sdefence"));

            fighterStats.setmTakeDownsLanded(stats.getString("taverage"));
            fighterStats.setmTakeDownAccuracy(stats.getString("taccuracy"));
            fighterStats.setmTakeDownDefence(stats.getString("tdefence"));
            fighterStats.setmSubmissionAttempts(stats.getString("submission"));
            notifyListeners(fighterStats);
        }
    }


    public void notifyListeners(FighterStats fighterStats){
        for(StatsDataListener listener:mStatsListener){
            listener.OnDataReceived(fighterStats);
        }
    }

    public interface StatsDataListener{
        public void OnDataReceived(FighterStats fighterStats);
    }


    @Override
    protected void dataReceived(String json, boolean isFirstTime) {
        readJsonData(json);
    }

    public void readJsonData(String json){
        Fighter fighter=new Fighter();
        try {
            mUFCfighters =new ArrayList<Fighter>();

            JSONArray jsonArray=new JSONArray(json);
            int length=jsonArray.length();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                 fighter=new Fighter();
                fighter.setFirstName(jsonObject.getString("first_name"));
                fighter.setmIsUFC(true);
                fighter.setLastName(jsonObject.getString("last_name"));
                fighter.setNickName(jsonObject.getString("nickname"));
                fighter.setmWeightClass(jsonObject.getString("weight_class"));
                fighter.setmFighterDetailsPageUrl(jsonObject.getString("link"));
                fighter.setmUrlSearchName(null);
                if(jsonObject.has("belt_thumbnail"))
                    fighter.setmBeltProfileUrl(jsonObject.getString("belt_thumbnail"));
                int wins,losses,draws;
                try {
                    wins = jsonObject.getInt("wins");
                    losses = jsonObject.getInt("losses");
                    draws = jsonObject.getInt("draws");
                    fighter.setFightsRecord(wins,losses,draws);

                }
                catch (JSONException e) {
                    continue;
                }

                try {
                    fighter.setProfileUrl(jsonObject.getString("profile_image"));
                }
                catch (JSONException e){
                    fighter.setProfileUrl("default");
                }
                if(jsonObject.has("left_full_body_image"))
                    fighter.setFullBodyUrl(jsonObject.getString("left_full_body_image"));
                else
                    fighter.setFullBodyUrl("default");


                fighter.setPFP(jsonObject.getString("pound_for_pound_rank"));
                if(fighter.getPFP()==null)
                    fighter.setPFP("none");
                fighter.setTitleHolder(jsonObject.getBoolean("title_holder"));
                try{
                    fighter.setId(jsonObject.getInt("id"));
                }
                catch (Exception e){
                    fighter.setId(-1);
                }

                mUFCfighters.add(fighter);
            }
            notifyListeners(true);
        } catch (JSONException e) {
            Log.v("EXCEPTION",e.getMessage());
        }
    }

    protected void notifyListeners(boolean sucess){
        if(sucess)
        {
            for (DataListener listener:mListeners){
                listener.onFightersDataReceived();
            }
        }
        else {
            for (DataListener listener:mListeners){
                listener.onDataFailed();
            }
        }
    }

    public interface DataListener{
        public void onFightersDataReceived();
        public void onDataFailed();
    }

    public void getAllTimeRanks()
    {
        AllTimeRanksJsoupReader reader=new AllTimeRanksJsoupReader();
        reader.execute();
    }

    public class AllTimeRanksJsoupReader extends AsyncTask<Void,ArrayList<String>,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            ArrayList<String> allTime=new ArrayList<>();


            try {
                Document document=Jsoup.connect(mAllTimeRankingsUrl).get();
                Elements alltimeTable=document.select("td:matches(Absolute)");
                Elements allTimenames=alltimeTable.select("strong");
                for (int i=0;i<allTimenames.size();i++){
                    allTime.add("Absolute "+allTimenames.get(i).text());
                }
                Elements womenTable=document.select("td:matches(Women)");
                Elements womennames=womenTable.select("strong");
                for (int i=0;i<womennames.size();i++)
                    allTime.add("Women "+womennames.get(i).text());

                Elements heavyTable=document.select("td:matches(Heavyweight +)");
                Elements heavynames=heavyTable.get(0).select("strong");
                for (int i=0;i<heavynames.size();i++)
                    allTime.add("Heavyweight "+heavynames.get(i).text());

                Elements lightheavyTable=document.select("td:matches(Light Heavyweight)");
                Elements lightheavynames=lightheavyTable.select("strong");
                for (int i=0;i<heavynames.size();i++)
                    allTime.add("Light_Heavyweight "+lightheavynames.get(i).text());

                Elements middleTable=document.select("td:matches(Middleweight)");
                Elements middlenames=middleTable.select("strong");
                for (int i=0;i<heavynames.size();i++)
                    allTime.add("MiddleWeight "+middlenames.get(i).text());

                Elements welterTable=document.select("td:matches(Welterweight)");
                Elements welternames=welterTable.select("strong");
                for (int i=0;i<welternames.size();i++)
                    allTime.add("Welterweight "+welternames.get(i).text());

                Elements lightTable=document.select("td:matches(Lightweight)");
                Elements lightnames=lightTable.select("strong");
                for (int i=0;i<lightnames.size();i++)
                    allTime.add("Lightweight "+lightnames.get(i).text());

                Elements featherTable=document.select("td:matches(Featherweight)");
                Elements feathernames=featherTable.select("strong");
                for (int i=0;i<feathernames.size();i++)
                    allTime.add("Featherweight "+feathernames.get(i).text());

                Elements bantamTable=document.select("td:matches(Bantamweight)");
                Elements bantamnames=bantamTable.select("strong");
                for (int i=0;i<bantamnames.size();i++)
                    allTime.add("Bantamweight "+bantamnames.get(i).text());

                Elements flyweightable=document.select("td:matches(Flyweight)");
                Elements flynames=flyweightable.select("strong");
                for (int i=0;i<flynames.size();i++)
                    allTime.add("Flyweight "+flynames.get(i).text());


            } catch (IOException e) {
                e.getMessage();
            }


            return allTime;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            mAllTimeFighters=new ArrayList<>();
            for (int i=0;i<strings.size();i++){
                String string=strings.get(i);
                String[] weightClassAndName=string.split(" ",2);
                String weightclass=weightClassAndName[0];
                string=weightClassAndName[1];
                string=string.replaceAll("Georges St. Pierre","Georges St-Pierre");
                string=string.replaceAll("Quinton","Rampage");
                string=string.replaceAll("B.J.","BJ");
                string=string.replaceAll("Cristiane Justino","Cris Cyborg");
                string=string.replaceAll("T.J.","TJ");
                String[] firstAndLast=string.split(" ");
                if(string.equals("Antonio Rodrigo Nogueira")) {
                    firstAndLast[0] = "Antonio Rogerio";
                    firstAndLast[1] = "Nogueira";
                }
                if(string.contains(" dos ")){
                   string= string.replaceAll("dos","Dos");
                    firstAndLast=string.split(" ",2);
                }
                if(string.equals("Mirko Filipovic")){
                    firstAndLast[0]="Mirko";
                    firstAndLast[1]="Cro Cop";
                }
                boolean found=false;
                for (Fighter fighter:mUFCfighters){
                    if(firstAndLast[0].equals(fighter.getFirstName())&&firstAndLast[1].equals(fighter.getLastName())){
                        found=true;
                        mAllTimeFighters.add(fighter);
                    }
                }
                if(!found)
                {
                    Fighter currentFighter=new Fighter();
                    if(firstAndLast.length==3) {
                        currentFighter.setFirstName(firstAndLast[0] + " " + firstAndLast[1]);
                        currentFighter.setLastName(firstAndLast[2]);
                    }
                    else {
                        currentFighter.setFirstName(firstAndLast[0]);
                        currentFighter.setLastName(firstAndLast[1]);
                    }
                    currentFighter.setProfileUrl("default");
                    currentFighter.setFullBodyUrl("default");
                    currentFighter.setmWeightClass(weightclass);
                    currentFighter.setNickName("");
                    mAllTimeFighters.add(currentFighter);
                }
            }

            notifyAllTimeDataListeners();
        }

    }

    public void notifyAllTimeDataListeners(){
        for (AllTimeDataListener listener:mAllTimeDataListeners)
            listener.OnAllTimeDataReceived();
    }

    public interface AllTimeDataListener {
        void OnAllTimeDataReceived();
    }

}
