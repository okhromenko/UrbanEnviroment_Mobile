package com.example.urbanenviroment.page.profile.user;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.FavoriteProfileAnimalsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoritesProfileUserAnimals extends AppCompatActivity {

    RecyclerView FavoritesRecyclerProfileAnimals;
    FavoriteProfileAnimalsAdapter animalsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_profile);

        init();
    }

    public void init() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Animals> animalsList = new ArrayList<>();


        db.collection("FavoriteAnimal").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        String date = document.getString("date_reg");
                        String name_animal = document.getString("name");
                        String age = document.getString("age");
                        String state = document.getString("state");
                        String kind_animal = document.getString("kind");
                        String species = document.getString("species");
                        String description = document.getString("description");
                        String sex = document.getString("sex");
                        String name_org = document.getString("username");
                        String image_org = document.getString("imageOrg");
                        String image_animal = document.getString("image");


                        animalsList.add(new Animals(id, name_org, image_org, name_animal, image_animal,
                                age, state, kind_animal, species, description, sex, date));
                    }
                    setAnimalsRecycler(animalsList);
                }
            }
        });
    }

    private void setAnimalsRecycler(List<Animals> animalsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        FavoritesRecyclerProfileAnimals = findViewById(R.id.FavoritesRecyclerProfileAnimals);
        FavoritesRecyclerProfileAnimals.setLayoutManager(layoutManager);

        animalsAdapter = new FavoriteProfileAnimalsAdapter(this, animalsList);
        FavoritesRecyclerProfileAnimals.setAdapter(animalsAdapter);
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

    public void favorites_menu_animal(View view){
        Intent intent = new Intent(this, FavoritesProfileUserAnimals.class);
        startActivity(intent);
    }

    public void favorites_menu_ads(View view){
        Intent intent = new Intent(this, FavoritesProfileUserAds.class);
        startActivity(intent);
    }
}