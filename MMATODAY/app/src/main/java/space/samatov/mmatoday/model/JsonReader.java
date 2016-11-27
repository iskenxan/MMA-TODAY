package space.samatov.mmatoday.model;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class JsonReader {

    protected void getData(String url, final boolean isFirstTime ) {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    dataReceived(json,isFirstTime);

                } else
                    notifyListeners(false);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                notifyListeners(false);
            }
        });
    }

    protected abstract void dataReceived(String json,boolean isFirstTime);

    protected abstract void notifyListeners(boolean b);
}

