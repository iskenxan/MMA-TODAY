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

    private int mCounter=0;
    public ArrayList<Fighter> mFighters;


    private ArrayList<DataListener> mListeners=new ArrayList<DataListener>();
    private ArrayList<OnCountryCodeFoundListener> mCountryCodeListeners=new ArrayList<>();

    public void addListener(DataListener listener){
        mListeners.add(listener);
    }
    public void addCountryCodeListener(OnCountryCodeFoundListener listener){
        mCountryCodeListeners.add(listener);
    }

    public void getFightersData(){
        getData(mFightersUrl,new GetFigthersData());
    }



    public  void getSpeficicCountryCode(Fighter fighter){
        ArrayList<String> weightclasses=new ArrayList<String>(Arrays.asList(FighterStats.mWeightClasses));
        ArrayList<String> countries=new ArrayList<String>(Arrays.asList(FighterStats.mCountries));
        getData(FighterStats.mCountriesUrl+countries.remove(0),new FindSpecificCountryCode(fighter,countries,weightclasses));
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

    public  void readFighterStatsHtml(FighterStats stats){
        JsoupReader reader=new JsoupReader();
        reader.execute(stats);
    }

    private static class JsoupReader extends AsyncTask<FighterStats,Void,Document>{


        @Override
        protected Document doInBackground(FighterStats... fighterStatses) {
            FighterStats stats=fighterStatses[0];
            String url=mFightersUrl+"/"+stats.getmFighterId();
            Document document=new Document(url);
            try {
                document=Jsoup.connect(url).get();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            FighterStats stats=new FighterStats();

            Elements values=document.select("td");
            Element from=values.get(1);
            stats.setmFrom(from.text());
            Element fightsOut=values.get(3);
            stats.setmFightsOutOf(fightsOut.text());
            Element age=values.get(5);
            stats.setmAge(age.text());
            Element height=values.get(7);
            stats.setmHeight(height.text());
            Element weight=values.get(9);
            stats.setmWeight(weight.text());
            Element weightclass=values.get(11);
            stats.setmWeightClass(weightclass.text());


        }
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
                    fighter.setLastName(jsonObject.getString("last_name"));
                    fighter.setNickName(jsonObject.getString("nickname"));
                    fighter.setmWeightClass(jsonObject.getString("weight_class"));
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

                    fighter.setId(jsonObject.getInt("id"));
                    fighter.setPFP(jsonObject.getString("pound_for_pound_rank"));
                    if(fighter.getPFP()==null)
                        fighter.setPFP("none");
                    fighter.setTitleHolder(jsonObject.getBoolean("title_holder"));
                    try{
                        fighter.setmStatId(jsonObject.getInt("statid"));
                    }
                    catch (Exception e){
                        fighter.setmStatId(-1);
                    }
                    mFighters.add(fighter);
                }
                    getData(FighterStats.mBaseFighterStatsUrl+FighterStats.WEIGHT_KEY_ALL,new GetCountryCode(mFighters));
            } catch (JSONException e) {
                Log.v("EXCEPTION",e.getMessage());
            }
        }
    }

    private class GetCountryCode implements readJson{
        private ArrayList<Fighter> mFighters;
        public GetCountryCode(ArrayList<Fighter> fighters){
            mFighters=fighters;
        }

        @Override
        public void execute(String json) {
            try {
                JSONArray array= new JSONArray(json);
                for (int x=0;x<mFighters.size();x++) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject stat = array.getJSONObject(i);
                        if (stat.getInt("statid") == (mFighters.get(x).getmStatId())) {
                            mFighters.get(x).setCountryCode(stat.getString("Born"));
                            break;
                        }
                    }
                }
                    NotifyListeners(true);
            } catch (JSONException e) {
                Log.v("EXCEPTION",e.getMessage());
            }

        }
    }


    private class FindSpecificCountryCode implements readJson{ //is used if the country code couldn't be found using GetCountryCode
        private Fighter mFighter;
        private ArrayList<String> mCountries;
        private ArrayList<String> mWeightClasses;
        public FindSpecificCountryCode(Fighter fighter,ArrayList<String> countries,ArrayList<String> weightclasses)
        {
            mFighter=fighter;
            mCountries=countries;
            mWeightClasses=weightclasses;
        }

        @Override
        public void execute(String json) {
            try {
                JSONArray array=new JSONArray(json);
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    if(object.getString("statid").equals(mFighter.getmStatId()+"")){
                        mFighter.setCountryCode(object.getString("Born"));
                        notifyListeners(object.getString("Born"),true);
                        return;
                    }
                }
                if(mCountries.size()>0)
                getData(FighterStats.mCountriesUrl+mCountries.remove(0),new FindSpecificCountryCode(mFighter,mCountries,mWeightClasses));
                else if(mWeightClasses.size()>0)
                    getData(FighterStats.mBaseFighterStatsUrl+mWeightClasses.remove(0),new FindSpecificCountryCode(mFighter,mCountries,mWeightClasses));
                else
                    notifyListeners(null,false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void notifyListeners(String code,boolean found){
            for (OnCountryCodeFoundListener listener:mCountryCodeListeners)
                listener.OnCountryCodeFound(code,found);
        }
    }

    public interface  OnCountryCodeFoundListener{
        public void OnCountryCodeFound(String code, boolean found);
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
