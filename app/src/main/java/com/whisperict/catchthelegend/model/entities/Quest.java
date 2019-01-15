package com.whisperict.catchthelegend.model.entities;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Quest implements Parcelable {
    private String name;
    private String descriptionEnglish;
    private String descriptionDutch;
    private String descriptionEndEnglish;
    private String descriptionEndDutch;
    private ArrayList<Location> locations;
    private Legend reward;

    public Quest(String name, String descriptionEnglish, String descriptionDutch, String descriptionEndEnglish, String descriptionEndDutch, ArrayList<Location> locations, Legend reward) {
        this.name = name;
        this.descriptionEnglish = descriptionEnglish;
        this.descriptionDutch = descriptionDutch;
        this.descriptionEndEnglish = descriptionEndEnglish;
        this.descriptionEndDutch = descriptionEndDutch;
        this.locations = locations;
        this.reward = reward;
    }

    protected Quest(Parcel in) {
        name = in.readString();
        descriptionEnglish = in.readString();
        descriptionDutch = in.readString();
        descriptionEndEnglish = in.readString();
        descriptionEndDutch = in.readString();
        locations = in.createTypedArrayList(Location.CREATOR);
        reward = in.readParcelable(Legend.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(descriptionEnglish);
        dest.writeString(descriptionDutch);
        dest.writeString(descriptionEndEnglish);
        dest.writeString(descriptionEndDutch);
        dest.writeTypedList(locations);
        dest.writeParcelable(reward, flags);
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


    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }


    public Legend getReward() {
        return reward;
    }

    public void setReward(Legend reward) {
        this.reward = reward;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    public String getDescriptionEndEnglish() {
        return descriptionEndEnglish;
    }

    public void setDescriptionEndEnglish(String descriptionEndEnglish) {
        this.descriptionEndEnglish = descriptionEndEnglish;
    }

    public String getdescriptionEndDutch() {
        return descriptionEndDutch;
    }

    public void setdescriptionEndDutch(String descriptionEndDutch) {
        this.descriptionEndDutch = descriptionEndDutch;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
