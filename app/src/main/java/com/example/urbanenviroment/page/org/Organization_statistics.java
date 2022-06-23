package com.example.urbanenviroment.page.org;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.R;

import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Organization_statistics extends AppCompatActivity {

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_statistics_animal);

        db = FirebaseFirestore.getInstance();

        @SuppressLint("ResourceType") View view = findViewById(R.layout.activity_organization_statistics_animal);
        button_animal_statistics(view);

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

        @RequiresApi(api = Build.VERSION_CODES.M)
    private void statistic_animal_rectangle(Map<String, Integer> count_kind){

        BarChart barChart = findViewById(R.id.rectangle_animals_statistics);

        ArrayList<BarEntry> animals_statistic = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        labels.add("    ");

        int number = 1;

        for (Map.Entry<String, Integer> i : count_kind.entrySet()){
            animals_statistic.add(new BarEntry(number++, (int)i.getValue()));
            labels.add(i.getKey());
        }


        BarDataSet barDataSet = new BarDataSet(animals_statistic, "");

        barDataSet.setColor(getResources().getColor(R.color.blue_light, getTheme()));
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
        barChart.setExtraOffsets(0,0,0,10);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        YAxis rightAxis = barChart.getAxisRight();
        YAxis leftAxis = barChart.getAxisLeft();
        rightAxis.setEnabled(false);
        leftAxis.setTextColor(getResources().getColor(R.color.white, getTheme()));
        leftAxis.setTextSize(12f);
        leftAxis.setDrawAxisLine(false);

        leftAxis.setStartAtZero(true);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setZeroLineColor(getResources().getColor(R.color.basic_blue, getTheme()));
        leftAxis.setZeroLineWidth(2f);
        leftAxis.setLabelCount(5, false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextSize(14f);
        xAxis.setLabelRotationAngle(75);
        xAxis.setGridColor(getResources().getColor(R.color.light_gray_2, getTheme()));
        xAxis.setAxisLineColor(getResources().getColor(R.color.basic_blue, getTheme()));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        //xAxis.setGranularityEnabled(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);


        barChart.animateY(10);
        barChart.getDescription().setText(" ");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void statistic_animal_circle(long count_woman, long count_man){
        setContentView(R.layout.activity_organization_statistics_animal);

        PieChart pieChart = findViewById(R.id.circle_animals_statistics);

        ArrayList<PieEntry> animals_statistic_circle = new ArrayList<>();
        if (count_woman != 0)
            animals_statistic_circle.add(new PieEntry(count_woman, "Самка"));
        if (count_man != 0)
            animals_statistic_circle.add(new PieEntry(count_man, "Самец"));

        PieDataSet pieDataSet = new PieDataSet(animals_statistic_circle, "Животные");

        pieDataSet.setColors(new int[] {getResources().getColor(R.color.pink, getTheme()),
                getResources().getColor(R.color.blue, getTheme())});
        if (count_woman != 0)
            pieDataSet.setColors(new int[] {getResources().getColor(R.color.blue, getTheme()),
                    getResources().getColor(R.color.pink, getTheme())});

        PieData data = new PieData(pieDataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);
        pieChart.setCenterText("Животные");
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(getResources().getColor(R.color.basic_blue, getTheme()));

        Description description = pieChart.getDescription();
        description.setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void statistic_ads_circle(long count_in_progress, long count_completion, long count_complete){
        PieChart pieChart = findViewById(R.id.circle_ads_statistics);

        ArrayList<PieEntry> animals_statistic_circle = new ArrayList<>();
        if (count_in_progress != 0)
            animals_statistic_circle.add(new PieEntry(count_in_progress, "В процессе"));
        if (count_completion != 0)
            animals_statistic_circle.add(new PieEntry(count_completion, "Завершается"));
        if (count_complete != 0)
            animals_statistic_circle.add(new PieEntry(count_complete, "Выполнено"));


        PieDataSet pieDataSet = new PieDataSet(animals_statistic_circle, "Объявления");

        pieDataSet.setColors(new int[] {getResources().getColor(R.color.food, getTheme()), getResources().getColor(R.color.red_static, getTheme()), getResources().getColor(R.color.money, getTheme())});

        PieData data = new PieData(pieDataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Объявления");
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(getResources().getColor(R.color.basic_blue, getTheme()));

        Description description = pieChart.getDescription();
        description.setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void statistic_ads_rectangle(long count_food, long count_ads, long count_things, long count_money, long count_other){

        setContentView(R.layout.activity_organization_statistics_ads);

        BarChart barChart = findViewById(R.id.rectangle_ads_statistics);

        ArrayList<BarEntry> animals_statistic = new ArrayList<>();

        animals_statistic.add(new BarEntry(1, count_food));
        animals_statistic.add(new BarEntry(2, count_things));
        animals_statistic.add(new BarEntry(3, count_ads));
        animals_statistic.add(new BarEntry(4, count_money));
        animals_statistic.add(new BarEntry(5, count_other));

        ArrayList<String> labels = new ArrayList<>();
        labels.add("    ");
        labels.add("Еда");
        labels.add("Вещи");
        labels.add("Волонтерство");
        labels.add("Финансы");
        labels.add("Другое");

        BarDataSet barDataSet = new BarDataSet(animals_statistic, "");

        barDataSet.setColors(new int[] {getResources().getColor(R.color.food, getTheme()), getResources().getColor(R.color.things, getTheme()), getResources().getColor(R.color.help, getTheme()), getResources().getColor(R.color.money, getTheme()), getResources().getColor(R.color.other, getTheme())});
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
        barChart.setExtraOffsets(0,0,0,10);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        YAxis rightAxis = barChart.getAxisRight();
        YAxis leftAxis = barChart.getAxisLeft();
        rightAxis.setEnabled(false);
        leftAxis.setTextColor(getResources().getColor(R.color.white, getTheme()));
        leftAxis.setTextSize(12f);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setStartAtZero(true);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setZeroLineColor(getResources().getColor(R.color.basic_blue, getTheme()));
        leftAxis.setZeroLineWidth(2f);
        leftAxis.setLabelCount(5, false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextSize(14f);
        xAxis.setLabelRotationAngle(75);
        xAxis.setGridColor(getResources().getColor(R.color.light_gray_2, getTheme()));
        xAxis.setAxisLineColor(getResources().getColor(R.color.basic_blue, getTheme()));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);


        barChart.animateY(10);
        barChart.getDescription().setText(" ");
    }

    private String status(String first_date, String last_date){
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

public void button_animal_statistics(View view){
    Map<String, Integer> count_kind = new HashMap<>();

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

                        for (DocumentSnapshot i : count_list){
                            String kind = i.getString("kind");
                            if (count_kind.containsKey(kind))
                                count_kind.put(kind, count_kind.get(kind) + 1);
                            else
                                count_kind.put(kind, 1);
                        }

                        statistic_animal_circle(count_woman, count_man);
                        statistic_animal_rectangle(count_kind);
                    }
                }
            });
}

    public void button_ads_statistics(View view){
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
                            long count_money = count_list.stream().filter(i -> i.get("type").equals("Финансы")).count();
                            long count_other = count_list.stream().filter(i -> i.get("type").equals("Другое")).count();

                            long count_in_progress = count_list.stream().filter(i -> status(i.getString("first_date"),
                                    i.getString("last_date")).equals("В процессе")).count();

                            long count_completion = count_list.stream().filter(i -> status(i.getString("first_date"),
                                    i.getString("last_date")).equals("Завершается")).count();

                            long count_complete = count_list.stream().filter(i -> status(i.getString("first_date"),
                                    i.getString("last_date")).equals("Выполнено")).count();


                            statistic_ads_rectangle(count_food, count_help, count_things, count_money,count_other);
                            statistic_ads_circle(count_in_progress, count_completion, count_complete);
                        }
                    }
                });
    }

}