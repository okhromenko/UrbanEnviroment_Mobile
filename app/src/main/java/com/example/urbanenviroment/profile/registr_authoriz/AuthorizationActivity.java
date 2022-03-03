package com.example.urbanenviroment.profile.registr_authoriz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.HelpActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.profile.ProfileActivity;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
    }

    public void registration(View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void goIn(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}