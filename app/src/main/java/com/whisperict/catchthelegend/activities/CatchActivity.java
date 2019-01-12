package com.whisperict.catchthelegend.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.database.AppDatabase;
import com.whisperict.catchthelegend.database.DatabaseManager;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.apis.legend.LegendApiManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);


        final Legend legend = getIntent().getParcelableExtra("LEGEND");

        ImageView legendImageView = findViewById(R.id.legend_image_view_catch_activity);
        Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(legend.getName())).into(legendImageView);


        Button catchButton = findViewById(R.id.catch_button);
        catchButton.setOnClickListener(view -> {
            AppDatabase appDb = DatabaseManager.getInstance(getApplicationContext()).getAppDatabase();
            Executor databaseThread = Executors.newSingleThreadExecutor();
            databaseThread.execute(() -> {
                if(appDb.legendDao().getLegendById(legend.getId()) != null){
                    Legend legendDb = appDb.legendDao().getLegendById(legend.getId());
                    legendDb.setCapturedAmount(legendDb.getCapturedAmount() + 1);
                    appDb.legendDao().updateLegend(legendDb);
                }
                else {
                    legend.setCapturedAmount(1);
                    legend.setCaptured(true);
                    appDb.legendDao().insertAll(legend);
                }
            });
        });
    }
}
