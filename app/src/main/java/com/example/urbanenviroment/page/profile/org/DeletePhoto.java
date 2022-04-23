package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalPhotoDeleteOrgAdapter;
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

public class DeletePhoto extends AppCompatActivity {

    RecyclerView animalsRecycler;
    AnimalPhotoDeleteOrgAdapter animalsAdapter;

    Dialog_Search dialog_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_photo);

        List<Animals> animalsList = new ArrayList<>();

        for (int i = 0; i < 100; i++){
            animalsList.add(new Animals("1", "Дивная долина", "img_org", "Державина 19А", "Кролик", "photo_delete_animal",
                    "3 года", "здоров", "тык тык", "тыу тыу тыу", "тык тык тык тык тык тык тык",
                    "ж", "12.12.2012"));
        }

        setAnimalsRecycler(animalsList);

        dialog_search = new Dialog_Search();
    }

    private void setAnimalsRecycler(List<Animals> animalsList){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        animalsRecycler = findViewById(R.id.RecyclerView_delete_photo);
        animalsRecycler.setLayoutManager(gridLayoutManager);

        animalsAdapter = new AnimalPhotoDeleteOrgAdapter(this, animalsList);
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

    public void filter(View view){
        Intent intent = new Intent(this, Filter.class);
        startActivity(intent);
    }

    public void sort(View view){
        dialog_search.show(getSupportFragmentManager(), "fragment");
    }

    public void delete_photo_animal_icon(View view){
        Toast.makeText(getApplicationContext(),
                "Фото удалено, капитан ヽ(ー_ー )ノ", Toast.LENGTH_SHORT).show();
    }
}