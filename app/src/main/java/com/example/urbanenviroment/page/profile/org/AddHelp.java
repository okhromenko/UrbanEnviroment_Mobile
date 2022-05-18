package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHelp extends AppCompatActivity {

    MaterialEditText add_date_help, add_description_help, add_date_help_first;
    String type;
    Calendar calendar_text;
    DatePickerDialog dpd;
    ImageButton food, thing, help;
    int type_flag = 3;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_help);

        add_date_help_first = (MaterialEditText) findViewById(R.id.add_date_help_first);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date todayDate = new Date();
            String firstDate = dateFormat.format(todayDate);
            add_date_help_first.setText(firstDate);

        } catch (Exception e) {
        e.printStackTrace();
    }

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

    public void calendar(View view){
        add_date_help = (MaterialEditText) findViewById(R.id.add_date_help);
        calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        dpd = new DatePickerDialog(AddHelp.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                add_date_help.setText(dayOfMonth + "." + (month + 1) + "." + year);
            }
        }, year_first, month_first, day_first);
        dpd.show();
    }

    public void calendar_first(View view){
        add_date_help_first = (MaterialEditText) findViewById(R.id.add_date_help_first);
        calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        dpd = new DatePickerDialog(AddHelp.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                add_date_help_first.setText(dayOfMonth + "." + (month + 1) + "." + year);
            }
        }, year_first, month_first, day_first);
        dpd.show();
    }

    public void first_date_layout(View view){
        if (!flag){
            ImageButton button = (ImageButton) findViewById(R.id.imageButton);
            findViewById(R.id.first_date_layout).setVisibility(View.VISIBLE);
            float deg = button.getRotation() + 180F;
            button.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
            flag = true;
        } else {
            ImageButton button = (ImageButton) findViewById(R.id.imageButton);
            findViewById(R.id.first_date_layout).setVisibility(View.GONE);
            float deg = button.getRotation() + 180F;
            button.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
            flag = false;
        }

    }

    public void getParameter(){

        ParseObject ads = new ParseObject("Ads");

        ParseObject ptr = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
        ads.put("id_user", ptr);

        ads.put("type", type);
        ads.put("last_date", add_date_help.getText().toString());
        ads.put("first_date", add_date_help_first.getText().toString());
        ads.put("description",  add_description_help.getText().toString());

        ads.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddHelp.this, ProfileActivityOrg.class);
                    startActivity(intent);
                } else {
                    ParseUser.logOut();
                    Toast.makeText(AddHelp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void btn_save(View view){
        Boolean flagCheckup = true;

        add_date_help = (MaterialEditText) findViewById(R.id.add_date_help);
        add_description_help = (MaterialEditText) findViewById(R.id.add_description_help);
        add_date_help_first = (MaterialEditText) findViewById(R.id.add_date_help_first);


        switch (type_flag){
            case (0):
                type = "Еда";
                goneMessage(R.id.error_help_type);
                break;
            case(1):
                type = "Вещи";
                goneMessage(R.id.error_help_type);
                break;
            case(2):
                type = "Волонтерство";
                goneMessage(R.id.error_help_type);
                break;
            case(3):
                errorMessage(R.id.error_help_type);
                flagCheckup = false;
                break;
        }

        if (add_date_help.getText().toString().equals("")){
            errorMessage(R.id.error_help_date);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_help_date);
        }

        if (add_description_help.getText().toString().equals("")){
            errorMessage(R.id.error_help_description);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_help_description);
        }

        if (flagCheckup){
            getParameter();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы заполнили не все поля!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void errorMessage(int id){
        TextView textName = (TextView) findViewById(id);
        textName.setVisibility(View.VISIBLE);
    }

    public void goneMessage(int id){
        TextView textName = (TextView) findViewById(id);
        textName.setVisibility(View.GONE);
    }

    public void btn_cancel(View view){
        add_date_help.setText("");
        add_description_help.setText("");
    }

    public void btn_help(View view){
        ImageButton food = (ImageButton) findViewById(R.id.food);
        ImageButton things = (ImageButton) findViewById(R.id.things);
        ImageButton help = (ImageButton) findViewById(R.id.help);

        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org_press);
        type_flag = 2;
    }

    public void btn_thing(View view){
        ImageButton food = (ImageButton) findViewById(R.id.food);
        ImageButton things = (ImageButton) findViewById(R.id.things);
        ImageButton help = (ImageButton) findViewById(R.id.help);

        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org_press);
        help.setImageResource(R.drawable.button_help_type_ad_org);
        type_flag = 1;
    }

    public void btn_food(View view){
        ImageButton food = (ImageButton) findViewById(R.id.food);
        ImageButton things = (ImageButton) findViewById(R.id.things);
        ImageButton help = (ImageButton) findViewById(R.id.help);

        food.setImageResource(R.drawable.button_food_type_ad_org_press);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org);
        type_flag = 0;
    }
}