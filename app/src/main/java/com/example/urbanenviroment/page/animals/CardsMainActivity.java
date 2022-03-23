package com.example.urbanenviroment.page.animals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalCardsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;

import java.util.ArrayList;
import java.util.List;

public class CardsMainActivity extends AppCompatActivity {

    RecyclerView AnimalsCardsRecycler;
    AnimalCardsAdapter cardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);

        List<Animals> cardsList = new ArrayList<>();
        cardsList.add(new Animals(1, "Дивная долина", "img_org", "Кролик", "img_org",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
        cardsList.add(new Animals(1, "Дивная долина", "img_org", "Кролик", "img_org",
                "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
        setCardsRecycler(cardsList);
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
        Intent intent = new Intent(this, FilterAnimals.class);
        startActivity(intent);
    }

}