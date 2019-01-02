package com.whisperict.catchthelegend.fragments;

import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.adapters.CompendiumAdapter;
import com.whisperict.catchthelegend.adapters.QuestAdapter;
import com.whisperict.catchthelegend.entities.Quest;
import com.whisperict.catchthelegend.managers.factories.QuestFactory;

import java.util.ArrayList;
import java.util.Objects;

public class QuestFragment extends Fragment {
    private ArrayList<Quest> quests = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quests = QuestFactory.getInstance().create();
        RecyclerView questRecyclerView = Objects.requireNonNull(getView()).findViewById(R.id.quest_recyclerview);
        questRecyclerView.setHasFixedSize(true);
        questRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        QuestAdapter questAdapter = new QuestAdapter(getContext(), quests);
        questRecyclerView.setAdapter(questAdapter);
    }
}
