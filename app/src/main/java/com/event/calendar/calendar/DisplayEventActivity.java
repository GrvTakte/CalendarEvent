package com.event.calendar.calendar;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.List;


/**
 * Created by gaurav on 25/04/17.
 */


public class DisplayEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_events);
        readEvents();
    }

    public void readEvents(){
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_layout);
        tableLayout.removeAllViews();

        List<ObjectCalendar> calendars = new CalendarController(this).read();

        if (calendars.size() > 0) {

            for (ObjectCalendar obj : calendars) {

                int id = obj.id;
                String date = obj.date;
                String ename = obj.eventname;
                String edescription = obj.eventdescription;

                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                TextView tv2 = new TextView(this);
                //tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tv2.setGravity(Gravity.CENTER);
                tv2.setTextSize(15);
                tv2.setText(String.valueOf(date));
                TableRow.LayoutParams dateParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.2f);
                dateParams.setMargins(7, 5, 5, 5);
                tv2.setLayoutParams(dateParams);
                tv2.setBackgroundResource(R.drawable.cell_shape);
                row.addView(tv2);

                TextView tv3 = new TextView(this);
                TableRow.LayoutParams nameParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.2f);
                nameParams.setMargins(7, 5, 5, 5);
                tv3.setLayoutParams(nameParams);
                tv3.setGravity(Gravity.CENTER);
                tv3.setTextSize(15);
                tv3.setBackgroundResource(R.drawable.cell_shape_one);
                tv3.setText(String.valueOf(ename));
                row.addView(tv3);

                TextView tv4 = new TextView(this);
                TableRow.LayoutParams descParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.2f);
                descParams.setMargins(7, 5, 5, 5);
                tv4.setLayoutParams(descParams);
                tv4.setGravity(Gravity.CENTER);
                tv4.setTextSize(15);
                tv4.setBackgroundResource(R.drawable.cell_shape_two);
                tv4.setText(String.valueOf(edescription));
                row.addView(tv4);

                tableLayout.addView(row);

               /* String textViewContents = date + " _ " + ename + " _ " +edescription;

                TextView textViewStudentItem= new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));

                linearLayoutRecords.addView(textViewStudentItem); */
            }
        }
        else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No events yet.");
            tableLayout.addView(locationItem);
        }
    }
}