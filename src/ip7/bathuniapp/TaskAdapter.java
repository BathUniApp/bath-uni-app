package ip7.bathuniapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate todo_row.xml
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layoutResourceID, parent, false);

        // Define how to add information to todo_row.xml
        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
        titleView.setText(data.get(position).getTitle());

        TextView dateView = (TextView) rowView.findViewById(R.id.item_date);
        dateView.setText(data.get(position).getDate().toString());

        CheckBox complete = (CheckBox) rowView.findViewById(R.id.item_check);
        complete.setChecked(data.get(position).getComplete());
//        complete.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                data.get(position).setComplete(complete.isChecked());
//                
//            }
//        });

        return rowView;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
