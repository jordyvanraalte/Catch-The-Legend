package com.whisperict.catchthelegend.fragments;

import android.os.Bundle;
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
import com.whisperict.catchthelegend.entities.Legend;

import java.util.ArrayList;
import java.util.Objects;

public class CompendiumFragment extends Fragment {

    private ArrayList<Legend> legends = new ArrayList<>();

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
        CompendiumAdapter compendiumAdapter = new CompendiumAdapter(view.getContext(),legends);
        compendiumRecyclerView.setAdapter(compendiumAdapter);
    }
}
