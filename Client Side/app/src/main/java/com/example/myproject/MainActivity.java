package com.example.myproject;

/**
 * Main screen
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Helpers.BeachLvAdapter;
import Helpers.ConnectionDialogHandler;
import Helpers.DataProcess;
import Helpers.GlobalVars;
import Objects.Beach;

public class MainActivity extends AppCompatActivity {


    BeachLvAdapter beachLvAdapter; //ListView Adapter
    ArrayList<Beach> beachesArray; //Array for ListView that contains all objects;
    ListView mainLV; //ListView of all beaches

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("");

        mainLV = (ListView)findViewById(R.id.lv_beach);

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
            beachesArray = DataProcess.getBeachList();


            if (beachesArray == null) {
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


                beachLvAdapter = new BeachLvAdapter(this, 0, 0, beachesArray); // Creates new adapter
                mainLV.setAdapter(beachLvAdapter); // Initialize list view with adapter

                mainLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, BeachPage.class); // Creates intent
                        intent.putExtra("Beach Name", beachLvAdapter.getItem(i).getName()); //Sends the name of the selected beach
                        intent.putExtra("Id", beachLvAdapter.getItem(i).getId()); //Sends the id of the beach selected
                        startActivity(intent); // Start activity using intent
                    }
                });
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_my_profile) {
            if (GlobalVars.getConnectedState() == false) { //Guest
                Intent intent = new Intent(MainActivity.this, GuestPage.class); // Creates intent
                startActivityForResult(intent, GlobalVars.GUEST_PAGE_REQUEST_CODE); // Start activity for result using intent
            } else { //Connected User
                Intent intent = new Intent(MainActivity.this, MyProfilePage.class); // Creates intent
                startActivity(intent); // Start activity using intent
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GlobalVars.GUEST_PAGE_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Intent intent = new Intent(MainActivity.this, MyProfilePage.class); // Creates intent
                startActivity(intent); // Start activity using intent
            }
        }
    }


}