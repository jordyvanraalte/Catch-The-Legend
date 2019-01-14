package com.whisperict.catchthelegend.managers.apis;

import android.location.Location;

import java.util.ArrayList;

public class RouteProvider {
    private static final String KEY = "7f663dad-dc2c-4660-ba02-d0f996ae5240";
    private static final String URL = "https://maps.moviecast.io/directions";
    public static String getUrlRoute(ArrayList<Location> locations){
        if(locations.size() != 0 && locations.get(0) != null){
            String request = String.format("%s?mode=walking&origin=%s,%s&destination=%s,%s", URL, locations.get(0).getLatitude(), locations.get(0).getLongitude(),
                    locations.get(locations.size() - 1).getLatitude(), locations.get(locations.size()- 1).getLongitude());
            String wayPoints = "";
            if(locations.size() > 2){
                wayPoints = "&waypoints=";
                for(int i = 0; i < locations.size() - 1; i++ ){
                    wayPoints += locations.get(i).getLatitude() + "," + locations.get(i).getLongitude();
                    if(i + 1 < locations.size())
                        wayPoints += "|";
                }
            }
            request += wayPoints + "&key=" + KEY;
            return request;
        }
        return "";
    }
}
