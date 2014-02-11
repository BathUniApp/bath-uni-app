package ip7.bathuniapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class ToDoListFragment extends ListFragment implements OnClickListener {
    // Database of Tasks
    private TasksDataSource datasource;
    
    // ArrayList of ToDos
    private ArrayList<ListItem> todolist = new ArrayList<ListItem>();

    // Code to run on startup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_todolist, container, false);

        // Get the datasource for Tasks
        datasource = new TasksDataSource(this.getActivity());
        datasource.open();

        // Return a list of tasks saved in the database
        List<Task> tasks = datasource.getAllTasks();

        // Use the SimpleCursorAdapter to show the elements in a ListView
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this.getActivity(),
                android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);

        // Make a button press call onClick in this class instead of MainActivity
        Button b = (Button) v.findViewById(R.id.addTask);
        b.setOnClickListener(this);

        return v;
    }
    
    // Handle the user clicking a button
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Task> adapter = (ArrayAdapter<Task>) getListAdapter();
        Task task = null;
        switch (view.getId()) {
        case R.id.addTask:
            // This is just a list of random names for the Task for testing, Obviously this
            // will eventually come from what the user types in.
            String[] tasks = new String[] { "Task Test 1", "Task Test 2",
                    "Task Test 3" };
            int nextInt = new Random().nextInt(3);
            // Save the new comment to the database
            // This obviously needs more values setting, I've defaulted them to 0 or "" for now
            task = datasource.createTask(tasks[nextInt], 0, "", 0, false, new Date());
            adapter.add(task);
            break;
            
        // For when it's useful
        // case R.id.deleteTask:
        // if (getListAdapter().getCount() > 0) {
        // task = (Task) getListAdapter().getItem(0);
        // datasource.deleteComment(task);
        // adapter.remove(task);
        // }
        // break;
            
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

    public void sortByDate()
    /*
     * Currently this uses bubblesort but upon reflection using insertion sort
     * should be hugely more efficient because you should only be inserting one
     * object in at a time normally and thus sorting the new item straight in
     * would be far more productive, but for now bubble sort is simple and
     * quicksort etc is annoying because of the way java works, it isn't really
     * a recursive language
     */
    {
        for (int j = 0; j < todolist.size() - 1; j++) {
            for (int i = 0; i < todolist.size() - 1; i++) {
                GregorianCalendar date1 = getCalendarVariable(todolist.get(i)
                        .getDate());
                GregorianCalendar date2 = getCalendarVariable(todolist.get(
                        i + 1).getDate());
                if (date1.after(date2)) {
                    // swap the two variables
                    ListItem temp = todolist.get(i);
                    todolist.set(i, todolist.get(i + 1));
                    todolist.set(i + 1, temp);
                }
            }
        }
    }

    public void addNewListItem(String t, int p, boolean x, String d, String e) {
        // use other function for adding a child
        todolist.add(new ListItem(t, p, x, d, e, findFreeID(), 0));
    }

    // Add child with given parent
    public void addNewChild(String t, int p, boolean x, String d, String e,
            int pid) {
        // ignore parentID for the time being
        todolist.add(new ListItem(t, p, x, d, e, findFreeID(), pid));
    }

    // Finds the first free ID for your new list item
    public int findFreeID() {
        int d = 1;
        while (true) {
            for (int i = 0; i < todolist.size(); i++) {
                if (todolist.get(i).getID() == d) {
                    d++;
                    break;
                }
            }
            return d;
        }
    }

    // removes the item with the given ID
    public void removeListItem(int i) {
        if (!hasChildren(i)) {
            int x = todolist.size();
            for (int j = 0; j < x; j++) {
                if (todolist.get(j).getID() == i) {
                    todolist.remove(j);
                    break;
                }
            }
            if (x == todolist.size()) {
                // shouldn't happen but just in case
                System.out.println("Failed to remove item");
            }
        } else {
            // For now at least, later will implement removal of children too
            // Also inform the user
            System.out.println("Cannot remove thread with children");
        }

    }

    public boolean hasChildren(int pp) {
        for (int i = 0; i < todolist.size(); i++) {
            if (todolist.get(i).getParentID() == pp) {
                return true;
            }
        }
        return false;
    }

    // Contrary to current above comments, this will remove all the children with
    // a given parentID
    public void removeChildren(int pp) {
        for (int i = 0; i < todolist.size(); i++) {
            if (todolist.get(i).getParentID() == pp) {
                todolist.remove(i);
            }
        }
    }

    public GregorianCalendar getCalendarVariable(String date) {
        String[] datesplit = date.split("/");
        int day = Integer.valueOf(datesplit[0]);
        int month = Integer.valueOf(datesplit[0]);
        int year = Integer.valueOf(datesplit[0]);
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.DAY_OF_MONTH, day);
        // Calendar uses months with Jan being 0 and Dec being 11
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    // mode being day or month, date input being DD/MM/YYYY
    public ArrayList<ListItem> getDateGroup(String mode, String date) {
        ArrayList<ListItem> filtered = new ArrayList<ListItem>();
        String[] datesplit = date.split("/");
        int day = Integer.valueOf(datesplit[0]);
        int month = Integer.valueOf(datesplit[0]);
        int year = Integer.valueOf(datesplit[0]);
        GregorianCalendar cal = getCalendarVariable(date);

        for (int i = 0; i < todolist.size(); i++) {
            ListItem temp = todolist.get(i);
            // You will obtain anything with a parent via another method
            if (temp.getParentID() != 0) {
                continue;
            }

            GregorianCalendar tempdate = temp.getDateClass();

            if (mode.equals("Day")) {
                if (day == tempdate.get(Calendar.DAY_OF_MONTH)
                        && year == tempdate.get(Calendar.YEAR)
                        && month == tempdate.get(Calendar.MONTH)) {
                    filtered.add(temp);
                }
            }
            if (mode.equals("Month")) {
                if (year == tempdate.get(Calendar.YEAR)
                        && month == tempdate.get(Calendar.MONTH)) {
                    filtered.add(temp);
                }
            }

            if (mode.equals("Week")) {
                if (cal.get(Calendar.WEEK_OF_YEAR) == tempdate
                        .get(Calendar.WEEK_OF_YEAR)
                        && year == tempdate.get(Calendar.YEAR)) {
                    filtered.add(temp);
                }
            }

        }

        return filtered;
    }

    public ArrayList<ListItem> getSubTasks(int pid) {
        ArrayList<ListItem> filtered = new ArrayList<ListItem>();
        for (int i = 0; i < todolist.size(); i++) {
            if (todolist.get(i).getParentID() == pid) {
                filtered.add(todolist.get(i));
            }
        }

        return filtered;
    }

    public void changeView(View view) {
        // Do something in response to button
    }

    public void textHasChanged() {
        // implement something maybe onTextChanged()
    }

    public void openAddTask() {
    }

}
