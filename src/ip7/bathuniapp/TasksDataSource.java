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

public class TasksDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_TITLE, SQLiteHelper.COLUMN_PRIORITY,
            SQLiteHelper.COLUMN_DESCRIPTION, SQLiteHelper.COLUMN_PARENT_ID,
            SQLiteHelper.COLUMN_COMPLETE, SQLiteHelper.COLUMN_DATE };

    @SuppressLint("SimpleDateFormat")
    private DateFormat df = new SimpleDateFormat("yyyyMMdd");

    // Run on startup
    public TasksDataSource(Context context) {
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
    public Task createTask(String title, int priority, String description,
            int parent_id, Boolean complete, Date date) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, title);
        values.put(SQLiteHelper.COLUMN_PRIORITY, priority);
        values.put(SQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(SQLiteHelper.COLUMN_PARENT_ID, parent_id);
        if (complete) {
            values.put(SQLiteHelper.COLUMN_COMPLETE, 1);
        } else {
            values.put(SQLiteHelper.COLUMN_COMPLETE, 0);
        }
        values.put(SQLiteHelper.COLUMN_DATE, df.format(date));
        long insertId = database.insert(SQLiteHelper.TABLE_TASKS, null, values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_TASKS, allColumns,
                SQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
                null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public void deleteTask(Task task) {
        long id = task.getId();
        System.out.println("Task deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_TASKS, SQLiteHelper.COLUMN_ID
                + " = " + id, null);
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
        task.setTitle(cursor.getString(1));
        task.setPriority(cursor.getInt(2));
        task.setDescription(cursor.getString(3));
        task.setParent_id(cursor.getInt(4));
        int complete = cursor.getInt(5);
        if (complete == 1) {
            task.setComplete(true);
        } else {
            task.setComplete(false);
        }
        try {
            task.setDate(df.parse(cursor.getString(6)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return task;
    }
}