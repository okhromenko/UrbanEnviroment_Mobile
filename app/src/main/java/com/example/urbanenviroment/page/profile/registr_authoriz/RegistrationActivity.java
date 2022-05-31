package com.example.urbanenviroment.page.profile.registr_authoriz;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.zzx;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
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
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private Boolean is_org;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(RegistrationActivity.this);

//        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public void button_registration(View view){

        MaterialEditText emailField = (MaterialEditText) findViewById(R.id.emailField);
        MaterialEditText nameFields = (MaterialEditText) findViewById(R.id.nameField);
        MaterialEditText passwordField = (MaterialEditText) findViewById(R.id.passField);
        MaterialEditText passwordFieldDuplicate = (MaterialEditText) findViewById(R.id.passFieldDuplicate);

        String email = emailField.getText().toString().toLowerCase();
        String password = passwordField.getText().toString();
        String name = nameFields.getText().toString();

        if (!email.isEmpty() && !name.isEmpty()
                && !password.isEmpty() && !passwordFieldDuplicate.getText().toString().isEmpty()){

            if (passwordField.getText().toString().equals(passwordFieldDuplicate.getText().toString())){

                progressDialog.show();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                mAuth = FirebaseAuth.getInstance();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Date date = new Date();

                                Map<String, Object> user = new HashMap<>();
                                user.put("email", email);
                                user.put("name", name);
                                user.put("password", password);
                                user.put("is_org", is_org);
                                user.put("reg_date", date);

                                progressDialog.dismiss();

                                db.collection("User").document(mAuth.getUid())
                                        .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Intent intent = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                                                startActivity(intent);

                                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
                                                        setDisplayName(name).build();

                                                assert firebaseUser != null;
                                                firebaseUser.updateProfile(profileChangeRequest);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                FirebaseAuth.getInstance().signOut();
                                            }
                                        });
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