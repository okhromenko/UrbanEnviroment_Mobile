package com.example.urbanenviroment.page.profile.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.adapter.AnimalCardsAdapter;
import com.example.urbanenviroment.adapter.AnimalsAdapter;
import com.example.urbanenviroment.adapter.HelpAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.Dialog_Search;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;

import java.util.ArrayList;
import java.util.List;

public class NotificationsProfileUser extends AppCompatActivity {

    RecyclerView animalsRecycler;
    AnimalsAdapter animalsAdapter;

    RecyclerView helpRecycler;
    HelpAdapter helpAdapter;

    RecyclerView AnimalsCardsRecycler;
    AnimalCardsAdapter cardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_profile);

        List<Animals> cardsList = new ArrayList<>();
        cardsList.add(new Animals(1, "Дивная долина", "img_org", "Кролик", "img_org",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
        cardsList.add(new Animals(1, "Дивная долина", "img_org", "Кролик", "img_org",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));

        List<Help> helpList = new ArrayList<>();
        helpList.add(new Help(1, "Заповедный край", "img_org", "Еда", "Есть елки?\n" +
                "Несите!", "03.03.2022", "В процессе"));
        helpList.add(new Help(2, "Заповедный край", "img_org", "Вещи", "Есть вещи?\n" +
                "Несите!", "03.03.2022", "Завершается"));


        List<Animals> animalsList = new ArrayList<>();
        animalsList.add(new Animals(1, "Дивная долина", "img_org", "Кролик", "animal_item_img",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
        animalsList.add(new Animals(1, "Дивная долина", "img_org", "Кролик", "animal_item_img",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));

        setAnimalsRecycler(animalsList, helpList, cardsList);
    }

    private void setAnimalsRecycler(List<Animals> animalsList, List<Help> helpList, List<Animals> cardsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        RecyclerView RecyclerView = findViewById(R.id.NotificationRecyclerProfile);

        animalsAdapter = new AnimalsAdapter(this, animalsList);
        helpAdapter = new HelpAdapter(this, helpList);
        cardsAdapter = new AnimalCardsAdapter(this, cardsList);

        ConcatAdapter concatAdapter = new ConcatAdapter(animalsAdapter, helpAdapter, cardsAdapter);

        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.setAdapter(concatAdapter);

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
}