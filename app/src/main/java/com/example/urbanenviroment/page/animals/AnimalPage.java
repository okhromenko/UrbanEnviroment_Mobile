package com.example.urbanenviroment.page.animals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.squareup.picasso.Picasso;

public class AnimalPage extends AppCompatActivity {
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_page);

        TextView kind_animal_page = (TextView) findViewById(R.id.kind_animal_page);
        TextView species_animal_page = (TextView) findViewById(R.id.species_animal_page);
        TextView data_animal_page = (TextView) findViewById(R.id.data_animal_page);
        ImageView image_animal_page = (ImageView) findViewById(R.id.image_animal_page);
        TextView name_animal_page = (TextView) findViewById(R.id.name_animal_page);
        TextView description_animal_page = (TextView) findViewById(R.id.description_animal_page);
        TextView sex_animal_page = (TextView) findViewById(R.id.sex_animal_page);
        TextView age_animal_page = (TextView) findViewById(R.id.age_animal_page);
        TextView state_animal_page = (TextView) findViewById(R.id.state_animal_page);

        kind_animal_page.setText(getIntent().getStringExtra("kind_animal"));
        species_animal_page.setText(getIntent().getStringExtra("species_animal"));
        data_animal_page.setText(getIntent().getStringExtra("reg_date_animal"));
        Picasso.get().load(getIntent().getStringExtra("image_animal")).into(image_animal_page);
        name_animal_page.setText(getIntent().getStringExtra("name_animal"));
        description_animal_page.setText(getIntent().getStringExtra("description_animal"));
        sex_animal_page.setText(getIntent().getStringExtra("sex_animal"));
        age_animal_page.setText(getIntent().getStringExtra("age_animal"));
        state_animal_page.setText(getIntent().getStringExtra("state_animal"));

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

    public void card(View view){
        Intent intent = new Intent(this, CardsMainActivity.class);
        startActivity(intent);
    }

    public void favorite(View view){
        ImageButton button_favorite = (ImageButton) findViewById(R.id.favorite_button);

        if (flag){
            button_favorite.setImageResource(R.drawable.button_favorite);
            flag = false;
        }
        else{
            button_favorite.setImageResource(R.drawable.button_favorite_press);
            flag = true;
        }
    }

}