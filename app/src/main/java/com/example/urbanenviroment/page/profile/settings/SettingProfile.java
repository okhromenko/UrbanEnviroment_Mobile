package com.example.urbanenviroment.page.profile.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.org.DeletePhoto;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class SettingProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        ImageButton button_setting_org_org = (ImageButton) findViewById(R.id.button_setting_org_org);
        ImageButton button_setting_profile = (ImageButton) findViewById(R.id.button_setting_profile);
        ImageButton button_setting_page_org = (ImageButton) findViewById(R.id.button_setting_page_org);
        ImageButton button_other_settings = (ImageButton) findViewById(R.id.button_other_settings);


        if (getIntent().getBooleanExtra("is_org", false)) {
            button_setting_org_org.setVisibility(View.VISIBLE);
            button_setting_page_org.setVisibility(View.VISIBLE);
            button_setting_profile.setVisibility(View.GONE);
            button_other_settings.setVisibility(View.VISIBLE);
        } else {
            button_setting_profile.setVisibility(View.VISIBLE);
            button_setting_org_org.setVisibility(View.GONE);
            button_setting_page_org.setVisibility(View.GONE);
            button_other_settings.setVisibility(View.GONE);
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

    public void setting_notification(View view) {
        Intent intent = new Intent(this, SettingNotificationsUser.class);
        startActivity(intent);
    }

    public void setting_page(View view) {
        Intent intent = new Intent(this, SettingPageOrg.class);
        startActivity(intent);
    }

    public void setting_user(View view) {
        Intent intent = new Intent(this, SettingProfileUser.class);
        startActivity(intent);
    }

    public void setting_other(View view) {
        Intent intent = new Intent(this, SettingOther.class);
        startActivity(intent);
    }

    public void delete_profile(View view) {
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String id = mAuth.getUid();

        db.collection("User").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();

                if (Boolean.FALSE.equals(document.getBoolean("is_org"))){
                    db.collection("FavoriteAds").whereEqualTo("userId", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("FavoriteAds").document(document.getId()).delete();
                            }
                        }
                    });

                    db.collection("FavoriteAnimal").whereEqualTo("userId", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("FavoriteAnimal").document(document.getId()).delete();
                            }
                        }
                    });
                }

                if (document.getString("image") != null){
                    storageRef.getStorage().getReferenceFromUrl(document.getString("image")).delete();
                }

                db.collection("User").document(document.getId()).delete();
            }
        });

        db.collection("Ads").whereEqualTo("userId", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    db.collection("Ads").document(document.getId()).delete();
                }
            }
        });

        db.collection("Animal").whereEqualTo("userId", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    storageRef.getStorage().getReferenceFromUrl(document.getString("image")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            db.collection("Animal").document(document.getId()).delete();
                        }
                    });

                }
            }
        });

        db.collection("Collection").whereEqualTo("userId", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    storageRef.getStorage().getReferenceFromUrl(document.getString("image_animal")).delete();
                    storageRef.getStorage().getReferenceFromUrl(document.getString("image_collection")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            db.collection("Collection").document(document.getId()).delete();
                        }
                    });
                }
            }
        });


        mAuth.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent intent = new Intent(SettingProfile.this, AuthorizationActivity.class);
                startActivity(intent);
            }
        });
    }
}
