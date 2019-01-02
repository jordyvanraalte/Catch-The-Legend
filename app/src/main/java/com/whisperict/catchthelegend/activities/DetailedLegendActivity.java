package com.whisperict.catchthelegend.activities;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.database.AppDatabase;
import com.whisperict.catchthelegend.database.DatabaseManager;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.apis.LegendApiManager;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailedLegendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_legend);
        final Legend legend = getIntent().getParcelableExtra("LEGEND");

        ImageView legendImageView = findViewById(R.id.legend_image_view_detailed);
        TextView legendNameTextView  = findViewById(R.id.legend_name_text_view);
        TextView legendDescriptionTextView = findViewById(R.id.legend_description_text_view);
        TextView amountCatchedTextView = findViewById(R.id.amout_catched_text_view);

        legendNameTextView.setText(legend.getName());
        legendDescriptionTextView.setText(legend.getDescriptionDutch());
        amountCatchedTextView.setText("Catched: " + legend.getCapturedAmount());
        Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(legend.getName())).into(legendImageView);
    }
}
