package com.ruddell.jokeuilibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.example.chris.myapplication.backend.com.udacity.gradle.builditbigger.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by chris1 on 12/23/15.
 */
public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private static final String TAG = "JokeActivity";
    private Context context;
    private AsyncListener mListener;

    public EndpointsAsyncTask(final Context context, final AsyncListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once

            if (BuildConfig.DEBUG && false) {
                android.util.Log.d(TAG,"requesting from local server");
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                // end options for devappserver
                myApiService = builder.build();
            }
            else {
                android.util.Log.d(TAG,"requesting from remote server");
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://nanodegree-jokeapp.appspot.com/_ah/api/");

                // end options for production server
                myApiService = builder.build();
            }




        }

        android.util.Log.d(TAG,"requesting joke from:" + myApiService.getRootUrl());

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.getJoke().execute().getData();
//            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        android.util.Log.d(TAG, "onPostExecute result:" + result);
        mListener.onResult(result);

    }

    public interface AsyncListener {
        void onResult(String result);
    }

}
