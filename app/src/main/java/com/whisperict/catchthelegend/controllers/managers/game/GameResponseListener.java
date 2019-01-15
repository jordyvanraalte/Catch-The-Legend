package com.whisperict.catchthelegend.controllers.managers.game;

import com.whisperict.catchthelegend.model.entities.Legend;

import java.util.ArrayList;

public interface GameResponseListener {
    public void spawnLegends(ArrayList<Legend> legends);
    public void deSpawnLegends();
}
