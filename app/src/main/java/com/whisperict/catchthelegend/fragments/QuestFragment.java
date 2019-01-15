package com.whisperict.catchthelegend.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.adapters.QuestAdapter;
import com.whisperict.catchthelegend.entities.Quest;
import com.whisperict.catchthelegend.managers.SoundManager;
import com.whisperict.catchthelegend.managers.apis.RouteManager;
import com.whisperict.catchthelegend.managers.game.QuestManager;

import java.util.ArrayList;
import java.util.Objects;

public class QuestFragment extends Fragment implements QuestAdapter.OnItemClickListener {
    private ArrayList<Quest> quests = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quests = QuestManager.getInstance(getContext()).getQuests();


        //recyclerview components.
        RecyclerView questRecyclerView = Objects.requireNonNull(getView()).findViewById(R.id.quest_recyclerview);
        questRecyclerView.setHasFixedSize(true);
        questRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        QuestAdapter questAdapter = new QuestAdapter(getContext(), quests);
        questRecyclerView.setAdapter(questAdapter);
        questAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        if(QuestManager.getInstance(getContext()).getCurrentQuest() == null){
            QuestManager.getInstance(getContext()).setCurrentQuest(quests.get(position));
            Toast.makeText(getContext(),R.string.set_quest,Toast.LENGTH_LONG).show();
        }
        else if(QuestManager.getInstance(getContext()).getCurrentQuest().equals(quests.get(position))){
            QuestManager.getInstance(getContext()).setCurrentQuest(null);
            Toast.makeText(getContext(),R.string.disable_quest,Toast.LENGTH_LONG).show();
        }
        else {
            QuestManager.getInstance(getContext()).setCurrentQuest(quests.get(position));
            Toast.makeText(getContext(),R.string.set_quest,Toast.LENGTH_LONG).show();
        }

    }

}
