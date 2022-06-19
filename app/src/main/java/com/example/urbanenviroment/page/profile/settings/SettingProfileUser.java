package com.example.urbanenviroment.page.profile.settings;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.org.EditHelpPage;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

public class SettingProfileUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Boolean is_org;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile_user);

        TextView text_user_name = (TextView) findViewById(R.id.text_user_name);
        TextView text_user_email = (TextView) findViewById(R.id.text_user_email);
        ImageView setting_text = (ImageView) findViewById(R.id.imageView6);
        TextView textchangeName = (TextView) findViewById(R.id.text_1);
        MaterialEditText hintchangeName = (MaterialEditText) findViewById(R.id.name_change_setting);

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
                        text_user_name.setText(document.getString("name"));
                        text_user_email.setText(document.getString("email"));
                        is_org = document.getBoolean("is_org");

                        if (Boolean.TRUE.equals(is_org)) {
                            setting_text.setImageResource(R.drawable.text_setting_org);
                            textchangeName.setText("Изменить название организации");
                            hintchangeName.setHint("Введите новое название");
                        }
                        else {
                            setting_text.setImageResource(R.drawable.text_setting_profile);
                            textchangeName.setText("Изменить имя пользователя");
                            hintchangeName.setHint("Введите новое имя");
                        }
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

    private void edit_fields_name(String name){
        db.collection("Animal").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    db.collection("Animal").document(document.getId())
                            .update("username", name);
                }
            }
        });

        db.collection("Ads").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("Ads").document(document.getId())
                                    .update("username", name);
                        }
                    }
                });

        db.collection("Collection").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("Collection").document(document.getId())
                                    .update("username", name);
                        }
                    }
                });

        db.collection("FavoriteAds").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("FavoriteAds").document(document.getId())
                                    .update("username", name);
                        }
                    }
                });

        db.collection("FavoriteAnimal").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("FavoriteAnimal").document(document.getId())
                                    .update("username", name);
                        }
                    }
                });
    }


    private void save_change(String field, String value, String password){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail()),
                password);

        assert user != null;
        user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                DocumentReference changeRef = db.collection("User").document(mAuth.getCurrentUser().getUid());

                changeRef.update(field, value).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Не забудь потом поправить - пароль не должен храниться в бд открыто!!!

                        switch (field){
                            case "password":
                                user.updatePassword(value);
                                break;
                            case "email":
                                user.updateEmail(value);
                                break;
                            case "name":
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
                                        setDisplayName(value).build();

                                user.updateProfile(profileChangeRequest);
                                edit_fields_name(value);
                                break;
                        }

                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SettingProfileUser.this, SettingProfileUser.class);
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void save_username(View view){
        TextView textName = (TextView) findViewById(R.id.name_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_password_setting_name);

        save_change("name", textName.getText().toString(), textPassword.getText().toString());


    }

    public void save_email(View view){
        TextView textEmail = (TextView) findViewById(R.id.email_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_password_setting_email);

        save_change("email", textEmail.getText().toString(), textPassword.getText().toString());
    }

    public void save_password(View view){
        TextView textPass = (TextView) findViewById(R.id.password_change_setting);
        TextView textPassword = (TextView) findViewById(R.id.change_password_setting_pass);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = textPass.getText().toString();

        save_change("password", newPassword, textPassword.getText().toString());
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