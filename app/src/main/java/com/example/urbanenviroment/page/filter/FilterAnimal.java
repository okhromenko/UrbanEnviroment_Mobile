package com.example.urbanenviroment.page.filter;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContentInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.CategoryAnimalAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FilterAnimal extends AppCompatActivity {

    public static List<CategoryAnimals> click_org_list_animal, click_animal_list_animal;
    static RecyclerView recyclerViewAnimal, recyclerViewOrg;
    static Context context;

    List<CategoryAnimals> kind_list, name_org_list;
    RecyclerView categoryRecycler;
    CategoryAnimalAdapter categoryAnimalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_animals);

        kind_list = new ArrayList<>();
        name_org_list = new ArrayList<>();
        click_animal_list_animal = new ArrayList<>();
        click_org_list_animal = new ArrayList<>();

        init();

        recyclerViewAnimal = findViewById(R.id.RecyclerView_animal_sort_list);
        recyclerViewOrg = findViewById(R.id.RecyclerView_org_sort_list);
        context = this;

        SearchView searchViewAnimal = (SearchView) findViewById(R.id.search_view_animal_filter_animal);
        searchViewAnimal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setCategoryAnimalsRecycler(filter(kind_list, newText), true, 1);
                return false;
            }
        });


        SearchView searchViewOrg = (SearchView) findViewById(R.id.search_view_animal_filter_org);
        searchViewOrg.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setCategoryAnimalsRecycler(filter(name_org_list, newText), false, 3);
                return false;
            }
        });
    }

    public void init(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("AnimalKind").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        kind_list.add(new CategoryAnimals(document.get("name").toString()));
                        setCategoryAnimalsRecycler(kind_list, true, 1);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        for (String nameOrg : (Set<String>) getIntent().getSerializableExtra("list_org"))
            name_org_list.add(new CategoryAnimals((String) nameOrg));
        setCategoryAnimalsRecycler(name_org_list, false, 3);
    }

    private List<CategoryAnimals> filter(List<CategoryAnimals> strings, String text){
        ArrayList<CategoryAnimals> filterString = new ArrayList<>();

        for (CategoryAnimals word: strings){
            String item = word.getTitle();
            if (item.contains(text) || item.toLowerCase().contains(text) || item.toUpperCase().contains(text))
                filterString.add(word);
        }
        return filterString;
    }

    public void setCategoryAnimalsRecycler(List<CategoryAnimals> categoryAnimalsList, boolean click_category, int type_recycler){
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager (3, LinearLayoutManager.HORIZONTAL);

        if (click_category)
            categoryRecycler = findViewById(R.id.RecyclerView_animal_list);
        else categoryRecycler = findViewById(R.id.RecyclerView_filter_org);

        categoryRecycler.setLayoutManager(gridLayoutManager);

        categoryAnimalAdapter = new CategoryAnimalAdapter(this, categoryAnimalsList, false, type_recycler);
        categoryRecycler.setAdapter(categoryAnimalAdapter);
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

    public void finish(View view){
        finish();
    }


    public static void click_filter_animal(int number){
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager (1, LinearLayoutManager.HORIZONTAL);

        if (number == 1){
            recyclerViewAnimal.setLayoutManager(gridLayoutManager);
            CategoryAnimalAdapter categoryAnimalAdapter = new CategoryAnimalAdapter(context, click_animal_list_animal, true, number);
            recyclerViewAnimal.setAdapter(categoryAnimalAdapter);
        }
        else{
            recyclerViewOrg.setLayoutManager(gridLayoutManager);
            CategoryAnimalAdapter categoryAnimalAdapter = new CategoryAnimalAdapter(context, click_org_list_animal, true, number);
            recyclerViewOrg.setAdapter(categoryAnimalAdapter);
        }
    }

    public void save_filter(View view){
        String page = getIntent().getStringExtra("page_last");
        Intent intent;

        if (page.equals("photo"))
            intent = new Intent(this, HomeActivity.class);

        else
            intent = new Intent(this, CardsMainActivity.class);

        intent.putExtra("flag_filter", true);
        startActivity(intent);
    }

}