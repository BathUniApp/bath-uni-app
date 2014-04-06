package ip7.bathuniapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ToDoListFragment extends ListFragment implements OnClickListener {
    // Database of Tasks
    private TasksDataSource datasource;
    private TaskAdapter adapter;

    // Code to run on startup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_todolist, container, false);
        // ListView listView = (ListView) v.findViewById(android.R.id.list);

        // Get the datasource for Tasks
        datasource = new TasksDataSource(this.getActivity());
        datasource.open();

        // Return a list of tasks saved in the database
        ArrayList<Task> tasks = datasource.getAllTasks();
        
        // TESTING
        for(Task task: tasks) {
            System.out.println(task);
        }

        // Use the SimpleCursorAdapter to show the elements in a ListView
        adapter = new TaskAdapter(this.getActivity(), R.layout.todo_row, tasks);
        setListAdapter(adapter);

        // Make a button press call onClick in this class instead of
        // MainActivity
        Button addTask = (Button) v.findViewById(R.id.addTask);
        addTask.setOnClickListener(this);
        Button removeTask = (Button) v.findViewById(R.id.removeTask);
        removeTask.setOnClickListener(this);

        // add options menu to actionbar
        // setHasOptionsMenu(true);
        return v;
    }

    // Handle the user clicking a button
    public void onClick(View view) {
        Task task = null;
        switch (view.getId()) {
        case R.id.addTask:
            // This is just a list of random names for the Task for testing,
            // Obviously this
            // will eventually come from what the user types in.
            String[] tasks = new String[] { "Task Test 1", "Task Test 2" };
            int nextInt = new Random().nextInt(1);
            // Save the new comment to the database
            // This obviously needs more values setting, I've defaulted them to
            // 0 or "" for now
            task = datasource.createTask(tasks[nextInt], nextInt, "", 0, false,
                    new Date());
            adapter.add(task);
            break;
        case R.id.removeTask:
            if (adapter.getCount() > 0) {
                task = (Task) adapter.getItem(0);
                datasource.deleteTask(task);
                adapter.remove(task);
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
