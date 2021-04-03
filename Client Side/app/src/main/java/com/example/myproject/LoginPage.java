package com.example.myproject;

/**
 * Login Page
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Helpers.GlobalVars;
import Helpers.LoginAsyncTask;



public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword;
    Button btnLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().setTitle("");

        if(GlobalVars.getConnectedState() == true) {
            setResult(RESULT_CANCELED);
            finish(); // Close activity
        }

        etUsername = (EditText)findViewById(R.id.et_Login_Username);
        etPassword = (EditText)findViewById(R.id.et_Login_password);
        btnLogin = (Button)findViewById(R.id.btn_Login);

        btnLogin.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_move_to_register){
            setResult(GlobalVars.RESULT_REGISTER_LOGIN);
            finish(); // Close activity
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin){
            LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) // AsyncTask execution changed to serial execution in API 11
                loginAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, etUsername.getText().toString(), etPassword.getText().toString());
            else
                loginAsyncTask.execute(etUsername.getText().toString(), etPassword.getText().toString());
        }
    }

}