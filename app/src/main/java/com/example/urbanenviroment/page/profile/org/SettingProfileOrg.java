package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.settings.SettingNotificationsUser;
import com.example.urbanenviroment.page.profile.settings.SettingOther;
import com.example.urbanenviroment.page.profile.settings.SettingPageOrg;
import com.example.urbanenviroment.page.profile.settings.SettingProfileUser;
import com.example.urbanenviroment.page.profile.user.SettingsProfileUser;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SettingProfileOrg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile_org);
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

    public void setting_page(View view) {
        Intent intent = new Intent(this, SettingPageOrg.class);
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
        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query_ads = ParseQuery.getQuery("Ads");
        query_ads.whereEqualTo("id_user", parseUser);
        query_ads.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject i : objects) {
                        i.deleteInBackground();
                    }

                    ParseQuery<ParseObject> query_animal = ParseQuery.getQuery("Animals");
                    query_animal.whereEqualTo("id_user", parseUser);
                    query_animal.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                for (ParseObject k : objects) {
                                    k.deleteInBackground();
                                }

                                ParseQuery<ParseObject> query_collection = ParseQuery.getQuery("Collection");
                                query_collection.whereEqualTo("id_user", parseUser);
                                query_collection.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (e == null) {
                                            for (ParseObject f : objects) {
                                                f.deleteInBackground();
                                            }

                                            ParseQuery<ParseObject> query_org = ParseQuery.getQuery("Organization");
                                            query_org.whereEqualTo("id_user", parseUser);
                                            query_org.findInBackground(new FindCallback<ParseObject>() {
                                                public void done(List<ParseObject> objects, ParseException e) {
                                                    if (e == null) {
                                                        for (ParseObject p : objects) {
                                                            p.deleteInBackground();
                                                        }

                                                        parseUser.deleteInBackground(new DeleteCallback() {
                                                            @Override
                                                            public void done(ParseException e) {
                                                                ParseUser.logOutInBackground();

                                                                Intent intent = new Intent(SettingProfileOrg.this, HomeActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
    }

}