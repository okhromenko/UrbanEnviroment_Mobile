package com.example.urbanenviroment.page.help;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HelpActivity extends AppCompatActivity {

    RecyclerView helpRecycler;
    HelpAdapter helpAdapter;

    Dialog_Search dialog_search;
    List<Help> helpList;
    List<Help> filterHelpList;

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
                ads_type_help.setText("Все объявления");
        }
    }

    public void init(boolean flag_org){
        DateTime current_date = new DateTime();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ads");

        if (flag_org){
            ParseObject id_ = ParseObject.createWithoutData("_User", getIntent().getStringExtra("id_org"));
            query.whereEqualTo("id_user", id_);
        }

        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    helpList = new ArrayList<>();
                    for (ParseObject i : objects){
                        ParseQuery<ParseObject> query_user = new ParseQuery<>("_User");
                        query_user.whereEqualTo("objectId", Objects.requireNonNull(i.getParseObject("id_user")).getObjectId());
                        query_user.findInBackground((object_user, ex) -> {
                            if (ex == null) {
                                DateTime date_ads_create = DateTime.parse(i.getString("first_date"), DateTimeFormat.forPattern("d.M.y"));
                                DateTime date_ads_delete = DateTime.parse(i.getString("last_date"), DateTimeFormat.forPattern("d.M.y"));

                                if (current_date.compareTo(date_ads_create) >= 0){
                                    String id = i.getObjectId();
                                    String name_org = object_user.get(0).getString("username");
                                    String image_org = Uri.parse(Objects.requireNonNull(object_user.get(0).getParseFile("image")).getUrl()).toString();
                                    String type = i.getString("type");
                                    String description = i.getString("description");
                                    String first_data = i.getString("first_date");
                                    String last_data = i.getString("last_date");

                                    helpList.add(new Help(id, name_org, image_org, type, description, first_data, last_data, status(first_data, last_data)));
                                    setHelpRecycler(helpList);
                                    if (getIntent().getBooleanExtra("flag_filter", false))
                                        filter_click(helpList);

                                    Days days = Days.daysBetween(current_date, date_ads_delete);
                                    //Тут мы устанавливаем срок, после которого объявление будет удалено
                                    if (days.getDays() > 2){
                                        i.deleteInBackground();
                                    }
                                }
                            }
                        });
                    }

                } else
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show();
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
        if (!getIntent().getBooleanExtra("flag_filter", false)){
            Intent intent = new Intent(this, FilterHelp.class);
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