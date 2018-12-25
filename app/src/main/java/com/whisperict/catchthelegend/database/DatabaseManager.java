package com.whisperict.catchthelegend.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseManager {

    private static DatabaseManager instance;
    private AppDatabase appDatabase;

    private DatabaseManager(Context context){
        appDatabase = Room.databaseBuilder(context,AppDatabase.class,"CatchTheLegend").build();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static DatabaseManager getInstance(Context context){
        if(instance == null){ instance = new DatabaseManager(context);}
        return instance;
    }
}
