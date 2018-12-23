package com.whisperict.catchthelegend.managers.game;

import android.content.Context;
import android.location.Location;

import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.apis.LegendApiManager;
import com.whisperict.catchthelegend.managers.apis.legend.OnLegendApiResponseListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class GameManager implements OnLegendApiResponseListener {
    private Context context;
    private static GameManager instance;
    private Random random = new Random();

    //length of Earth in meters.
    private static final double EARTH_LENGTH = 6371000;

    private LinkedHashMap<Integer, Tier> tierList = new LinkedHashMap<>();

    private Location lastSpawnLocation;
    private boolean gameIsStarted = false;


    public GameManager(Context context){
        tierList.put(0, Tier.COMMON);
        tierList.put(60, Tier.UNCOMMON);
        tierList.put(85, Tier.RARE);
        tierList.put(90, Tier.LEGEND);
        tierList.put(95, Tier.ULTRA_LEGEND);
        this.context = context;
    }

    public void start(){
        gameIsStarted = true;
    }

    public void pause(){
        gameIsStarted = false;
    }

    public void update(Location userLocation, Location lastSpawnLocation) {
        if(userLocation.bearingTo(lastSpawnLocation) >= 200){
            this.lastSpawnLocation = userLocation;
            //generate 3 legends in radius of 200.
            for(int i = 0; i < 3; i++){
                getRandomLegend(generateTier());
            }
        }
    }


    private Tier generateTier(){
        ArrayList<Integer> chances = new ArrayList<>(tierList.keySet());
        for(int i = chances.size() - 1; i >= 0; i--){
            double chance = random.nextInt(101);
            if(chance >= chances.get(i)){
                return tierList.get(chances.get(i));
            }
        }
        return Tier.COMMON;
    }

    private void getRandomLegend(Tier tier){
        switch (tier){
            case COMMON:
                LegendApiManager.getInstance().getRandomLegendByTier(context, this, "common");
                break;
            case UNCOMMON:
                LegendApiManager.getInstance().getRandomLegendByTier(context, this, "uncommon");
                break;
            case RARE:
                LegendApiManager.getInstance().getRandomLegendByTier(context, this, "rare");
                break;
            case LEGEND:
                LegendApiManager.getInstance().getRandomLegendByTier(context, this, "legend");
            case ULTRA_LEGEND:
                LegendApiManager.getInstance().getRandomLegendByTier(context, this, "ultra_legend");
        }
    }

    @Override
    public void OnRandomLegendReceive(Legend legend) {
        //200 = 200m from last location
        /*double maxLatitude = lastSpawnLocation.getLatitude() + (200 / EARTH_LENGTH) * (180 / Math.PI);
        double maxLongitude = lastSpawnLocation.getLongitude() + (200 / EARTH_LENGTH) / Math.cos(lastSpawnLocation.getLatitude() * Math.PI / 180);

        double minLatitude = lastSpawnLocation.getLatitude() + (-200 / EARTH_LENGTH) * (180 / Math.PI);
        double minLongitude = lastSpawnLocation.getLongitude() + (-200 / EARTH_LENGTH) / Math.cos(lastSpawnLocation.getLatitude() * Math.PI / 180);*/

        double spawnLatitude = lastSpawnLocation.getLatitude() + ((random.nextInt(400) - 200) / EARTH_LENGTH) * (180 / Math.PI);
        double spawnLongitude = lastSpawnLocation.getLongitude() + ((random.nextInt(400)-200) / EARTH_LENGTH) / Math.cos(lastSpawnLocation.getLatitude() * Math.PI / 180);

        legend.setLatitude(spawnLatitude);
        legend.setLongitude(spawnLongitude);

    }



    @Override
    public void OnLegendReceive(Legend legend) {

    }

    @Override
    public void OnLegendsReceive(ArrayList<String> names) {

    }

    @Override
    public void OnLegendCountReceive(int count) {

    }

    public static GameManager getInstance(Context context){
        if(instance == null) {instance = new GameManager(context);}
        return instance;
    }
}
