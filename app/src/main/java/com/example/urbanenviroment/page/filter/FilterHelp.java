package com.example.urbanenviroment.page.filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urbanenviroment.R;

public class FilterHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_help);
    }

    public void food_check(View view){

        ImageButton food = (ImageButton) findViewById(R.id.food_filter);
        ImageButton things = (ImageButton) findViewById(R.id.things_filter);
        ImageButton help = (ImageButton) findViewById(R.id.help_filter);

        food.setImageResource(R.drawable.button_food_type_ad_org_press);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org);

    }

    public void things_check(View view){

        ImageButton food = (ImageButton) findViewById(R.id.food_filter);
        ImageButton things = (ImageButton) findViewById(R.id.things_filter);
        ImageButton help = (ImageButton) findViewById(R.id.help_filter);

        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org_press);
        help.setImageResource(R.drawable.button_help_type_ad_org);
    }

    public void help_check(View view){

        ImageButton food = (ImageButton) findViewById(R.id.food_filter);
        ImageButton things = (ImageButton) findViewById(R.id.things_filter);
        ImageButton help = (ImageButton) findViewById(R.id.help_filter);

        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org_press);
    }

}