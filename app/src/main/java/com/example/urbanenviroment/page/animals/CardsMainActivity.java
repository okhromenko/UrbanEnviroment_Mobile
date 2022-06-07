package com.example.urbanenviroment.page.animals;

import static android.content.ContentValues.TAG;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.page.filter.FilterAnimal;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalCardsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardsMainActivity extends AppCompatActivity {

    RecyclerView AnimalsCardsRecycler;
    AnimalCardsAdapter cardsAdapter;
    List<Animals> animalsList, filterAnimalList;
    boolean flag_sort, flag_org;
    Set<String> list_org_name;

    class AnimalsComparator implements Comparator<Animals> {

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

            return date_1.compareTo(date_2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);

        flag_org = getIntent().getBooleanExtra("flag_org", false);
        init(flag_org);
    }

    public void init(boolean flag_org){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        animalsList = new ArrayList<>();
        list_org_name = new HashSet<>();

        Query docRef = db.collection("Animal");

        if (flag_org)
            docRef = db.collection("Animal").whereEqualTo("userId", getIntent().getStringExtra("id_org"));

        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
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

                        list_org_name.add(name_org);

                        animalsList.add(new Animals(id, name_org, image_org, name_animal, image_animal,
                                age, state, kind_animal, species, description, sex, date));

                        Collections.sort(animalsList, new AnimalsComparator().reversed());
                        setCardsRecycler(animalsList);

                        if (getIntent().getBooleanExtra("flag_filter", false))
                            filter_click(animalsList);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void setCardsRecycler(List<Animals> cardsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        AnimalsCardsRecycler = findViewById(R.id.AnimalsCardsRecycler);
        AnimalsCardsRecycler.setLayoutManager(layoutManager);

        cardsAdapter = new AnimalCardsAdapter(this, cardsList);
        AnimalsCardsRecycler.setAdapter(cardsAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void filter_click(List<Animals> animalsList){
        filterAnimalList = new ArrayList<>(animalsList);

        if (!FilterAnimal.click_org_list_animal.isEmpty() && FilterAnimal.click_animal_list_animal.isEmpty()){
            filterAnimalList.clear();
            for (CategoryAnimals word: FilterAnimal.click_org_list_animal){
                animalsList.stream().filter(o -> o.getName_org().equals(word.getTitle())).forEach(o -> {
                    filterAnimalList.add(o);
                });
            }
        }
        else if (FilterAnimal.click_org_list_animal.isEmpty() && !FilterAnimal.click_animal_list_animal.isEmpty()){
            filterAnimalList.clear();
            for (CategoryAnimals word: FilterAnimal.click_animal_list_animal){
                animalsList.stream().filter(o -> o.getKind().equals(word.getTitle())).forEach(o -> {
                    filterAnimalList.add(o);
                });
            }
        }
        else {
            filterAnimalList.clear();
            for (CategoryAnimals org : FilterAnimal.click_org_list_animal){
                for (CategoryAnimals word: FilterAnimal.click_animal_list_animal){
                    animalsList.stream().filter(o -> o.getKind().equals(word.getTitle()) &&
                            o.getName_org().equals(org.getTitle())).forEach(o -> {
                        filterAnimalList.add(o);
                    });
                }
            }
        }

        setCardsRecycler(filterAnimalList);
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
            intent.putExtra("page_last","cards");
            intent.putExtra("list_org", (Serializable) list_org_name);
            startActivity(intent);
        }
        else finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort(View view){
        ImageView button_up = (ImageView) findViewById(R.id.img_sort_arrow_up);

        if (flag_sort){
            button_up.setImageResource(R.drawable.img_sort_arrow_up);
            flag_sort = false;
            Collections.sort(animalsList, new AnimalsComparator().reversed());
            setCardsRecycler(animalsList);
        }
        else{
            button_up.setImageResource(R.drawable.img_sort_arrow_down);
            flag_sort = true;
            Collections.sort(animalsList, new AnimalsComparator());
            setCardsRecycler(animalsList);
        }
    }
}