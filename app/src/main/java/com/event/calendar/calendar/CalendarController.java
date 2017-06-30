package com.event.calendar.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gaurav on 03/05/17.
 */

public class CalendarController extends DatabaseHandler {
    public CalendarController(Context context) {
        super(context);
    }

    public boolean create(ObjectCalendar objectCalendar){
        ContentValues values = new ContentValues();
        values.put("Date",objectCalendar.date);
        values.put("EventName",objectCalendar.eventname);
        values.put("EventDescription",objectCalendar.eventdescription);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("calendar_events",null,values)>0;
        db.close();
        return createSuccessful;
    }

    public int count(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM calendar_events";
        int eventCount = db.rawQuery(sql,null).getCount();
        return eventCount;
    }

    public List<ObjectCalendar> read(){
        List<ObjectCalendar> eventList = new ArrayList<>();
        String sql = "SELECT * FROM calendar_events ORDER BY Id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id")));
                String EventDate = cursor.getString(cursor.getColumnIndex("Date"));
                String EventName = cursor.getString(cursor.getColumnIndex("EventName"));
                String EventDescription = cursor.getString(cursor.getColumnIndex("EventDescription"));

                ObjectCalendar objectCalendar = new ObjectCalendar();
                objectCalendar.id=id;
                objectCalendar.date = EventDate;
                objectCalendar.eventname = EventName;
                objectCalendar.eventdescription = EventDescription;

                eventList.add(objectCalendar);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return eventList;
    }

    public List<ObjectCalendar> readEvent(){
        List<ObjectCalendar> eventDate = new ArrayList<>();

        String sql = "SELECT Date FROM calendar_events ORDER BY Date asc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do {
                String compareDate = cursor.getString(cursor.getColumnIndex("Date"));

                ObjectCalendar obj = new ObjectCalendar();
                obj.compareDate = compareDate;
                eventDate.add(obj);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return eventDate;

    }

    public List<ObjectCalendar> displayEvent(String date){
        List<ObjectCalendar> selectedEvent = new ArrayList<>();
        String sql="SELECT * FROM calendar_events WHERE Date="+date;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id")));
                String EventDate = cursor.getString(cursor.getColumnIndex("Date"));
                String EventName = cursor.getString(cursor.getColumnIndex("EventName"));
                String EventDescription = cursor.getString(cursor.getColumnIndex("EventDescription"));

                ObjectCalendar objectCalendar = new ObjectCalendar();
                objectCalendar.id = id;
                objectCalendar.date = EventDate;
                objectCalendar.eventname = EventName;
                objectCalendar.eventdescription = EventDescription;

                selectedEvent.add(objectCalendar);


            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return selectedEvent;
    }

}
