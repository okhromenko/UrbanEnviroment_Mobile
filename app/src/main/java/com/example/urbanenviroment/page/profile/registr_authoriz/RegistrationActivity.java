package com.example.urbanenviroment.page.profile.registr_authoriz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.org.AddAnimal;
import com.example.urbanenviroment.page.profile.org.ProfileActivityOrg;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseSession;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {

    private MaterialEditText emailField, nameFields, passwordField, passwordFieldDuplicate;
    private Boolean is_org;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(RegistrationActivity.this);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public void button_registration(View view){

        emailField = (MaterialEditText) findViewById(R.id.emailField);
        nameFields = (MaterialEditText)  findViewById(R.id.nameField);
        passwordField = (MaterialEditText) findViewById(R.id.passField);
        passwordFieldDuplicate = (MaterialEditText) findViewById(R.id.passFieldDuplicate);

        if (!emailField.getText().toString().isEmpty() && !nameFields.getText().toString().isEmpty()
                && !passwordField.getText().toString().isEmpty() && !passwordFieldDuplicate.getText().toString().isEmpty()){

            if (passwordField.getText().toString().equals(passwordFieldDuplicate.getText().toString())){

                progressDialog.show();

                ParseUser user = new ParseUser();
                user.setUsername(nameFields.getText().toString());
                user.setPassword(passwordField.getText().toString());
                user.setEmail(emailField.getText().toString().toLowerCase());
                user.put("is_org", is_org);

                user.signUpInBackground(e -> {
                    progressDialog.dismiss();

                    if (e == null) {
                        if (is_org){
                            ParseObject org = new ParseObject("Organization");
                            org.put("id_user", user);
                            org.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null) {
                                        Toast.makeText(getApplicationContext(), "Successful; Please verify your email before Login", Toast.LENGTH_LONG).show();
                                        ParseUser.logOut();
                                        Intent intent = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        ParseUser.logOut();
                                        Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Successful; Please verify your email before Login", Toast.LENGTH_LONG).show();
                            ParseUser.logOut();
                            Intent intent = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_LONG).show();
    }

    public void authorization(View view){
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }

    public void user_register(View view){

        is_org = false;

        ImageView ivname = findViewById(R.id.imageView4);
        ivname.setImageResource(R.drawable.text_reg_user);

        ImageButton ibu = findViewById(R.id.user_register);
        ibu.setImageResource(R.drawable.button_user_press);

        ImageButton ibo = findViewById(R.id.org_register);
        ibo.setImageResource(R.drawable.button_organization);

        LinearLayout lldata = findViewById(R.id.linearLayoutData);
        lldata.setVisibility(View.VISIBLE);

        com.rengwuxian.materialedittext.MaterialEditText inputname = findViewById(R.id.nameField);
        inputname.setHint("Введите имя пользователя");

    }

    public void org_register(View view){

        is_org = true;

        ImageView ivname = findViewById(R.id.imageView4);
        ivname.setImageResource(R.drawable.text_reg_org);

        ImageButton ibu = findViewById(R.id.user_register);
        ibu.setImageResource(R.drawable.button_user);

        ImageButton ibo = findViewById(R.id.org_register);
        ibo.setImageResource(R.drawable.button_org_press);

        LinearLayout lldata = findViewById(R.id.linearLayoutData);
        lldata.setVisibility(View.VISIBLE);

        com.rengwuxian.materialedittext.MaterialEditText inputname = findViewById(R.id.nameField);
        inputname.setHint("Введите название организации");

    }
}