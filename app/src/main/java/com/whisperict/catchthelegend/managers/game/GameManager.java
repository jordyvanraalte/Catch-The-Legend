package com.whisperict.catchthelegend.managers.game;

import android.content.Context;
import android.location.Location;

import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.apis.legend.LegendApiManager;
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

    private Location lastSpawnLocation = new Location("");
    private boolean gameIsStarted = false;

    private GameResponseListener gameResponseListener;


    public GameManager(Context context, GameResponseListener gameResponseListener){
        tierList.put(0, Tier.COMMON);
        tierList.put(60, Tier.UNCOMMON);
        tierList.put(85, Tier.RARE);
        tierList.put(90, Tier.LEGEND);
        tierList.put(95, Tier.ULTRA_LEGEND);
        this.context = context;
        this.gameResponseListener = gameResponseListener;
    }

    public void start(){
        gameIsStarted = true;
    }

    public void update(Location userLocation) {
        if(userLocation.distanceTo(this.lastSpawnLocation) >= 200){
            this.lastSpawnLocation = userLocation;
            //gameResponseListener.deSpawnLegends();
            //generate 15 legends in radius of 200m.
            for(int i = 0; i < 15; i++){
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
        // 1.11m = 0.00001 graden src= https://gis.stackexchange.com/questions/8650/measuring-accuracy-of-latitude-and-longitude
        double x = random.nextInt(300) - 150;
        double y = random.nextInt(300) - 150;

        double spawnLatitude = lastSpawnLocation.getLatitude() + (x * 0.00001 / 1.11);
        double spawnLongitude = lastSpawnLocation.getLongitude() + (y * 0.00001 / 1.11);

        /*double spawnLatitude = lastSpawnLocation.getLatitude() + ((random.nextInt(400) - 200 ) / EARTH_LENGTH) * (180 / Math.PI);
        double spawnLongitude = lastSpawnLocation.getLongitude() + ((random.nextInt(1600) * 10 - 800 * 10 ) / EARTH_LENGTH) / Math.cos(lastSpawnLocation.getLatitude() * Math.PI / 180);*/

        legend.setLatitude(spawnLatitude);
        legend.setLongitude(spawnLongitude);
        gameResponseListener.spawnLegend(legend);
    }



    @Override
    public void OnLegendReceive(Legend legend) {
        // 1.11m = 0.00001 graden src= https://gis.stackexchange.com/questions/8650/measuring-accuracy-of-latitude-and-longitude
        double x = random.nextInt(300) - 150;
        double y = random.nextInt(300) - 150;

        double spawnLatitude = lastSpawnLocation.getLatitude() + (x * 0.00001 / 1.11);
        double spawnLongitude = lastSpawnLocation.getLongitude() + (y * 0.00001 / 1.11);

        /*double spawnLatitude = lastSpawnLocation.getLatitude() + ((random.nextInt(400) - 200 ) / EARTH_LENGTH) * (180 / Math.PI);
        double spawnLongitude = lastSpawnLocation.getLongitude() + ((random.nextInt(1600) * 10 - 800 * 10 ) / EARTH_LENGTH) / Math.cos(lastSpawnLocation.getLatitude() * Math.PI / 180);*/

        legend.setLatitude(spawnLatitude);
        legend.setLongitude(spawnLongitude);
        gameResponseListener.spawnLegend(legend);
    }

    @Override
    public void OnLegendsReceive(ArrayList<String> names) {

    }

    @Override
    public void OnLegendCountReceive(int count) {

    }

    public static GameManager getInstance(Context context, GameResponseListener gameResponseListener){
        if(instance == null) {instance = new GameManager(context, gameResponseListener);}
        return instance;
    }

    public void setGameResponseListener(GameResponseListener gameResponseListener) {
        this.gameResponseListener = gameResponseListener;
    }
}
