package com.example.urbanenviroment.page.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FilterAnimal extends AppCompatActivity {

    List<CategoryAnimals> kind_list = new ArrayList<>();
    List<String> name_org_list = new ArrayList<>();
    public static List<CategoryAnimals> click_org_list_animal = new ArrayList<>();

    RecyclerView categoryRecycler;
    ArrayAdapter<String> arrayAdapter;
    CategoryAnimalAdapter categoryAnimalAdapter;
    public static List<CategoryAnimals> click_animal_list_animal;
    ListView listViewOrg;
    @SuppressLint("StaticFieldLeak")
    static RecyclerView recyclerView;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_animals);

        click_animal_list_animal = new ArrayList<>();

        init();

        recyclerView = findViewById(R.id.RecyclerView_animal_sort_list);
        context = this;

        SearchView searchViewAnimal = (SearchView) findViewById(R.id.search_view_animal_filter_animal);
        searchViewAnimal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setCategoryAnimalsRecycler(filter(kind_list, newText), false);
                return false;
            }
        });

        listViewOrg = findViewById(R.id.list_filter_org);

        SearchView searchViewOrg = (SearchView) findViewById(R.id.search_view_animal_filter_org);
        searchViewOrg.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        listViewOrg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView tv = (TextView) view;
                click_org_list_animal.add(new CategoryAnimals(tv.getText().toString()));
                setCategoryAnimalsRecycler(click_org_list_animal, true);

            }
        });
    }

    public void init(){
        ParseQuery<ParseObject> query_kind = new ParseQuery<>("Animal_kind");
        query_kind.findInBackground((object_kind, exp) -> {
            if (exp == null) {
                for (ParseObject r : object_kind)
                    kind_list.add(new CategoryAnimals(r.get("name").toString()));
                setCategoryAnimalsRecycler(kind_list, false);
            }
        });

        ParseQuery<ParseObject> query_org = new ParseQuery<>("_User");
        query_org.whereEqualTo("is_org", true);
        query_org.findInBackground((object_org, exp) -> {
            if (exp == null) {
                for (ParseObject r : object_org)
                    name_org_list.add(r.get("username").toString());

                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name_org_list);
                listViewOrg.setAdapter(arrayAdapter);
            }
        });
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

    public void setCategoryAnimalsRecycler(List<CategoryAnimals> categoryAnimalsList, boolean click){
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager (1, LinearLayoutManager.HORIZONTAL);

        if (click)
            categoryRecycler = findViewById(R.id.RecyclerView_org_sort_list);
        else
            categoryRecycler = findViewById(R.id.RecyclerView_animal_list);

        categoryRecycler.setLayoutManager(gridLayoutManager);

        categoryAnimalAdapter = new CategoryAnimalAdapter(this, categoryAnimalsList, click, 1);
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

    public void authorization(View view){
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }

    public void organization(View view){
        Intent intent = new Intent(this, OrganizationsActivity.class);
        startActivity(intent);
    }

    public void map(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void finish(View view){
        finish();
    }


    public static void click_filter_animal(){
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager (1, LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        CategoryAnimalAdapter categoryAnimalAdapter = new CategoryAnimalAdapter(context, click_animal_list_animal, true, 1);
        recyclerView.setAdapter(categoryAnimalAdapter);
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