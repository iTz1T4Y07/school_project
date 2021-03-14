package com.example.myproject;

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

        if (DataProcess.checkConnectionAvailability(getApplicationContext()) == false){ //If connection is disabled
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            beachesArray = DataProcess.getBeachList();


            if (beachesArray == null) {
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


                beachLvAdapter = new BeachLvAdapter(this, 0, 0, beachesArray);
                mainLV.setAdapter(beachLvAdapter);

                mainLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, BeachPage.class);
                        intent.putExtra("Beach Name", beachLvAdapter.getItem(i).getName()); //Sends the name of the selected beach
                        intent.putExtra("Id", beachLvAdapter.getItem(i).getId()); //Sends the id of the beach selected
                        startActivity(intent);
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
                Intent intent = new Intent(MainActivity.this, GuestPage.class);
                startActivityForResult(intent, GlobalVars.GUEST_PAGE_REQUEST_CODE);
            } else { //Connected User
                Intent intent = new Intent(MainActivity.this, MyProfilePage.class);
                startActivity(intent);
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GlobalVars.GUEST_PAGE_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Intent intent = new Intent(MainActivity.this, MyProfilePage.class);
                startActivity(intent);
            }
        }
    }


}