package com.example.myproject;

/**
 * Register Page
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Helpers.DataProcess;
import Helpers.GlobalVars;
import Helpers.RegisterAsyncTask;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText etUsername, etPassword, etRePassword, etPName, etLName, etEmail;
    Button btnRegister, btnDate;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().setTitle("");

        if(GlobalVars.getConnectedState() == true) { // If user is connected
            setResult(RESULT_CANCELED);
            finish(); // Close activity
        }

        etUsername = (EditText)findViewById(R.id.et_Register_Username);
        etPassword = (EditText)findViewById(R.id.et_Register_password);
        etRePassword = (EditText)findViewById(R.id.et_Register_repeat_password);
        etPName = (EditText)findViewById(R.id.et_Register_private_name);
        etLName = (EditText)findViewById(R.id.et_Register_last_name);
        etEmail = (EditText)findViewById(R.id.et_Register_email);

        btnDate = (Button)findViewById(R.id.btn_Register_Date);
        btnRegister = (Button)findViewById(R.id.btn_Register);

        btnDate.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.register_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_move_to_login){
            setResult(GlobalVars.RESULT_REGISTER_LOGIN);
            finish(); // Close activity
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == btnDate){
            //Opens date picker dialog, the date must be between 13-100 years ago
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, this, 2000, 0, 1); // Creates date picker dialog
            datePickerDialog.setTitle("Date Of Birth"); // Sets dialog title
            Calendar currentTime = Calendar.getInstance(); // get Current time
            currentTime.add(Calendar.YEAR, -13); // Remove 13 years
            datePickerDialog.getDatePicker().setMaxDate(currentTime.getTimeInMillis()); // Sets dialog max date
            currentTime.add(Calendar.YEAR, -87); // Remove 87 years
            datePickerDialog.getDatePicker().setMinDate(currentTime.getTimeInMillis()); // Sets dialog min date
            datePickerDialog.show(); // Shows dialog
        }

        else if (view == btnRegister){
            if (validation()){

                RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(this);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) // AsyncTask execution changed to serial execution in API 11
                    registerAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, etUsername.getText().toString(), etPassword.getText().toString(), etPName.getText().toString(),
                            etLName.getText().toString(), etEmail.getText().toString(), btnDate.getText().toString());
                else
                    registerAsyncTask.execute(etUsername.getText().toString(), etPassword.getText().toString(), etPName.getText().toString(),
                            etLName.getText().toString(), etEmail.getText().toString(), btnDate.getText().toString());

            }
        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        String dayStr = String.valueOf(day);
        String monthStr = String.valueOf(month+1);
        if (day < 10)
            dayStr = "0" + dayStr;
        if (month < 9)
            monthStr = "0" + monthStr;
        this.btnDate.setText(dayStr + "/" + monthStr + "/" + year); // Change button text
    }

    private boolean validation(){
        /** Checks validation of the information
         * Rises errors on the invalid edit texts
         * If all information is valid it return true, else it returns false
         */
        boolean status = true;
        String email = etEmail.getText().toString();
        if (etUsername.getText().toString().length() == 0) { // If username field is empty
            etUsername.setError("");
            status = false;
        }
        if (etPassword.getText().toString().length() == 0) { // If password field is empty
            etPassword.setError("");
            status = false;
        }
        if (etRePassword.getText().toString().length() == 0) { // If rePassword field is empty
            etRePassword.setError("");
            status = false;
        }
        if (etPName.getText().toString().length() == 0) { // If private name field is empty
            etPName.setError("");
            status = false;
        }
        if (etLName.getText().toString().length() == 0) { // If last name field is empty
            etLName.setError("");
            status = false;
        }
        if (etEmail.getText().toString().length() == 0) { // If email field is empty
            etEmail.setError("");
            status = false;
        }
        if (btnDate.getText().toString().equals("בחר")){ // If date has not been picked
            btnDate.setError("נא לבחור תאריך לידה");
            status = false;
        }
        if (!status) // if any field is empty
            return false;
        if (etUsername.getText().toString().length() < 3) { // if username is less then 3 chars
            etUsername.setError("שם המשתמש חייב להיות באורך של 3 תויים לפחות");
            status = false;
        }
        if (etPassword.getText().toString().length() < 8){ // if password is less then 8 chars
            etPassword.setError("על הסיסמא להיות באורך של 8 תווים לפחות");
            status = false;
        }
        else if (etPassword.getText().toString().equals(etUsername.getText().toString())){ // if password is the same as username
            etPassword.setError("על הסיסמא להיות שונה משם המשתמש");
            status = false;
        }
        else if (!etPassword.getText().toString().equals(etRePassword.getText().toString())){ // if password and rePassword does not match
            etRePassword.setError("הסיסמאות אינן תואמות!");
            status = false;
        }
        if ( // if email is not in the correct format
                (email.indexOf('@') == -1) || ((email.indexOf('.') == -1)) ||
                        (email.charAt(0) == '@') || (email.charAt(0) == '.') ||
                        (email.lastIndexOf('@') != email.indexOf('@')) ||
                        (email.lastIndexOf('.') != email.indexOf('.')) ||
                        (!email.substring(email.length()-4, email.length()).equals(".com") ||
                                (email.contains("@.")))
        ){
            etEmail.setError("על כתובת המייל להיות תקינה!\nכתובת מייל תקינה לדוגמא:\nexample@example.com");
            status = false;
        }

        return status;
    }
}