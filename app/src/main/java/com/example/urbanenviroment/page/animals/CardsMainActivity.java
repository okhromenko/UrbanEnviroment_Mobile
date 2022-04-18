package com.example.urbanenviroment.page.animals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.urbanenviroment.page.Filter;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalCardsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardsMainActivity extends AppCompatActivity {

    RecyclerView AnimalsCardsRecycler;
    AnimalCardsAdapter cardsAdapter;

    boolean flag_sort = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);

        init();
    }

    public void init(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Animals");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    List<Animals> animalsList = new ArrayList<>();
                    for (ParseObject i : objects){

                        ParseQuery<ParseObject> query_kind = new ParseQuery<>("Animal_kind");
                        query_kind.whereEqualTo("objectId", i.getParseObject("id_kind").getObjectId());
                        query_kind.findInBackground((object_kind, ex) -> {
                            if (ex == null) {
                                ParseQuery<ParseObject> query_user = new ParseQuery<>("_User");
                                query_user.whereEqualTo("objectId", i.getParseObject("id_user").getObjectId());
                                query_user.findInBackground((object_user, exception) -> {
                                    if (exception == null) {
                                        String id = i.getObjectId().toString();
                                        String name_org = object_user.get(0).getString("username").toString();
                                        String image_org = Uri.parse(object_user.get(0).getParseFile("image").getUrl()).toString();
                                        String name_animal = i.get("name").toString();
                                        String image_animal = Uri.parse(i.getParseFile("image").getUrl()).toString();
                                        String age = i.get("age").toString();
                                        String kind_animal =  object_kind.get(0).get("name").toString();
                                        String state = i.get("state").toString();
                                        String species = i.get("species").toString();
                                        String description = i.get("description").toString();
                                        String sex = i.get("sex").toString();
                                        String date = i.getCreatedAt().toString();

                                        animalsList.add(new Animals(id, name_org, image_org, name_animal, image_animal,
                                                age, state, kind_animal, species, description, sex, date));

                                        setCardsRecycler(animalsList);
                                    }
                                });
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, Filter.class);
        startActivity(intent);
    }

    public void sort(View view){
        ImageView button_up = (ImageView) findViewById(R.id.img_sort_arrow_up);

        if (flag_sort){
            button_up.setImageResource(R.drawable.img_sort_arrow_up);
            flag_sort = false;
        }
        else{
            button_up.setImageResource(R.drawable.img_sort_arrow_down);
            flag_sort = true;
        }
    }
}