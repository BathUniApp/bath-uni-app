package ip7.bathuniapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CalendarDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_TITLE, SQLiteHelper.COLUMN_DESCRIPTION,
            SQLiteHelper.COLUMN_LOCATION, SQLiteHelper.COLUMN_COURSE,
            SQLiteHelper.COLUMN_START, SQLiteHelper.COLUMN_END };

    @SuppressLint("SimpleDateFormat")
    private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    // Run on startup
    public CalendarDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Create a new task
    // Note: SQLlite only supports int and String.
    public Event createEvent(String title, String description, String location, String course, Date start, Date end) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, title);
        values.put(SQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(SQLiteHelper.COLUMN_LOCATION, location);
        values.put(SQLiteHelper.COLUMN_COURSE, course);
        values.put(SQLiteHelper.COLUMN_START, df.format(start));
        values.put(SQLiteHelper.COLUMN_END, df.format(end));
        long insertId = database.insert(SQLiteHelper.TABLE_EVENTS, null, values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_EVENTS, allColumns,
                SQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
                null);
        cursor.moveToFirst();
        Event newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    public void deleteEvent(Event event) {
        long id = event.getId();
        System.out.println("Event deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_EVENTS, SQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_EVENTS, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return events;
    }

    // Takes a cursor and returns the task at that position
    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setId(cursor.getLong(0));
        event.setTitle(cursor.getString(1));
        event.setDescription(cursor.getString(2));
        event.setLocation(cursor.getString(3));
        event.setCourse(cursor.getString(4));
        try {
            event.setStart(df.parse(cursor.getString(5)));
            event.setEnd(df.parse(cursor.getString(6)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return event;
    }
}