package ip7.bathuniapp;

import java.util.Date;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.v4.app.ListFragment;

public class ClassesFragment extends ListFragment {
    private CalendarDataSource datasource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_classes, container, false);

        // Get the datasource for Events
        datasource = new CalendarDataSource(this.getActivity());
        datasource.open();

        // Return a list of Events saved in the database
        List<Event> events = datasource.getAllEvents();

        CalendarArrayAdapter<Event> adapter = new CalendarArrayAdapter<Event>(
                this.getActivity(), events);
        setListAdapter(adapter);

        addEvent(v);
        addEvent(v);
        addEvent(v);

        return v;
    }

    // Handle the user clicking a button
    public void addEvent(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Event> adapter = (ArrayAdapter<Event>) getListAdapter();

        Event event = null;
        String[] events = new String[] { "Event Test 1", "Event Test 2" };
        int nextInt = new Random().nextInt(1);
        // Save the new comment to the database
        // This obviously needs more values setting, I've defaulted them to 0 or
        // "" for now
        event = datasource.createEvent(events[nextInt], "TestDesc",
                "TestLocation", "TestCourse", new Date(), new Date());
        System.err.println(event); // TEST
        System.err.println(adapter); // TEST
        adapter.add(event);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        datasource.close();
        super.onPause();
    }
}
