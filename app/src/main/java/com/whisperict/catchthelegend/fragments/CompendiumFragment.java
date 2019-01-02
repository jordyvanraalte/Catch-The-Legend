package com.whisperict.catchthelegend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.activities.DetailedLegendActivity;
import com.whisperict.catchthelegend.adapters.CompendiumAdapter;
import com.whisperict.catchthelegend.database.DatabaseManager;
import com.whisperict.catchthelegend.entities.Legend;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CompendiumFragment extends Fragment implements CompendiumAdapter.OnItemClickListener {

    private ArrayList<Legend> legends = new ArrayList<>();

    private CompendiumAdapter compendiumAdapter;

    final Handler databaseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            compendiumAdapter.notifyDataSetChanged();
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compendium, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView compendiumRecyclerView = Objects.requireNonNull(getView()).findViewById(R.id.compendium_recycler_view);
        compendiumRecyclerView.setHasFixedSize(true);
        compendiumRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),3));
        compendiumAdapter = new CompendiumAdapter(view.getContext(),legends);
        compendiumRecyclerView.setAdapter(compendiumAdapter);
        compendiumAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Executor databaseThread = Executors.newSingleThreadExecutor();
        databaseThread.execute(() -> {
            this.legends.clear();
            this.legends.addAll(DatabaseManager.getInstance(getContext()).getAppDatabase().legendDao().getAll());
            databaseHandler.sendMessage(new Message());
        });
    }

    @Override
    public void onItemClick(int position) {
        Legend legend = legends.get(position);
        Intent detailedIntent = new Intent(getContext(), DetailedLegendActivity.class);
        detailedIntent.putExtra("LEGEND", legend);
        startActivity(detailedIntent);
    }
}
