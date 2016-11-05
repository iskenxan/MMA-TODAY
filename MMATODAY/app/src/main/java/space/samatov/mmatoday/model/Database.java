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
    private int mCounter=0;
    public ArrayList<Fighter> mFighters;

    public Fighter mCurrentDetailsFighter=new Fighter();

    private ArrayList<DataListener> mListeners=new ArrayList<DataListener>();
    private ArrayList<StaticDataListener> mStatsListener=new ArrayList<>();

    public void addListener(DataListener listener){
        mListeners.add(listener);
    }
    public void addStatsListener(StaticDataListener listener){mStatsListener.add(listener);}

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
        JsoupReader reader=new JsoupReader();
        reader.execute(fighter);
    }

    private  class JsoupReader extends AsyncTask<Fighter,Void,ArrayList<String>>{


        @Override
        protected ArrayList<String> doInBackground(Fighter... fighters) {
            Fighter fighter=fighters[0];
            mCurrentDetailsFighter=fighter;
            String fightMatrixUrl;
            String martialArtsUrl=mMartialArtsSearchUrl+fighter.getmUrlSearchName();
            if(fighter.ismIsUFC())
                fightMatrixUrl=mFightMatrixSearchUrl+fighter.getmUrlSearchName();

            else
                 fightMatrixUrl=mFightMatrixSearchUrl+fighter.getFirstName()+"+"+fighter.getLastName();
            ArrayList<String > results=new ArrayList<>();
            results.add(fighter.getFirstName());
            results.add(fighter.getLastName());
            try {
                results.addAll(getUFCAboutDetailsData(fighter.getmFighterDetailsPageUrl(),martialArtsUrl));
                results.addAll(getFightMatrixData(fightMatrixUrl));

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


        public ArrayList<String> getFightMatrixData(String url) throws IOException {
            Document document=Jsoup.connect(url).get();
            ArrayList<String> stats=new ArrayList<>();
            String fighterUrl;

            Elements table=document.select("table.tblRank");
            Elements links=table.select("a[href]");
            try {
            fighterUrl=links.get(0).attr("abs:href");
            Document fighterPage=Jsoup.connect(fighterUrl).get();

                Elements rankHeadRow = fighterPage.select("td.tdRankHead");
                Element proDebutROw = rankHeadRow.get(3);
                Elements proDebutCell = proDebutROw.select("td");
                Element proDebut = proDebutCell.get(5);
                stats.add(proDebut.text());

                Elements rankRow = fighterPage.select("td.tdRank");
                Elements rankLinks = rankRow.select("a");
                Element link = rankLinks.get(0);
                stats.add(link.text());

                Elements team=fighterPage.select("td:containsOwn(Association)+td");
                stats.add(team.text());
            }
            catch (Exception e){
                try {
                    stats = getFightMatrixData(mFightMatrixSearchUrl + mCurrentDetailsFighter.getFirstName() + "+" + mCurrentDetailsFighter.getLastName());
                }
                catch (Exception e2)
                {}
                stats.add("--");
                stats.add("--");
                stats.add("--");
            }
            return stats;
        }

        @Override
        protected void onPostExecute(ArrayList<String> stats) {
            FighterStats fighterStats=new FighterStats();
            fighterStats.setmFirst(stats.get(0));
            fighterStats.setmLast(stats.get(1));
            fighterStats.setmAge(stats.get(2));
            fighterStats.setmHeight(stats.get(3));
            fighterStats.setmWeight(stats.get(4));
            fighterStats.setmFightsOutOf(stats.get(5));
            fighterStats.setmProfDebut(stats.get(6));
            fighterStats.setmRank(stats.get(7));
            fighterStats.setmTeam(stats.get(8));
            notifyListeners(fighterStats);
        }
    }

    public void notifyListeners(FighterStats fighterStats){
        for(StaticDataListener listener:mStatsListener){
            listener.OnDataReceived(fighterStats);
        }
    }

    public interface StaticDataListener{
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


}
