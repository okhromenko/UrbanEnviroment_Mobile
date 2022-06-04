package com.example.urbanenviroment.page.profile.user;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.urbanenviroment.adapter.FavoriteProfileAdsAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.HelpAdapter;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class FavoritesProfileUserAds extends AppCompatActivity {

    RecyclerView helpRecycler;
    com.example.urbanenviroment.adapter.FavoriteProfileAdsAdapter FavoriteProfileAdsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_profile_user_ads);

        init();
    }

    public void init(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Help> helpList = new ArrayList<>();

        db.collection("FavoriteAds").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        String name_org = document.getString("username");
                        String image_org = document.getString("imageOrg");
                        String type = document.getString("type");
                        String description = document.getString("description");
                        String first_data = document.getString("first_date");
                        String last_data = document.getString("last_date");

                        helpList.add(new Help(id, name_org, image_org, type, description, first_data, last_data, status(first_data, last_data)));
                    }
                    setHelpRecycler(helpList);
                }
            }
        });
    }

    public String status(String first_date, String last_date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date lastDate = dateFormat.parse(last_date);
            Date todayDate = new Date();
            if (todayDate.compareTo(lastDate) <= 0){
                long milliseconds = lastDate.getTime() - todayDate.getTime();
                int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
                if (days >= 2){
                    return "В процессе";
                }
                else {
                    return "Завершается";
                }
            }
            else if (todayDate.compareTo(lastDate) > 0){
                return "Выполнено";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Формат даты не совпадает!";
    }

    private void setHelpRecycler(List<Help> helpList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        helpRecycler = findViewById(R.id.FavoritesRecyclerHelp);
        helpRecycler.setLayoutManager(layoutManager);

        FavoriteProfileAdsAdapter = new FavoriteProfileAdsAdapter(this, helpList);
        helpRecycler.setAdapter(FavoriteProfileAdsAdapter);
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