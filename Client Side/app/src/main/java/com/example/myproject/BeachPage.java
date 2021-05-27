package com.example.myproject;

/**
 * Screen of a beach, shows the following days in the beach
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */


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
            // Extract intent extras
            beachName.setText(intent.getExtras().getString("Beach Name"));
            beachId = intent.getExtras().getInt("Id");
        }

        events.setTag(beachId);

        if (DataProcess.checkConnectionAvailability(getApplicationContext()) == false){ // If connection is disabled
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("שגיאת חיבור"); // Sets builder title
            builder.setMessage("אנא בדוק את החיבור לאינטרנט ונסה שוב"); // Sets builder message
            builder.setCancelable(false); // Sets builder cancelable option
            builder.setPositiveButton("נסה שוב", new ConnectionDialogHandler()); // Sets builder positive button and the handler
            builder.setNegativeButton("צא", new ConnectionDialogHandler()); // Sets builder negative button and the handler
            AlertDialog dialog = builder.create(); // Creates dialog
            dialog.setOwnerActivity(this); // Sets dialog owner activity

            dialog.show(); // Shows dialog
        }
        else {
            eventsArray = DataProcess.getEventList(GlobalVars.getConnectedId(), beachId);

            if (eventsArray == null) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("שגיאת חיבור"); // Sets builder title
                builder.setMessage("ניסיון ההתחברות לשרת נכשל, אנא נסה שוב בעוד כמה דקות"); // Sets builder message
                builder.setCancelable(false); // Sets builder cancelable option
                builder.setPositiveButton("נסה שוב", new ConnectionDialogHandler()); // Sets builder positive button and the handler
                builder.setNegativeButton("צא", new ConnectionDialogHandler()); // Sets builder negative button and the handler
                AlertDialog dialog = builder.create(); // Creates dialog
                dialog.setOwnerActivity(this); // Sets dialog owner activity

                dialog.show(); dialog.show(); // Shows dialog
            } else {
                eventLvAdapter = new EventLvAdapter(this, 0, 0, eventsArray); // Creates new adapter
                events.setAdapter(eventLvAdapter); // Initialize list view with adapter
            }
        }

    }

}