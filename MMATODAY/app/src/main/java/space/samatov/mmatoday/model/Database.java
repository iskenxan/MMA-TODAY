package space.samatov.mmatoday.model;


import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public  class Database  {
    private static String mFightersUrl="http://ufc-data-api.ufc.com/api/v3/iphone/fighters";
    public static final String mFightMatrixSearchUrl="http://www.fightmatrix.com/fighter-search/?fName=";
    public static final String mMartialArtsSearchUrl="http://www.mixedmartialarts.com/fighter/search?search=";
    public static final String mFightMetricsSearchUrl="http://www.fightmetric.com/statistics/fighters/search?query=";
    public static final String mAllTimeRankingsUrl="http://www.fightmatrix.com/all-time-mma-rankings";




    private int mCounter=0;
    public ArrayList<Fighter> mFighters;

    public Fighter mCurrentDetailsFighter=new Fighter();

    private ArrayList<DataListener> mListeners=new ArrayList<DataListener>();
    private ArrayList<StatsDataListener> mStatsListener=new ArrayList<>();

    public void addListener(DataListener listener){
        mListeners.add(listener);
    }
    public void addStatsListener(StatsDataListener listener){mStatsListener.add(listener);}

    public void getFightersData(){
        getData(mFightersUrl,new GetFigthersData());
    }



    protected void getData(String url, final readJson method ) {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    method.execute(json);

                } else
                    NotifyListeners(false);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                NotifyListeners(false);
            }
        });
    }

    public  void readFighterStatsHtml(Fighter fighter){
        StatsJsoupReader reader=new StatsJsoupReader();
        reader.execute(fighter);
    }

    private  class StatsJsoupReader extends AsyncTask<Fighter,Void,ArrayList<String>>{


        @Override
        protected ArrayList<String> doInBackground(Fighter... fighters) {
            Fighter fighter=fighters[0];
            mCurrentDetailsFighter=fighter;
            String fightMatrixUrl;
            String martialArtsUrl=mMartialArtsSearchUrl+fighter.getmUrlSearchName();
            String fightmeetricsUrl=mFightMetricsSearchUrl+fighter.getLastName();
            if(fighter.ismIsUFC())
                fightMatrixUrl=mFightMatrixSearchUrl+fighter.getmUrlSearchName();

            else
                 fightMatrixUrl=mFightMatrixSearchUrl+fighter.getFirstName()+"+"+fighter.getLastName();
            ArrayList<String > results=new ArrayList<>();
            results.add(fighter.getFirstName());
            results.add(fighter.getLastName());
            try {
                results.addAll(getUFCAboutDetailsData(fighter.getmFighterDetailsPageUrl(),martialArtsUrl));
                results.addAll(getFightMatrixData(fightMatrixUrl,true));
                results.addAll(getFightMetricsData(fightmeetricsUrl));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        public ArrayList<String> getMartialArtsData(String fightMatrixUrl,ArrayList<String> failedToGet) throws IOException{
            Document  document =Jsoup.connect(fightMatrixUrl).get();
            ArrayList<String> stats=new ArrayList<>();
            Elements infoTable=document.select("table.fighter-info");
            try {
                if(failedToGet.contains("Age")) {
                    Elements age = infoTable.select("th:containsOwn(Age:)+td");
                    stats.add(age.text());
                }
                else
                stats.add("--");
                if(failedToGet.contains("Height")) {
                    Elements height = infoTable.select("th:containsOwn(Height:)+td");
                    stats.add(height.text());
                }
                else
                    stats.add("--");
                if(failedToGet.contains("Weight")) {
                    Elements weight = infoTable.select("th:containsOwn(Weight:)+td");
                    stats.add(weight.text());
                }
                else
                    stats.add("--");
                if(failedToGet.contains("Out")) {
                    Elements outOf = infoTable.select("th:containsOwn(Out of:)+td");
                    stats.add(outOf.text());
                }
                else
                    stats.add("--");
            }
            catch (Exception e){
                stats.add("--");
                stats.add("--");
                stats.add("--");
                stats.add("--");
            }
            return stats;
        }

        public ArrayList<String> getUFCAboutDetailsData(String ufcUrl,String martialArtsUrl) throws IOException{
            Document document=Jsoup.connect(ufcUrl).get();
            ArrayList<String> stats=new ArrayList<>();
            ArrayList<String> failedToGet=new ArrayList<>();
            Elements outOf=document.select("li:contains(From:)");
            Elements  age= document.select("li:contains(Age:)");
            Elements height=document.select("li:contains(Height:)");
            Elements weight=document.select("li:contains(Weight:)");
            String ageStr=age.text().replaceAll("Age:","");
            if(ageStr.equals(""))
                failedToGet.add("Age");
            String outStr=outOf.text().replaceAll("From:","");
            if(outStr.equals(""))
                failedToGet.add("Out");
            String heightStr=height.text().replaceAll("Height:","");
            if(heightStr.equals(""))
                failedToGet.add("Height");
            String weightStr=weight.text().replaceAll("Weight:","");
            if(weightStr.equals(""))
                failedToGet.add("Weight");
            ArrayList<String> result=new ArrayList<>();
            if(failedToGet.size()>0){

              result=  getMartialArtsData(martialArtsUrl,failedToGet);
            }
            if(result.size()>0&&!result.get(0).equals("--"))
                stats.add(result.get(0));
                else
            stats.add(ageStr);
            if(result.size()>0&&!result.get(1).equals("--"))
                stats.add(result.get(1));
                else
            stats.add(heightStr);
            if(result.size()>0&&!result.get(2).equals("--"))
                stats.add(result.get(2));
                else
            stats.add(weightStr);
            if(result.size()>0&&!result.get(3).equals("--"))
                stats.add(result.get(3));
                else
            stats.add(outStr);

            return stats;
        }


        public ArrayList<String> getFightMatrixData(String url,boolean isFirstTime) throws IOException {
            Document document=Jsoup.connect(url).get();
            ArrayList<String> stats=new ArrayList<>();
            String fighterUrl;

            Elements table=document.select("table.tblRank");
            Elements links=table.select("a[href]");
            try {
            fighterUrl=links.get(0).attr("abs:href");
            Document fighterPage=Jsoup.connect(fighterUrl).get();

                Elements proDebut=fighterPage.select("td:containsOwn(Pro Debut Date:)+td");
                stats.add(proDebut.text());

                Elements currentrank=fighterPage.select("td:contains(Current Rank:) a");
                if(currentrank.size()==0){
                    Elements lastRank=fighterPage.select("td:contains(Last Ranked:) a");
                    stats.add(lastRank.get(0).text());
                }
                else {
                    stats.add(currentrank.get(0).text());
                }
                Elements team=fighterPage.select("td:containsOwn(Association)+td");
                stats.add(team.text());
            }
            catch (Exception e){
                if(isFirstTime) {
                    try {
                        stats = getFightMatrixData(mFightMatrixSearchUrl + mCurrentDetailsFighter.getFirstName() + "+" + mCurrentDetailsFighter.getLastName(), false);
                    } catch (Exception e2) {

                    }
                    if(stats.size()==0){
                        stats.add("n/a");
                        stats.add("n/a");
                        stats.add("n/a");
                    }
                }

            }
            return stats;
        }


        public ArrayList<String> getFightMetricsData(String url) throws IOException {
            ArrayList<String> stats=new ArrayList<>();
              Document  document= (Document) Jsoup.connect(url).get();
            Elements link=new Elements();
            if(!mCurrentDetailsFighter.getNickName().equals(""))
                link=document.select("a:matchesOwn(^"+mCurrentDetailsFighter.getNickName()+"$)");
            else
                 link=document.select("a:matchesOwn(^"+mCurrentDetailsFighter.getFirstName()+"$)");


            String fighterUrl=link.get(0).attr("abs:href");

            Document fighterPage=Jsoup.connect(fighterUrl).get();

            Elements slpm=fighterPage.select("li:contains(SLpM:)");
            stats.add(slpm.text().replace("SLpM:",""));
            Elements sAccuracy=fighterPage.select("li:contains(Str. Acc.:)");
            stats.add(sAccuracy.text().replace("Str. Acc.:",""));
            Elements sapm=fighterPage.select("li:contains(SApM:)");
            stats.add(sapm.text().replace("SApM:",""));
            Elements sDefence=fighterPage.select("li:contains(Str. Def:)");
            stats.add(sDefence.text().replace("Str. Def:",""));
            Elements tAverage=fighterPage.select("li:contains(TD Avg.:)");
            stats.add(tAverage.text().replace("TD Avg.:",""));
            Elements tAccuracy=fighterPage.select("li:contains(TD Acc.:)");
            stats.add(tAccuracy.text().replace("TD Acc.:",""));
            Elements tDefence=fighterPage.select("li:contains(TD Def.:)");
            stats.add(tDefence.text().replace("TD Def.:",""));
            Elements submissions=fighterPage.select("li:contains(Sub. Avg.:)");
            stats.add(submissions.text().replace("Sub. Avg.:",""));

            return stats;
        }
        @Override
        protected void onPostExecute(ArrayList<String> stats) {
            try {
                FighterStats fighterStats = new FighterStats();
                fighterStats.setmFirst(stats.get(0));
                fighterStats.setmLast(stats.get(1));
                fighterStats.setmAge(stats.get(2));
                fighterStats.setmHeight(stats.get(3));
                fighterStats.setmWeight(stats.get(4));
                if (stats.get(5).equals(""))
                    fighterStats.setmFightsOutOf("n/a");
                else
                    fighterStats.setmFightsOutOf(stats.get(5));

                fighterStats.setmProfDebut(stats.get(6));
                fighterStats.setmRank(stats.get(7));
                if (stats.get(8).equals("N/A"))
                    fighterStats.setmTeam("n/a");
                else
                    fighterStats.setmTeam(stats.get(8));

                fighterStats.setmSigStrikesLanded(stats.get(9));
                fighterStats.setmSigStrikesAccuracy(stats.get(10));
                fighterStats.setmStrikesAbsorbed(stats.get(11));
                fighterStats.setmSigStrikesDef(stats.get(12));
                fighterStats.setmTakeDownsLanded(stats.get(13));
                fighterStats.setmTakeDownAccuracy(stats.get(14));
                fighterStats.setmTakeDownDefence(stats.get(15));
                fighterStats.setmSubmissionAttempts(stats.get(16));

                notifyListeners(fighterStats);
            }
            catch (Exception e){
                e.getMessage();
                readFighterStatsHtml(mCurrentDetailsFighter);
            }
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

    //Interface used to read JSon files from data each class that implements it , is used to extract different data from the item
    public interface  readJson{
        void execute(String json);
    }

    private class  GetFigthersData implements readJson{
        @Override
        public void execute(String json) {
            try {
                mFighters=new ArrayList<Fighter>();
                JSONArray jsonArray=new JSONArray(json);
                int length=jsonArray.length();
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    Fighter fighter=new Fighter();
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
                    fighter.setProfileUrl(jsonObject.getString("profile_image"));
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

                    mFighters.add(fighter);
                }
                NotifyListeners(true);
            } catch (JSONException e) {
                Log.v("EXCEPTION",e.getMessage());
            }
        }
    }

    private void  NotifyListeners(boolean sucess){
        if(sucess)
        {
            for (DataListener listener:mListeners){
                listener.onDataReceived();
            }
        }
        else {
            for (DataListener listener:mListeners){
                listener.onDataFailed();
            }
        }
    }

    public interface DataListener{
        public void onDataReceived();
        public void onDataFailed();
    }

    public void readAllTimeRanks()
    {
        AllTimeRanksJsoupReader reader=new AllTimeRanksJsoupReader();
        reader.execute();
    }

    public class AllTimeRanksJsoupReader extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<String> allTime=new ArrayList<>();
            ArrayList<String> women=new ArrayList<>();
            ArrayList<String> heavy=new ArrayList<>();
            ArrayList<String> lightheavy=new ArrayList<>();
            ArrayList<String> middle=new ArrayList<>();
            ArrayList<String> welter=new ArrayList<>();
            ArrayList<String> light=new ArrayList<>();
            ArrayList<String> feather=new ArrayList<>();
            ArrayList<String> bantam=new ArrayList<>();
            ArrayList<String> fly=new ArrayList<>();
            ArrayList<String> straw=new ArrayList<>();


            try {
                Document document=Jsoup.connect(mAllTimeRankingsUrl).get();
                Elements alltimeTable=document.select("td:matches(Absolute)");
                Elements allTimenames=alltimeTable.select("strong");
                for (int i=0;i<allTimenames.size();i++){
                    allTime.add(allTimenames.get(i).text());
                }
                Elements womenTable=document.select("td:matches(Women)");
                Elements womennames=womenTable.select("strong");
                for (int i=0;i<womennames.size();i++)
                    women.add(womennames.get(i).text());

                Elements heavyTable=document.select("td:matches(Heavyweight +)");
                Elements heavynames=heavyTable.get(0).select("strong");
                for (int i=0;i<heavynames.size();i++)
                    heavy.add(heavynames.get(i).text());

                Elements lightheavyTable=document.select("td:matches(Light Heavyweight)");
                Elements lightheavynames=lightheavyTable.select("strong");
                for (int i=0;i<heavynames.size();i++)
                    lightheavy.add(lightheavynames.get(i).text());

                Elements middleTable=document.select("td:matches(Middleweight)");
                Elements middlenames=middleTable.select("strong");
                for (int i=0;i<heavynames.size();i++)
                    middle.add(middlenames.get(i).text());

                Elements welterTable=document.select("td:matches(Welterweight)");
                Elements welternames=welterTable.select("strong");
                for (int i=0;i<welternames.size();i++)
                    welter.add(welternames.get(i).text());

                Elements lightTable=document.select("td:matches(Lightweight)");
                Elements lightnames=lightTable.select("strong");
                for (int i=0;i<lightnames.size();i++)
                    light.add(welternames.get(i).text());

                Elements featherTable=document.select("td:matches(Featherweight)");
                Elements feathernames=featherTable.select("strong");
                for (int i=0;i<feathernames.size();i++)
                    feather.add(feathernames.get(i).text());

                Elements bantamTable=document.select("td:matches(Bantamweight)");
                Elements bantamnames=bantamTable.select("strong");
                for (int i=0;i<bantamnames.size();i++)
                    bantam.add(bantamnames.get(i).text());

                Elements flyweightable=document.select("td:matches(Flyweight)");
                Elements flynames=flyweightable.select("strong");
                for (int i=0;i<flynames.size();i++)
                    fly.add(bantamnames.get(i).text());

                Elements strawweighttable=document.select("td:matches(Strawweight)");
                Elements strawnames=strawweighttable.select("strong");
                for (int i=0;i<strawnames.size();i++)
                    straw.add(strawnames.get(i).text());

            } catch (IOException e) {
                e.getMessage();
            }


            return null;
        }
    }

}
