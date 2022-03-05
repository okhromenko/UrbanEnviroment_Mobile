package com.example.urbanenviroment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.adapter.OrganizationsAdapter;
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.profile.registr_authoriz.AuthorizationActivity;

import java.util.ArrayList;
import java.util.List;

public class OrganizationsActivity extends AppCompatActivity {

    RecyclerView orgRecycler;
    OrganizationsAdapter orgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations);

        List<Organizations> orgList = new ArrayList<>();
        orgList.add(new Organizations(1, "Заповедный край", "img_org", "Описание", "4", "03.03.2022"));

        setHelpRecycler(orgList);
    }

    private void setHelpRecycler(List<Organizations> orgList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        orgRecycler = findViewById(R.id.OrgRecycler);
        orgRecycler.setLayoutManager(layoutManager);

        orgAdapter = new OrganizationsAdapter(this, orgList);
        orgRecycler.setAdapter(orgAdapter);
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

    public void find(View view){
        Toast.makeText(getApplicationContext(),
                "Ну, допустим, нашел", Toast.LENGTH_SHORT).show();
    }
}