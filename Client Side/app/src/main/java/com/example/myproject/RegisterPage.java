package com.example.myproject;

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

        if(GlobalVars.getConnectedState() == true) {
            setResult(RESULT_CANCELED);
            finish();
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
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == btnDate){
            //Opens date picker dialog, the date must be between 13-100 years ago
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, this, 2000, 0, 1);
            datePickerDialog.setTitle("Date Of Birth");
            Calendar currentTime = Calendar.getInstance();
            currentTime.add(Calendar.YEAR, -13);
            datePickerDialog.getDatePicker().setMaxDate(currentTime.getTimeInMillis());
            currentTime.add(Calendar.YEAR, -100);
            datePickerDialog.getDatePicker().setMinDate(currentTime.getTimeInMillis());
            datePickerDialog.show();
        }

        else if (view == btnRegister){
            if (validation()){

                RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(this);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
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
        this.btnDate.setText(dayStr + "/" + monthStr + "/" + year);
    }

    private boolean validation(){
        //Checks validation of the information
        //Rise errors on the invalid edit texts
        //If all information is valid it return true, else it returns false;
        boolean status = true;
        String email = etEmail.getText().toString();
        if (etUsername.getText().toString().length() < 3) {
            etUsername.setError("שם המשתמש חייב להיות באורך של 3 תויים לפחות");
            status = false;
        }
        if (etPassword.getText().toString().length() < 8){
            etPassword.setError("על הסיסמא להיות באורך של 8 תווים לפחות");
            status = false;
        }
        else if (etPassword.getText().toString().equals(etUsername.getText().toString())){
            etPassword.setError("על הסיסמא להיות שונה משם המשתמש");
            status = false;
        }
        else if (!etPassword.getText().toString().equals(etRePassword.getText().toString())){
            etRePassword.setError("הסיסמאות אינן תואמות!");
            status = false;
        }
        if (
                (email.indexOf('@') == -1) || ((email.indexOf('.') == -1)) ||
                        (email.charAt(0) == '@') || (email.charAt(0) == '.') ||
                        (email.lastIndexOf('@') != email.indexOf('@')) ||
                        (email.lastIndexOf('.') != email.indexOf('.')) ||
                        (!email.substring(email.length()-4, email.length()).equals(".com"))
        ){
            etEmail.setError("על כתובת המייל להיות תקינה!\nכתובת מייל תקינה לדוגמא:\nexample@example.com");
            status = false;
        }
        if (btnDate.getText().toString().equals("בחר")){
            btnDate.setError("נא לבחור תאריך לידה");
            status = false;
        }


        status = true;


        return status;
    }
}