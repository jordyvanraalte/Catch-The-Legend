package com.whisperict.catchthelegend.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class Legend implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo
    private String uniqueId;

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
        this.uniqueId = UUID.randomUUID().toString();
    }

    protected Legend(Parcel in) {
        id = in.readInt();
        uniqueId = in.readString();
        name = in.readString();
        franchise = in.readString();
        descriptionEnglish = in.readString();
        descriptionDutch = in.readString();
        rarity = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        isCaptured = in.readByte() != 0;
        capturedAmount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(uniqueId);
        dest.writeString(name);
        dest.writeString(franchise);
        dest.writeString(descriptionEnglish);
        dest.writeString(descriptionDutch);
        dest.writeString(rarity);
        dest.writeParcelable(location, flags);
        dest.writeByte((byte) (isCaptured ? 1 : 0));
        dest.writeInt(capturedAmount);
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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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



}
