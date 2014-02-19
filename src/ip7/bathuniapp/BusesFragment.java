package ip7.bathuniapp;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;

public class BusesFragment extends Fragment {
    
    ArrayList<E> busTimes = new ArrayList<E>();
    
    private String[] routes = { "U18" };
    private String[] days = { "M-F Term" };
    
    // Access a stop with routeno, stopno. So the fourth stop of the first route
    // would be stops[1][4].
    private String[][] stops = { { "Lower Oldfield Park", "Corn Street",
            "High Street", "Bathwick Hill North", "University of Bath",
            "Bathwick Hill South", "City Centre", } };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.frag_buses, container, false);
    }
    
    // Create a new Bus time element
    // Note: SQLlite only supports int and String.
    private void addStop(int route, int day, int run, int stop, int time) {
        
    }
    
    // Little helper method which adds any number of minutes
    // to a time-int. 
    private int addClockTimes(int start, int toAdd){
        int minutes = (start % 100) + (toAdd % 60);
        int hours = (start / 100) + (toAdd / 60);
        return ((hours + (minutes / 60)) % 24) * 100 + (minutes % 60);
    }
    
    // Create an arraylist of stops
    private void fillStops(){
        // Fill in the stops for the U18 on a weekday
        for(int i = 0; i < 2; i++) {
            addStop(0, 0, i, 0, addClockTimes(700, 10 * i));
            addStop(0, 0, i, 1, addClockTimes(707, 10 * i));
            addStop(0, 0, i, 2, addClockTimes(711, 10 * i));
            addStop(0, 0, i, 3, addClockTimes(717, 10 * i));
            addStop(0, 0, i, 4, addClockTimes(725, 10 * i));
            addStop(0, 0, i, 5, addClockTimes(729, 10 * i));
            addStop(0, 0, i, 6, addClockTimes(735, 10 * i));
        }   
    }
    

}
