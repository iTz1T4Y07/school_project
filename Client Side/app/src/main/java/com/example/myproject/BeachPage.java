package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Helpers.ConnectionDialogHandler;
import Helpers.DataProcess;
import Helpers.EventLvAdapter;
import Helpers.GlobalVars;
import Objects.BeachEvent;

public class BeachPage extends AppCompatActivity {

    TextView beachName;//TextView of the page header
    int beachId; //For further use
    ListView events; //ListView of all events
    EventLvAdapter eventLvAdapter; //ListView adapter
    ArrayList<BeachEvent> eventsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_page);
        getSupportActionBar().setTitle("");

        beachName = (TextView)findViewById(R.id.tv_Beach_Name);
        events = (ListView)findViewById(R.id.lv_events);

        Intent intent = getIntent();
        if (intent.getExtras() == null)
            finish();
        else {
            beachName.setText(intent.getExtras().getString("Beach Name"));
            beachId = intent.getExtras().getInt("Id");
        }

        events.setTag(beachId);

        //TODO: Call a function that connects to the server and asks for beach event
        //TODO: Provide Beach id in the call
        //TODO: returns ArrayList of events
        if (!DataProcess.checkConnectionAvailability(getApplicationContext())){ //If connection is disabled
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("שגיאת חיבור");
            builder.setMessage("אנא בדוק את החיבור לאינטרנט ונסה שוב");
            builder.setCancelable(false);
            builder.setPositiveButton("נסה שוב", new ConnectionDialogHandler());
            builder.setNegativeButton("צא", new ConnectionDialogHandler());
            AlertDialog dialog = builder.create();
            dialog.setOwnerActivity(this);

            dialog.show();
        }
        else {
            eventsArray = DataProcess.getEventList(GlobalVars.getConnectedId(), beachId);

            if (eventsArray == null) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("שגיאת חיבור");
                builder.setMessage("ניסיון ההתחברות לשרת נכשל, אנא נסה שוב בעוד כמה דקות");
                builder.setCancelable(false);
                builder.setPositiveButton("נסה שוב", new ConnectionDialogHandler());
                builder.setNegativeButton("צא", new ConnectionDialogHandler());
                AlertDialog dialog = builder.create();
                dialog.setOwnerActivity(this);

                dialog.show();
            } else {
                eventLvAdapter = new EventLvAdapter(this, 0, 0, eventsArray);
                events.setAdapter(eventLvAdapter);
            }
        }

    }

}