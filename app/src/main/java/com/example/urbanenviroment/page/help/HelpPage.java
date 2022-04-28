package com.example.urbanenviroment.page.help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

public class HelpPage extends AppCompatActivity {

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);

        LinearLayout panel_date = (LinearLayout) findViewById(R.id.panel_date);
        CardView panel_top = (CardView) findViewById(R.id.panel_top);
        CardView panel_text = (CardView) findViewById(R.id.panel_text);
        TextView type_help_page = (TextView) findViewById(R.id.type_help_page);
        ImageView type_img_help_page = (ImageView) findViewById(R.id.type_img_help_page);
        TextView date_help_page = (TextView) findViewById(R.id.date_help_page);
        //TextView first_date_help_page = (TextView) findViewById(R.id.first_date_help_page);
        TextView status_help_page = (TextView) findViewById(R.id.status_help_page);
        TextView description_help_page = (TextView) findViewById(R.id.description_help_page);
        TextView org_help_page = (TextView) findViewById(R.id.org_help_page);
        ImageButton button_favorite_help_page = findViewById(R.id.button_favorite_help_page);


        LinearLayout edit_del_buttons = findViewById(R.id.edit_delete_buttons);

        panel_date.setBackgroundColor(getIntent().getIntExtra("color_transperent", 0));
        panel_top.setCardBackgroundColor(getIntent().getIntExtra("color", 0));
        panel_text.setCardBackgroundColor(getIntent().getIntExtra("color", 0));
        type_help_page.setText(getIntent().getStringExtra("type_ads_help"));
        type_img_help_page.setImageResource(getIntent().getIntExtra("image", 0));
        date_help_page.setText(getIntent().getStringExtra("date_help"));
        //first_date_help_page.setText(getIntent().getStringExtra("first_date_help"));
        status_help_page.setText(getIntent().getStringExtra("status_help"));
        description_help_page.setText(getIntent().getStringExtra("description_help"));
        org_help_page.setText(getIntent().getStringExtra("name_org_help"));

        ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser != null){
            if ((Boolean) parseUser.get("is_org")) {
                button_favorite_help_page.setVisibility(View.GONE);
            } else {
                button_favorite_help_page.setVisibility(View.VISIBLE);
            }
        }


        ParseQuery<ParseObject> query_ads = ParseQuery.getQuery("Ads");
        query_ads.whereEqualTo("objectId", getIntent().getStringExtra("id"));
        query_ads.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null && parseUser != null){
                    if (parseUser.getObjectId().equals(object.getParseObject("id_user").getObjectId()))
                        edit_del_buttons.setVisibility(View.VISIBLE);
                    else edit_del_buttons.setVisibility(View.GONE);
                }
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteAds");
        ParseObject id_ads = ParseObject.createWithoutData("Ads", getIntent().getStringExtra("id"));
        query.whereEqualTo("id_ads", id_ads);
        query.whereEqualTo("id_user", parseUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null)
                    button_favorite_help_page.setImageResource(R.drawable.button_favorite_press);
                else
                    button_favorite_help_page.setImageResource(R.drawable.button_favorite);
            }
        });

        button_favorite_help_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteAds");

                ParseUser parseUser = ParseUser.getCurrentUser();
                ParseObject id_ads = ParseObject.createWithoutData("Ads", getIntent().getStringExtra("id"));

                query.whereEqualTo("id_ads", id_ads);
                query.whereEqualTo("id_user", parseUser);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object == null){
                            button_favorite_help_page.setImageResource(R.drawable.button_favorite_press);

                            ParseObject favorite_ads = new ParseObject("FavoriteAds");
                            favorite_ads.put("id_user", parseUser);
                            favorite_ads.put("id_ads", id_ads);

                            favorite_ads.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null){
                                        Intent intent = new Intent(HelpPage.this, HelpPage.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            button_favorite_help_page.setImageResource(R.drawable.button_favorite);
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

    public void delete_edit_ads(View view){
        Toast.makeText(getApplicationContext(),
                "Ты все удалил :(", Toast.LENGTH_SHORT).show();
    }
}