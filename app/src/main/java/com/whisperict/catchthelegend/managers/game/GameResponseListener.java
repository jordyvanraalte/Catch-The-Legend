package com.whisperict.catchthelegend.managers.game;

import com.whisperict.catchthelegend.entities.Legend;

import java.util.ArrayList;

public interface GameResponseListener {
    public void spawnLegends(ArrayList<Legend> legends);
    public void deSpawnLegends();
}
