package com.whisperict.catchthelegend.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.entities.Legend;

public class DetailedLegendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_legend);
        Legend legend = getIntent().getParcelableExtra("LEGEND");
    }
}
