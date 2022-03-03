package com.example.urbanenviroment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.adapter.AnimalCardsAdapter;
import com.example.urbanenviroment.adapter.AnimalsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.Cards;
import com.example.urbanenviroment.profile.registr_authoriz.AuthorizationActivity;

import java.util.ArrayList;
import java.util.List;

public class CardsMainActivity extends AppCompatActivity {

    RecyclerView AnimalsCardsRecycler;
    AnimalCardsAdapter cardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);

        List<Cards> cardsList = new ArrayList<>();
        cardsList.add(new Cards(1, "Дивная долина", "Кролик", "Степаша", "img_org",
                "Кролик нашелся одним холодным зимним днем в снегу. Он был такой крохой, что у сотрудников заповедника сжалось сердце, они решили взять его под свое крыло. Сейчас у него все хорошо."));
        cardsList.add(new Cards(2, "Дивная долина", "Кролик", "Степаша", "img_org",
                "Кролик нашелся одним холодным зимним днем в снегу. Он был такой крохой, что у сотрудников заповедника сжалось сердце, они решили взять его под свое крыло. Сейчас у него все хорошо."));
        cardsList.add(new Cards(3, "Дивная долина", "Кролик", "Степаша", "img_org",
                "Кролик нашелся одним холодным зимним днем в снегу. Он был такой крохой, что у сотрудников заповедника сжалось сердце, они решили взять его под свое крыло. Сейчас у него все хорошо."));
        cardsList.add(new Cards(4, "Дивная долина", "Кролик", "Степаша", "img_org",
                "Кролик нашелся одним холодным зимним днем в снегу. Он был такой крохой, что у сотрудников заповедника сжалось сердце, они решили взять его под свое крыло. Сейчас у него все хорошо."));

        setCardsRecycler(cardsList);
    }

    private void setCardsRecycler(List<Cards> cardsList){
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
}