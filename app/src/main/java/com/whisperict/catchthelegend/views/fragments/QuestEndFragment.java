package com.whisperict.catchthelegend.views.fragments;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.controllers.database.AppDatabase;
import com.whisperict.catchthelegend.controllers.database.DatabaseManager;
import com.whisperict.catchthelegend.model.entities.Legend;
import com.whisperict.catchthelegend.model.entities.Quest;
import com.whisperict.catchthelegend.controllers.managers.Sound;
import com.whisperict.catchthelegend.controllers.managers.SoundManager;
import com.whisperict.catchthelegend.controllers.managers.apis.legend.LegendApiManager;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LegendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestEndFragment extends DialogFragment {
    private boolean started = false;
    private Quest quest;


    public QuestEndFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LegendFragment.
     */
    public static QuestEndFragment newInstance(Quest quest) {
        QuestEndFragment fragment = new QuestEndFragment();
        Bundle args = new Bundle();
        args.putParcelable("QUEST", quest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quest = getArguments().getParcelable("QUEST");
        }

        if(!SoundManager.getInstance().getConstantPlayer().isPlaying()){
            SoundManager.getInstance().getConstantPlayer().start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quest_end, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String reward = quest.getReward().getName().replace(" ", "-");
        if(Locale.getDefault().getLanguage().equals("en")){
            TextView questNameTextView = view.findViewById(R.id.end_quest_fragment_quest_name);
            questNameTextView.setText(quest.getName());
            ImageView legendImage = view.findViewById(R.id.end_quest_fragment_image_view);
            Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(reward)).into(legendImage);
            TextView description = view.findViewById(R.id.end_quest_fragment_end_description_text_view);
            description.setText(quest.getDescriptionEndEnglish());
        }
        else if(Locale.getDefault().getLanguage().equals("nl")){
            TextView questNameTextView = view.findViewById(R.id.end_quest_fragment_quest_name);
            questNameTextView.setText(quest.getName());
            ImageView legendImage = view.findViewById(R.id.end_quest_fragment_image_view);
            Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(reward)).into(legendImage);
            TextView description = view.findViewById(R.id.end_quest_fragment_end_description_text_view);
            description.setText(quest.getdescriptionEndDutch());
        }
        else {
            TextView questNameTextView = view.findViewById(R.id.end_quest_fragment_quest_name);
            questNameTextView.setText(quest.getName());
            ImageView legendImage = view.findViewById(R.id.end_quest_fragment_image_view);
            Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(reward)).into(legendImage);
            TextView description = view.findViewById(R.id.end_quest_fragment_end_description_text_view);
            description.setText(quest.getDescriptionEndEnglish());
        }
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

            SoundManager.getInstance().playSound(new Sound(R.raw.victory_sound, getContext()));

            started = true;
        }

        AppDatabase appDb = DatabaseManager.getInstance(getContext()).getAppDatabase();
        Executor databaseThread = Executors.newSingleThreadExecutor();
        databaseThread.execute(() -> {
            if (appDb.legendDao().getLegendById(quest.getReward().getId()) != null) {
                Legend legendDb = appDb.legendDao().getLegendById(quest.getReward().getId());
                legendDb.setCapturedAmount(legendDb.getCapturedAmount() + 1);
                appDb.legendDao().updateLegend(legendDb);
            } else {
                quest.getReward().setCapturedAmount(1);
                quest.getReward().setCaptured(true);
                appDb.legendDao().insertAll(quest.getReward());

            }
        });
    }

}
