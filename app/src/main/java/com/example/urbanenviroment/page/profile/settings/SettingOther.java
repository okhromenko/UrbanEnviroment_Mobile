package com.example.urbanenviroment.page.profile.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;

public class SettingOther extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch_email_other, switch_phone_other, switch_website_other;
    ParseUser parseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_other);

        FrameLayout frame_org_number = (FrameLayout) findViewById(R.id.frame_org_setting_number);
        FrameLayout frame_org_website = (FrameLayout) findViewById(R.id.frame_org_setting_website);

        parseUser = ParseUser.getCurrentUser();

        if ((Boolean) parseUser.get("is_org")) {
            frame_org_number.setVisibility(View.VISIBLE);
            frame_org_website.setVisibility(View.VISIBLE);
        }
        else {
            frame_org_number.setVisibility(View.GONE);
            frame_org_website.setVisibility(View.GONE);
        }

        switch_email_other = findViewById(R.id.switch_email_other);
        switch_phone_other = findViewById(R.id.switch_phone_other);
        switch_website_other = findViewById(R.id.switch_website_other);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", parseUser.getObjectId());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    switch_onCreate(switch_email_other, object.getBoolean("hidden_email"));
                    switch_onCreate(switch_phone_other, object.getBoolean("hidden_phone"));
                    switch_onCreate(switch_website_other, object.getBoolean("hidden_website"));
                }
            }
        });

    }

    public void switch_onCreate(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch_other, boolean flag){
        switch_other.setChecked(flag);
    }

    public void save_switch(String hidden_type, boolean flag){
        ParseQuery<ParseObject> query_user = ParseQuery.getQuery("_User");
        query_user.whereEqualTo("objectId", parseUser.getObjectId());
        query_user.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.put(hidden_type, flag);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                Toast.makeText(getApplicationContext(), "Настройки сохранены", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(getApplicationContext(),  "Что-то пошло не так", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public void switch_email(View view){
        save_switch("hidden_email", switch_email_other.isChecked());
    }

    public void switch_phone(View view){
        save_switch("hidden_phone", switch_phone_other.isChecked());
    }

    public void switch_website(View view){
        save_switch("hidden_website", switch_website_other.isChecked());
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