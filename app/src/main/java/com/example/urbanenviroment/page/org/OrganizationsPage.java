package com.example.urbanenviroment.page.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.page.animals.CardsMainActivity;
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
import com.squareup.picasso.Picasso;

public class OrganizationsPage extends AppCompatActivity {

    ParseUser parseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations_page);

        parseUser = ParseUser.getCurrentUser();

        TextView name_org_org_page = findViewById(R.id.name_org_org_page);
        TextView email_org_org_page = findViewById(R.id.email_org_org_page);
        TextView phone_org_org_page = findViewById(R.id.phone_org_org_page);
        TextView description_animal_page = findViewById(R.id.description_animal_page);
        TextView count_animal_org_page = findViewById(R.id.count_animal_org_page);
        TextView count_ads_org_page = findViewById(R.id.count_ads_org_page);
        TextView count_photo_org_page = findViewById(R.id.count_photo_org_page);
        TextView date_reg_org_org = findViewById(R.id.date_reg_org_org);
        TextView website_org_org_page = findViewById(R.id.website_org_org_page);
        TextView address_org_org_page = findViewById(R.id.address_org_org_page);
        ImageView img_org_org_page = findViewById(R.id.img_org_org_page);
        ImageButton button_org_page = findViewById(R.id.button_org_page);
        ImageButton button_org_edit = findViewById(R.id.button_setting_edit_org);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", parseUser.getObjectId());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (getIntent().getStringExtra("phone").equals("Номер телефона"))
                        phone_org_org_page.setVisibility(View.GONE);
                    else {
                        phone_org_org_page.setVisibility(View.VISIBLE);
                        hidden_other(phone_org_org_page, object.getBoolean("hidden_phone"));
                    }

                    if (getIntent().getStringExtra("website").equals("Сайт организации"))
                        website_org_org_page.setVisibility(View.GONE);
                    else {
                        website_org_org_page.setVisibility(View.VISIBLE);
                        hidden_other(website_org_org_page, object.getBoolean("hidden_website"));
                    }

                    if (getIntent().getStringExtra("address").equals("Адрес"))
                        address_org_org_page.setVisibility(View.GONE);
                    else
                        address_org_org_page.setVisibility(View.VISIBLE);

                    hidden_other(email_org_org_page, object.getBoolean("hidden_email"));
                }
            }
        });

        Picasso.get().load(getIntent().getStringExtra("image")).into(img_org_org_page);

        name_org_org_page.setText(getIntent().getStringExtra("name"));
        email_org_org_page.setText(getIntent().getStringExtra("email"));
        phone_org_org_page.setText(getIntent().getStringExtra("phone"));
        description_animal_page.setText(getIntent().getStringExtra("description"));
        count_animal_org_page.setText(getIntent().getStringExtra("count_animal"));
        count_ads_org_page.setText(getIntent().getStringExtra("count_ads"));
        count_photo_org_page.setText(getIntent().getStringExtra("count_photo"));
        date_reg_org_org.setText(getIntent().getStringExtra("date"));

        String str = getIntent().getStringExtra("address");
        SpannableString content = new SpannableString(str);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        address_org_org_page.setText(content);

        str = getIntent().getStringExtra("website");
        SpannableString contentlink = new SpannableString(str);
        contentlink.setSpan(new UnderlineSpan(), 0, contentlink.length(), 0);
        website_org_org_page.setText(contentlink);


        if (parseUser != null){
            if ((Boolean) parseUser.get("is_org")) {
                button_org_page.setVisibility(View.GONE);
            } else {
                button_org_page.setVisibility(View.VISIBLE);
            }
        }

        ParseQuery<ParseObject> query_org = ParseQuery.getQuery("Organization");
        query_org.whereEqualTo("objectId", getIntent().getStringExtra("id"));
        query_org.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null && parseUser != null){
                    if (parseUser.getObjectId().equals(object.getParseObject("id_user").getObjectId()))
                        button_org_edit.setVisibility(View.VISIBLE);
                    else button_org_edit.setVisibility(View.GONE);
                }
            }
        });

        ParseQuery<ParseObject> query_fav = ParseQuery.getQuery("FavoriteOrganization");
        ParseObject id_org = ParseObject.createWithoutData("Organization", getIntent().getStringExtra("id"));
        query_fav.whereEqualTo("id_org", id_org);
        query_fav.whereEqualTo("id_user", parseUser);
        query_fav.getFirstInBackground(new GetCallback<ParseObject>() {
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

        CardView btn_animal_org_page = findViewById(R.id.btn_animal_org_page);
        CardView btn_ads_org_page = findViewById(R.id.btn_ads_org_page);
        CardView btn_photo_org_page = findViewById(R.id.btn_photo_org_page);

        btn_animal_org_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query_org = new ParseQuery<>("Organization");
                query_org.whereEqualTo("objectId",  getIntent().getStringExtra("id"));
                query_org.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object_org, ParseException exp) {
                        if (exp == null) {
                            Intent intent = new Intent(OrganizationsPage.this, CardsMainActivity.class);
                            intent.putExtra("flag_org", true);
                            intent.putExtra("id_org", object_org.getParseObject("id_user").getObjectId());
                            startActivity(intent);
                        }

                    }
                });
            }
        });

        btn_ads_org_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query_org = new ParseQuery<>("Organization");
                query_org.whereEqualTo("objectId",  getIntent().getStringExtra("id"));
                query_org.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object_org, ParseException exp) {
                        if (exp == null) {
                            Intent intent = new Intent(OrganizationsPage.this, HelpActivity.class);
                            intent.putExtra("flag_org", true);
                            intent.putExtra("id_org", object_org.getParseObject("id_user").getObjectId());
                            startActivity(intent);
                        }

                    }
                });
            }
        });

        btn_photo_org_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query_org = new ParseQuery<>("Organization");
                query_org.whereEqualTo("objectId",  getIntent().getStringExtra("id"));
                query_org.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object_org, ParseException exp) {
                        if (exp == null) {
                            Intent intent = new Intent(OrganizationsPage.this, HomeActivity.class);
                            intent.putExtra("flag_org", true);
                            intent.putExtra("id_org", object_org.getParseObject("id_user").getObjectId());
                            startActivity(intent);
                        }

                    }
                });
            }
        });
    }

    public void hidden_other(TextView textView, boolean flag){
        if (flag){
            textView.setVisibility(View.GONE);
        }
        else
            textView.setVisibility(View.VISIBLE);
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
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
    }

}