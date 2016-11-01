package space.samatov.mmatoday.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public  class Database  {
    private String mFightersUrl="http://ufc-data-api.ufc.com/api/v3/iphone/fighters";
    public ArrayList<Fighter> mFighters;


    private ArrayList<DataListener> mListeners=new ArrayList<DataListener>();


    public void addListener(DataListener listener){
        mListeners.add(listener);
    }

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
                    NotifyListeners(true);
                } else
                    NotifyListeners(false);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                NotifyListeners(false);
            }
        });
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
                    fighter.setId(jsonObject.getInt("id"));
                    fighter.setPFP(jsonObject.getString("pound_for_pound_rank"));
                    if(fighter.getPFP()==null)
                        fighter.setPFP("none");
                    fighter.setTitleHolder(jsonObject.getBoolean("title_holder"));
                    mFighters.add(fighter);
                }
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
