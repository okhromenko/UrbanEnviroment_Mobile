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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CardsMainActivity extends AppCompatActivity {

    RecyclerView AnimalsCardsRecycler;
    AnimalCardsAdapter cardsAdapter;

    boolean flag_sort = false;
    String id, image_animal, date, name_animal, age, state, species, description, sex, name_org, image_org, kind_animal, address;
    String kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);

        init();
    }

    public void init() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Collection");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    List<Animals> animalsList = new ArrayList<>();
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
                                                    public void done(ParseObject object_org, ParseException exp) {
                                                        if (exp == null) {
                                                            id = i.getObjectId().toString();
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
                                                            setCardsRecycler(animalsList);
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