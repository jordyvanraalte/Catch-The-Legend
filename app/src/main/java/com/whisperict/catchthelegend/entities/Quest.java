package com.whisperict.catchthelegend.entities;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Quest implements Parcelable {
    private String name;
    private String description;
    private ArrayList<Location> locations;

    public Quest(String name, String description, ArrayList<Location> locations) {
        this.name = name;
        this.description = description;
        this.locations = locations;
    }

    protected Quest(Parcel in) {
        name = in.readString();
        description = in.readString();
        locations = in.createTypedArrayList(Location.CREATOR);
    }

    public static final Creator<Quest> CREATOR = new Creator<Quest>() {
        @Override
        public Quest createFromParcel(Parcel in) {
            return new Quest(in);
        }

        @Override
        public Quest[] newArray(int size) {
            return new Quest[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeTypedList(locations);
    }
}
