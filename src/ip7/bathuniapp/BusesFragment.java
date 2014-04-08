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
    private Spinner routeTypeSpinner;
    private Spinner stopSpinner;

    private TextView nextBus1;
    private TextView nextBus2;
    private TextView nextBus3;

    private TableLayout busTable;
    private TableLayout busTimes;

    private String selectedRoute;
    private String selectedRouteType;
 //   private String selectedStop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_buses, container, false);
        routeSpinner = (Spinner) v.findViewById(R.id.route_spinner);
        routeTypeSpinner = (Spinner) v.findViewById(R.id.route_type_spinner);
        stopSpinner = (Spinner) v.findViewById(R.id.busstop_spinner);

        nextBus1 = (TextView) v.findViewById(R.id.time1);
        nextBus2 = (TextView) v.findViewById(R.id.time2);
        nextBus3 = (TextView) v.findViewById(R.id.time3);

        busTable = (TableLayout) v.findViewById(R.id.times_table);
        busTimes = (TableLayout) v.findViewById(R.id.times);

        selectedRoute = "U18";
        selectedRouteType = "MTF";

        fillBusRoutes();

        routeSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int pos, long id) {

                        // Update spinner from appropriate array
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

                        selectedRoute = parent.getItemAtPosition(pos)
                                .toString();

                        // TODO: Change this to draw table function or
                        // something?
                        BusRoute route = allRoutes.get(selectedRouteType
                                + selectedRoute);
                        busTable.removeAllViews();
                        busTimes.removeAllViews();

                        if (route != null) {
                            ArrayList<String> busStops = route.getAllStops();

                            for (int i = 0; i < busStops.size(); i++) {
                                String stop = busStops.get(i);
                                fillRow(stop, busTable, busTimes, allRoutes
                                        .get(selectedRouteType + selectedRoute)
                                        .getAllTimes(stop), i);
                            }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Interface callback
                    }
                });

        routeTypeSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int pos, long id) {

                        // Update spinner from appropriate array
                        if (parent.getItemAtPosition(pos)
                                .equals("Term Weekday")) {
                            selectedRouteType = "MTF";
                        } else if (parent.getItemAtPosition(pos).equals(
                                "Term Weekend")) {
                            selectedRouteType = "SS";
                        } else if (parent.getItemAtPosition(pos).equals(
                                "Term Weekday")) {
                            selectedRouteType = "MTFH";
                        } else if (parent.getItemAtPosition(pos).equals(
                                "Term Weekday")) {
                            selectedRouteType = "SSH";
                        } else {
                            selectedRouteType = "MTF";
                        }

                        BusRoute route = allRoutes.get(selectedRouteType
                                + selectedRoute);
                        busTable.removeAllViews();
                        busTimes.removeAllViews();

                        if (route != null) {
                            ArrayList<String> busStops = route.getAllStops();

                            for (int i = 0; i < busStops.size(); i++) {
                                String stop = busStops.get(i);
                                fillRow(stop, busTable, busTimes, allRoutes
                                        .get(selectedRouteType + selectedRoute)
                                        .getAllTimes(stop), i);
                            }
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
                        
                        // TODO Move -this- to a seperate thing, have it get re-filled out.
                        
                  //      selectedStop = parent.getItemAtPosition(pos).toString();

                        int currentTime = getCurrentTime();
                        String busStop = parent.getItemAtPosition(pos)
                                .toString();
                        // TODO If there are no other times, print something more graceful
                        Calendar calendar = GregorianCalendar.getInstance();
                        calendar.setTimeZone(TimeZone.getTimeZone("Etc/GMT-1"));

                        BusRoute route = allRoutes.get(selectedRouteType
                                + selectedRoute);
                        System.out.println(selectedRouteType + selectedRoute);

                        if (route != null) {
                            int nextTime1 = route.getNextTime(busStop,
                                    currentTime);
                            int nextTime2 = route.getNextTime(busStop,
                                    nextTime1 + 1);
                            int nextTime3 = route.getNextTime(busStop,
                                    nextTime2 + 1);

                            nextBus1.setText(timeToString(nextTime1));
                            nextBus2.setText(timeToString(nextTime2));
                            nextBus3.setText(timeToString(nextTime3));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Interface callback
                    }
                });

        SharedPreferences settings = this.getActivity().getPreferences(0);
        routeSpinner.setSelection(settings.getInt("bus", 0));

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

        // U18 offsets
        Map<String, Integer> u18offset = new HashMap<String, Integer>();
        u18offset.put("Lower Oldfield Park", 0);
        u18offset.put("Corn Street", 7);
        u18offset.put("High Street", 11);
        u18offset.put("Youth Hostel North", 17);
        u18offset.put("University of Bath", 25);
        u18offset.put("Youth Hostel South", 29);
        u18offset.put("North Parade", 35);

        // Weekday U18
        BusRoute u18mtf = new BusRoute("U18", "MTF");
        for (String stopName : u18offset.keySet()) {
            int j = u18offset.get(stopName);
            ArrayList<Integer> times = new ArrayList<Integer>();
            times.add(420 + j);
            times.add(430 + j);
            times.add(440 + j);
            for (int i = 0; i < 157; i++) {
                times.add(448 + i * 5 + j);
            }
            for (int i = 0; i < 18; i++) {
                times.add(1240 + i * 20 + j);
            }
            u18mtf.addStop(stopName, times);
        }
        allRoutes.put("MTFU18", u18mtf);

        // Weekend U18
        BusRoute u18ss = new BusRoute("U18", "SS");
        for (String stopName : u18offset.keySet()) {
            int j = u18offset.get(stopName);
            ArrayList<Integer> times = new ArrayList<Integer>();
            for (int i = 0; i < 81; i++) {
                times.add(448 + i * 20 + j);
            }
            u18ss.addStop(stopName, times);
        }
        allRoutes.put("SSU18", u18ss);

        // X18 Offsets
        Map<String, Integer> x18offset = new HashMap<String, Integer>();
        x18offset.put("Lower Oldfield Park", 0);
        x18offset.put("University of Bath", 5);
        x18offset.put("Bathwick hill", 9);

        // Weekday X18
        BusRoute x18mtf = new BusRoute("X18", "MTF");
        for (String stopName : x18offset.keySet()) {
            int j = x18offset.get(stopName);
            ArrayList<Integer> times = new ArrayList<Integer>();
            for (int i = 0; i < 28; i++) {
                times.add(590 + i * 20 + j);
            }
            x18mtf.addStop(stopName, times);
        }
        allRoutes.put("MTFX18", x18mtf);

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
