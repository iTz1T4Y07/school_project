package com.example.myproject;

/**
 * My profile page for connected user, shows information about the user and an option to log out
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Helpers.ConnectionDialogHandler;
import Helpers.DataProcess;
import Helpers.GlobalVars;
import Helpers.PropertyLvAdapter;
import Objects.Beach;
import Objects.Property;

public class MyProfilePage extends AppCompatActivity {

    PropertyLvAdapter propertyLvAdapter;
    ArrayList<Property> propertiesArray;
    ListView mainLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_page);
        getSupportActionBar().setTitle("");

        if(GlobalVars.getConnectedState() == false) {
            setResult(RESULT_CANCELED);
            finish(); // Close activity
        }

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
            JSONObject properties = DataProcess.getUserInfo(GlobalVars.getConnectedId());
            if (properties == null) {
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
                propertiesArray = new ArrayList<Property>(); // Creates new ArrayList
                try {
                    propertiesArray.add(new Property("שם משתמש", properties.getString(GlobalVars.JsonArg.USERNAME_KEY.getValue()))); // Extracts username from properties
                    propertiesArray.add(new Property("שם פרטי", properties.getString(GlobalVars.JsonArg.PRIVATE_NAME_KEY.getValue()))); // Extracts private name from properties
                    propertiesArray.add(new Property("שם משפחה", properties.getString(GlobalVars.JsonArg.LAST_NAME_KEY.getValue()))); // Extracts last name from properties
                    propertiesArray.add(new Property("אימייל", properties.getString(GlobalVars.JsonArg.EMAIL_KEY.getValue()))); // Extracts email from properties
                    propertiesArray.add(new Property("תאריך לידה", properties.getString(GlobalVars.JsonArg.BIRTHDAY_KEY.getValue()))); // Extracts birthday from properties
                } catch (JSONException e) {
                    Log.e("Exception", e.toString());
                }

                mainLV = (ListView) findViewById(R.id.lv_property);


                propertyLvAdapter = new PropertyLvAdapter(this, 0, 0, propertiesArray); // Creates new adapter
                mainLV.setAdapter(propertyLvAdapter); // Initialize list view with adapter
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout){
            GlobalVars.setConnectedId(-1); // Sets connected id to not connected
            GlobalVars.setConnectedState(false); // Change connected status to false
            Toast.makeText(this, "התנתקת מהמשתמש", Toast.LENGTH_SHORT).show();
            finish(); // Close activity
        }
        return true;
    }
}