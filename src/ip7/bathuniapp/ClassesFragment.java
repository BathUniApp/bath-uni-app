package ip7.bathuniapp;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.ListFragment;

public class ClassesFragment extends ListFragment {
    private CalendarDataSource datasource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.frag_classes, container, false);

        // Get the datasource for Events
        datasource = new CalendarDataSource(this.getActivity());
        datasource.open();

        // Return a list of Events saved in the database
        List<Event> events = datasource.getAllEvents();

        // Use the SimpleCursorAdapter to show the elements in a ListView
     //   ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this.getActivity(),
      //          android.R.layout.simple_list_item_1, events);
     //   setListAdapter(adapter);
        
        ListView list = (ListView) v.findViewById(android.R.id.list);
        String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
        android.R.layout.simple_list_item_1, days);
        list.setAdapter(adapter);

        return v;
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
