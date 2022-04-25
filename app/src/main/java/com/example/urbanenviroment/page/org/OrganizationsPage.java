package com.example.urbanenviroment.page.org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class OrganizationsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations_page);

        TextView name_org_org_page = findViewById(R.id.name_org_org_page);
        TextView email_org_org_page = findViewById(R.id.email_org_org_page);
        TextView phone_org_org_page = findViewById(R.id.phone_org_org_page);
        TextView description_animal_page = findViewById(R.id.description_animal_page);
        TextView count_animal_org_page = findViewById(R.id.count_animal_org_page);
        TextView count_ads_org_page = findViewById(R.id.count_ads_org_page);
        TextView count_photo_org_page = findViewById(R.id.count_photo_org_page);
        TextView date_reg_org_org = findViewById(R.id.date_reg_org_org);
        ImageView img_org_org_page = findViewById(R.id.img_org_org_page);
        ImageButton button_org_page = findViewById(R.id.button_org_page);

        img_org_org_page.setImageResource(getIntent().getIntExtra("image", 0));

        TextView UnderlineLink = (TextView) findViewById(R.id.address_org_org_page);
        String str = getIntent().getStringExtra("address");
        SpannableString content = new SpannableString(str);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        UnderlineLink.setText(content);

        name_org_org_page.setText(getIntent().getStringExtra("name"));
        email_org_org_page.setText(getIntent().getStringExtra("email"));
        phone_org_org_page.setText(getIntent().getStringExtra("phone"));
        description_animal_page.setText(getIntent().getStringExtra("description"));
        count_animal_org_page.setText(getIntent().getStringExtra("count_animal"));
        count_ads_org_page.setText(getIntent().getStringExtra("count_ads"));
        count_photo_org_page.setText(getIntent().getStringExtra("count_photo"));
        date_reg_org_org.setText(getIntent().getStringExtra("date"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteOrganization");

        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseObject id_org = ParseObject.createWithoutData("Organization", getIntent().getStringExtra("id"));

        query.whereEqualTo("id_org", id_org);
        query.whereEqualTo("id_user", parseUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null)
                    button_org_page.setImageResource(R.drawable.button_favorite_press);
                else
                    button_org_page.setImageResource(R.drawable.button_favorite);
            }
        });

        button_org_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteOrganization");

                ParseUser parseUser = ParseUser.getCurrentUser();
                ParseObject id_org = ParseObject.createWithoutData("Organization", getIntent().getStringExtra("id"));

                query.whereEqualTo("id_org", id_org);
                query.whereEqualTo("id_user", parseUser);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object == null){
                            button_org_page.setImageResource(R.drawable.button_favorite_press);

                            ParseObject favorite_org = new ParseObject("FavoriteOrganization");
                            favorite_org.put("id_user", parseUser);
                            favorite_org.put("id_org", id_org);

                            favorite_org.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null){
                                        Intent intent = new Intent(OrganizationsPage.this, OrganizationsPage.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            button_org_page.setImageResource(R.drawable.button_favorite);
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

    public void statistics(View view){
        Intent intent = new Intent(this, Organization_statistics.class);
        startActivity(intent);
    }

}