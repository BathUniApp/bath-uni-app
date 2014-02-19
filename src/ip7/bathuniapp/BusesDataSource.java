package ip7.bathuniapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BusesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_ROUTE_NUMBER, SQLiteHelper.COLUMN_DAY_NUMBER,
            SQLiteHelper.COLUMN_RUN_NUMBER, SQLiteHelper.COLUMN_STOP_NUMBER,
            SQLiteHelper.COLUMN_TIME };
    
    private String[] routes = { "U18" };
    private String[] days = { "M-F Term" };
    
    // Access a stop with routeno, stopno. So the fourth stop of the first route
    // would be stops[1][4].
    private String[][] stops = { { "Lower Oldfield Park", "Corn Street",
            "High Street", "Bathwick Hill North", "University of Bath",
            "Bathwick Hill South", "City Centre", } };

    // Run on startup
    public BusesDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Create a new Bus time element
    // Note: SQLlite only supports int and String.
    public void addStop(int route, int day, int run, int stop, int time) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_ROUTE_NUMBER, route);
        values.put(SQLiteHelper.COLUMN_DAY_NUMBER, day);
        values.put(SQLiteHelper.COLUMN_RUN_NUMBER, run);
        values.put(SQLiteHelper.COLUMN_STOP_NUMBER, stop);
        values.put(SQLiteHelper.COLUMN_TIME, time);
        database.insert(SQLiteHelper.TABLE_TASKS, null, values);
    }
    
    // As the database may be cleared, this function fills
    // in the bus stops.
    public void fillStops(){
        // Fill in the stops for the U18 on a weekday
        for(int i = 0; i < 2; i++) {
            addStop(0, 0, i, 0, addClockTimes(700, 10 * i));
            addStop(0, 0, i, 1, addClockTimes(707, 10 * i));
            addStop(0, 0, i, 2, addClockTimes(711, 10 * i));
            addStop(0, 0, i, 3, addClockTimes(717, 10 * i));
            addStop(0, 0, i, 4, addClockTimes(725, 10 * i));
            addStop(0, 0, i, 5, addClockTimes(729, 10 * i));
            addStop(0, 0, i, 6, addClockTimes(735, 10 * i));
        }   
    }
    
    // Little helper method which adds any number of minutes
    // to a time-int. 
    public int addClockTimes(int start, int toAdd){
        int minutes = (start % 100) + (toAdd % 60);
        int hours = (start / 100) + (toAdd / 60);
        return ((hours + (minutes / 60)) % 24) * 100 + (minutes % 60);
    }
    
    public ArrayList<ArrayList<Integer>> getTimes(int route, int day) {
        ArrayList<ArrayList<Integer>> times = new ArrayList<ArrayList<Integer>>();
        
        // Set up a new arraylist of integers for each stop
        for(int i = 0; i < stops[route].length; i++) {
            times.add(new ArrayList<Integer>());
        }
        
        // Do a database query for the given route and day
        Cursor cursor = database.query(SQLiteHelper.TABLE_TASKS, new String[] {
                SQLiteHelper.COLUMN_STOP_NUMBER, SQLiteHelper.COLUMN_TIME },
                SQLiteHelper.COLUMN_ROUTE_NUMBER + "=? AND "
                        + SQLiteHelper.COLUMN_DAY_NUMBER + "=?", new String[] {
                        Integer.toString(route), Integer.toString(day) }, null,
                null, SQLiteHelper.COLUMN_TIME);

        cursor.moveToFirst();
        
        // Loop through the results adding them to the arraylist
        while (!cursor.isAfterLast()) {
            times.get(cursor.getInt(0)).add(cursor.getInt(1));
            cursor.moveToNext();
        }
        
        // Make sure to close the cursor
        cursor.close();
        return times;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<Task>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_TASKS, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return tasks;
    }

    // Takes a cursor and returns the task at that position
    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
        return task;
    }
}