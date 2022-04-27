package com.example.urbanenviroment.page.animals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
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
        TextView address_animal_page = (TextView) findViewById(R.id.address_animal_page);
        ImageButton favorite_button_animal = findViewById(R.id.favorite_button_animal);

        LinearLayout edit_del_buttons = findViewById(R.id.edit_delete_buttons);

        kind_animal_page.setText(getIntent().getStringExtra("kind_animal"));
        species_animal_page.setText(getIntent().getStringExtra("species_animal"));
        data_animal_page.setText(getIntent().getStringExtra("reg_date_animal"));
        Picasso.get().load(getIntent().getStringExtra("image_animal")).into(image_animal_page);
        name_animal_page.setText(getIntent().getStringExtra("name_animal"));
        description_animal_page.setText(getIntent().getStringExtra("description_animal"));
        sex_animal_page.setText(getIntent().getStringExtra("sex_animal"));
        age_animal_page.setText(getIntent().getStringExtra("age_animal"));
        state_animal_page.setText(getIntent().getStringExtra("state_animal"));
        address_animal_page.setText(getIntent().getStringExtra("address"));

        ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser != null){
            if ((Boolean) parseUser.get("is_org")) {
                favorite_button_animal.setVisibility(View.GONE);
            } else {
                favorite_button_animal.setVisibility(View.VISIBLE);
            }
        }

        ParseQuery<ParseObject> query_animal = ParseQuery.getQuery("Animals");
        query_animal.whereEqualTo("objectId", getIntent().getStringExtra("id"));
        query_animal.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null && parseUser != null){
                    if (parseUser.getObjectId().equals(object.getParseObject("id_user").getObjectId()))
                        edit_del_buttons.setVisibility(View.VISIBLE);
                    else edit_del_buttons.setVisibility(View.GONE);
                }
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteAnimal");
        ParseObject id_animal = ParseObject.createWithoutData("Animals", getIntent().getStringExtra("id"));
        query.whereEqualTo("id_animal", id_animal);
        query.whereEqualTo("id_user", parseUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null)
                    favorite_button_animal.setImageResource(R.drawable.button_favorite_press);
                else
                    favorite_button_animal.setImageResource(R.drawable.button_favorite);
            }
        });

        favorite_button_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteAnimal");

                ParseUser parseUser = ParseUser.getCurrentUser();
                ParseObject id_animal = ParseObject.createWithoutData("Animals", getIntent().getStringExtra("id"));

                query.whereEqualTo("id_animal", id_animal);
                query.whereEqualTo("id_user", parseUser);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object == null){
                            favorite_button_animal.setImageResource(R.drawable.button_favorite_press);

                            ParseObject favorite_animal = new ParseObject("FavoriteAnimal");
                            favorite_animal.put("id_user", parseUser);
                            favorite_animal.put("id_animal", id_animal);

                            favorite_animal.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null){
                                        Intent intent = new Intent(AnimalPage.this, AnimalPage.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            favorite_button_animal.setImageResource(R.drawable.button_favorite);
                            object.deleteInBackground();
                        }
                    }
                });
            }
        });

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

    public void delete_edit_animal(View view){
        Toast.makeText(getApplicationContext(),
                "Ты все удалил :(", Toast.LENGTH_SHORT).show();
    }

}