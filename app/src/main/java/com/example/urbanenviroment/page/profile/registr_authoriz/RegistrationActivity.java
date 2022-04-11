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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegistrationActivity extends AppCompatActivity {

    MaterialEditText emailField, nameFields, passwordField, passwordFieldDuplicate;

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

        if (!nameFields.getText().toString().isEmpty() && !passwordField.getText().toString().isEmpty()){
            progressDialog.show();
            ParseUser user = new ParseUser();

            user.setUsername(nameFields.getText().toString());
            user.setPassword(passwordField.getText().toString());
            user.signUpInBackground(e -> {
                progressDialog.dismiss();
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                } else {
                    ParseUser.logOut();
                    Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void authorization(View view){
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }

    public void user_register(View view){

        ImageView ivname = findViewById(R.id.imageView4);
        ivname.setImageResource(R.drawable.text_reg_user);

        ImageButton ibu = findViewById(R.id.imageButton);
        ibu.setImageResource(R.drawable.button_user_press);

        ImageButton ibo = findViewById(R.id.imageButton2);
        ibo.setImageResource(R.drawable.button_organization);

        LinearLayout lldata = findViewById(R.id.linearLayoutData);
        lldata.setVisibility(View.VISIBLE);

        com.rengwuxian.materialedittext.MaterialEditText inputname = findViewById(R.id.nameField);
        inputname.setHint("Введите имя пользователя");

    }

    public void org_register(View view){

        ImageView ivname = findViewById(R.id.imageView4);
        ivname.setImageResource(R.drawable.text_reg_org);

        ImageButton ibu = findViewById(R.id.imageButton);
        ibu.setImageResource(R.drawable.button_user);

        ImageButton ibo = findViewById(R.id.imageButton2);
        ibo.setImageResource(R.drawable.button_org_press);

        LinearLayout lldata = findViewById(R.id.linearLayoutData);
        lldata.setVisibility(View.VISIBLE);

        com.rengwuxian.materialedittext.MaterialEditText inputname = findViewById(R.id.nameField);
        inputname.setHint("Введите название организации");

    }
}