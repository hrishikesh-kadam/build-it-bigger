package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.androidjokelibrary.DisplayJokeActivity;


public class MainActivity extends AppCompatActivity
        implements EndpointsAsyncTask.OnPostExecuteListener {

    private FrameLayout progressBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        progressBarLayout = fragment.getView().findViewById(R.id.progressBar);
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

    public void tellJoke(View view) {

        progressBarLayout.setVisibility(View.VISIBLE);

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        endpointsAsyncTask.setOnPostExecuteListener(this);
        endpointsAsyncTask.execute();
    }

    @Override
    public void onPostExecute(CustomMessage customMessage) {
        Log.d("MainActivity", "-> onPostExecute -> " + customMessage);

        if (!customMessage.isSuccessful() || TextUtils.isEmpty(customMessage.getResult()))
            Toast.makeText(this, R.string.no_joke_found, Toast.LENGTH_SHORT).show();

        else {
            Intent intent = new Intent(this, DisplayJokeActivity.class);
            intent.putExtra(DisplayJokeActivity.JOKE_KEY, customMessage.getResult());
            startActivity(intent);
        }

        progressBarLayout.setVisibility(View.GONE);
    }
}
