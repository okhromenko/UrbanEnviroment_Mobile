package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.HelpAdapter;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.Dialog_Search;
import com.example.urbanenviroment.page.animals.FilterAnimals;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;

import java.util.ArrayList;
import java.util.List;

public class EditHelp extends AppCompatActivity {

    RecyclerView helpRecycler;
    HelpAdapter helpAdapter;

    Dialog_Search dialog_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_help);

        List<Help> helpList = new ArrayList<>();
        helpList.add(new Help(1, "Заповедный край", "img_org", "Еда", "Есть елки?\n" +
                "Несите!", "03.03.2022", "В процессе"));
        helpList.add(new Help(2, "Заповедный край", "img_org", "Вещи", "Есть вещи?\n" +
                "Несите!", "03.03.2022", "Завершается"));
        helpList.add(new Help(3, "Заповедный край", "img_org", "Волонтерство", "Есть свободные руки?\n" +
                "Несите!", "03.03.2022", "Выполнено"));
        helpList.add(new Help(1, "Заповедный край", "img_org", "Еда", "Есть елки?\n" +
                "Несите!", "03.03.2022", "В процессе"));
        helpList.add(new Help(2, "Заповедный край", "img_org", "Вещи", "Есть вещи?\n" +
                "Несите!", "03.03.2022", "Завершается"));
        helpList.add(new Help(3, "Заповедный край", "img_org", "Волонтерство", "Есть свободные руки?\n" +
                "Несите!", "03.03.2022", "Выполнено"));

        setHelpRecycler(helpList);

        dialog_search = new Dialog_Search();
    }

    private void setHelpRecycler(List<Help> helpList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        helpRecycler = findViewById(R.id.EditHelpRecycler);
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

    public void filter(View view){
        Intent intent = new Intent(this, FilterAnimals.class);
        startActivity(intent);
    }

    public void sort(View view){
        dialog_search.show(getSupportFragmentManager(), "fragment");
    }

}