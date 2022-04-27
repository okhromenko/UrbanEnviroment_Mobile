package com.example.urbanenviroment.page.profile.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.settings.SettingNotificationsUser;
import com.example.urbanenviroment.page.profile.settings.SettingOther;
import com.example.urbanenviroment.page.profile.settings.SettingProfileUser;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

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
        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query_favorite_ads = ParseQuery.getQuery("FavoriteAds");
        query_favorite_ads.whereEqualTo("user_id", parseUser);
        query_favorite_ads.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException ex) {
                if (ex == null) {
                    for (ParseObject j : objects) {
                        j.deleteInBackground();
                    }

                    ParseQuery<ParseObject> query_favorite_animal = ParseQuery.getQuery("FavoriteAnimal");
                    query_favorite_animal.whereEqualTo("user_id", parseUser);
                    query_favorite_animal.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> objects, ParseException ex) {
                            if (ex == null) {
                                for (ParseObject i : objects) {
                                    i.deleteInBackground();
                                }

                                ParseQuery<ParseObject> query_favorite_org = ParseQuery.getQuery("FavoriteOrganization");
                                query_favorite_org.whereEqualTo("user_id", parseUser);
                                query_favorite_org.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> objects, ParseException ex) {
                                        if (ex == null) {
                                            for (ParseObject k : objects) {
                                                k.deleteInBackground();
                                            }

                                            parseUser.deleteInBackground(new DeleteCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    ParseUser.logOutInBackground();

                                                    Intent intent = new Intent(SettingsProfileUser.this, HomeActivity.class);
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