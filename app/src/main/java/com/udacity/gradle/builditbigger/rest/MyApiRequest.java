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
public class MyApiRequest extends AsyncTask<MyApiParams, Void, String> {

    private static final String TAG = MyApiRequest.class.getSimpleName();
    private static MyApi myApiService = null;
    private String urlRestApi = null;
    private Exception mException;

    public MyApiRequest(Context context){
        this.urlRestApi = context.getString(R.string.url_api);
        initApi();
    }

    public MyApiCallback mDelegate;
    public interface MyApiCallback {
        void onResponse(String result);
        void onException(Exception e);
    }

    @Override
    protected String doInBackground(MyApiParams... myApiParams) {
        MyApiParams requestParam = myApiParams[0];
        try {
            switch (requestParam.getMethod()) {
                case MyApiParams.SAY_HI:
                    String name = (String) requestParam.getParams()[0];
                    return myApiService.sayHi(name).execute().getData();
                case MyApiParams.DO_JOKE:
                    return myApiService.doJoke().execute().getData();
            }
        } catch (Exception e) {
            mException = e;
            Log.e(TAG,"Error on request MyApi",e);
            return null;
        }
        return null;
    }

    private void initApi() {
        if (myApiService == null) {
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
    }

    @Override
    protected void onPostExecute(String result) {
        if(mException == null){
            mDelegate.onResponse(result);
        }else{
            mDelegate.onException(mException);
        }
    }

    public void setDelegate(MyApiCallback delegate) {
        mDelegate = delegate;
    }
}
