package com.whisperict.catchthelegend.controllers.managers.game;

import com.whisperict.catchthelegend.model.entities.Quest;

public interface QuestStatusListener {
    void OnQuestFinish(Quest quest);
}
