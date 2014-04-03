package ip7.bathuniapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class TaskArrayAdapter<T> extends ArrayAdapter<T> {
	private final Context context;
	private final List<Task> tasks;
	
	// Generic constructor for custom array adapter
	public TaskArrayAdapter (Context context, List<Task> tasks) {
		super(context, R.layout.todo_row);
		this.context = context;
		this.tasks = tasks;
	}
	
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	// Inflate todo_row.xml
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.todo_row, parent, false);
	    
	    // Define how to add information to todo_row.xml
	    TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
	    titleView.setText(tasks.get(position).getTitle());
	    
	    TextView dateView = (TextView) rowView.findViewById(R.id.item_date);
	    dateView.setText(tasks.get(position).getDate().toString());
	    
	    CheckBox complete = (CheckBox) rowView.findViewById(R.id.item_check);
	    complete.setChecked(tasks.get(position).getComplete());

	    return rowView;
	}
    
    @Override
    public int getCount () {
        return tasks.size ();
    }
}
