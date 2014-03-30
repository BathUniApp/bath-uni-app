package ip7.bathuniapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PARENT_ID = "parent_id";
    public static final String COLUMN_COMPLETE = "complete";
    public static final String COLUMN_DATE = "date";
    
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_COURSE = "course";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_END = "end";

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 9;

    // Database creation sql statement
    private static final String TASK_DATABASE_CREATE = "create table " + TABLE_TASKS
            + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, " 
            + COLUMN_PRIORITY + " int, "
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_PARENT_ID + " int, "
            + COLUMN_COMPLETE + " int, "
            + COLUMN_DATE + " int);";
    
    private static final String EVENT_DATABASE_CREATE = "create table " + TABLE_EVENTS
            + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, " 
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_LOCATION + " text, "
            + COLUMN_COURSE + " text, "
            + COLUMN_START + " text, "
            + COLUMN_END + " text);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TASK_DATABASE_CREATE);
        database.execSQL(EVENT_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

}