package com.example.urbanenviroment.page.profile.org;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalPhotoDeleteOrgAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.filter.FilterAnimal;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DeletePhoto extends AppCompatActivity {

    boolean flag = false;

    RecyclerView animalsRecycler;
    AnimalPhotoDeleteOrgAdapter animalsAdapter;
    List<Animals> animalsList;

    String name_animal, age, state, species, description, sex, name_org, image_org, kind_animal;

    static class AnimalsComparator implements Comparator<Animals> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Animals o1, Animals o2) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("d.M.y");

            Date date_1 = null;
            Date date_2 = null;
            try {
                date_1 = format.parse(o1.getReg_data());
                date_2 = format.parse(o2.getReg_data());
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            assert date_1 != null;
            return date_1.compareTo(date_2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_photo);

        init();
    }

    public void init(){
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        animalsList = new ArrayList<>();

        db.collection("Collection").whereEqualTo("userId", mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        String date = new SimpleDateFormat("d.M.y").format(document.getDate("reg_date"));
                        String image_animal = document.getString("image_collection");

                        animalsList.add(new Animals(id, name_org, image_org, name_animal, image_animal,
                                age, state, kind_animal, species, description, sex, date));

                        Collections.sort(animalsList, new AnimalsComparator().reversed());
                        setAnimalsRecycler(animalsList);
                    }
                }
            }
        });
    }

    private void setAnimalsRecycler(List<Animals> animalsList){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        animalsRecycler = findViewById(R.id.RecyclerView_delete_photo);
        animalsRecycler.setLayoutManager(gridLayoutManager);

        animalsAdapter = new AnimalPhotoDeleteOrgAdapter(this, animalsList);
        animalsRecycler.setAdapter(animalsAdapter);

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

    public void filter(View view){
        Intent intent = new Intent(this, FilterAnimal.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort(View view){
        ImageView button_up = (ImageView) findViewById(R.id.img_sort_arrow_up);
        if (flag){
            button_up.setImageResource(R.drawable.img_sort_arrow_up);
            flag = false;
            Collections.sort(animalsList, new AnimalsComparator().reversed());
            setAnimalsRecycler(animalsList);
        }
        else{
            button_up.setImageResource(R.drawable.img_sort_arrow_down);
            flag = true;
            Collections.sort(animalsList, new AnimalsComparator());
            setAnimalsRecycler(animalsList);
        }
    }
}