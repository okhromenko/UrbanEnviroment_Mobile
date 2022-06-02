package com.example.urbanenviroment.page.org;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Organization_statistics extends AppCompatActivity {

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_statistics_animal);

        db = FirebaseFirestore.getInstance();

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


    public void statistic_rectangle(int addressChart, boolean clickTypeAnimals) {

        if (clickTypeAnimals) {
            setContentView(R.layout.activity_organization_statistics_animal);
        } else {
            setContentView(R.layout.activity_organization_statistics_ads);
        }

        db.collection("Ads").whereEqualTo("userId", getIntent().getStringExtra("id"))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> count_list = new ArrayList<>();
                            count_list.addAll(task.getResult().getDocuments());

                            long count_food = count_list.stream().filter(i -> i.get("type").equals("Еда")).count();
                            long count_help = count_list.stream().filter(i -> i.get("type").equals("Волонтерство")).count();
                            long count_things = count_list.stream().filter(i -> i.get("type").equals("Вещи")).count();

                            BarChart barChart = findViewById(addressChart);

                            ArrayList<BarEntry> animals_statistic = new ArrayList<>();

                            animals_statistic.add(new BarEntry(1, count_food));
                            animals_statistic.add(new BarEntry(2, count_things));
                            animals_statistic.add(new BarEntry(3, count_help));

                            ArrayList<String> labels = new ArrayList<>();
                            labels.add("    ");
                            labels.add("Еда");
                            labels.add("Вещи");
                            labels.add("Волонтерство");

                            BarDataSet barDataSet = new BarDataSet(animals_statistic, "");

                            int food = getResources().getColor(R.color.food, getTheme());
                            int things = getResources().getColor(R.color.things, getTheme());
                            int help = getResources().getColor(R.color.help, getTheme());

                            barDataSet.setColors(new int[] {food, things, help});
                            barDataSet.setValueTextColor(Color.BLACK);
                            barDataSet.setValueTextSize(12f);

                            BarData barData = new BarData(barDataSet);

                            barChart.setFitBars(true);
                            barChart.setData(barData);

                            barChart.setTouchEnabled(false);
                            barChart.setDragEnabled(false);
                            barChart.setScaleEnabled(false);
                            barChart.setPinchZoom(false);
                            barChart.setDoubleTapToZoomEnabled(false);

                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setTextSize(14f);
                            xAxis.setGridColor(getResources().getColor(R.color.light_gray_2, getTheme()));
                            xAxis.setAxisLineColor(getResources().getColor(R.color.basic_blue, getTheme()));
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                            xAxis.setGranularity(1f);
                            xAxis.setAxisLineWidth(1f);
                            xAxis.setDrawGridLines(false);


                            barChart.animateY(10);
                            barChart.getDescription().setText(" ");
                        }
                    }
                });
    }

    public void statistic_circle(int addressChart, boolean clickTypeAnimals){
        String typeStatistic;

        if (clickTypeAnimals)
            typeStatistic = "Животные";
        else
            typeStatistic = "Объявления";

        db.collection("Animal").whereEqualTo("userId", getIntent().getStringExtra("id"))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> count_list = new ArrayList<>();
                            count_list.addAll(task.getResult().getDocuments());

                            long count_woman = count_list.stream().filter(i -> i.get("sex").equals("Самка")).count();
                            long count_man = count_list.stream().filter(i -> i.get("sex").equals("Самец")).count();

                            PieChart pieChart = findViewById(addressChart);

                            ArrayList<PieEntry> animals_statistic_circle = new ArrayList<>();
                            animals_statistic_circle.add(new PieEntry(count_woman, "Самка"));
                            animals_statistic_circle.add(new PieEntry(count_man, "Самец"));

                            PieDataSet pieDataSet = new PieDataSet(animals_statistic_circle, typeStatistic);

                            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            pieDataSet.setValueTextColor(Color.BLACK);
                            pieDataSet.setValueTextSize(10f);


                            PieData data = new PieData(pieDataSet);
                            data.setDrawValues(false);
                            data.setValueFormatter(new PercentFormatter(pieChart));
                            pieChart.setCenterText(typeStatistic);
                            pieChart.setCenterTextSize(16f);
                            pieChart.setCenterTextColor(R.color.basic_blue);

                            data.setValueTextSize(12f);
                            data.setValueTextColor(Color.BLACK);

                            pieChart.setData(data);
                            pieChart.invalidate();

                            pieChart.animateY(1400, Easing.EaseInOutQuad);
                        }
                    }
                });
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