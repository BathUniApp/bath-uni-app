package ip7.bathuniapp;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class BusRoute {
    private Map<String, ArrayList<Integer>> stops;
    
    public BusRoute(String name, String type){
        this.stops = new HashMap<String, ArrayList<Integer>>();
    }
    
    // Adds a stop to this bus route with the given ArrayList of times
    public void addStop(String stopName, ArrayList<Integer> times){
        // Make sure all times are correct
        for(int i = 0; i < times.size(); i++){
            times.set(i, times.get(i) % 1440);
        }
        stops.put(stopName, times);
    }
    
    // Adds the given time to the given stopName
    // If time is >= 1440, it is in the next day.
    public void addStopTime(String stopName, int time){
        stops.get(stopName).add(time % 1400);
    }
    
    // Returns an ArrayList of all the times for this stop
    public ArrayList<Integer> getAllTimes(String stopName){
        return stops.get(stopName);
    }
    
    // Returns the next bus time for the given stopName on this route. 
    // If no next time is found, returns -1
    public int getNextTime(String stopName, int currentTime){
        // Make sure current time is correct
        currentTime = currentTime % 1440;
        
        ArrayList<Integer> stopTimes = stops.get(stopName);
        Collections.sort(stopTimes);
        for (int i = 0; i < stopTimes.size(); i++) {
            int testTime = stopTimes.get(i);
            if (testTime >= currentTime){
                return testTime;
            }
        }
        return -1;
    }
}

