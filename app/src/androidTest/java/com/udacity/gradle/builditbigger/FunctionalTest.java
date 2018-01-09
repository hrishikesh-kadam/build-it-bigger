package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FunctionalTest {

    private CustomMessage customMessage = null;
    private CountDownLatch signal = null;

    @Before
    public void beforeEndpointAsyncTask() {
        signal = new CountDownLatch(1);
    }

    @After
    public void afterEndpointAsyncTask() {
        signal.countDown();
    }

    @Test
    public void testEndpointAsyncTask() throws InterruptedException {

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();

        endpointsAsyncTask.setOnPostExecuteListener(new EndpointsAsyncTask.OnPostExecuteListener() {
            @Override
            public void onPostExecute(CustomMessage customMessage) {

                FunctionalTest.this.customMessage = customMessage;

                signal.countDown();
            }
        });

        endpointsAsyncTask.execute();
        signal.await();

        Log.d("FunctionalTest", "-> testEndpointAsyncTask -> " + customMessage);

        Assert.assertNotNull(customMessage);

        if (customMessage != null) {

            Assert.assertEquals(true, customMessage.isSuccessful());
            Assert.assertEquals(false, TextUtils.isEmpty(customMessage.getResult()));
        }
    }
}
