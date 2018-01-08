package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Hrishikesh Kadam on 08/01/2018
 */

class EndpointsAsyncTask extends AsyncTask<Void, Void, CustomMessage> {

    private static MyApi myApiService = null;
    private OnPostExecuteListener onPostExecuteListener;

    public void setOnPostExecuteListener(Object object) {
        onPostExecuteListener = (OnPostExecuteListener) object;
    }

    @Override
    protected CustomMessage doInBackground(Void... params) {

        if (myApiService == null) {  // Only do this once
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

        try {
            return new CustomMessage(true, myApiService.getJoke().execute().getData());
        } catch (IOException e) {
            return new CustomMessage(false, e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(CustomMessage customMessage) {
        Log.d("EndpointsAsyncTask", "-> onPostExecute -> " + customMessage);
        onPostExecuteListener.onPostExecute(customMessage);
    }

    public interface OnPostExecuteListener {
        public void onPostExecute(CustomMessage customMessage);
    }
}