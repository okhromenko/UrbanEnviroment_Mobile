package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;

public class EditHelpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_help_page);

        // Нужно взять данные: тип объявления, дата, описание и добавить их в поля ввода\правильно окрасить кнопку.
    }

    public void change(FrameLayout frame, LinearLayout layout){
        frame.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    public void cancel(FrameLayout frame, LinearLayout layout){
        frame.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
    }

    public void clear(int id){
        TextView textName = (TextView) findViewById(id);
        textName.setText("");
    }

    public void change_type_ads(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_type_ads);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_type_ads);

        change(frame, layout);
    }

    public void cancel_type_ads(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_type_ads);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_type_ads);

        ImageButton food = (ImageButton) findViewById(R.id.food_filter);
        ImageButton things = (ImageButton) findViewById(R.id.things_filter);
        ImageButton help = (ImageButton) findViewById(R.id.help_filter);

        //Нужно сделать определение типа и окрасить только ту кнопку, какого типа объявление нажато.

        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org);

        cancel(frame, layout);
    }

    public void change_last_date(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_last_date);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_last_date);

        change(frame, layout);
    }

    public void cancel_last_date(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_last_date);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_last_date);

        cancel(frame, layout);
        clear(R.id.text_edit_last_date);
    }

    public void change_description(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_description);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_description);

        change(frame, layout);
    }

    public void cancel_description(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_description);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_description);

        cancel(frame, layout);
        clear(R.id.text_edit_description);
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