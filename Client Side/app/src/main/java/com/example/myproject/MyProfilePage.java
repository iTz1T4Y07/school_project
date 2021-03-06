package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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

        if(GlobalVars.getConnectedState() == false) {
            setResult(RESULT_CANCELED);
            finish();
        }

        mainLV = (ListView)findViewById(R.id.lv_property);

        //TODO: Call a function that connects to the server and asks for properties
        //TODO: Provide id in the call
        //TODO: returns ArrayList of properties

        //For testing until server side will be set
        propertiesArray = new ArrayList<Property>();
        propertiesArray.add(new Property("שם פרטי", "Private Name"));
        propertiesArray.add(new Property("שם משפחה", "Last Name"));
        propertiesArray.add(new Property("שם משתמש", "Username"));
        propertiesArray.add(new Property("אימייל", "Email@email.com"));
        propertiesArray.add(new Property("תאריך לידה", "01/01/0000"));
        //For testing until server side will be set

        propertyLvAdapter = new PropertyLvAdapter(this, 0, 0, propertiesArray);
        mainLV.setAdapter(propertyLvAdapter);

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