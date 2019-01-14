package com.whisperict.catchthelegend.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

public class DatabaseManager {

    private static DatabaseManager instance;
    private AppDatabase appDatabase;

    private DatabaseManager(Context context){
        appDatabase = Room.databaseBuilder(context,AppDatabase.class,"CatchTheLegend").addMigrations(new Migration(1,2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("ALTER TABLE legend" + " ADD COLUMN uniqueId TEXT");
            }
        }).build();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static DatabaseManager getInstance(Context context){
        if(instance == null){ instance = new DatabaseManager(context);}
        return instance;
    }
}
