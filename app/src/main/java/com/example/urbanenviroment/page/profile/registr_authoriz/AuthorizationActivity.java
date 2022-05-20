package com.example.urbanenviroment.page.profile.registr_authoriz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.org.ProfileActivityOrg;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AuthorizationActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    MaterialEditText emailField, passwordField;
    Dialog dialog_forgotten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        dialog_forgotten = new Dialog(this);

        progressDialog = new ProgressDialog(AuthorizationActivity.this);

        ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser != null){

            if ((Boolean) parseUser.get("is_org")) {
                Intent intent = new Intent(this, ProfileActivityOrg.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(this, ProfileActivityUser.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void registration(View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void forgotten_password(View view){
        dialog_forgotten.setContentView(R.layout.dialog_forgotten_password);
        dialog_forgotten.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_forgotten.show();
    }

    public void goIn(View view){

        emailField = (MaterialEditText) findViewById(R.id.emailField);
        passwordField = (MaterialEditText) findViewById(R.id.passField);

        progressDialog.show();

        ParseUser.logInInBackground(emailField.getText().toString().toLowerCase(), passwordField.getText().toString(), (parseUser, e) -> {

            progressDialog.dismiss();

            if (parseUser != null) {
                Toast.makeText(getApplicationContext(), "Successful ", Toast.LENGTH_LONG).show();
                if ((Boolean) parseUser.get("is_org")){
                    Intent intent = new Intent(this, ProfileActivityOrg.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(this, ProfileActivityUser.class);
                    startActivity(intent);
                }
            } else {
                ParseUser.logOut();
                Toast.makeText(AuthorizationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void send_message(View view){
        MaterialEditText forgotten_passwd_email = dialog_forgotten.findViewById(R.id.forgotten_passwd_email);
        String email = forgotten_passwd_email.getText().toString();

        try {
            ParseUser.requestPasswordReset(email);
            Toast.makeText(getApplicationContext(), "Письмо отправлено на почту", Toast.LENGTH_LONG).show();
            dialog_forgotten.cancel();
        }
        catch (ParseException e){
            Toast.makeText(AuthorizationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}