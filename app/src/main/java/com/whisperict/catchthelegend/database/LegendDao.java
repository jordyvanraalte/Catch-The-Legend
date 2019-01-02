package com.whisperict.catchthelegend.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.whisperict.catchthelegend.entities.Legend;

import java.util.List;

@Dao
public interface LegendDao {

    @Query("SELECT * FROM legend")
    List<Legend> getAll();

    @Query("SELECT * FROM legend WHERE id == :legendId")
    Legend getLegendById(int legendId);

    @Insert
    void insertAll(Legend... legends);

    @Update
    public void updateLegend(Legend legend);

    @Delete
    void delete(Legend legend);

    @Query("DELETE FROM legend")
    public void reset();
}
