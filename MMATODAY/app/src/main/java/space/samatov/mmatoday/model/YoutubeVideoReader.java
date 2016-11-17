package space.samatov.mmatoday.model;

import android.util.Log;
import android.widget.Toast;

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

public class YoutubeVideoReader {
    public static final String YOUTUBE_API_KEY="AIzaSyD2-vaKQT7vPwXri55JjIoZCDN8avStNjA";
    public static final String MMA_WORLD_CHANNEL_ID="UClkruV5L-hsu20MDYOa1hvw";
    public static final String MMA_WORLD_UPLOADS_ID="UUlkruV5L-hsu20MDYOa1hvw";
    private static String mMMAUploadsListRequest="https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails%2Csnippet&playlistId=UUlkruV5L-hsu20MDYOa1hvw&key=AIzaSyD2-vaKQT7vPwXri55JjIoZCDN8avStNjA";


    public ArrayList<YoutubeVideo> mVideos=new ArrayList<>();
    private ArrayList<OnYoutubeVideoListLoaded> mListeners=new ArrayList<>();

    public void addListener(OnYoutubeVideoListLoaded listener){
        mListeners.add(listener);

    }
    public void readYouTubeChannel(){
        getData(mMMAUploadsListRequest,true);

    }

    private void getData(String url, final boolean isFirstTime){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               if (response.isSuccessful())
               {
                   String json=response.body().string();
                   getYoutubeChannelList(json,isFirstTime);
               }
                else
                   Log.v("exception","connection is unsucessful");
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("exception",e.getMessage()+"iskander");
            }
        });
    }



    private void getYoutubeChannelList(String json,boolean isFirstTime) {

        try {
            JSONObject listInfo=new JSONObject(json);
            JSONArray items=listInfo.getJSONArray("items");
            addVideosToArray(items);
            if(isFirstTime) {
                String nexPageToken = listInfo.getString("nextPageToken");
                String[] urlSplit=mMMAUploadsListRequest.split("\\?");
                String url=urlSplit[0]+"?pageToken="+nexPageToken+"&"+urlSplit[1];
                getData(url,false);
            }
            if(!isFirstTime)
                notifyListeners();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addVideosToArray(JSONArray jsonArray) throws JSONException {
        JSONArray items=jsonArray;
        for (int i=0;i<items.length();i++){
            YoutubeVideo youtubeVideo=new YoutubeVideo();
            JSONObject snippet=items.getJSONObject(i).getJSONObject("snippet");
            String tittle=snippet.getString("title");
            youtubeVideo.setmTitle(tittle);
            JSONObject contentDetails=items.getJSONObject(i).getJSONObject("contentDetails");
            String id=contentDetails.getString("videoId");
            youtubeVideo.setmId(id);

            mVideos.add(youtubeVideo);
        }
    }

    private void notifyListeners(){
        for (OnYoutubeVideoListLoaded listener:mListeners)
            listener.OnVideosLoaded();
    }


}
