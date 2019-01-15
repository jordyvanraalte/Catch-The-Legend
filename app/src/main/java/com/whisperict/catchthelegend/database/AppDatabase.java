package com.whisperict.catchthelegend.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.whisperict.catchthelegend.entities.Legend;

@Database(entities = {Legend.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LegendDao legendDao();
    public abstract LocationDao locationDao();
}
