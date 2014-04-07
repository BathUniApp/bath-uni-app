package ip7.bathuniapp;

import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

public class BusesFragment extends Fragment {

    private static Map<String, BusRoute> allRoutes = new HashMap<String, BusRoute>();

    private Spinner routeSpinner;
    private Spinner stopSpinner;

    private TextView nextBus1;
    private TextView nextBus2;
    private TextView nextBus3;

    private TableLayout busTable;
    private TableLayout busTimes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_buses, container, false);
        routeSpinner = (Spinner) v.findViewById(R.id.route_spinner);
        stopSpinner = (Spinner) v.findViewById(R.id.busstop_spinner);

        nextBus1 = (TextView) v.findViewById(R.id.time1);
        nextBus2 = (TextView) v.findViewById(R.id.time2);
        nextBus3 = (TextView) v.findViewById(R.id.time3);

        busTable = (TableLayout) v.findViewById(R.id.times_table);
        busTimes = (TableLayout) v.findViewById(R.id.times);

        routeSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int pos, long id) {

                        // Update department spinner from appropriate array
                        if (parent.getItemAtPosition(pos).equals("U18")) {
                            setSpinnerContent(view, stopSpinner,
                                    R.array.stops_U18);
                        } else if (parent.getItemAtPosition(pos).equals("18")) {
                            setSpinnerContent(view, stopSpinner,
                                    R.array.stops_18);
                        } else if (parent.getItemAtPosition(pos).equals("X18")) {
                            setSpinnerContent(view, stopSpinner,
                                    R.array.stops_X18);
                        } else if (parent.getItemAtPosition(pos).equals("U10")) {
                            setSpinnerContent(view, stopSpinner,
                                    R.array.stops_U10);
                        } else if (parent.getItemAtPosition(pos).equals("10")) {
                            setSpinnerContent(view, stopSpinner,
                                    R.array.stops_10);
                        } else {
                            setSpinnerContent(view, stopSpinner,
                                    R.array.empty_array);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Interface callback
                    }
                });

        stopSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int pos, long id) {

                        int currentTime = getCurrentTime();
                        String busStop = parent.getItemAtPosition(pos)
                                .toString();
                        // TODO make sure this gets all the information it
                        // needs, not just the one spinner
                        // TODO What happens if there are no other times? Check.
                        Calendar calendar = GregorianCalendar.getInstance();
                        calendar.setTimeZone(TimeZone.getTimeZone("Etc/GMT-1"));

                        int nextTime1 = allRoutes.get("U18MTF").getNextTime(
                                busStop, currentTime);
                        int nextTime2 = allRoutes.get("U18MTF").getNextTime(
                                busStop, nextTime1 + 1);
                        int nextTime3 = allRoutes.get("U18MTF").getNextTime(
                                busStop, nextTime2 + 1);

                        nextBus1.setText(timeToString(nextTime1));
                        nextBus2.setText(timeToString(nextTime2));
                        nextBus3.setText(timeToString(nextTime3));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Interface callback
                    }
                });

        SharedPreferences settings = this.getActivity().getPreferences(0);
        routeSpinner.setSelection(settings.getInt("bus", 0));

        fillBusRoutes();
        
        

        
        
        // Example code for accessing all bus times for a stop. ***
        // This seem to get ALL the times, not just the times from one
        // bus stop *** unless the bus does come every 5 min, all day?
