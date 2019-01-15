package com.whisperict.catchthelegend.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.whisperict.catchthelegend.entities.Legend;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM LocationDatabase")
    List<LocationDatabase> getAll();
    @Insert
    void insertAll(LocationDatabase... locationDatabases);


}
