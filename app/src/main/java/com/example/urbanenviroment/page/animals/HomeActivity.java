package com.example.urbanenviroment.page.animals;

import com.example.urbanenviroment.adapter.CategoryAnimalAdapter;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.page.filter.FilterHelp;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.urbanenviroment.page.filter.FilterAnimal;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView animalsRecycler;
    AnimalsAdapter animalsAdapter;
    List<Animals> animalsList;
    List<Animals> filterAnimalList;
    boolean flag_org;
    boolean flag = false;
    String id, image_animal, date, name_animal, age, state, species, description, sex, name_org, image_org, kind_animal, address;
    String kind;

    static class AnimalsComparator implements Comparator<Animals>{

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

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );

        setContentView(R.layout.activity_home);

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

    public void init(boolean flag_org) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Collection");

        if (flag_org){
            ParseObject id_ = ParseObject.createWithoutData("_User", getIntent().getStringExtra("id_org"));
            query.whereEqualTo("id_user", id_);
        }

        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    animalsList = new ArrayList<>();
                    for (ParseObject i : objects) {

                        ParseQuery<ParseObject> query_animal = new ParseQuery<>("Animals");
                        query_animal.whereEqualTo("objectId", i.getParseObject("id_animal").getObjectId());
                        query_animal.findInBackground((object_animal, exception) -> {
                            if (exception == null) {
                                for (ParseObject j : object_animal) {

                                    ParseQuery<ParseObject> query_kind = new ParseQuery<>("Animal_kind");
                                    query_kind.whereEqualTo("objectId", j.getParseObject("id_kind").getObjectId());
                                    query_kind.findInBackground((object_kind, exp) -> {
                                        if (exp == null) {
                                            for (ParseObject r : object_kind) {
                                                kind = r.getString("name").toString();
                                            }
                                        }
                                    });

                                    ParseQuery<ParseObject> query_user = new ParseQuery<>("_User");
                                    query_user.whereEqualTo("objectId", j.getParseObject("id_user").getObjectId());
                                    query_user.findInBackground((object_user, ex) -> {
                                        if (ex == null) {
                                            for (ParseObject k : object_user) {

                                                ParseObject id_org = ParseObject.createWithoutData("_User", k.getObjectId());

                                                ParseQuery<ParseObject> query_org = new ParseQuery<>("Organization");
                                                query_org.whereEqualTo("id_user",  id_org);
                                                query_org.getFirstInBackground(new GetCallback<ParseObject>() {
                                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                                    public void done(ParseObject object_org, ParseException exp) {
                                                        if (exp == null) {
                                                            id = j.getObjectId().toString();
                                                            image_animal = Uri.parse(i.getParseFile("image").getUrl()).toString();
                                                            date = new SimpleDateFormat("d.M.y").format(i.getCreatedAt());
                                                            name_animal = j.get("name").toString();
                                                            age = j.get("age").toString();
                                                            state = j.get("state").toString();
                                                            kind_animal = kind;
                                                            species = j.get("species").toString();
                                                            description = j.get("description").toString();
                                                            sex = j.get("sex").toString();
                                                            name_org = k.getString("username").toString();
                                                            image_org = Uri.parse(k.getParseFile("image").getUrl()).toString();
                                                            address = object_org.get("address").toString();

                                                            animalsList.add(new Animals(id, name_org, image_org, address, name_animal, image_animal,
                                                                    age, state, kind_animal, species, description, sex, date));

                                                            Collections.sort(animalsList, new AnimalsComparator().reversed());
                                                            setAnimalsRecycler(animalsList);

                                                            if (getIntent().getBooleanExtra("flag_filter", false))
                                                                filter_click(animalsList);
                                                        }

                                                    }
                                                });
                                            }

                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }


    private void setAnimalsRecycler(List<Animals> animalsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        animalsRecycler = findViewById(R.id.AnimalsRecycler);
        animalsRecycler.setLayoutManager(layoutManager);

        animalsAdapter = new AnimalsAdapter(this, animalsList);
        animalsRecycler.setAdapter(animalsAdapter);

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

        setAnimalsRecycler(filterAnimalList);
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

    public void card(View view){
        Intent intent = new Intent(this, CardsMainActivity.class);
        startActivity(intent);
    }

    public void filter(View view){
        Intent intent = new Intent(this, FilterAnimal.class);
        intent.putExtra("page_last","photo");
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

    public void cancel_filter(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("flag_filter",false);
        startActivity(intent);
    }
}