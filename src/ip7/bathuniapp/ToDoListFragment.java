package ip7.bathuniapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/*
 *  This fragment allows the user to input tasks,
 *  along with checking them off as completed,
 *  and deleting completed tasks.
 */
public class ToDoListFragment extends ListFragment implements OnClickListener {
    // Database of Tasks
    private TasksDataSource datasource;
    private TaskAdapter adapter;

    private TextView title;
    private TextView date;
    private TextView desc;

    // Code to run on startup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_todolist, container, false);

        // Get the datasource for Tasks
        datasource = new TasksDataSource(this.getActivity());
        datasource.open();

        // Return a list of tasks saved in the database
        ArrayList<Task> tasks = datasource.getAllTasks();

        // Use the SimpleCursorAdapter to show the elements in a ListView
        adapter = new TaskAdapter(this.getActivity(), R.layout.todo_row, tasks);
        setListAdapter(adapter);

        // Find the TextViews for inputting task information
        title = (TextView) v.findViewById(R.id.item1);
        date = (TextView) v.findViewById(R.id.item1Date);
        desc = (TextView) v.findViewById(R.id.item1Des);

        // Make buttonpresses call onClick in this class
        Button addTask = (Button) v.findViewById(R.id.addTask);
        addTask.setOnClickListener(this);
        Button removeTask = (Button) v.findViewById(R.id.removeTask);
        removeTask.setOnClickListener(this);

        return v;
    }

    // Handle the user clicking a button
    @SuppressLint("SimpleDateFormat")
    public void onClick(View view) {
        Task task = null;
        switch (view.getId()) {
        case R.id.addTask:
            // If the date isn't input in the correct format, we just ignore it.
            try {
                task = datasource.createTask(
                        title.getText().toString(),
                        0,
                        desc.getText().toString(),
                        0,
                        false,
                        new SimpleDateFormat("dd/MM/yyyy").parse(date.getText()
                                .toString() + "/2014"));

            } catch (ParseException e) {
                task = datasource.createTask(title.getText().toString(), 0,
                        desc.getText().toString(), 0, false, new Date());
            }
            // Clear the text fields ready for the next task
            title.setText("");
            desc.setText("");
            date.setText("");
            adapter.add(task);
            break;
        case R.id.removeTask:
            // Loop through all tasks, removing any which are completed
            if (adapter.getCount() > 0) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    task = (Task) adapter.getItem(i);
                    if (task.getComplete() == true) {
                        datasource.deleteTask(task);
                        adapter.remove(task);
                    }
                }
            }
            break;
        }
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
