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
import java.util.Objects;

public class AddHelp extends AppCompatActivity {

    MaterialEditText add_date_help, add_description_help, add_date_help_first;
    String type;
    Calendar calendar_text;
    DatePickerDialog dpd;
    ImageButton food, things, help;
    int type_flag = 3;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_help);

        food = findViewById(R.id.food);
        things = findViewById(R.id.things);
        help = findViewById(R.id.help);

        add_date_help = findViewById(R.id.add_date_help);
        add_date_help_first = findViewById(R.id.add_date_help_first);

        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
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
        calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        dpd = new DatePickerDialog(AddHelp.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Month, Day;
                Month = Integer.toString(month + 1);
                if (Month.length() == 1) {
                    Month = "0" + Month;
                }
                Day = Integer.toString(dayOfMonth);
                if (Day.length() == 1) {
                    Day = "0" + Day;
                }
                add_date_help.setText(dayOfMonth + "." + (month + 1) + "." + year);
            }
        }, year_first, month_first, day_first);
        dpd.show();
    }

    public void calendar_first(View view){
        calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        dpd = new DatePickerDialog(AddHelp.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Month, Day;
                Month = Integer.toString(month + 1);
                if (Month.length() == 1) {
                    Month = "0" + Month;
                }
                Day = Integer.toString(dayOfMonth);
                if (Day.length() == 1) {
                    Day = "0" + Day;
                }
                add_date_help_first.setText(dayOfMonth + "." + (month + 1) + "." + year);
            }
        }, year_first, month_first, day_first);
        dpd.show();
    }

    public void first_date_layout(View view){
        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        if (!flag){
            findViewById(R.id.first_date_layout).setVisibility(View.VISIBLE);
            button.setRotation(0F);
            flag = true;
        } else {
            button.setRotation(180F);
            flag = false;
        }

    }

    public void getParameter(){

        ParseObject notification = new ParseObject("Notification");
        ParseObject ads = new ParseObject("Ads");

        ParseObject ptr = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());

        notification.put("id_user", ptr);
        notification.put("type_notification", "объявление");
        notification.put("other_info", type);

        ads.put("id_user", ptr);
        ads.put("type", type);
        ads.put("last_date", Objects.requireNonNull(add_date_help.getText()).toString());
        ads.put("first_date", Objects.requireNonNull(add_date_help_first.getText()).toString());
        ads.put("description",  Objects.requireNonNull(add_description_help.getText()).toString());

        Intent intent = new Intent(AddHelp.this, ProfileActivityOrg.class);
        startActivity(intent);

        notification.saveInBackground();
        ads.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                } else {
                    ParseUser.logOut();
                    Toast.makeText(AddHelp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void btn_save(View view){
        boolean flagCheckup = true;
        boolean flagInput = true;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        add_description_help = findViewById(R.id.add_description_help);
        add_date_help_first = findViewById(R.id.add_date_help_first);

        try {
            Date date = dateFormat.parse(add_date_help.getText().toString());
            add_date_help.setText(dateFormat.format(date));
            goneMessage(R.id.error_input_help_date);
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage(R.id.error_input_help_date);
            flagInput = false;
        }

        try {
            Date date = dateFormat.parse(add_date_help_first.getText().toString());
            add_date_help_first.setText(dateFormat.format(date));
            goneMessage(R.id.error_input_first_date);
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage(R.id.error_input_first_date);
            flagInput = false;
        }

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

        if (flagCheckup && flagInput){
            getParameter();
        } else if (!flagCheckup) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы заполнили не все поля!", Toast.LENGTH_SHORT);
            toast.show();
        } else if (!flagInput) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы неккоректно заполнили поля!", Toast.LENGTH_SHORT);
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
        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org_press);
        type_flag = 2;
    }

    public void btn_thing(View view){
        food.setImageResource(R.drawable.button_food_type_ad_org);
        things.setImageResource(R.drawable.button_things_type_ad_org_press);
        help.setImageResource(R.drawable.button_help_type_ad_org);
        type_flag = 1;
    }

    public void btn_food(View view){
        food.setImageResource(R.drawable.button_food_type_ad_org_press);
        things.setImageResource(R.drawable.button_things_type_ad_org);
        help.setImageResource(R.drawable.button_help_type_ad_org);
        type_flag = 0;
    }
}