//        ArrayList<Integer> returnedTimes = allRoutes.get("U18MTF").getAllTimes(
//                "University of Bath");
//        ArrayList<String> busTimesArray = new ArrayList<String>();
//        for (Integer time : returnedTimes) {
//            busTimesArray.add(timeToString(time));
//        }
        
        ArrayList<String> busStops = allRoutes.get("U18MTF").getAllStops();
        
        for(int i = 0; i < busStops.size(); i++) {
            String stop = busStops.get(i);
            fillRow(stop, busTable, busTimes, allRoutes.get("U18MTF").getAllTimes(stop), i);
        }
        
        return v;

    }

    public void fillRow(String stopName, TableLayout table, TableLayout time,
            ArrayList<Integer> timesArray, int rowCounter) {

        TableRow busStopRow = new TableRow(this.getActivity());
        TableRow.LayoutParams busStopParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        busStopRow.setLayoutParams(busStopParams);

        TableRow busTimesRow = new TableRow(this.getActivity());
        TableRow.LayoutParams busTimesParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 2.0f);
        busTimesRow.setLayoutParams(busTimesParams);

        TextView stop = new TextView(this.getActivity());
        stop.setText(stopName);
        stop.setPadding(10, 5, 10, 5);
        busStopRow.addView(stop);
        table.addView(busStopRow);

        if (rowCounter % 2 == 0) {
            busStopRow.setBackgroundResource(R.color.list_background);
            busTimesRow.setBackgroundResource(R.color.list_background);
        }

        for (int iTime : timesArray) {
            TextView times = new TextView(getActivity());
            times.setText(timeToString(iTime));
            times.setPadding(10, 5, 10, 5);
            busTimesRow.addView(times);
        }

        time.addView(busTimesRow);
    }
    
    // Populates a spinner with the contents of the given array
    private void setSpinnerContent(View view, Spinner spinner, int textArrayId) {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getActivity(), textArrayId,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
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

        for (String stopName : offset.keySet()) {
            int j = offset.get(stopName);
            ArrayList<Integer> times = new ArrayList<Integer>();
            times.add(420 + j);
            times.add(430 + j);
            times.add(440 + j);
            for (int i = 0; i < 157; i++) {
                times.add(445 + i * 5 + j);
            }
            for (int i = 0; i < 18; i++) {
                times.add(1240 + i * 20 + j);
            }
            myRoute.addStop(stopName, times);
            allRoutes.put("U18MTF", myRoute);
        }

        // BusRoute myRoute2 = new BusRoute("U18", "SAT");
        // for (String stopName : offset.keySet()) {
        // int j = offset.get(stopName);
        // ArrayList<Integer> times = new ArrayList<Integer>();
        //
        // for (int i = 0; i < 40; i++) {
        // times.add(465 + i * 20 + j);
        // }
        // times.add(1300 + j);
        // times.add(1315 + j);
        // for (int i = 0; i < 18; i++) {
        // times.add(1315 + i * 20 + j);
        // }
        // myRoute2.addStop(stopName, times);
        // allRoutes.put("U18SAT", myRoute2);
        // }
        //
        // BusRoute myRoute3 = new BusRoute("U18", "SUN");
        // for (String stopName : offset.keySet()) {
        // int j = offset.get(stopName);
        // ArrayList<Integer> times = new ArrayList<Integer>();
        //
        // for (int i = 0; i < 40; i++) {
        // times.add(525 + i * 20 + j);
        // }
        // times.add(1300 + j);
        // times.add(1315 + j);
        // for (int i = 0; i < 9; i++) {
        // times.add(1315 + i * 20 + j);
        // }
        // myRoute3.addStop(stopName, times);
        // allRoutes.put("U18SUN", myRoute3);
        // }
        //
        // BusRoute xroute1 = new BusRoute("X18", "MTF");
        //
        // Map<String, Integer> offset3 = new HashMap<String, Integer>();
        // offset3.put("Lower Oldfield Park", 0);
        // offset3.put("Bathwick Hill", 13);
        // offset3.put("University Of Bath", 18);
        //
        // for (String stopName : offset3.keySet()) {
        // int j = offset3.get(stopName);
        // ArrayList<Integer> times = new ArrayList<Integer>();
        //
        // for (int i = 0; i < 5; i++) {
        // times.add(460 + i * 15 + j);
        // }
        // for (int i = 0; i < 28; i++) {
        // times.add(555 + i * 20 + j);
        // }
        // xroute1.addStop(stopName, times);
        // allRoutes.put("X18MTF", xroute1);
        // }
        //
        // BusRoute nextRoute = new BusRoute("18", "MTF");
        // Map<String, Integer> offset2 = new HashMap<String, Integer>();
        // offset2.put("University Of Bath", 0);
        // offset2.put("Dorchester Street (1)", 13);
        // offset2.put("Oldfield Park", 24);
        // offset2.put("Dorchester Street (2)", 32);
        // offset2.put("University of Bath", 50);
        //
        // for (String stopName : offset2.keySet()) {
        // int j = offset2.get(stopName);
        // ArrayList<Integer> times = new ArrayList<Integer>();
        // // Very approximate times
        //
        // for (int i = 0; i < 140; i++) {
        // times.add(409 + i * 6 + j);
        // }
        // for (int i = 0; i < 31; i++) {
        // times.add(1170 + i * 15 + j);
        // }
        // nextRoute.addStop(stopName, times);
        // allRoutes.put("18MTF", nextRoute);
        // }
        //
        // BusRoute Route218 = new BusRoute("18", "SAT");
        //
        // for (String stopName : offset2.keySet()) {
        // int j = offset2.get(stopName);
        // ArrayList<Integer> times = new ArrayList<Integer>();
        // // Very approximate times
        //
        // for (int i = 0; i < 61; i++) {
        // times.add(240 + i * 20 + j);
        // }
        // Route218.addStop(stopName, times);
        // allRoutes.put("18SAT", Route218);
        // }
        //
        // BusRoute Route318 = new BusRoute("18", "SUN");
        //
        // for (String stopName : offset2.keySet()) {
        // int j = offset2.get(stopName);
        // ArrayList<Integer> times = new ArrayList<Integer>();
        // // Very approximate times
        //
        // for (int i = 0; i < 30; i++) {
        // times.add(310 + i * 30 + j);
        // }
        // Route318.addStop(stopName, times);
        // allRoutes.put("18SUN", Route318);
        // }
        //
        // BusRoute Route10 = new BusRoute("U10", "MTF");
        //
        // Map<String, Integer> offset4 = new HashMap<String, Integer>(); // 35
        // offset4.put("Kingsway Post Office", 0);
        // offset4.put("Moorland Road", 7);
        // offset4.put("Lower Oldfield Park", 12);
        // offset4.put("St Marys Church", 22);
        // offset4.put("Youth Hostel", 27);
        // offset4.put("University of Bath", 33);
        //
        // for (String stopName : offset4.keySet()) {
        // int j = offset4.get(stopName);
        // ArrayList<Integer> times = new ArrayList<Integer>();
        // // Very approximate times
        //
        // for (int i = 0; i < 8; i++) {
        // times.add(455 + i * 30 + j);
        // }
        // times.add(670 + j);
        //
        // for (int i = 0; i < 9; i++) {
        // times.add(702 + i * 30 + j);
        // }
        //
        // times.add(944 + j);
        // times.add(983 + j);
        // times.add(1018 + j);
        // times.add(1058 + j);
        // times.add(1098 + j);
        // times.add(1138 + j);
        // times.add(1182 + j);
        // times.add(1242 + j);
        // times.add(1302 + j);
        // times.add(1362 + j);
        //
        // Route10.addStop(stopName, times);
        // allRoutes.put("U10MTF", Route10);
        // }

    }

    public String timeToString(int time) {
        return String.format("%02d", time / 60) + ":"
                + String.format("%02d", time % 60);
    }

    public int getCurrentTime() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Etc/GMT-1"));
        return calendar.get(Calendar.HOUR_OF_DAY) * 60
                + calendar.get(Calendar.MINUTE);
    }

}
