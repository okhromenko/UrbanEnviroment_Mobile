package com.example.urbanenviroment.page.filter;

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

    ArrayList<String> name_org_list = new ArrayList<>();
    public static ArrayList<CategoryAnimals> click_org_list = new ArrayList<>();
    RecyclerView categoryRecycler;
    ArrayAdapter<String> arrayAdapter;
    CategoryAnimalAdapter categoryAnimalAdapter;
    ListView listViewOrg;
    String type_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_help);

        click_org_list.clear();

        init();

        listViewOrg = findViewById(R.id.list_filter_help_org);

        SearchView searchViewOrg = (SearchView) findViewById(R.id.search_view_ads_filter_org);
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
                click_org_list.add(new CategoryAnimals(tv.getText().toString()));
                setCategoryAnimalsRecycler(click_org_list, true);
            }
        });
    }

    public void init(){
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


    private void setCategoryAnimalsRecycler(List<CategoryAnimals> categoryList, boolean click){
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager (3, LinearLayoutManager.HORIZONTAL);

        categoryRecycler = findViewById(R.id.RecyclerView_org_list);
        categoryRecycler.setLayoutManager(gridLayoutManager);

        categoryAnimalAdapter = new CategoryAnimalAdapter(this, categoryList, click);
        categoryRecycler.setAdapter(categoryAnimalAdapter);
    }

    public void food_check(View view){

        ImageButton food = (ImageButton) findViewById(R.id.food_filter);
        ImageButton things = (ImageButton) findViewById(R.id.things_filter);
        ImageButton help = (ImageButton) findViewById(R.id.help_filter);

        food.setImageResource(R.drawable.button_food_type_ad_org_press);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org);

        type_help = "Еда";
    }

    public void things_check(View view){

        ImageButton food = (ImageButton) findViewById(R.id.food_filter);
        ImageButton things = (ImageButton) findViewById(R.id.things_filter);
        ImageButton help = (ImageButton) findViewById(R.id.help_filter);

        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org_press);
        help.setImageResource(R.drawable.button_help_type_ad_org);

        type_help = "Вещи";
    }

    public void help_check(View view){

        ImageButton food = (ImageButton) findViewById(R.id.food_filter);
        ImageButton things = (ImageButton) findViewById(R.id.things_filter);
        ImageButton help = (ImageButton) findViewById(R.id.help_filter);

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

}