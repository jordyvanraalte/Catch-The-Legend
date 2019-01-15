package com.whisperict.catchthelegend.controllers.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.whisperict.catchthelegend.model.entities.Legend;

@Database(entities = {Legend.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LegendDao legendDao();
}
