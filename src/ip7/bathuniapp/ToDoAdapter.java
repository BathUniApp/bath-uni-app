package ip7.bathuniapp;

import android.content.ClipData.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.*;

public class ToDoAdapter extends ArrayAdapter<ListItem> {

    public ToDoAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ToDoAdapter(Context context, int resource,
                       List<ListItem> items) {
        super(context, resource, items);
        /* HACKED, NERD */
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.todo_row, null);
        }

        ListItem p = getItem(position);
        if (p != null) {
            TextView tt = (TextView) v.findViewById(R.id.todo_title);
            TextView des = (TextView) v.findViewById(R.id.todo_description);
        }

        return v;
    }
}
