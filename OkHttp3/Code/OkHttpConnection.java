package com.uni.okhttp3;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpConnection {

    private String RETURN_STATEMENT_ERROR = "ERROR has occured";

    Context context;

    public OkHttpConnection(Context context) {
        this.context = context;
    }

    public String GETFunc(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("name", "value")
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("ERROR", RETURN_STATEMENT_ERROR);
        return RETURN_STATEMENT_ERROR;
    }

    public String POSTFunc(String url, String json) {
        try {
            OkHttpClient client = new OkHttpClient();

            //JSON 형태로 POST한다고 가정
            final MediaType JSON = MediaType.parse("appliation/json; charset=utf-8");

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSON, json))
                    .build();

            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("ERROR", RETURN_STATEMENT_ERROR);

        return RETURN_STATEMENT_ERROR;
    }

}
