package com.whisperict.catchthelegend.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.adapters.CompendiumAdapter;
import com.whisperict.catchthelegend.entities.Legend;

import java.util.ArrayList;

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
        RecyclerView compendiumRecylcerView = getView().findViewById(R.id.compendium_recycler_view);
        compendiumRecylcerView.setHasFixedSize(true);
        for(int i = 0; i < 4; i++){
            legends.add(new Legend("Rupay","Dikke beschrijving","common","0.2%"));
        }


        compendiumRecylcerView.setLayoutManager(new GridLayoutManager(view.getContext(),3));
        CompendiumAdapter compendiumAdapter = new CompendiumAdapter(view.getContext(),legends);
        compendiumRecylcerView.setAdapter(compendiumAdapter);
    }
}
