package com.example.urbanenviroment.page.profile.settings;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class SettingOther extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch_email_other, switch_phone_other, switch_website_other;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_other);

        switch_email_other = findViewById(R.id.switch_email_other);
        switch_phone_other = findViewById(R.id.switch_phone_other);
        switch_website_other = findViewById(R.id.switch_website_other);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("User").document(Objects.requireNonNull(mAuth.getUid()));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        switch_onCreate(switch_email_other, Boolean.TRUE.equals(document.getBoolean("hidden_email")));
                        switch_onCreate(switch_phone_other, Boolean.TRUE.equals(document.getBoolean("hidden_phone")));
                        switch_onCreate(switch_website_other, Boolean.TRUE.equals(document.getBoolean("hidden_website")));
                    } else {
                        Log.d(TAG, "Проблемы при входе, пользователь не найден");
                    }
                } else {
                    Log.d(TAG, "Проблемы при входе ", task.getException());
                }
            }
        });
    }

    public void switch_onCreate(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch_other, boolean flag){
        switch_other.setChecked(flag);
    }

    public void save_switch(String hidden_type, boolean flag){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference changeRef = db.collection("User").document(mAuth.getCurrentUser().getUid());

        changeRef.update(hidden_type, flag).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SettingOther.this, SettingOther.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),  "Что-то пошло не так", Toast.LENGTH_LONG).show();
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
}