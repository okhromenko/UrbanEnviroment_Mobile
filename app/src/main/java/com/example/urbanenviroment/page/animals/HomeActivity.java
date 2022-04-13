package com.example.urbanenviroment.page.animals;

import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.parse.GetCallback;
import com.parse.Parse;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.urbanenviroment.page.Dialog_Search;
import com.example.urbanenviroment.page.Filter;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView animalsRecycler;
    AnimalsAdapter animalsAdapter;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );

        setContentView(R.layout.activity_home);

        List<Animals> animalsList = new ArrayList<>();
        animalsList.add(new Animals(1, "Дивная долина", "img_org", "Кролик", "animal_item_img",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
        animalsList.add(new Animals(2, "Дивная долина", "img_org", "Кролик", "animal_item_img",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
        animalsList.add(new Animals(3, "Дивная долина", "img_org", "Кролик", "animal_item_img",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));

        setAnimalsRecycler(animalsList);
    }

    private void initTodoList(List<ParseObject> list) {
    }

    private void setAnimalsRecycler(List<Animals> animalsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        animalsRecycler = findViewById(R.id.AnimalsRecycler);
        animalsRecycler.setLayoutManager(layoutManager);

        animalsAdapter = new AnimalsAdapter(this, animalsList);
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