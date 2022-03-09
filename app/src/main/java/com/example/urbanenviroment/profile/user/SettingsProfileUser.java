package com.example.urbanenviroment.profile.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.HelpActivity;
import com.example.urbanenviroment.HomeActivity;
import com.example.urbanenviroment.MapActivity;
import com.example.urbanenviroment.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.profile.user.settings.SettingNotificationsUser;
import com.example.urbanenviroment.profile.user.settings.SettingOther;
import com.example.urbanenviroment.profile.user.settings.SettingProfileUser;

public class SettingsProfileUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);
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

    public void setting_notification(View view) {
        Intent intent = new Intent(this, SettingNotificationsUser.class);
        startActivity(intent);
    }

    public void setting_user(View view) {
        Intent intent = new Intent(this, SettingProfileUser.class);
        startActivity(intent);
    }

    public void setting_other(View view) {
        Intent intent = new Intent(this, SettingOther.class);
        startActivity(intent);
    }

    public void delete_profile(View view) {
        Toast.makeText(getApplicationContext(),
                "Ты все удалил :(", Toast.LENGTH_SHORT).show();
    }

}