package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalEditOrgAdapter;
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

public class EditAnimal extends AppCompatActivity {

    RecyclerView animalEditRecycler;
    AnimalEditOrgAdapter animalEditOrg;

    Dialog_Search dialog_search;
    String id, image_animal, date, name_animal, age, state, species, description, sex, name_org, image_org, kind_animal, address;
    String kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animal);

        init();

        dialog_search = new Dialog_Search();
    }

    public void init() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        List<Animals> animalsList = new ArrayList<>();

        ParseQuery<ParseObject> query_animal = new ParseQuery<>("Animals");
        query_animal.whereEqualTo("id_user", parseUser);
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
                                            id = j.getObjectId();
                                            image_animal = Uri.parse(j.getParseFile("image").getUrl()).toString();
                                            date = new SimpleDateFormat("d.M.y").format(j.getCreatedAt());
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
                                            setAnimalsRecycler(animalsList);
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

    private void setAnimalsRecycler(List<Animals> animalsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        animalEditRecycler = findViewById(R.id.RecyclerView_edit_animal);
        animalEditRecycler.setLayoutManager(layoutManager);

        animalEditOrg = new AnimalEditOrgAdapter(this, animalsList);
        animalEditRecycler.setAdapter(animalEditOrg);

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
        dialog_search.show(getSupportFragmentManager(), "fragment");
    }



    public void delete_edit_animal(View view){
        Toast.makeText(getApplicationContext(),
                "Ты все удалил :(", Toast.LENGTH_SHORT).show();
    }
}