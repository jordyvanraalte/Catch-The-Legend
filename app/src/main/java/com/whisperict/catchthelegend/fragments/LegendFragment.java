package com.whisperict.catchthelegend.fragments;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.database.AppDatabase;
import com.whisperict.catchthelegend.database.DatabaseManager;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.Sound;
import com.whisperict.catchthelegend.managers.SoundManager;
import com.whisperict.catchthelegend.managers.apis.legend.LegendApiManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LegendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegendFragment extends DialogFragment {
    private Legend legend;
    private boolean started = false;


    public LegendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LegendFragment.
     */
    public static LegendFragment newInstance(Legend legend) {
        LegendFragment fragment = new LegendFragment();
        Bundle args = new Bundle();
        args.putParcelable("LEGEND", legend);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            legend = getArguments().getParcelable("LEGEND");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_legend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView legendNameTextView = view.findViewById(R.id.legend_fragment_text_view);
        legendNameTextView.setText(legend.getName());

        ImageView legendImage = view.findViewById(R.id.legend_fragment_image_view);
        Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(legend.getName())).into(legendImage);

        ImageView background = view.findViewById(R.id.CardBackground);
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

        AppDatabase appDb = DatabaseManager.getInstance(getContext()).getAppDatabase();
        Executor databaseThread = Executors.newSingleThreadExecutor();
        databaseThread.execute(() -> {
            if (appDb.legendDao().getLegendById(legend.getId()) != null) {
                Legend legendDb = appDb.legendDao().getLegendById(legend.getId());
                legendDb.setCapturedAmount(legendDb.getCapturedAmount() + 1);
                appDb.legendDao().updateLegend(legendDb);
            } else {
                legend.setCapturedAmount(1);
                legend.setCaptured(true);
                appDb.legendDao().insertAll(legend);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        View animatedView = getDialog().getWindow().getDecorView();

        if (!started) {
            ObjectAnimator scaledown = ObjectAnimator.ofPropertyValuesHolder(animatedView, PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                    PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
            scaledown.setDuration(3000);
            scaledown.start();

            if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("SOUND_BOOL", true)){
                SoundManager.getInstance().playSound(new Sound(R.raw.victory_sound, getContext()));
            }

            started = true;
        }
    }

}
