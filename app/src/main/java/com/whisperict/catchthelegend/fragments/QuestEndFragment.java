package com.whisperict.catchthelegend.fragments;

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
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.entities.Quest;
import com.whisperict.catchthelegend.managers.Sound;
import com.whisperict.catchthelegend.managers.SoundManager;
import com.whisperict.catchthelegend.managers.apis.legend.LegendApiManager;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quest_end, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView questNameTextView = view.findViewById(R.id.end_quest_fragment_quest_name);
        questNameTextView.setText(quest.getName());
        ImageView legendImage = view.findViewById(R.id.end_quest_fragment_image_view);
        Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(quest.getReward().getName())).into(legendImage);
        TextView description = view.findViewById(R.id.end_quest_fragment_end_description_text_view);
        description.setText(quest.getdescriptionEndDutch());
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

            SoundManager.playSound(new Sound(R.raw.victory_sound, getContext()));

            started = true;
        }
    }
}
