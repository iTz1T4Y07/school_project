package com.example.myproject;

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
            finish();
        }

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
            JSONObject properties = DataProcess.getUserInfo(GlobalVars.getConnectedId());
            if (properties == null) {
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
                propertiesArray = new ArrayList<Property>();
                try {
                    propertiesArray.add(new Property("שם משתמש", properties.getString(GlobalVars.JsonArg.USERNAME_KEY.getValue())));
                    propertiesArray.add(new Property("שם פרטי", properties.getString(GlobalVars.JsonArg.PRIVATE_NAME_KEY.getValue())));
                    propertiesArray.add(new Property("שם משפחה", properties.getString(GlobalVars.JsonArg.LAST_NAME_KEY.getValue())));
                    propertiesArray.add(new Property("אימייל", properties.getString(GlobalVars.JsonArg.EMAIL_KEY.getValue())));
                    propertiesArray.add(new Property("תאריך לידה", properties.getString(GlobalVars.JsonArg.BIRTHDAY_KEY.getValue())));
                } catch (JSONException e) {
                    Log.e("Exception", e.toString());
                }

                mainLV = (ListView) findViewById(R.id.lv_property);

                //TODO: Call a function that connects to the server and asks for properties
                //TODO: Provide id in the call
                //TODO: returns ArrayList of properties

                /*For testing until server side will be set
                propertiesArray = new ArrayList<Property>();
                propertiesArray.add(new Property("שם פרטי", "Private Name"));
                propertiesArray.add(new Property("שם משפחה", "Last Name"));
                propertiesArray.add(new Property("שם משתמש", "Username"));
                propertiesArray.add(new Property("אימייל", "Email@email.com"));
                propertiesArray.add(new Property("תאריך לידה", "01/01/0000"));
                *///For testing until server side will be set

                propertyLvAdapter = new PropertyLvAdapter(this, 0, 0, propertiesArray);
                mainLV.setAdapter(propertyLvAdapter);
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
            GlobalVars.setConnectedId(-1);
            GlobalVars.setConnectedState(false);
            Toast.makeText(this, "התנתקת מהמשתמש", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}