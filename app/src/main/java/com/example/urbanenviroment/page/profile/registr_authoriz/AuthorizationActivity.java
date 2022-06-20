package com.example.urbanenviroment.page.profile.registr_authoriz;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AuthorizationActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener mAuthListener;
    MaterialEditText emailField, passwordField;
    Dialog dialog_forgotten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        dialog_forgotten = new Dialog(this);
        progressDialog = new ProgressDialog(AuthorizationActivity.this);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            DocumentReference docRef = db.collection("User").document(mAuth.getCurrentUser().getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            Intent intent = new Intent(AuthorizationActivity.this, ProfileActivityUser.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "Проблемы при входе, пользователь не найден");
                        }
                    } else {
                        Log.d(TAG, "Проблемы при входе ", task.getException());
                    }
                }
            });
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
        String email = emailField.getText().toString().toLowerCase();
        String password = passwordField.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {

            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {

                                mAuth.getCurrentUser().getProviderData();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                DocumentReference docRef = db.collection("User").document(mAuth.getCurrentUser().getUid());
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Intent intent = new Intent(AuthorizationActivity.this, ProfileActivityUser.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Ошибка, логин/пароль не совпадают", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                });

                            } else {
                                progressDialog.dismiss();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Ошибка, логин/пароль не совпадают", Toast.LENGTH_LONG).show();
                        }
                    });
            }
        else {
            Toast.makeText(getApplicationContext(), "Введите данные!", Toast.LENGTH_LONG).show();
        }
    }

    public void send_message(View view){
        MaterialEditText forgotten_passwd_email = dialog_forgotten.findViewById(R.id.forgotten_passwd_email);
        String email = forgotten_passwd_email.getText().toString();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Письмо отправлено на почту", Toast.LENGTH_LONG).show();
                            dialog_forgotten.cancel();
                        }
                    }
                });
    }
}