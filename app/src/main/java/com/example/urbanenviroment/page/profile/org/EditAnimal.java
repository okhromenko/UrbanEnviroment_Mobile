package com.example.urbanenviroment.page.profile.org;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalEditOrgAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.Organizations;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class EditAnimal extends AppCompatActivity {

    RecyclerView animalEditRecycler;
    AnimalEditOrgAdapter animalEditOrg;
    List<Animals> animalsList;
    FirebaseUser mAuth;
    FirebaseFirestore db;
    StorageReference storageRef;

    boolean flag = false;

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
        setContentView(R.layout.activity_edit_animal);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        init();
    }

    public void init() {
        animalsList = new ArrayList<>();

        db.collection("Animal").whereEqualTo("userId", mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        String date = new SimpleDateFormat("d.M.y").format(document.getDate("date_reg"));
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

                        setAnimalsRecycler(animalsList);
                    }
                }
            }
        });
    }

    private void setAnimalsRecycler(List<Animals> animalsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        animalEditRecycler = findViewById(R.id.RecyclerView_edit_animal);
        animalEditRecycler.setLayoutManager(layoutManager);

        animalEditOrg = new AnimalEditOrgAdapter(this, animalsList);
        animalEditRecycler.setAdapter(animalEditOrg);

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