package com.whisperict.catchthelegend.managers;



import android.location.Location;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.whisperict.catchthelegend.entities.Quest;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    private static QuestManager instance;
    private ArrayList<Quest> quests = new ArrayList<>();
    private Quest currentQuest;
    private QuestManager(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference quests = db.collection("Quests");
        quests.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.isEmpty()){
                Log.d("QUEST_TAG", "succes but quests are empty");
            }
            else {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String name = documentSnapshot.getString("name");
                    String description = documentSnapshot.getString("description");
                    List<GeoPoint> geoPoints = (List<GeoPoint>) documentSnapshot.get("locations");
                    ArrayList<Location> locations = new ArrayList<>();
                    for(GeoPoint geoPoint : geoPoints){
                        Location location = new Location("");
                        location.setLatitude(geoPoint.getLatitude());
                        location.setLongitude(geoPoint.getLongitude());
                        locations.add(location);
                    }
                    this.quests.add(new Quest(name, description, locations));
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
        this.currentQuest = currentQuest;
    }

    public static QuestManager getInstance(){
        if(instance == null){
            instance = new QuestManager();
        }
        return instance;
    }
}
