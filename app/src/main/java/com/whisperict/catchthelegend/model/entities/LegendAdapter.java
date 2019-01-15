package com.whisperict.catchthelegend.model.entities;

import android.location.Location;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class LegendAdapter implements JsonSerializer<Legend>, JsonDeserializer<Legend> {

    @Override
    public JsonElement serialize(Legend src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",src.getId());
        jsonObject.addProperty("uniqueId",src.getUniqueId());
        jsonObject.addProperty("name",src.getName());
        jsonObject.addProperty("franchise",src.getFranchise());
        jsonObject.addProperty("descriptionEnglish",src.getDescriptionEnglish());
        jsonObject.addProperty("descriptionDutch",src.getDescriptionDutch());
        jsonObject.addProperty("rarity", src.getRarity());
        jsonObject.addProperty("latitude", src.getLocation().getLatitude());
        jsonObject.addProperty("longitude", src.getLocation().getLongitude());
        jsonObject.addProperty("isCaptured", src.isCaptured());
        jsonObject.addProperty("capturedAmount", src.getCapturedAmount());

        return jsonObject;
    }

    @Override
    public Legend deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String uniqueId = jsonObject.get("uniqueId").getAsString();
        String name = jsonObject.get("name").getAsString();
        String franchise = jsonObject.get("franchise").getAsString();
        String descriptionEnglish = jsonObject.get("descriptionEnglish").getAsString();
        String descriptionDutch = jsonObject.get("descriptionDutch").getAsString();
        String rarity = jsonObject.get("rarity").getAsString();
        double latitude = jsonObject.get("latitude").getAsDouble();
        double longitude = jsonObject.get("longitude").getAsDouble();
        boolean isCaptured = jsonObject.get("isCaptured").getAsBoolean();
        int capturedAmount = jsonObject.get("capturedAmount").getAsInt();

        Location location = new Location("");
        location.setLongitude(longitude);
        location.setLatitude(latitude);

        return new Legend(id,uniqueId,name,franchise,descriptionEnglish,descriptionDutch,rarity,location,isCaptured,capturedAmount);
    }
}
