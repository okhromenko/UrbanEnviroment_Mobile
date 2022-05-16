package com.example.urbanenviroment.page.profile.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.ParseUser;

public class SettingOther extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_other);

        FrameLayout frame_org_number = (FrameLayout) findViewById(R.id.frame_org_setting_number);
        FrameLayout frame_org_website = (FrameLayout) findViewById(R.id.frame_org_setting_website);

        ParseUser parseUser = ParseUser.getCurrentUser();

        if ((Boolean) parseUser.get("is_org")) {
            frame_org_number.setVisibility(View.VISIBLE);
            frame_org_website.setVisibility(View.VISIBLE);
        }
        else {
            frame_org_number.setVisibility(View.GONE);
            frame_org_website.setVisibility(View.GONE);
        }
    }

    public void switch_email(View view){
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch_email_other = findViewById(R.id.switch_email_other);

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

}