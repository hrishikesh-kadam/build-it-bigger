package com.example.android.androidjokelibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        if (getIntent().hasExtra(JOKE_KEY)) {
            String joke = getIntent().getStringExtra(JOKE_KEY);
            TextView textView = findViewById(R.id.textView);
            textView.setText(joke);
        }
    }
}
