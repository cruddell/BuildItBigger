package com.ruddell.jokeuilibrary;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    public static final String ARG_JOKE = "joke";
    private static final String TAG = "JokeActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.util.Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_joke);

        mTextView = (TextView)findViewById(R.id.jokeView);

        //check if we have a joke passed in the intent - if so, use that.  otherwise fetch a new one
        String joke = getIntent().getStringExtra(ARG_JOKE);
        if (joke!=null && joke.length()>0) {
            mTextView.setText(joke);
            JokeActivity.this.findViewById(R.id.progressBar1).setVisibility(View.GONE);
        }
        else getJoke();

    }

    private void hideProgressIndicator() {
        JokeActivity.this.findViewById(R.id.progressBar1).setVisibility(View.GONE);
    }

    private void getJoke() {
        new EndpointsAsyncTask(this, new EndpointsAsyncTask.AsyncListener() {
            @Override
            public void onResult(final String result) {
                android.util.Log.d(TAG,result);
                mTextView.setText(result);
                hideProgressIndicator();
            }
        }).execute(new Pair<Context, String>(this, ""));
    }

}