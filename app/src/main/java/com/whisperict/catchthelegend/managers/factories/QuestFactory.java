package com.whisperict.catchthelegend.managers.factories;

import android.location.Location;

import com.whisperict.catchthelegend.entities.Quest;

import java.util.ArrayList;

public class QuestFactory {

    private static QuestFactory instance;

    public ArrayList<Quest> create(){
        Quest max = new Quest("Max's feest","aaaa", new ArrayList<Location>());
        ArrayList<Quest> quests = new ArrayList<>();
        quests.add(max);
        return quests;
    }


    public static QuestFactory getInstance(){
        if(instance == null){
            instance = new QuestFactory();
        }
        return instance;
    }
}
