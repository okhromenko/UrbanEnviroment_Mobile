package com.example.urbanenviroment.page.profile.org;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.HelpAdapter;
import com.example.urbanenviroment.model.Help;
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

public class EditHelp extends AppCompatActivity {

    boolean flag = false;

    RecyclerView helpRecycler;
    HelpAdapter helpAdapter;
    List<Help> helpList;


    static class HelpComparator implements Comparator<Help> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Help o1, Help o2) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

            Date date_1 = null;
            Date date_2 = null;
            try {
                date_1 = format.parse(o1.getDate_first());
                date_2 = format.parse(o2.getDate_first());
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
        setContentView(R.layout.activity_edit_help);

        init();
    }

    public void init(){
        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ads");
        query.whereEqualTo("id_user", parseUser);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    helpList = new ArrayList<>();
                    for (ParseObject i : objects){

                        ParseQuery<ParseObject> query_user = new ParseQuery<>("_User");
                        query_user.whereEqualTo("objectId", i.getParseObject("id_user").getObjectId());
                        query_user.findInBackground((object_user, ex) -> {
                            if (ex == null) {
                                String id = i.getObjectId().toString();
                                String name_org = object_user.get(0).getString("username").toString();
                                String image_org = Uri.parse(object_user.get(0).getParseFile("image").getUrl()).toString();
                                String type = i.get("type").toString();
                                String description = i.get("description").toString();
                                String first_data = i.get("first_date").toString();
                                String last_data = i.get("last_date").toString();

                                helpList.add(new Help(id, name_org, image_org, type, description, first_data, last_data, status(first_data, last_data)));

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

    public String status(String first_date, String last_date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date lastDate = dateFormat.parse(last_date);
            Date todayDate = new Date();
            if (todayDate.compareTo(lastDate) <= 0){
                long milliseconds = lastDate.getTime() - todayDate.getTime();
                int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
                if (days >= 2){
                    return "В процессе";
                }
                else {
                    return "Завершается";
                }
            }
            else if (todayDate.compareTo(lastDate) > 0){
                return "Выполнено";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Формат даты не совпадает!";
    }

    private void setHelpRecycler(List<Help> helpList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        helpRecycler = findViewById(R.id.EditHelpRecycler);
        helpRecycler.setLayoutManager(layoutManager);

        helpAdapter = new HelpAdapter(this, helpList, true);
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
            Collections.sort(helpList, new HelpComparator().reversed());
            setHelpRecycler(helpList);
        }
        else{
            button_up.setImageResource(R.drawable.img_sort_arrow_down);
            flag = true;
            Collections.sort(helpList, new HelpComparator());
            setHelpRecycler(helpList);
        }
    }
}