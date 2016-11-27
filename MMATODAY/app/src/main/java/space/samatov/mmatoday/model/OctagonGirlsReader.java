package space.samatov.mmatoday.model;


import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.interfaces.OctagonGirlsDataReceived;

public class OctagonGirlsReader extends JsonReader {
    public static final String OCT_GIRLS_KEY="octagon_girls";

    public static final String mOctagonGirlsUrl="http://ufc-data-api.ufc.com/api/v3/iphone/octagon_girls";

    public ArrayList<OctagonGirl> mOctagonGirls=new ArrayList<>();
    private Activity mActivity;
    private ArrayList<OctagonGirlsDataReceived> mListeners=new ArrayList<>();

    public void addListener(OctagonGirlsDataReceived listener){
        mListeners.add(listener);
    }
    private OctagonGirlsReader(Activity activity){
        mActivity=activity;
    }
    public static OctagonGirlsReader referenceActivity(Activity activity){
        return new OctagonGirlsReader(activity);
    }
    public void getOctagonGirls(){
        getData(mOctagonGirlsUrl,true);
    }
    @Override
    protected void dataReceived(String json, boolean isFirstTime) {
        try {
            JSONArray jsonArray=new JSONArray(json);
            String[] bios=mActivity.getResources().getStringArray(R.array.octagon_girls_bios);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsongirl=jsonArray.getJSONObject(i);
                OctagonGirl girl=new OctagonGirl();
                girl.setmFirst(jsongirl.getString("first_name"));
                girl.setmLast(jsongirl.getString("last_name"));
                girl.setmCountry(jsongirl.getString("country_residing"));
                girl.setmCity(jsongirl.getString("city_residing"));
                girl.setmFavoriteFood(jsongirl.getString("favorite_foods"));
                girl.setmHobbies(jsongirl.getString("hobbies"));
                girl.setmBirthDate(jsongirl.getString("date_of_birth"));
                girl.setmQuote(jsongirl.getString("quote"));
                girl.setmTwitterUsername(jsongirl.getString("twitter_username"));
                String body_pic=jsongirl.getString("large_body_picture");
                if(body_pic.equals("null")){
                    body_pic=jsongirl.getString("large_profile_picture");
                }
                girl.setmBodyPic(body_pic);
                girl.setmBanner(jsongirl.getString("banner_background_image"));
                girl.setmHeight(jsongirl.getInt("height"));
                girl.setmWeight(jsongirl.getInt("weight"));

                ArrayList<String> gallery = new ArrayList<>();
                try {
                    girl.setmBio(bios[i]);
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
        notifyListeners(true);
    }

    @Override
    protected void notifyListeners(boolean sucess) {
        if(sucess) {
            for (OctagonGirlsDataReceived listener : mListeners)
                listener.OnOctagonGirlsDataReceived();
        }
    }
}
