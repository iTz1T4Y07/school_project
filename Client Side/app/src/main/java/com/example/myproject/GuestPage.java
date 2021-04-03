package com.example.myproject;

/**
 * My Profile Page for guest screen, asks to register and login
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Helpers.GlobalVars;

public class GuestPage extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_page);
        getSupportActionBar().setTitle("");

        if(GlobalVars.getConnectedState() == true) {
            setResult(RESULT_CANCELED);
            finish();
        }

        btnLogin = (Button)findViewById(R.id.btn_Login);
        btnRegister = (Button)findViewById(R.id.btn_Register);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin){
            //Moves user to Login Page
            Intent intent = new Intent(GuestPage.this, LoginPage.class); // Creates intent
            startActivityForResult(intent, GlobalVars.LOGIN_PAGE_REQUEST_CODE); // Start activity for result using intent
        }
        else if (view == btnRegister){
            //Moves user to Register Page
            Intent intent = new Intent(GuestPage.this, RegisterPage.class); // Creates intent
            startActivityForResult(intent, GlobalVars.REGISTER_PAGE_REQUEST_CODE); // Start activity for result using intent
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GlobalVars.LOGIN_PAGE_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                setResult(RESULT_OK);
                finish(); // Close activity
            }

            else if (resultCode == GlobalVars.RESULT_REGISTER_LOGIN){
                //Moves user to Register Page
                Intent intent = new Intent(GuestPage.this, RegisterPage.class); // Creates intent
                startActivityForResult(intent, GlobalVars.REGISTER_PAGE_REQUEST_CODE); // Start activity for result using intent
            }
        }

        else if (requestCode == GlobalVars.REGISTER_PAGE_REQUEST_CODE){
            if ((resultCode == RESULT_OK) || (resultCode == GlobalVars.RESULT_REGISTER_LOGIN)){
                //Moves user to Login Page
                Intent intent = new Intent(GuestPage.this, LoginPage.class); // Creates intent
                startActivityForResult(intent, GlobalVars.LOGIN_PAGE_REQUEST_CODE); // Start activity for result using intent
            }
        }
    }
}