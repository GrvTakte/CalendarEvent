package com.event.calendar.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText eventDate, eventTitle, eventDescription;
    CaldroidFragment caldroidFragment;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = (Button) findViewById(R.id.add_event);
        Button clear = (Button) findViewById(R.id.clear_event);
        eventDate = (EditText) findViewById(R.id.event_date);
        eventTitle = (EditText) findViewById(R.id.event_title);
        eventDescription = (EditText) findViewById(R.id.event_desription);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDate.setText("");
                eventTitle.setText("");
                eventDescription.setText("");
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateString = eventDate.getText().toString().trim();
                String nameString = eventTitle.getText().toString().trim();
                String descString = eventDescription.getText().toString().trim();


                if (dateString.isEmpty() || dateString.length() == 0 || dateString.equals("") || dateString == null) {
                    if (nameString.isEmpty() || nameString.length() == 0 || nameString.equals("") || nameString == null) {
                        if (descString.isEmpty() || descString.length() == 0 || descString.equals("") || descString == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Please fill all the required information");
                            builder.setCancelable(true);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                } else {
                    String event_date = eventDate.getText().toString();
                    String event_title = eventTitle.getText().toString();
                    String event_description = eventDescription.getText().toString();

                    ObjectCalendar objectCalendar = new ObjectCalendar();
                    objectCalendar.date = event_date;
                    objectCalendar.eventname = event_title;
                    objectCalendar.eventdescription = event_description;

                    boolean createSuccessful = new CalendarController(getApplicationContext()).create(objectCalendar);

                    if (createSuccessful) {
                        Toast.makeText(getApplicationContext(), "Event Successfully saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to save event", Toast.LENGTH_SHORT).show();
                    }
                }
                compareDate();
                caldroidFragment.refreshView();
            }
        });

        loadCalendar();
        compareDate();

    }

    public void loadCalendar() {
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        //args.putBoolean(caldroidFragment.SQUARE_TEXT_VIEW_CELL,false);
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        final CaldroidListener caldroidListener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                eventDate.setText(sdf.format(date));
              /*  caldroidFragment.refreshView();
                ColorDrawable select = new ColorDrawable(getResources().getColor(R.color.caldroid_gray));
                caldroidFragment.setBackgroundDrawableForDate(select, date);
                caldroidFragment.refreshView();
                compareDate(); */
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                CalendarController controller = new CalendarController(MainActivity.this);
                controller.displayEvent(sdf.format(date));

                AlertDialog.Builder buildSingle = new AlertDialog.Builder(MainActivity.this);
                buildSingle.setIcon(R.mipmap.ic_launcher);
                buildSingle.setTitle("Events List");
                ObjectCalendar objectCalendar = new ObjectCalendar();
               // final List<ObjectCalendar> selectedEvent = new CalendarController(MainActivity.this).displayEvent(date);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Event 1");
                arrayAdapter.add("Event 2");
                arrayAdapter.add("Event 3");
                arrayAdapter.add("Event 4");
                arrayAdapter.add("Event 5");

                buildSingle.setNegativeButton("OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                buildSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
                buildSingle.show();
            }
        };
        caldroidFragment.setCaldroidListener(caldroidListener);
        //CaldroidGridAdapter adapter = new CaldroidGridAdapter(getApplicationContext(),caldroidFragment.getMonth(),caldroidFragment.getYear(),caldroidFragment.getCaldroidData(),caldroidFragment.getExtraData());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent i = new Intent(MainActivity.this, DisplayEventActivity.class);
                startActivity(i);
                break;
            case R.id.call:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:8446992711"));
                startActivity(intent);
                break;
        }
        return(super.onOptionsItemSelected(item));
    }


    public void compareDate(){
        List<ObjectCalendar> calendar = new CalendarController(this).readEvent();
        if(calendar.size() > 0){
            for(ObjectCalendar obj : calendar){
                String comDate = obj.compareDate;
                try{
                        Date date = sdf.parse(comDate);
                        ColorDrawable back = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
                        caldroidFragment.setBackgroundDrawableForDate(back, date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}