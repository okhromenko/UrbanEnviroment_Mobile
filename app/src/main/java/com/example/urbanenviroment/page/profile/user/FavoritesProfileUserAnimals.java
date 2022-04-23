package com.example.urbanenviroment.page.profile.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.FavoritesProfileAnimalsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoritesProfileUserAnimals extends AppCompatActivity {

    RecyclerView FavoritesRecyclerProfileAnimals;
    FavoritesProfileAnimalsAdapter animalsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_profile);

        List<Animals> animalsList = new ArrayList<>();
        animalsList.add(new Animals("1", "Дивная долина", "img_org", "Державина 19А", "Кролик", "img_org",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
    }

    private void setAnimalsRecycler(List<Animals> animalsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        FavoritesRecyclerProfileAnimals = findViewById(R.id.FavoritesRecyclerProfileAnimals);
        FavoritesRecyclerProfileAnimals.setLayoutManager(layoutManager);

        animalsAdapter = new FavoritesProfileAnimalsAdapter(this, animalsList);
        FavoritesRecyclerProfileAnimals.setAdapter(animalsAdapter);
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