package com.whisperict.catchthelegend.managers.game;



import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.entities.Quest;
import com.whisperict.catchthelegend.managers.apis.OnRouteResponseListener;
import com.whisperict.catchthelegend.managers.apis.RouteManager;
import com.whisperict.catchthelegend.managers.apis.legend.LegendApiManager;
import com.whisperict.catchthelegend.managers.apis.legend.OnLegendApiResponseListener;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    private static QuestManager instance;
    private ArrayList<Quest> quests = new ArrayList<>();
    private Quest currentQuest;
    private Location lastLocation;
    private QuestManager(Context context){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference quests = db.collection("Quests");
        quests.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.isEmpty()){
                Log.d("QUEST_TAG", "succes but quests are empty");
            }
            else {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String name = documentSnapshot.getString("name");
                    String descriptionDutch = documentSnapshot.getString("descriptionDutch");
                    String descriptionEndDutch = documentSnapshot.getString("descriptionEndDutch");
                    String descriptionEnglish = documentSnapshot.getString("descriptionEnglish");
                    String descriptionEndEnglish = documentSnapshot.getString("descriptionEndEnglish");
                    String legendName = documentSnapshot.getString("reward");

                    List<GeoPoint> geoPoints = (List<GeoPoint>) documentSnapshot.get("locations");
                    ArrayList<Location> locations = new ArrayList<>();
                    for(GeoPoint geoPoint : geoPoints){
                        Location location = new Location("");
                        location.setLatitude(geoPoint.getLatitude());
                        location.setLongitude(geoPoint.getLongitude());
                        locations.add(location);
                    }
                    LegendApiManager.getInstance().getLegend(context, new OnLegendApiResponseListener() {
                        @Override
                        public void OnLegendReceive(Legend legend) {
                            QuestManager.instance.quests.add(new Quest(name,descriptionEnglish,descriptionDutch,descriptionEndEnglish,descriptionEndDutch,locations,legend));
                        }

                        @Override
                        public void OnLegendsReceive(ArrayList<String> names) {

                        }

                        @Override
                        public void OnLegendCountReceive(int count) {

                        }

                        @Override
                        public void OnRandomLegendReceive(Legend legend) {

                        }
                    }, legendName);
                }
            }
        });
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }

    public void setCurrentQuest(Quest currentQuest) {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(lastLocation);
        locations.addAll(currentQuest.getLocations());
        currentQuest.setLocations(locations);
        this.currentQuest = currentQuest;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public static QuestManager getInstance(Context context){
        if(instance == null){
            instance = new QuestManager(context);
        }
        return instance;
    }

    public void getRoute(Context context, OnRouteResponseListener onRouteResponseListener) {
        RouteManager.getInstance().getRoute(currentQuest.getLocations(),context, onRouteResponseListener);
    }

    public void update(Location location, QuestStatusListener questStatusListener, Polyline polyline) {
        if(location != null && currentQuest != null && polyline != null){
            if(location.distanceTo(currentQuest.getLocations().get(currentQuest.getLocations().size() - 1)) < Double.MAX_VALUE){
                questStatusListener.OnQuestFinish(currentQuest);
                currentQuest = null;
            }
        }
    }
}
