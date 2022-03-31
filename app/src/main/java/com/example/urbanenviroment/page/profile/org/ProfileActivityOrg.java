package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;

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
        Intent intent = new Intent(this, SettingProfileOrg.class);
        startActivity(intent);
    }

    public void NAN(View view){
        Intent intent = new Intent(this, ProfileActivityUser.class);
        startActivity(intent);
    }

    public void add_animal_photo_org(View view) {
        Intent intent = new Intent(this, AddPhoto.class);
        startActivity(intent);
    }

    public void delete_photo_animal(View view) {
        Intent intent = new Intent(this, DeletePhoto.class);
        startActivity(intent);
    }

    public void add_animal_description(View view) {
        Intent intent = new Intent(this, AddAnimal.class);
        startActivity(intent);
    }

    public void edit_animal(View view) {
        Intent intent = new Intent(this, EditAnimal.class);
        startActivity(intent);
    }

}