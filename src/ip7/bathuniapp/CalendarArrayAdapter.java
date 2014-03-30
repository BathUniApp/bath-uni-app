package ip7.bathuniapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CalendarArrayAdapter<T> extends ArrayAdapter<T> {
    private final Context context;
    private final List<Event> events;
    
    // Generic constructor for custom array adapter
    public CalendarArrayAdapter (Context context, List<Event> events) {
        super(context, R.layout.todo_row);
        this.context = context;
        this.events = events;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate todo_row.xml
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cal_row, parent, false);
        
        // Define how to add information to todo_row.xml
        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
        titleView.setText(events.get(position).getTitle());
        
        TextView locView = (TextView) rowView.findViewById(R.id.item_location);
        locView.setText(events.get(position).getLocation());

        return rowView;
    }
}
