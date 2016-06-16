package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Pair;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by chris1 on 12/23/15.
 */
public class JokeTest extends AndroidTestCase {

    public void test_getJoke() {
        android.util.Log.d("JokeActivityTest", "running getJokeTest");
        final CountDownLatch signal = new CountDownLatch(1);
        new com.ruddell.jokeuilibrary.EndpointsAsyncTask(getContext(), new com.ruddell.jokeuilibrary.EndpointsAsyncTask.AsyncListener() {
            @Override
            public void onResult(final String result) {
                android.util.Log.d("JokeActivityTest", result);
                //fail the test if the joke comes back empty
                assertTrue(result.length() > 0);
                signal.countDown();
            }
        }).execute(new Pair<Context, String>(getContext(), ""));

        try {
            //fail the test if we spend more than 30 seconds trying to retrieve the joke
            assertTrue(signal.await(30, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
            //fail the test due to unknown exception
            assertTrue(false);
        }
    }
}
