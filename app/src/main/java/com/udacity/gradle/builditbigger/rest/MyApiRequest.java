package com.udacity.gradle.builditbigger.rest;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Andre Martins dos Santos on 20/06/2018.
 */
public class MyApiRequest extends AsyncTask<String, Void, String> {

    private static final String TAG = MyApiRequest.class.getSimpleName();
    private static MyApi myApiService = null;
    private String urlRestApi = null;

    public enum apiMethods{
        sayHi,
        doJoke
    }

    public MyApiRequest(Context context){
        this.urlRestApi = context.getString(R.string.url_api);
    }

    public MyApiResponseHandler mMyApiResponseHandler;
    public interface MyApiResponseHandler {
        void onResponse(String result);
    }

    @Override
    protected String doInBackground(String... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(urlRestApi)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        String method = params[0];
        try {
            switch (apiMethods.valueOf(method)) {
                case sayHi:
                    String name = params[1];
                    return myApiService.sayHi(name).execute().getData();
                case doJoke:
                    return myApiService.doJoke().execute().getData();
            }
        } catch (Exception e) {
            Log.e(TAG,"Error on request MyApi",e);
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        mMyApiResponseHandler.onResponse(result);
    }


    public void setDelegate(MyApiResponseHandler delegate) {
        mMyApiResponseHandler = delegate;
    }
}
