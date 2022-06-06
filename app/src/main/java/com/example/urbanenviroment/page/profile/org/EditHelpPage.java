package com.example.urbanenviroment.page.profile.org;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.settings.SettingPageOrg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Objects;

public class EditHelpPage extends AppCompatActivity {

    private FirebaseFirestore db;
    int type_flag = 0;
    String type, id, description;
    MaterialEditText text_edit_last_date;
    Calendar calendar_text;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_help_page);

        TextView type_ads = findViewById(R.id.text_type_ads_edit_page);
        TextView date_ads = findViewById(R.id.text_data_ads_edit_page);
        EditText description_ads = findViewById(R.id.text_edit_help_description);

        db = FirebaseFirestore.getInstance();

        if (getIntent().getStringExtra("id") != null){

            DocumentReference docRef = db.collection("Ads").document(getIntent().getStringExtra("id"));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            description = document.getString("description");
                            type_ads.setText(document.getString("type"));
                            date_ads.setText(document.getString("last_date"));
                            description_ads.setText(description);
                        } else {
                            Log.d(TAG, "Данные не найдены");
                        }
                    }
                }
            });
        }
        else{
            type_ads.setText(getIntent().getStringExtra("type_ads_help"));
            date_ads.setText(getIntent().getStringExtra("date_help"));
            description_ads.setText(getIntent().getStringExtra("description_help"));
            description = getIntent().getStringExtra("description_help");
        }

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

        ImageButton food = (ImageButton) findViewById(R.id.food);
        ImageButton things = (ImageButton) findViewById(R.id.things);
        ImageButton help = (ImageButton) findViewById(R.id.help);

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

    public void cancel_description(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_description);
        EditText desc = (EditText) findViewById(R.id.text_edit_help_description);
        desc.setText(description);
    }

    public void calendar(View view){
        text_edit_last_date = (MaterialEditText) findViewById(R.id.text_edit_last_date);
        calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        dpd = new DatePickerDialog(EditHelpPage.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                text_edit_last_date.setText(dayOfMonth + "." + (month + 1) + "." + year);
            }
        }, year_first, month_first, day_first);
        dpd.show();
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

    private void save(String field, String value){
        id = getIntent().getStringExtra("id");

        DocumentReference changeRef = db.collection("Ads").document(id);

        changeRef.update(field, value).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),  "Что-то пошло не так", Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditHelpPage.this, EditHelpPage.class);
                intent.putExtra("id_ads_intent", id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void save_type_ads(View view){
        id = getIntent().getStringExtra("id");

        switch (type_flag){
            case (0):
                type = "Еда";
                break;
            case(1):
                type = "Вещи";
                break;
            case(2):
                type = "Волонтерство";
                break;
        }

        save("type", type);
    }

    public void save_last_date(View view){
        save("last_date", Objects.requireNonNull(text_edit_last_date.getText()).toString());
    }

    public void save_description(View view){
        MaterialEditText text_edit_description = findViewById(R.id.text_edit_help_description);
        save("description", Objects.requireNonNull(text_edit_description.getText()).toString());
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

    public void delete_edit_ads(View view) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ads");

        query.whereEqualTo("objectId", getIntent().getStringExtra("id"));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException ex) {
                if (object != null){
                    object.deleteInBackground();

                    Intent intent = new Intent(EditHelpPage.this, EditHelp.class);
                    startActivity(intent);
                }
            }
        });
    }
}