package com.whisperict.catchthelegend.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Legend implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "franchise")
    private String franchise;

    @ColumnInfo(name = "description_english")
    private String descriptionEnglish;

    @ColumnInfo(name = "description_dutch")
    private String descriptionDutch;

    @ColumnInfo(name = "rarity")
    private String rarity;

    @Ignore
    private Location location;

    @ColumnInfo(name = "is_captured")
    private boolean isCaptured = false;

    @ColumnInfo(name = "captured_amount")
    private int capturedAmount = 0;


    public Legend(int id, String name, String franchise, String descriptionEnglish, String descriptionDutch, String rarity) {
        this.id = id;
        this.name = name;
        this.franchise = franchise;
        this.descriptionEnglish = descriptionEnglish;
        this.descriptionDutch = descriptionDutch;
        this.rarity = rarity;
        this.location = new Location("");
    }


    protected Legend(Parcel in) {
        id = in.readInt();
        name = in.readString();
        franchise = in.readString();
        descriptionEnglish = in.readString();
        descriptionDutch = in.readString();
        rarity = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<Legend> CREATOR = new Creator<Legend>() {
        @Override
        public Legend createFromParcel(Parcel in) {
            return new Legend(in);
        }

        @Override
        public Legend[] newArray(int size) {
            return new Legend[size];
        }
    };

    public void setLatitude(double latitude){
        this.location.setLatitude(latitude);
    }

    public void setLongitude(double longitude){
        this.location.setLongitude(longitude);
    }

    public Location getLocation(){
        return this.location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
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

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    public int getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(int capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    @Override
    public String toString() {
        return "Legend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", franchise='" + franchise + '\'' +
                ", descriptionEnglish='" + descriptionEnglish + '\'' +
                ", descriptionDutch='" + descriptionDutch + '\'' +
                ", rarity='" + rarity + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(franchise);
        parcel.writeString(descriptionEnglish);
        parcel.writeString(descriptionDutch);
        parcel.writeString(rarity);
        parcel.writeParcelable(location, i);
    }
}
