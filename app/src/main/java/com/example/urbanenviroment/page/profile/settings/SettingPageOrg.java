package com.example.urbanenviroment.page.profile.settings;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class SettingPageOrg extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page_org);

        TextView text_location = (TextView) findViewById(R.id.text_location);
        TextView text_phone = (TextView) findViewById(R.id.text_phone);
        MaterialEditText text_description = (MaterialEditText) findViewById(R.id.description_change_setting);
        TextView text_website = (TextView) findViewById(R.id.text_website);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("User").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        text_location.setText(document.getString("address"));
                        text_phone.setText(document.getString("phone"));
                        text_description.setText(document.getString("description"));
                        text_website.setText(document.getString("website"));
                    } else {
                        Log.d(TAG, "Данные не найдены");
                    }
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

    public void profile(View view){
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;

        if (mAuth != null)
            intent = new Intent(this, ProfileActivityUser.class);
        else
            intent = new Intent(this, RegistrationActivity.class);

        startActivity(intent);
    }

    public void organization(View view){
        Intent intent = new Intent(this, OrganizationsActivity.class);
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

    public void change_website(View view) {
        TextView textWebsite = (TextView) findViewById(R.id.text_website);
        TextView textchange = (TextView) findViewById(R.id.button_change_website);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_website);

        change(textWebsite, textchange, layout);
    }

    private void save_change(String field, String value){
        DocumentReference changeRef = db.collection("User").document(mAuth.getCurrentUser().getUid());

        changeRef.update(field, value).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SettingPageOrg.this, SettingPageOrg.class);
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

    public void save_local(View view){
        TextView textName = (TextView) findViewById(R.id.location_change_setting);
        save_change("address", textName.getText().toString());
    }

    public void save_phone(View view){
        TextView textName = (TextView) findViewById(R.id.phone_change_setting);
        save_change("phone", textName.getText().toString());
    }

    public void save_description(View view){
        TextView textName = (TextView) findViewById(R.id.description_change_setting);
        save_change("description", textName.getText().toString());
    }

    public void save_website(View view) {
        TextView textName = (TextView) findViewById(R.id.website_change_setting);
        save_change("website", textName.getText().toString());
    }

    public void cancel_location(View view){
        TextView textName = (TextView) findViewById(R.id.text_location);
        TextView textchange = (TextView) findViewById(R.id.button_change_location);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_location);

        cancel(textName, textchange, layout);
        clear(R.id.location_change_setting);
    }

    public void cancel_phone(View view){
        TextView textPhone = (TextView) findViewById(R.id.text_phone);
        TextView textchange = (TextView) findViewById(R.id.button_change_phone);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_phone);

        cancel(textPhone, textchange, layout);
        clear(R.id.phone_change_setting);
    }

    public void cancel_description(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_description);
        MaterialEditText text_description = (MaterialEditText) findViewById(R.id.description_change_setting);
        text_description.setText(" ");
    }

    public void cancel_website(View view) {
        clear(R.id.website_change_setting);
    }

    public void clear_location(View view){
        clear(R.id.location_change_setting);
    }

    public void clear_phone(View view){
        clear(R.id.phone_change_setting);
    }

    public void clear_website(View view) {
        TextView textWebsite = (TextView) findViewById(R.id.text_website);
        TextView textchange = (TextView) findViewById(R.id.button_change_website);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_website);

        cancel(textWebsite, textchange, layout);
        clear(R.id.website_change_setting);
    }

}