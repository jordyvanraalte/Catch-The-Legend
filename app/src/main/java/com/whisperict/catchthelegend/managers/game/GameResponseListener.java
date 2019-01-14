package com.whisperict.catchthelegend.managers.game;

import com.whisperict.catchthelegend.entities.Legend;

public interface GameResponseListener {
    public void spawnLegend(Legend legend);
    public void deSpawnLegends();
}
