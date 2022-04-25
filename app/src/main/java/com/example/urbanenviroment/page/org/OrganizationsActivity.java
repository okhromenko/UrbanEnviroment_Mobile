package com.example.urbanenviroment.page.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.OrganizationsAdapter;
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.profile.org.AddHelp;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.Dialog_Search;
import com.loopeer.shadow.ShadowView;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrganizationsActivity extends AppCompatActivity {

    RecyclerView orgRecycler;
    OrganizationsAdapter orgAdapter;
    String id, name, image, date, address, description, phone, email, count_animal, count_photo, count_ads;

    Dialog dialog_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations);

        init();

        dialog_search = new Dialog(this);
    }

    public void init(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Organization");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    List<Organizations> orgList = new ArrayList<>();
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
                                                            count_animal = Integer.toString(query_count_animal);
                                                            count_ads = Integer.toString(query_count_ads);
                                                            count_photo = Integer.toString(query_count_photo);

                                                            orgList.add(new Organizations(id, name, image, phone, address, email, description,
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

    private void setOrgRecycler(List<Organizations> orgList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        orgRecycler = findViewById(R.id.OrgRecycler);
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

    public void find(View view){
        FrameLayout rectangle = (FrameLayout) findViewById(R.id.rectangle_search_org);

        if (rectangle.getVisibility() == View.GONE){
            rectangle.setVisibility(View.VISIBLE);
        }
        else{
            rectangle.setVisibility(View.GONE);
        }
    }

    public void sort(View view){

        CardView org_view = this.findViewById(R.id.org_cardview);
        TextView sort_text = this.findViewById(R.id.sort_text);
        ImageView sort_arrow = this.findViewById(R.id.sort_arrow);

        sort_arrow.setColorFilter(getResources().getColor(R.color.white));
        org_view.setCardBackgroundColor(getResources().getColor(R.color.basic_blue));
        sort_text.setTextColor(getResources().getColor(R.color.white));

        dialog_search.setContentView(R.layout.dialog_search);
        dialog_search.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_search.show();
    }

    public void accept(View view) {

        CardView org_view = this.findViewById(R.id.org_cardview);
        TextView sort_text = this.findViewById(R.id.sort_text);
        ImageView sort_arrow = this.findViewById(R.id.sort_arrow);

        sort_arrow.setColorFilter(getResources().getColor(R.color.black));
        org_view.setCardBackgroundColor(getResources().getColor(R.color.white));
        sort_text.setTextColor(getResources().getColor(R.color.black));

        dialog_search.dismiss();
    }

}