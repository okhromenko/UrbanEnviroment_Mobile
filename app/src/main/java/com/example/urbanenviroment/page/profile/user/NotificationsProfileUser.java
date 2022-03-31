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
import com.example.urbanenviroment.adapter.NotificationsAdapter;
import com.example.urbanenviroment.adapter.OrganizationsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.model.Notifications;
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

    RecyclerView notificationsRecycler;
    NotificationsAdapter notificationsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_profile);

        List<Notifications> notificationsList = new ArrayList<>();
        notificationsList.add(new Notifications(1, "Дивная долина", "img_org", "животное"));
        notificationsList.add(new Notifications(2, "Дивная долина", "img_org", "объявления"));
        notificationsList.add(new Notifications(3, "Дивная долина", "img_org", "фото"));

        setNotificationsRecycler(notificationsList);
    }

    private void setNotificationsRecycler(List<Notifications> notificationsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        notificationsRecycler = findViewById(R.id.NotificationRecyclerProfile);
        notificationsRecycler.setLayoutManager(layoutManager);

        notificationsAdapter = new NotificationsAdapter(this, notificationsList);
        notificationsRecycler.setAdapter(notificationsAdapter);
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