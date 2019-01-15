package com.whisperict.catchthelegend.activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.Sound;
import com.whisperict.catchthelegend.managers.SoundManager;
import com.whisperict.catchthelegend.managers.apis.legend.LegendApiManager;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Objects;

public class DetailedLegendActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_legend);
        final Legend legend = getIntent().getParcelableExtra("LEGEND");

        if(Locale.getDefault().getLanguage().equals("en")){
            TextView legendRarity = findViewById(R.id.realrariry);
            TextView legendFranchise = findViewById(R.id.realfranchise);
            //TextView legendNameTextView  = findViewById(R.id.legend_name_text_view);
            TextView legendDescriptionTextView = findViewById(R.id.legend_description_text_view);
            TextView amountCatchedTextView = findViewById(R.id.caughted_amount_text_view);

            //legendNameTextView.setText(legend.getName());
            legendDescriptionTextView.setText(legend.getDescriptionEnglish());


            Log.d("LEGEND_CAPTURED", legend.getCapturedAmount() + "");

            amountCatchedTextView.setText(legend.getCapturedAmount() + "");
            legendRarity.setText(legend.getRarity());
            legendFranchise.setText(legend.getFranchise());
        }
        else if(Locale.getDefault().getLanguage().equals("nl")){
            TextView legendRarity = findViewById(R.id.realrariry);
            TextView legendFranchise = findViewById(R.id.realfranchise);
            //TextView legendNameTextView  = findViewById(R.id.legend_name_text_view);
            TextView legendDescriptionTextView = findViewById(R.id.legend_description_text_view);
            TextView amountCatchedTextView = findViewById(R.id.caughted_amount_text_view);

            //legendNameTextView.setText(legend.getName());
            legendDescriptionTextView.setText(legend.getDescriptionDutch());
            amountCatchedTextView.setText(legend.getCapturedAmount() + "");
            legendRarity.setText(legend.getRarity());
            legendFranchise.setText(legend.getFranchise());
        }
        else {
            TextView legendRarity = findViewById(R.id.realrariry);
            TextView legendFranchise = findViewById(R.id.realfranchise);
            //TextView legendNameTextView  = findViewById(R.id.legend_name_text_view);
            TextView legendDescriptionTextView = findViewById(R.id.legend_description_text_view);
            TextView amountCatchedTextView = findViewById(R.id.caughted_amount_text_view);

            //legendNameTextView.setText(legend.getName());
            legendDescriptionTextView.setText(legend.getDescriptionEnglish());
            amountCatchedTextView.setText(legend.getCapturedAmount() + "");
            legendRarity.setText(legend.getRarity());
            legendFranchise.setText(legend.getFranchise());
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(legend.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));

        ImageView legendImageView = findViewById(R.id.legend_image_view_detailed);
        ImageView background = findViewById(R.id.backgroundcarddetail);
        Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(legend.getName())).into(legendImageView);

        String rarity = legend.getRarity();

        switch (rarity) {
            case "common" :
                background.setImageResource(R.mipmap.eenster);
                break;

            case "uncommon" :
                background.setImageResource(R.mipmap.tweesterren);
                break;

            case "rare" :
                background.setImageResource(R.mipmap.driesterren);
                break;

            case "legend" :
                background.setImageResource(R.mipmap.viersterren);
                break;

            case "ultra_legend" :
                background.setImageResource(R.mipmap.vijfsterren);
                break;

        }
    }
}
