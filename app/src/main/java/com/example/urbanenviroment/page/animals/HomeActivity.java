package com.example.urbanenviroment.page.animals;

import static android.content.ContentValues.TAG;

import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.model.Collection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.urbanenviroment.page.filter.FilterAnimal;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.PhotoAnimalsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    RecyclerView animalsRecycler;
    PhotoAnimalsAdapter photoAnimalsAdapter;
    List<Collection> photoList, filterPhotoList;
    Set<String> list_org_name;
    boolean flag_org, flag;

    static class AnimalsComparator implements Comparator<Collection>{

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Collection o1, Collection o2) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

            Date date_1 = null;
            Date date_2 = null;
            try {
                date_1 = format.parse(o1.getReg_date());
                date_2 = format.parse(o2.getReg_date());
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
        setContentView(R.layout.activity_home);

        //Флаг подсказывает, что мы зашли по переходу от организации и должны увидеть только ее животных
        flag_org = getIntent().getBooleanExtra("flag_org", false);
        init(flag_org);

        if (getIntent().getBooleanExtra("flag_filter", false)){
            findViewById(R.id.button_cancel_filter).setVisibility(View.VISIBLE);
            ImageButton button_cancel = findViewById(R.id.button_filter);
            button_cancel.setImageResource(R.drawable.button_filter_press);
        }
        else{
            findViewById(R.id.button_cancel_filter).setVisibility(View.GONE);
            ImageButton button_cancel = findViewById(R.id.button_filter);
            button_cancel.setImageResource(R.drawable.button_filter);
        }

    }

    public void init(boolean flag_org){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        photoList = new ArrayList<>();
        list_org_name = new HashSet<>();

        Query docRef = db.collection("Collection");

        if (flag_org)
            docRef = db.collection("Collection").whereEqualTo("userId", getIntent().getStringExtra("id_org"));

        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String id = document.getId();
                        String image_collection = document.getString("image_collection");

                        String name_org = document.getString("username");
                        String image_org = document.getString("imageOrg");

                        String id_animal = document.getString("id_animal");
                        String kind = document.getString("kind");

                        String reg_date = new SimpleDateFormat("dd.MM.yyyy").format(document.getDate("reg_date"));

                        list_org_name.add(name_org);

                        photoList.add(new Collection(id, image_collection, name_org, image_org, id_animal, kind, reg_date));

                        Collections.sort(photoList, new AnimalsComparator().reversed());
                        setAnimalsRecycler(photoList);

                        if (getIntent().getBooleanExtra("flag_filter", false))
                            filter_click(photoList);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void setAnimalsRecycler(List<Collection> photoList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        animalsRecycler = findViewById(R.id.AnimalsRecycler);
        animalsRecycler.setLayoutManager(layoutManager);

        photoAnimalsAdapter = new PhotoAnimalsAdapter(this, photoList);
        animalsRecycler.setAdapter(photoAnimalsAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void filter_click(List<Collection> photoList){
        filterPhotoList = new ArrayList<>(photoList);

        if (!FilterAnimal.click_org_list_animal.isEmpty() && FilterAnimal.click_animal_list_animal.isEmpty()){
            filterPhotoList.clear();
            for (CategoryAnimals word: FilterAnimal.click_org_list_animal){
                photoList.stream().filter(o -> o.getName_org().equals(word.getTitle())).forEach(o -> {
                    filterPhotoList.add(o);
                });
            }
        }
        else if (FilterAnimal.click_org_list_animal.isEmpty() && !FilterAnimal.click_animal_list_animal.isEmpty()){
            filterPhotoList.clear();
            for (CategoryAnimals word: FilterAnimal.click_animal_list_animal){
                photoList.stream().filter(o -> o.getKind().equals(word.getTitle())).forEach(o -> {
                    filterPhotoList.add(o);
                });
            }
        }
        else {
            filterPhotoList.clear();
            for (CategoryAnimals org : FilterAnimal.click_org_list_animal){
                for (CategoryAnimals word: FilterAnimal.click_animal_list_animal){
                    photoList.stream().filter(o -> o.getKind().equals(word.getTitle()) &&
                            o.getName_org().equals(org.getTitle())).forEach(o -> {
                        filterPhotoList.add(o);
                    });
                }
            }
        }

        setAnimalsRecycler(filterPhotoList);
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

    public void card(View view){
        Intent intent = new Intent(this, CardsMainActivity.class);
        startActivity(intent);
    }

    public void filter(View view){
        if (!getIntent().getBooleanExtra("flag_filter", false)){
            Intent intent = new Intent(this, FilterAnimal.class);
            intent.putExtra("page_last","photo");
            intent.putExtra("list_org", (Serializable) list_org_name);
            startActivity(intent);
        }
        else finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort(View view){
        ImageView button_up = (ImageView) findViewById(R.id.img_sort_arrow_up);

        if (flag){
            button_up.setImageResource(R.drawable.img_sort_arrow_up);
            flag = false;
            Collections.sort(photoList, new AnimalsComparator().reversed());
            setAnimalsRecycler(photoList);
        }
        else{
            button_up.setImageResource(R.drawable.img_sort_arrow_down);
            flag = true;
            Collections.sort(photoList, new AnimalsComparator());
            setAnimalsRecycler(photoList);
        }
    }

    public void cancel_filter(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("flag_filter",false);
        startActivity(intent);
    }
}