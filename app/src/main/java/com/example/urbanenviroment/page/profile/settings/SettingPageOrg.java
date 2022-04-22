package com.example.urbanenviroment.page.profile.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SettingPageOrg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page_org);

        TextView text_location = (TextView) findViewById(R.id.text_location);
        TextView text_phone = (TextView) findViewById(R.id.text_phone);
        TextView text_description = (TextView) findViewById(R.id.text_description);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query_3 = ParseQuery.getQuery("Organization");

        ParseObject id_user = ParseObject.createWithoutData("_User", parseUser.getObjectId());

        query_3.whereEqualTo("id_user", id_user);
        query_3.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    text_location.setText(object.get("address").toString());
                    text_phone.setText(object.get("phone").toString());
                    text_description.setText(object.get("description").toString());
                }
            }
        });
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

    public void change(TextView text, TextView button, LinearLayout layout){
        text.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }

    public void cancel(TextView text, TextView button, LinearLayout layout){
        text.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
    }

    public void clear(int id){
        TextView textName = (TextView) findViewById(id);
        textName.setText("");
    }

    public void change_location(View view){
        TextView textLocation = (TextView) findViewById(R.id.text_location);
        TextView textchange = (TextView) findViewById(R.id.button_change_location);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_location);

        change(textLocation, textchange, layout);

    }

    public void change_phone(View view){
        TextView textPhone = (TextView) findViewById(R.id.text_phone);
        TextView textchange = (TextView) findViewById(R.id.button_change_phone);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_phone);

        change(textPhone, textchange, layout);
    }

    public void change_description(View view){
        TextView textDescription = (TextView) findViewById(R.id.text_description);
        TextView textchange = (TextView) findViewById(R.id.button_change_description);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_description);

        change(textDescription, textchange, layout);

    }

    public void save_local(View view){
        TextView textName = (TextView) findViewById(R.id.location_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_password_setting_location);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), textPassword.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    ParseQuery<ParseObject> query_3 = ParseQuery.getQuery("Organization");

                    ParseObject id_user = ParseObject.createWithoutData("_User", parseUser.getObjectId());
                    query_3.whereEqualTo("id_user", id_user);
                    query_3.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                object.put("address", textName.getText().toString());
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null) {
                                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(SettingPageOrg.this, SettingPageOrg.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void save_phone(View view){
        TextView textName = (TextView) findViewById(R.id.phone_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_password_setting_phone);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), textPassword.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    ParseQuery<ParseObject> query_3 = ParseQuery.getQuery("Organization");

                    ParseObject id_user = ParseObject.createWithoutData("_User", parseUser.getObjectId());
                    query_3.whereEqualTo("id_user", id_user);
                    query_3.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                object.put("phone", textName.getText().toString());
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null) {
                                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(SettingPageOrg.this, SettingPageOrg.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void save_description(View view){
        TextView textName = (TextView) findViewById(R.id.description_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_description_setting_pass);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), textPassword.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    ParseQuery<ParseObject> query_3 = ParseQuery.getQuery("Organization");

                    ParseObject id_user = ParseObject.createWithoutData("_User", parseUser.getObjectId());
                    query_3.whereEqualTo("id_user", id_user);
                    query_3.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                object.put("description", textName.getText().toString());
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null) {
                                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(SettingPageOrg.this, SettingPageOrg.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel_location(View view){
        TextView textName = (TextView) findViewById(R.id.text_location);
        TextView textchange = (TextView) findViewById(R.id.button_change_location);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_location);

        cancel(textName, textchange, layout);
        clear(R.id.location_change_setting);
        clear(R.id.change_password_setting_location);
    }

    public void cancel_phone(View view){
        TextView textPhone = (TextView) findViewById(R.id.text_phone);
        TextView textchange = (TextView) findViewById(R.id.button_change_phone);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_phone);

        cancel(textPhone, textchange, layout);
        clear(R.id.phone_change_setting);
        clear(R.id.change_password_setting_phone);
    }

    public void cancel_description(View view){
        TextView textDescription = (TextView) findViewById(R.id.text_description);
        TextView textchange = (TextView) findViewById(R.id.button_change_description);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_description);

        cancel(textDescription, textchange, layout);
        clear(R.id.description_change_setting);
        clear(R.id.change_description_setting_pass);
    }

    public void clear_location(View view){
        clear(R.id.location_change_setting);
    }

    public void clear_location_password(View view){
        clear(R.id.change_password_setting_location);
    }

    public void clear_phone(View view){
        clear(R.id.phone_change_setting);
    }

    public void clear_phone_password(View view){
        clear(R.id.change_password_setting_phone);
    }

    public void clear_description(View view){
        clear(R.id.description_change_setting);
    }

    public void clear_description_password(View view){
        clear(R.id.change_description_setting_pass);
    }
}