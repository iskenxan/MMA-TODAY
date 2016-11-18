package space.samatov.mmatoday.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OctagonGirlsReader extends JsonReader {
    public static final String mOctagonGirlsUrl="http://ufc-data-api.ufc.com/api/v3/iphone/octagon_girls";

    public ArrayList<OctagonGirl> mOctagonGirls=new ArrayList<>();
    public ArrayList<OnOctagonGirlsDataReceived> mListeners=new ArrayList<>();

    public void addListener(OnOctagonGirlsDataReceived listener){
        mListeners.add(listener);
    }

    public void getOctagonGirls(){
        getData(mOctagonGirlsUrl,true);
    }
    @Override
    protected void dataReceived(String json, boolean isFirstTime) {
        try {
            JSONArray jsonArray=new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsongirl=jsonArray.getJSONObject(i);
                OctagonGirl girl=new OctagonGirl();
                girl.setmFirst(jsongirl.getString("first_name"));
                girl.setmLast(jsongirl.getString("last_name"));
                girl.setmCountry(jsongirl.getString("country_residing"));
                girl.setmCity(jsongirl.getString("city_residing"));
                girl.setmBio1(jsongirl.getString("biography1"));
                girl.setmBio2(jsongirl.getString("biography2"));
                girl.setmFavoriteFood(jsongirl.getString("favorite_foods"));
                girl.setmHobbies(jsongirl.getString("hobbies"));
                girl.setmBirthDate(jsongirl.getString("date_of_birth"));
                girl.setmQuote(jsongirl.getString("quote"));
                girl.setmTwitterUsername(jsongirl.getString("twitter_username"));
                girl.setmBodyPic(jsongirl.getString("large_body_picture"));
                girl.setmBanner(jsongirl.getString("banner_background_image"));
                girl.setmHeight(jsongirl.getInt("height"));
                girl.setmWeight(jsongirl.getInt("weight"));

                ArrayList<String> gallery = new ArrayList<>();
                try {
                    JSONArray jsongallery = jsongirl.getJSONArray("gallery");
                    for (int x = 0; x < jsongallery.length(); x++) {
                        JSONObject picture = jsongallery.getJSONObject(x);
                        gallery.add(picture.getString("path"));
                    }
                }
                catch (Exception e)
                {}
                girl.setmGallery(gallery);
                mOctagonGirls.add(girl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NotifyListeners(true);
    }

    @Override
    protected void NotifyListeners(boolean sucess) {
        if(sucess) {
            for (OnOctagonGirlsDataReceived listener : mListeners)
                listener.OnOctagonGirlsDataReceived();
        }
    }
}
