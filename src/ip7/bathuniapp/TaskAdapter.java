package ip7.bathuniapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/*
 *  Adapt the information stored in the database to be displayed
 *  in the ListView for the To-Do Fragment
 */

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private int layoutResourceID;
    private ArrayList<Task> data;

    // Generic constructor for custom array adapter
    public TaskAdapter(Context context, int layoutResourceID, ArrayList<Task> data) {
        super(context, layoutResourceID, data);
        this.context = context;
        this.layoutResourceID = layoutResourceID;
        this.data = data;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Inflate todo_row.xml
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layoutResourceID, parent, false);

        // Define how to add information to todo_row.xml
        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
        titleView.setText(data.get(position).getTitle());

        TextView dateView = (TextView) rowView.findViewById(R.id.item_date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        String date = sdf.format(data.get(position).getDate());
        dateView.setText(date);

        CheckBox complete = (CheckBox) rowView.findViewById(R.id.item_check);
        
        // Set a listener to change the state of completion
        // in the database when the user checks or un-checks a task
        complete.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                data.get(position).setComplete(isChecked);
            }
        });
        complete.setChecked(data.get(position).getComplete());

        return rowView;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
