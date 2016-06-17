package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ruddell.jokeuilibrary.*;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String mJoke;
    public void tellJoke(View view){

        //free mode - show interstitial ad before going to joke.
        //note: we use fully qualified object references here so as to avoid setting a dependency on the import for paid versions
        final com.google.android.gms.ads.InterstitialAd mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));

        final String deviceId = "7E984E5E49B8E7B1723B3F2DD5FBF4ED";
        //final String deviceId = com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR;

        com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();

        mInterstitialAd.loadAd(adRequest);

        //since we have a little time before the joke is shown, go ahead and fetch it now so the user doens't have to wait after they close the ad
        new com.ruddell.jokeuilibrary.EndpointsAsyncTask(this, new com.ruddell.jokeuilibrary.EndpointsAsyncTask.AsyncListener() {
            @Override
            public void onResult(final String result) {
                mJoke = result;
            }
        }).execute(new Pair<Context, String>(this, ""));

        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            private boolean mDidInit = false;

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mDidInit == false) {
                    mInterstitialAd.show();
                    mDidInit = true;
                }
            }

            @Override
            public void onAdClosed() {


                com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder()
                        .addTestDevice(deviceId)
                        .build();

                mInterstitialAd.loadAd(adRequest);
                showJokeActivity(mJoke);
                mJoke = ""; //reset back to empty value in case we aren't able to pre-fetch the joke in time for the next time
            }
        });


    }

    private void showJokeActivity(String joke) {
        Intent jokeIntent = new Intent(this,JokeActivity.class);
        jokeIntent.putExtra(JokeActivity.ARG_JOKE, joke);
        this.startActivity(jokeIntent);
    }


}
