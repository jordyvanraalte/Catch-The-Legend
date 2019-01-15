package com.whisperict.catchthelegend.managers.game;

import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.entities.Quest;

public interface QuestStatusListener {
    void OnQuestFinish(Quest quest);
}
