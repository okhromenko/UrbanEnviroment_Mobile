package com.example.urbanenviroment.profile.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.urbanenviroment.HelpActivity;
import com.example.urbanenviroment.HomeActivity;
import com.example.urbanenviroment.MapActivity;
import com.example.urbanenviroment.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.HelpAdapter;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.profile.registr_authoriz.AuthorizationActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoritesProfileUserAds extends AppCompatActivity {

    RecyclerView helpRecycler;
    HelpAdapter helpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_profile_user_ads);

        List<Help> helpList = new ArrayList<>();
        helpList.add(new Help(11, "Заповедный край", "img_org", "Еда", "Есть елки?\n" +
                "Несите!", "03.03.2022", "В процессе"));
        helpList.add(new Help(12, "Заповедный край", "img_org", "Вещи", "Есть вещи?\n" +
                "Несите!", "03.03.2022", "Завершается"));
        helpList.add(new Help(13, "Заповедный край", "img_org", "Волонтерство", "Есть свободные руки?\n" +
                "Несите!", "03.03.2022", "Выполнено"));
        helpList.add(new Help(15, "Заповедный край", "img_org", "Еда", "Есть елки?\n" +
                "Несите!", "03.03.2022", "В процессе"));
        helpList.add(new Help(17, "Заповедный край", "img_org", "Вещи", "Есть вещи?\n" +
                "Несите!", "03.03.2022", "Завершается"));
        helpList.add(new Help(86, "Заповедный край", "img_org", "Волонтерство", "Есть свободные руки?\n" +
                "Несите!", "03.03.2022", "Выполнено"));

        setHelpRecycler(helpList);
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
}