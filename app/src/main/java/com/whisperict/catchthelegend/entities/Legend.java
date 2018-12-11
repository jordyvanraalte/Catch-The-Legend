package com.whisperict.catchthelegend.entities;

public class Legend {
    private String name;
    private String description;
    private String rarity;
    private String dropRate;


    public Legend(String name, String description, String rarity, String dropRate) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.dropRate = dropRate;
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
}
