package com.example.urbanenviroment.page.profile.registr_authoriz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;

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
        Intent intent = new Intent(this, ProfileActivityUser.class);
        startActivity(intent);
    }
}