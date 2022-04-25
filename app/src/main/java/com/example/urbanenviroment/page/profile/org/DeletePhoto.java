package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalPhotoDeleteOrgAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.Dialog_Search;
import com.example.urbanenviroment.page.Filter;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DeletePhoto extends AppCompatActivity {

    boolean flag = false;

    RecyclerView animalsRecycler;
    AnimalPhotoDeleteOrgAdapter animalsAdapter;

    String id, image_animal, date, name_animal, age, state, species, description, sex, name_org, image_org, kind_animal, address;

    //Dialog_Search dialog_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_photo);

        init();

        //dialog_search = new Dialog_Search();
    }

    public void init(){
        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Collection");
        query.orderByAscending("createdAt");
        query.whereEqualTo("id_user", parseUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    List<Animals> animalsList = new ArrayList<>();
                    for (ParseObject i : objects) {

                        id = i.getObjectId();
                        image_animal = Uri.parse(i.getParseFile("image").getUrl()).toString();

                        animalsList.add(new Animals(id, name_org, image_org, address, name_animal, image_animal,
                                age, state, kind_animal, species, description, sex, date));
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

    public void map(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void filter(View view){
        Intent intent = new Intent(this, Filter.class);
        startActivity(intent);
    }

    public void sort(View view){
        //dialog_search.show(getSupportFragmentManager(), "fragment");
        ImageView button_up = (ImageView) findViewById(R.id.img_sort_arrow_up);
        if (flag){
            button_up.setImageResource(R.drawable.img_sort_arrow_up);
            flag = false;
        }
        else{
            button_up.setImageResource(R.drawable.img_sort_arrow_down);
            flag = true;
        }
    }
}