package com.whisperict.catchthelegend.entities;

public class Legend {
    private int id;
    private String name;
    private String franchise;
    private String descriptionEnglish;
    private String descriptionDutch;
    private String rarity;
    private String dropRate;

    public Legend(int id, String name, String franchise, String descriptionEnglish, String descriptionDutch, String rarity, String dropRate) {
        this.id = id;
        this.name = name;
        this.franchise = franchise;
        this.descriptionEnglish = descriptionEnglish;
        this.descriptionDutch = descriptionDutch;
        this.rarity = rarity;
        this.dropRate = dropRate;
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

    public String getDropRate() {
        return dropRate;
    }

    public void setDropRate(String dropRate) {
        this.dropRate = dropRate;
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
                ", dropRate='" + dropRate + '\'' +
                '}';
    }
}
