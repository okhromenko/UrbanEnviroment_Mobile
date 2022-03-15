package com.example.urbanenviroment.profile.org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.HelpActivity;
import com.example.urbanenviroment.HomeActivity;
import com.example.urbanenviroment.MapActivity;
import com.example.urbanenviroment.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.profile.user.FavoritesProfileUserAnimals;
import com.example.urbanenviroment.profile.user.NotificationsProfileUser;
import com.example.urbanenviroment.profile.user.ProfileActivityUser;
import com.example.urbanenviroment.profile.user.SettingsProfileUser;

public class ProfileActivityOrg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_org);
    }

    public void animals(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void help(View view){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void authorization(View view){
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }

    public void organization(View view){
        Intent intent = new Intent(this, OrganizationsActivity.class);
        startActivity(intent);
    }

    public void map(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void settings(View view){
        Intent intent = new Intent(this, SettingsProfileUser.class);
        startActivity(intent);
    }

    public void NAN(View view){
        Intent intent = new Intent(this, ProfileActivityUser.class);
        startActivity(intent);
    }
}