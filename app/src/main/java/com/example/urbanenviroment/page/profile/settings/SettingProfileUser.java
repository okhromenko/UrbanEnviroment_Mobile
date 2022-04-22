package com.example.urbanenviroment.page.profile.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.org.AddAnimal;
import com.example.urbanenviroment.page.profile.org.ProfileActivityOrg;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseSession;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SettingProfileUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile_user);

        ParseUser parseUser = ParseUser.getCurrentUser();

        TextView text_user_name = (TextView) findViewById(R.id.text_user_name);
        TextView text_user_email = (TextView) findViewById(R.id.text_user_email);

        ParseQuery<ParseObject> query_3 = ParseQuery.getQuery("_User");
        query_3.whereEqualTo("objectId", parseUser.getObjectId());
        query_3.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    text_user_name.setText(object.get("username").toString());
                    text_user_email.setText(object.get("email").toString());
                }

            }
        });

        ImageView setting_text = (ImageView) findViewById(R.id.imageView6);
        TextView textchangeName = (TextView) findViewById(R.id.text_1);
        MaterialEditText hintchangeName = (MaterialEditText) findViewById(R.id.name_change_setting);

        if ((Boolean) parseUser.get("is_org")) {
            setting_text.setImageResource(R.drawable.text_setting_org);
            textchangeName.setText("Изменить название организации");
            hintchangeName.setHint("Введите новое название");
        }
        else {
            setting_text.setImageResource(R.drawable.text_setting_profile);
            textchangeName.setText("Изменить имя пользователя");
            hintchangeName.setHint("Введите новое имя");
        }
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

    public void change_user_name(View view){
        TextView textName = (TextView) findViewById(R.id.text_user_name);
        TextView textchange = (TextView) findViewById(R.id.button_change_user_name);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_name_user);

        change(textName, textchange, layout);
    }

    public void change_user_email(View view){
        TextView textEmail = (TextView) findViewById(R.id.text_user_email);
        TextView textchange = (TextView) findViewById(R.id.button_change_user_email);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_email_user);

        change(textEmail, textchange, layout);
    }

    public void change_user_password(View view){
        TextView textPassword = (TextView) findViewById(R.id.text_user_password);
        TextView textchange = (TextView) findViewById(R.id.button_change_user_password);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_password_user);

        change(textPassword, textchange, layout);

    }

    public void save_username(View view){
        TextView textName = (TextView) findViewById(R.id.name_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_password_setting_name);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), textPassword.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    parseUser.setUsername(textName.getText().toString());
                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SettingProfileUser.this, SettingProfileUser.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void save_email(View view){
        TextView textEmail = (TextView) findViewById(R.id.email_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_password_setting_email);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), textPassword.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    parseUser.setEmail(textEmail.getText().toString());
                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SettingProfileUser.this, SettingProfileUser.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void save_password(View view){
        TextView textPass = (TextView) findViewById(R.id.password_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_password_setting_pass);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), textPassword.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    parseUser.setPassword(textPass.getText().toString());
                    parseUser.saveInBackground();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_LONG).show();
                }
            }
        });

        ParseUser.logOutInBackground(p -> {
            if (p == null){
                Toast.makeText(getApplicationContext(), "Введите новый пароль", Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = new Intent(SettingProfileUser.this, AuthorizationActivity.class);
        startActivity(intent);
        finish();
    }

    public void cancel_name(View view){
        TextView textName = (TextView) findViewById(R.id.text_user_name);
        TextView textchange = (TextView) findViewById(R.id.button_change_user_name);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_name_user);

        cancel(textName, textchange, layout);
        clear(R.id.name_change_setting);
        clear(R.id.change_password_setting_name);
    }

    public void cancel_email(View view){
        TextView textEmail = (TextView) findViewById(R.id.text_user_email);
        TextView textchange = (TextView) findViewById(R.id.button_change_user_email);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_email_user);

        cancel(textEmail, textchange, layout);
        clear(R.id.email_change_setting);
        clear(R.id.change_password_setting_email);
    }

    public void cancel_password(View view){
        TextView textPassword = (TextView) findViewById(R.id.text_user_password);
        TextView textchange = (TextView) findViewById(R.id.button_change_user_password);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_password_user);

        cancel(textPassword, textchange, layout);
        clear(R.id.password_change_setting);
        clear(R.id.change_password_setting_pass);
    }

    public void clear_name(View view){
        clear(R.id.name_change_setting);
    }

    public void clear_name_password(View view){
        clear(R.id.change_password_setting_name);
    }

    public void clear_email(View view){
        clear(R.id.email_change_setting);
    }

    public void clear_email_password(View view){
        clear(R.id.change_password_setting_email);
    }

    public void clear_password(View view){
        clear(R.id.password_change_setting);
    }

    public void clear_password_password(View view){
        clear(R.id.change_password_setting_pass);
    }

}