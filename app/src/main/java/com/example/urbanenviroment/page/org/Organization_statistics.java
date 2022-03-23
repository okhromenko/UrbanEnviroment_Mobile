package com.example.urbanenviroment.page.org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Organization_statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_statistics_animal);

        BarChart barChart = findViewById(R.id.rectangle_animals_statistics);

        ArrayList<BarEntry> animals_statistic = new ArrayList<>();
        animals_statistic.add(new BarEntry(1, 9));
        animals_statistic.add(new BarEntry(2, 7));
        animals_statistic.add(new BarEntry(3, 9));
        animals_statistic.add(new BarEntry(4, 8));
        animals_statistic.add(new BarEntry(5, 5));
        animals_statistic.add(new BarEntry(6, 6));
        animals_statistic.add(new BarEntry(7, 2));

        BarDataSet barDataSet = new BarDataSet(animals_statistic, "Animals");

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(10);
        barChart.getDescription().setText(" ");


        PieChart pieChart = findViewById(R.id.circle_animals_statistics);

        ArrayList<PieEntry> animals_statistic_circle = new ArrayList<>();
        animals_statistic_circle.add(new PieEntry(1, 9));
        animals_statistic_circle.add(new PieEntry(2, 7));
        animals_statistic_circle.add(new PieEntry(3, 9));
        animals_statistic_circle.add(new PieEntry(4, 8));
        animals_statistic_circle.add(new PieEntry(5, 5));
        animals_statistic_circle.add(new PieEntry(6, 6));
        animals_statistic_circle.add(new PieEntry(7, 2));

        PieDataSet pieDataSet = new PieDataSet(animals_statistic_circle, "Animals");

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Animals");
        pieChart.animate();
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

    public void button_animal_statistics(View view){
        setContentView(R.layout.activity_organization_statistics_animal);

        BarChart barChart = findViewById(R.id.rectangle_animals_statistics);

        ArrayList<BarEntry> animals_statistic = new ArrayList<>();
        animals_statistic.add(new BarEntry(1, 9));
        animals_statistic.add(new BarEntry(2, 7));
        animals_statistic.add(new BarEntry(3, 9));
        animals_statistic.add(new BarEntry(4, 8));
        animals_statistic.add(new BarEntry(5, 5));
        animals_statistic.add(new BarEntry(6, 6));
        animals_statistic.add(new BarEntry(7, 2));

        BarDataSet barDataSet = new BarDataSet(animals_statistic, "Animals");

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(10);
        barChart.getDescription().setText(" ");



        PieChart pieChart = findViewById(R.id.circle_animals_statistics);

        ArrayList<PieEntry> animals_statistic_circle = new ArrayList<>();
        animals_statistic_circle.add(new PieEntry(1, 9));
        animals_statistic_circle.add(new PieEntry(2, 7));
        animals_statistic_circle.add(new PieEntry(3, 9));
        animals_statistic_circle.add(new PieEntry(4, 8));
        animals_statistic_circle.add(new PieEntry(5, 5));
        animals_statistic_circle.add(new PieEntry(6, 6));
        animals_statistic_circle.add(new PieEntry(7, 2));

        PieDataSet pieDataSet = new PieDataSet(animals_statistic_circle, "Animals");

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Animals");
        pieChart.animate();
    }

    public void button_ads_statistics(View view){
        setContentView(R.layout.activity_organization_statistics_ads);

        BarChart barChart = findViewById(R.id.rectangle_ads_statistics);

        ArrayList<BarEntry> animals_statistic = new ArrayList<>();
        animals_statistic.add(new BarEntry(1, 10));
        animals_statistic.add(new BarEntry(2, 2));
        animals_statistic.add(new BarEntry(3, 3));
        animals_statistic.add(new BarEntry(4, 7));
        animals_statistic.add(new BarEntry(5, 2));
        animals_statistic.add(new BarEntry(6, 2));
        animals_statistic.add(new BarEntry(7, 6));

        BarDataSet barDataSet = new BarDataSet(animals_statistic, "Ads");

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(10);
        barChart.getDescription().setText(" ");



        PieChart pieChart = findViewById(R.id.circle_ads_statistics);

        ArrayList<PieEntry> animals_statistic_circle = new ArrayList<>();
        animals_statistic_circle.add(new PieEntry(1, 9));
        animals_statistic_circle.add(new PieEntry(2, 7));
        animals_statistic_circle.add(new PieEntry(3, 9));
        animals_statistic_circle.add(new PieEntry(4, 8));
        animals_statistic_circle.add(new PieEntry(5, 5));
        animals_statistic_circle.add(new PieEntry(6, 6));
        animals_statistic_circle.add(new PieEntry(7, 2));

        PieDataSet pieDataSet = new PieDataSet(animals_statistic_circle, "Ads");

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Ads");
        pieChart.animate();
    }

}