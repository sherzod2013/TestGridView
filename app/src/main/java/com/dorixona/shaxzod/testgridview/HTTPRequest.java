package com.dorixona.shaxzod.testgridview;

import android.content.Context;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Shaxzod on 23.07.2017.
 */

public class HTTPRequest {
    private Context context;
    private OkHttpClient client;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public HTTPRequest(Context context){
        this.context = context;
        client = new OkHttpClient();
    }
    @SuppressWarnings("ConstantConditions")
    public String Get(String uri) throws IOException {
        try {
            Request request = new Request.Builder()
                    .url(uri)
                    .build();
            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public String Post(String uri, String json){
        try {

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                .url(uri)
                .post(body)
                .build();
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {

            e.printStackTrace();
            return null;

        }
    }
}
