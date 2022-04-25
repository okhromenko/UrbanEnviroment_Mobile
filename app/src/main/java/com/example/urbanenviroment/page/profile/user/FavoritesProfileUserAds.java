package com.example.urbanenviroment.page.profile.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.HelpAdapter;
import com.example.urbanenviroment.model.Help;
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

public class FavoritesProfileUserAds extends AppCompatActivity {

    RecyclerView helpRecycler;
    HelpAdapter helpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_profile_user_ads);

        init();
    }

    public void init(){
        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteAds");
        query.orderByAscending("createdAt");
        query.whereEqualTo("id_user", parseUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    List<Help> helpList = new ArrayList<>();

                    for (ParseObject i : objects) {
                        ParseQuery<ParseObject> query_ads = ParseQuery.getQuery("Ads");
                        query_ads.orderByDescending("createdAt");
                        query_ads.whereEqualTo("objectId", i.getParseObject("id_ads").getObjectId());
                        query_ads.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null) {

                                    for (ParseObject k : objects){
                                        ParseQuery<ParseObject> query_user = new ParseQuery<>("_User");
                                        query_user.whereEqualTo("objectId", k.getParseObject("id_user").getObjectId());
                                        query_user.findInBackground((object_user, ex) -> {
                                            if (ex == null) {
                                                String id = k.getObjectId();
                                                String name_org = object_user.get(0).getString("username");
                                                String image_org = Uri.parse(object_user.get(0).getParseFile("image").getUrl()).toString();
                                                String type = k.get("type").toString();
                                                String description = k.get("description").toString();
                                                String first_data = new SimpleDateFormat("d.M.y").format(k.getCreatedAt());
                                                String last_data = k.get("last_date").toString();

                                                helpList.add(new Help(id, name_org, image_org, type, description, last_data, status(first_data, last_data)));

                                                setHelpRecycler(helpList);
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public String status(String first_date, String last_date){
        return "потом сделаю";
    }

    private void setHelpRecycler(List<Help> helpList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        helpRecycler = findViewById(R.id.FavoritesRecyclerHelp);
        helpRecycler.setLayoutManager(layoutManager);

        helpAdapter = new HelpAdapter(this, helpList);
        helpRecycler.setAdapter(helpAdapter);
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

    public void favorites_menu_animal(View view){
        Intent intent = new Intent(this, FavoritesProfileUserAnimals.class);
        startActivity(intent);
    }

    public void favorites_menu_ads(View view){
        Intent intent = new Intent(this, FavoritesProfileUserAds.class);
        startActivity(intent);
    }

    public void favorites_menu_org(View view){
        Intent intent = new Intent(this, FavoritesProfileUserOrg.class);
        startActivity(intent);
    }
}