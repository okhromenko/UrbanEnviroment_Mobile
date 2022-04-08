package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalEditOrgAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.Dialog_Search;
import com.example.urbanenviroment.page.Filter;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;

import java.util.ArrayList;
import java.util.List;

public class EditAnimal extends AppCompatActivity {

    RecyclerView animalEditRecycler;
    AnimalEditOrgAdapter animalEditOrg;

    Dialog_Search dialog_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animal);

        List<Animals> animalsList = new ArrayList<>();
        animalsList.add(new Animals(1, "Дивная долина", "img_org", "Степаша", "img_org",
                "3 года", "здоров", "Кролик", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
        animalsList.add(new Animals(1, "Дивная долина", "img_org", "Василий", "img_org",
                "3 года", "здоров", "Енот", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));
        animalsList.add(new Animals(1, "Дивная долина", "img_org", "Гена", "img_org",
                "3 года", "здоров", "Сурок", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                "ж", "12.12.2012"));

        setAnimalsRecycler(animalsList);

        dialog_search = new Dialog_Search();
    }

    private void setAnimalsRecycler(List<Animals> animalsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        animalEditRecycler = findViewById(R.id.RecyclerView_edit_animal);
        animalEditRecycler.setLayoutManager(layoutManager);

        animalEditOrg = new AnimalEditOrgAdapter(this, animalsList);
        animalEditRecycler.setAdapter(animalEditOrg);

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
        Intent intent = new Intent(this, Filter.class);
        startActivity(intent);
    }

    public void sort(View view){
        dialog_search.show(getSupportFragmentManager(), "fragment");
    }

    public void setting_edit_animal(View view){
        Intent intent = new Intent(this, EditAnimalPage.class);
        startActivity(intent);
    }

    public void delete_edit_animal(View view){
        Toast.makeText(getApplicationContext(),
                "Ты все удалил :(", Toast.LENGTH_SHORT).show();
    }
}