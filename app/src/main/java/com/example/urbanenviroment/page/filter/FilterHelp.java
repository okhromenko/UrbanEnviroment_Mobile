package com.example.urbanenviroment.page.filter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.CategoryAnimalAdapter;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.model.Organizations;
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

public class FilterHelp extends AppCompatActivity {

    public static List<CategoryAnimals> click_org_list_help;
    static RecyclerView recyclerView;
    static Context context;

    RecyclerView categoryRecycler;
    CategoryAnimalAdapter categoryAnimalAdapter;
    String type_help;
    List<CategoryAnimals> categoryOrgList;
    ImageButton food, things, help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_help);

        recyclerView = findViewById(R.id.RecyclerView_org_list);
        click_org_list_help = new ArrayList<>();

        food = findViewById(R.id.food_filter);
        things = findViewById(R.id.things_filter);
        help = findViewById(R.id.help_filter);

        context = this;

        init();

        SearchView searchViewOrg = findViewById(R.id.search_view_ads_filter_org);
        searchViewOrg.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setCategoryAnimalsRecycler(filter(categoryOrgList, newText));
                return false;
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

    public void init(){
        categoryOrgList = new ArrayList<>();

        ParseQuery<ParseObject> query_org = new ParseQuery<>("_User");
        query_org.whereEqualTo("is_org", true);
        query_org.findInBackground((object_org, exp) -> {
            if (exp == null) {
                for (ParseObject r : object_org){
                    categoryOrgList.add(new CategoryAnimals(r.get("username").toString()));

                    setCategoryAnimalsRecycler(categoryOrgList);
                }
            }
        });
    }


    private void setCategoryAnimalsRecycler(List<CategoryAnimals> categoryList){
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager (3, LinearLayoutManager.HORIZONTAL);


        categoryRecycler = findViewById(R.id.RecyclerView_category_org_list);

        categoryRecycler.setLayoutManager(gridLayoutManager);

        categoryAnimalAdapter = new CategoryAnimalAdapter(this, categoryList, false, 2);
        categoryRecycler.setAdapter(categoryAnimalAdapter);
    }

    public void food_check(View view){
        food.setImageResource(R.drawable.button_food_type_ad_org_press);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org);

        type_help = "Еда";
    }

    public void things_check(View view){
        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org_press);
        help.setImageResource(R.drawable.button_help_type_ad_org);

        type_help = "Вещи";
    }

    public void help_check(View view){
        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org_press);

        type_help = "Волонтерство";
    }

    public void save_filter(View view){
        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra("type_help", type_help);
        intent.putExtra("flag_filter", true);
        startActivity(intent);
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
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager (3, LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        CategoryAnimalAdapter categoryAnimalAdapter = new CategoryAnimalAdapter(context, click_org_list_help, true, 2);
        recyclerView.setAdapter(categoryAnimalAdapter);
    }

}