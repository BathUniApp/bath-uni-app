package ip7.bathuniapp;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;

public class BusesFragment extends Fragment {
    
    private static Map<String, BusRoute> allRoutes = new HashMap<String, BusRoute>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fillBusRoutes();
        
        // Example code for accessing all bus times for a stop.
        ArrayList<Integer> returnedTimes = allRoutes.get("U18MTF").getAllTimes("University of Bath");
        for (Integer time: returnedTimes){
            System.out.println(timeToString(time));
        }
        
        // Example code for accessing the next bus time
        System.out.println(timeToString(getCurrentTime()));
        System.out.println(timeToString(allRoutes.get("U18MTF").getNextTime(
                "Youth Hostel South", getCurrentTime())));
        
        return  inflater.inflate(R.layout.frag_buses, container, false);
    }
    
    public void fillBusRoutes() {
        BusRoute myRoute = new BusRoute("U18", "MTF");
        
        Map<String, Integer> offset = new HashMap<String, Integer>();
        offset.put("Lower Oldfield Park", 0);
        offset.put("Corn Street", 7);
        offset.put("High Street", 11);
        offset.put("Youth Hostel North", 17);
        offset.put("University of Bath", 25);
        offset.put("Youth Hostel South", 29);
        offset.put("North Parade", 35);
        
        for (String stopName: offset.keySet()) {
            int j = offset.get(stopName);
            ArrayList<Integer> times = new ArrayList<Integer>();
            times.add(420 + j);
            times.add(430 + j);
            times.add(440 + j);
            for(int i = 0; i < 157; i++) {
                times.add(445 + i * 5 + j);
            }
            for(int i = 0; i < 18; i++){
                times.add(1240 + i * 20 + j);
            }
            myRoute.addStop(stopName, times);
            allRoutes.put("U18MTF", myRoute);
        }
    }
    
    public String timeToString(int time) {
        return String.format("%02d", time / 60) + ":" + String.format("%02d", time % 60);
    }
    
    public int getCurrentTime() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
    }

}
