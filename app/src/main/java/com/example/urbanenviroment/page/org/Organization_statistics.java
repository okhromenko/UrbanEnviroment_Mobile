package com.example.urbanenviroment.page.org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Organization_statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_statistics_animal);

        statistic_rectangle(R.id.rectangle_animals_statistics, true);
        statistic_circle(R.id.circle_animals_statistics, true);
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

    //Что-то вылетает, т.ч. это все в процессе
    public void statistic_rectangle(int addressChart, boolean clickTypeAnimals){

        if (clickTypeAnimals){
            setContentView(R.layout.activity_organization_statistics_animal);
        }
        else{
            setContentView(R.layout.activity_organization_statistics_ads);
        }

        ParseQuery<ParseObject> query_animal = new ParseQuery<>("Organization");
        query_animal.whereEqualTo("objectId", getIntent().getStringExtra("id"));
        query_animal.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                ParseQuery<ParseObject> query_ads = new ParseQuery<>("Ads");
                query_ads.whereEqualTo("id_user", object.getParseObject("id_user"));
                query_ads.whereEqualTo("type", "Еда");
                query_ads.countInBackground(new CountCallback() {
                    @Override
                    public void done(int query_count_ads_food, ParseException e) {
                        query_ads.whereEqualTo("id_user", object.getParseObject("id_user"));
                        query_ads.whereEqualTo("type", "Волонтерство");
                        query_ads.countInBackground(new CountCallback() {
                            @Override
                            public void done(int query_count_ads_help, ParseException e) {
                                query_ads.whereEqualTo("id_user", object.getParseObject("id_user"));
                                query_ads.whereEqualTo("type", "Вещи");
                                query_ads.countInBackground(new CountCallback() {
                                    @Override
                                    public void done(int query_count_ads_things, ParseException e) {
                                        BarChart barChart = findViewById(addressChart);

                                        ArrayList<BarEntry> animals_statistic = new ArrayList<>();

                                        animals_statistic.add(new BarEntry(1, query_count_ads_food));
                                        animals_statistic.add(new BarEntry(2, query_count_ads_things));
                                        animals_statistic.add(new BarEntry(3, query_count_ads_help));

                                        ArrayList<String> labels = new ArrayList<>();
                                        labels.add("    ");
                                        labels.add("Еда");
                                        labels.add("Вещи");
                                        labels.add("Волонтерство");

                                        BarDataSet barDataSet = new BarDataSet(animals_statistic, "");

                                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                        barDataSet.setValueTextColor(Color.BLACK);
                                        barDataSet.setValueTextSize(10f);

                                        BarData barData = new BarData(barDataSet);

                                        barChart.setFitBars(true);
                                        barChart.setData(barData);

                                        XAxis xAxis = barChart.getXAxis();
                                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                                        xAxis.setGranularity(1f);

                                        barChart.animateY(10);
                                        barChart.getDescription().setText(" ");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    public void statistic_circle(int addressChart, boolean clickTypeAnimals){
        String typeStatistic;

        if (clickTypeAnimals)
            typeStatistic = "Animals";
        else
            typeStatistic = "Ads";

        PieChart pieChart = findViewById(addressChart);

        ArrayList<PieEntry> animals_statistic_circle = new ArrayList<>();
        animals_statistic_circle.add(new PieEntry(1, 9));
        animals_statistic_circle.add(new PieEntry(2, 7));
        animals_statistic_circle.add(new PieEntry(3, 9));

        PieDataSet pieDataSet = new PieDataSet(animals_statistic_circle, typeStatistic);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(typeStatistic);
        pieChart.animate();

    }

    public void button_animal_statistics(View view){
        statistic_rectangle(R.id.rectangle_animals_statistics, true);
        statistic_circle(R.id.circle_animals_statistics, true);
    }

    public void button_ads_statistics(View view){
        statistic_rectangle(R.id.rectangle_ads_statistics, false);
        statistic_circle(R.id.circle_ads_statistics, false);
    }

}