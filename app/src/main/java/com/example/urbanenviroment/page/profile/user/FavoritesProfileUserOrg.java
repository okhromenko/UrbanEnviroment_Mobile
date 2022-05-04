package com.example.urbanenviroment.page.profile.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.HelpAdapter;
import com.example.urbanenviroment.adapter.OrganizationsAdapter;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.org.EditHelp;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FavoritesProfileUserOrg extends AppCompatActivity {

    RecyclerView orgRecycler;
    OrganizationsAdapter orgAdapter;
    String id, name, image, date, address, description, phone, email, website, count_animal, count_photo, count_ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_profile_user_org);

        init();
    }

    public void init(){
        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteOrganization");
        query.orderByAscending("createdAt");
        query.whereEqualTo("id_user", parseUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    List<Organizations> orgList = new ArrayList<>();

                    for (ParseObject p : objects) {
                        ParseQuery<ParseObject> query_org = ParseQuery.getQuery("Organization");
                        query_org.whereEqualTo("objectId", p.getParseObject("id_org").getObjectId());
                        query_org.orderByAscending("createdAt");
                        query_org.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null) {

                                    for (ParseObject i : objects){
                                        ParseQuery<ParseObject> query_user = new ParseQuery<>("_User");
                                        query_user.whereEqualTo("objectId", i.getParseObject("id_user").getObjectId());
                                        query_user.getFirstInBackground(new GetCallback<ParseObject>() {
                                            public void done(ParseObject object_user, ParseException ex) {
                                                if (ex == null) {
                                                    ParseObject id_user = ParseObject.createWithoutData("_User", object_user.getObjectId());

                                                    ParseQuery<ParseObject> query_animal = new ParseQuery<>("Animals");
                                                    query_animal.whereEqualTo("id_user", id_user);
                                                    query_animal.countInBackground(new CountCallback() {
                                                        @Override
                                                        public void done(int query_count_animal, ParseException e) {
                                                            ParseQuery<ParseObject> query_animal = new ParseQuery<>("Ads");
                                                            query_animal.whereEqualTo("id_user", id_user);
                                                            query_animal.countInBackground(new CountCallback() {
                                                                @Override
                                                                public void done(int query_count_ads, ParseException e) {
                                                                    ParseQuery<ParseObject> query_animal = new ParseQuery<>("Collection");
                                                                    query_animal.whereEqualTo("id_user", id_user);
                                                                    query_animal.countInBackground(new CountCallback() {
                                                                        @Override
                                                                        public void done(int query_count_photo, ParseException e) {
                                                                            id = i.getObjectId();
                                                                            name = object_user.getString("username");
                                                                            image = Uri.parse(object_user.getParseFile("image").getUrl()).toString();
                                                                            date = new SimpleDateFormat("d.M.y").format(i.getCreatedAt());
                                                                            address = i.get("address").toString();
                                                                            description = i.get("description").toString();
                                                                            phone = i.get("phone").toString();
                                                                            email = object_user.getString("email");
                                                                            website = i.get("website").toString();
                                                                            count_animal = Integer.toString(query_count_animal);
                                                                            count_ads = Integer.toString(query_count_ads);
                                                                            count_photo = Integer.toString(query_count_photo);

                                                                            orgList.add(new Organizations(id, name, image, phone, address, email, website, description,
                                                                                    count_animal, count_ads, count_photo, date));
                                                                            setOrgRecycler(orgList);
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
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

    private void setOrgRecycler(List<Organizations> orgList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        orgRecycler = findViewById(R.id.FavoritesRecyclerOrg);
        orgRecycler.setLayoutManager(layoutManager);

        orgAdapter = new OrganizationsAdapter(this, orgList);
        orgRecycler.setAdapter(orgAdapter);
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