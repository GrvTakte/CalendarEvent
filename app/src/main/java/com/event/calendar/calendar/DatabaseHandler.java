package com.event.calendar.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gaurav on 03/05/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "events";
    final static int DATABASE_VERSION = 1;

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE calendar_events(Id INTEGER PRIMARY KEY AUTOINCREMENT,Date date,EventName text,EventDescription text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS calendar_events";
        db.execSQL(sql);
        onCreate(db);
    }
}
