package com.whisperict.catchthelegend.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.support.annotation.NonNull;

@Entity
public class LocationDatabase {

    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "id")
    private String uuid;
    @ColumnInfo (name = "location")
    private Location location;

    public LocationDatabase(String uuid, Location location) {
        this.uuid = uuid;
        this.location = location;
    }
}
