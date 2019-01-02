package com.whisperict.catchthelegend.entities;

import android.location.Location;

import java.util.ArrayList;

public class Quest {
    private String name;
    private String description;
    private ArrayList<Location> locations;

    public Quest(String name, String description, ArrayList<Location> locations) {
        this.name = name;
        this.description = description;
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }
}
