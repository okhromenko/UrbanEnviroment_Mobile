package com.example.urbanenviroment.page.help;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.page.Dialog_Search;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.HelpAdapter;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.filter.FilterAnimal;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.filter.FilterHelp;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class HelpActivity extends AppCompatActivity {

    RecyclerView helpRecycler;
    HelpAdapter helpAdapter;

    Dialog_Search dialog_search;
    List<Help> helpList;
    List<Help> filterHelpList;
    Set<String> list_org_name;

    boolean flag_org;
    boolean flag = false;

    static class HelpComparator implements Comparator<Help> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Help o1, Help o2) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("d.M.y");

            Date date_1 = null;
            Date date_2 = null;
            try {
                date_1 = format.parse(o1.getDate_first());
                date_2 = format.parse(o2.getDate_first());
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            assert date_1 != null;
            return date_1.compareTo(date_2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        flag_org = getIntent().getBooleanExtra("flag_org", false);
        init(flag_org);

        dialog_search = new Dialog_Search();

        if (getIntent().getBooleanExtra("flag_filter", false)){
            findViewById(R.id.button_cancel_filter).setVisibility(View.VISIBLE);
            ImageButton button_cancel = findViewById(R.id.button_filter);
            button_cancel.setImageResource(R.drawable.button_filter_press);
        }
        else{
            findViewById(R.id.button_cancel_filter).setVisibility(View.GONE);
            ImageButton button_cancel = findViewById(R.id.button_filter);
            button_cancel.setImageResource(R.drawable.button_filter);
        }

        if (getIntent().getBooleanExtra("flag_filter", false)){
            TextView ads_type_help = findViewById(R.id.ads_type_help);
            ads_type_help.setVisibility(View.VISIBLE);

            String type_help = getIntent().getStringExtra("type_help");

            if (type_help != null)
                ads_type_help.setText(type_help);
            else
                ads_type_help.setText("?????? ????????????????????");
        }

        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init(flag_org);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }

    public void init(boolean flag_org){
        DateTime current_date = new DateTime();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query docRef = db.collection("Ads");

        if (flag_org)
            docRef = db.collection("Ads").whereEqualTo("userId", getIntent().getStringExtra("id_org"));

        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    helpList = new ArrayList<>();
                    list_org_name = new HashSet<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DateTime date_ads_create = DateTime.parse(document.getString("first_date"), DateTimeFormat.forPattern("d.M.y"));
                        DateTime date_ads_delete = DateTime.parse( document.getString("last_date"), DateTimeFormat.forPattern("d.M.y"));

                        if (current_date.compareTo(date_ads_create) >= 0) {
                            String id = document.getId();
                            String id_org = document.getString("userId");
                            String name_org = document.getString("username");
                            String image_org = document.getString("imageOrg");
                            String type = document.getString("type");
                            String description = document.getString("description");
                            String first_data = document.getString("first_date");
                            String last_data = document.getString("last_date");

                            helpList.add(new Help(id, id_org, name_org, image_org, type, description, first_data, last_data, status(first_data, last_data)));
                            list_org_name.add(name_org);

                            setHelpRecycler(helpList);
                            if (getIntent().getBooleanExtra("flag_filter", false))
                                filter_click(helpList);

                            Days days = Days.daysBetween(current_date, date_ads_delete);

                            if (days.getDays() < -2) {
                                db.collection("Ads").document(id).delete();
                            }
                        }
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
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
                    return "?? ????????????????";
                }
                else {
                    return "??????????????????????";
                }
            }
            else if (todayDate.compareTo(lastDate) > 0){
                return "??????????????????";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "???????????? ???????? ???? ??????????????????!";
    }

    private void setHelpRecycler(List<Help> helpList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        helpRecycler = findViewById(R.id.HelpRecycler);
        helpRecycler.setLayoutManager(layoutManager);

        helpAdapter = new HelpAdapter(this, helpList, false);
        helpRecycler.setAdapter(helpAdapter);

        TextView count_ads = findViewById(R.id.count_ads_help);
        count_ads.setText(String.valueOf(helpList.size()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void filter_click(List<Help> helpList){

        filterHelpList = new ArrayList<>(helpList);

        String type_help = getIntent().getStringExtra("type_help");

        if (FilterHelp.click_org_list_help.size() != 0 && type_help == null){
            filterHelpList.clear();
            for (CategoryAnimals word: FilterHelp.click_org_list_help){
                helpList.stream().filter(o -> o.getName_org().equals(word.getTitle())).forEach(
                        o -> {
                            filterHelpList.add(o);
                        }
                );
            }
        }
        else if (FilterHelp.click_org_list_help.size() != 0 && type_help != null){
            filterHelpList.clear();
            for (CategoryAnimals word: FilterHelp.click_org_list_help){
                helpList.stream().filter(o -> o.getName_org().equals(word.getTitle()) && o.getType_help().equals(type_help)).forEach(
                        o -> {
                            filterHelpList.add(o);
                        }
                );
            }
        }
        else if (FilterHelp.click_org_list_help.size() == 0 && type_help != null){
            filterHelpList.clear();
            helpList.stream().filter(o -> o.getType_help().equals(getIntent().getStringExtra("type_help"))).forEach(
                    o -> {
                        filterHelpList.add(o);
                    }
            );
        }

        setHelpRecycler(filterHelpList);
    }

    public void animals(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void help(View view){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void profile(View view){
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;

        if (mAuth != null)
            intent = new Intent(this, ProfileActivityUser.class);
        else
            intent = new Intent(this, RegistrationActivity.class);

        startActivity(intent);
    }

    public void organization(View view){
        Intent intent = new Intent(this, OrganizationsActivity.class);
        startActivity(intent);
    }

    public void filter(View view){
        if (!getIntent().getBooleanExtra("flag_filter", false)){
            Intent intent = new Intent(this, FilterHelp.class);
            intent.putExtra("list_org", (Serializable) list_org_name);
            startActivity(intent);
        }
        else finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort(View view){
        ImageView button_up = (ImageView) findViewById(R.id.img_sort_arrow_up);

        if (flag){
            button_up.setImageResource(R.drawable.img_sort_arrow_up);
            flag = false;
            Collections.sort(helpList, new HelpComparator().reversed());
            setHelpRecycler(helpList);
        }
        else{
            button_up.setImageResource(R.drawable.img_sort_arrow_down);
            flag = true;
            Collections.sort(helpList, new HelpComparator());
            setHelpRecycler(helpList);
        }
    }

    public void cancel_filter(View view){
        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra("flag_filter",false);
        startActivity(intent);
    }
}