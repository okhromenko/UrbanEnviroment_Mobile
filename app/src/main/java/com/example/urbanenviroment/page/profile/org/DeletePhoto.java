package com.example.urbanenviroment.page.profile.org;

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
import com.example.urbanenviroment.page.filter.FilterAnimal;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.FindCallback;
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

public class DeletePhoto extends AppCompatActivity {

    boolean flag = false;

    RecyclerView animalsRecycler;
    AnimalPhotoDeleteOrgAdapter animalsAdapter;
    List<Animals> animalsList;

    String id, image_animal, date, name_animal, age, state, species, description, sex, name_org, image_org, kind_animal, address;

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
        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Collection");
        query.orderByAscending("createdAt");
        query.whereEqualTo("id_user", parseUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    animalsList = new ArrayList<>();
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

        animalsAdapter = new AnimalPhotoDeleteOrgAdapter(this, animalsList, false);
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