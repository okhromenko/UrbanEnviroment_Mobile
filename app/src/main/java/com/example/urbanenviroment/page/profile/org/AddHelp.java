package com.example.urbanenviroment.page.profile.org;

import androidx.annotation.NonNull;
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
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddHelp extends AppCompatActivity {

    MaterialEditText add_date_help, add_description_help, add_date_help_first;
    String type, today;
    Calendar calendar_text;
    DatePickerDialog dpd;
    ImageButton food, things, help;
    int type_flag = 3;
    boolean flag = false, flagInput, flagCheckup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_help);

        food = findViewById(R.id.food);
        things = findViewById(R.id.things);
        help = findViewById(R.id.help);

        add_date_help = findViewById(R.id.add_date_help);
        add_date_help_first = findViewById(R.id.add_date_help_first);
        add_description_help = findViewById(R.id.add_description_help);

        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date todayDate = new Date();
            today = dateFormat.format(todayDate);
            add_date_help_first.setText(today);

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
                add_date_help.setText(Day + "." + Month + "." + year);
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
                add_date_help_first.setText(Day + "." + Month + "." + year);
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
            findViewById(R.id.first_date_layout).setVisibility(View.GONE);
            button.setRotation(180F);
            if (add_date_help_first.getText().toString().equals("")){
                try {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    Date todayDate = new Date();
                    String firstDate = dateFormat.format(todayDate);
                    add_date_help_first.setText(firstDate);
                    goneMessage(R.id.error_input_first_date);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            flag = false;
        }

    }

    public void getParameter(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        Map<String, Object> ads = new HashMap<>();
        ads.put("userId", mAuth.getCurrentUser().getUid());
        ads.put("username", mAuth.getCurrentUser().getDisplayName());
        ads.put("imageOrg", mAuth.getCurrentUser().getPhotoUrl().toString());
        ads.put("type", type);
        ads.put("last_date", add_date_help.getText().toString());
        ads.put("first_date", add_date_help_first.getText().toString());
        ads.put("description",  add_description_help.getText().toString());

        Intent intent = new Intent(AddHelp.this, ProfileActivityUser.class);
        startActivity(intent);

        db.collection("Ads").add(ads).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
            }
        });

        DocumentReference User = db.collection("User").document(mAuth.getCurrentUser().getUid());

        User.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                long count_animal = 1;
                if (documentSnapshot.getLong("count_ads") != null)
                    count_animal = documentSnapshot.getLong("count_ads") + 1;

                User.update("count_ads", count_animal);
            }
        });
    }

    public void btn_save(View view){
        flagCheckup = true;
        flagInput = true;

        checkDate(R.id.add_date_help, R.id.error_input_help_date);
        checkDate(R.id.add_date_help_first, R.id.error_input_first_date);

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

        MaterialEditText first = findViewById(R.id.add_date_help_first);
        MaterialEditText last = findViewById(R.id.add_date_help);

        checkAll(R.id.add_date_help, R.id.error_help_date);
        checkAll(R.id.add_description_help, R.id.error_help_description);

        if (flagInput) {
            messages(compare(first.getText().toString(), last.getText().toString()) < 0, R.id.wrong_help_date);
            messages(compare(today, first.getText().toString()) < 1, R.id.wrong_first_date);
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

    public void messages(boolean flag, int id) {
        if (flag) {
            goneMessage(id);
        } else {
            errorMessage(id);
            flagInput = false;
        }
    }

    public int compare(String first, String last) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date date_first = null;
        Date date_last = null;
        try {
            date_first = format.parse(first);
            date_last = format.parse(last);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        assert date_first != null;
        return date_first.compareTo(date_last);
    }

    public void checkDate(int id_ET, int id_TV){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        MaterialEditText ET = findViewById(id_ET);
        try {
            Date date = dateFormat.parse(ET.getText().toString());
            ET.setText(dateFormat.format(date));
            goneMessage(id_TV);
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage(id_TV);
            flagInput = false;
        }
    }

    public void checkAll(int id_ET, int id_TV) {
        MaterialEditText ET = findViewById(id_ET);
        if (ET.getText().toString().equals("")){
            errorMessage(id_TV);
            flagCheckup = false;
        } else {
            goneMessage(id_TV);
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