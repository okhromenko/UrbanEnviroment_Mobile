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
import com.example.urbanenviroment.page.profile.settings.SettingProfileUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EditHelpPage extends AppCompatActivity {

    private FirebaseFirestore db;
    int type_flag = 0;
    String type, id, description;
    MaterialEditText text_edit_last_date, text_edit_first_date;
    Calendar calendar_text;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_help_page);

        TextView type_ads = findViewById(R.id.text_type_ads_edit_page);
        TextView date_ads = findViewById(R.id.text_data_ads_edit_page);
        TextView first_date_ads = findViewById(R.id.text_first_data_ads_edit_page);
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
                            first_date_ads.setText(document.getString("first_date"));
                            description_ads.setText(description);

                            LinearLayout first_date_ll = (LinearLayout) findViewById(R.id.layout_change_first_date);
                            FrameLayout first_date_fl = (FrameLayout) findViewById(R.id.text_change_first_date);
                            TextView first_date_tv = (TextView) findViewById(R.id.text_no_first_date);

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                            Date date_first = new Date();
                            Date date_last = null;
                            try {
                                date_last = format.parse(document.getString("first_date"));
                                assert date_first != null;
                                if (date_first.compareTo(date_last) >= 0) {
                                    first_date_ll.setVisibility(View.GONE);
                                    first_date_fl.setVisibility(View.GONE);
                                    first_date_tv.setVisibility(View.VISIBLE);
                                }
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d(TAG, "Данные не найдены");
                        }
                    }
                }
            });
        }
        else{
            type_ads.setText(getIntent().getStringExtra("type_ads_help"));
            date_ads.setText(getIntent().getStringExtra("date_first_help"));
            first_date_ads.setText(getIntent().getStringExtra("date_help"));
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

    public void change_first_date(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_first_date);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_first_date);

        change(frame, layout);
    }

    public void cancel_first_date(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_first_date);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_first_date);

        cancel(frame, layout);
        clear(R.id.text_edit_first_date);
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

    public void calendar_first(View view){
        text_edit_first_date = (MaterialEditText) findViewById(R.id.text_edit_first_date);
        calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        dpd = new DatePickerDialog(EditHelpPage.this, new DatePickerDialog.OnDateSetListener() {
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
                text_edit_first_date.setText(Day + "." + Month + "." + year);
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

    public void save_first_date(View view){
        save("first_date", Objects.requireNonNull(text_edit_first_date.getText()).toString());
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
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("User").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document_user = task.getResult();
                db.collection("User").document(mAuth.getUid()).update("count_ads",
                        document_user.getLong("count_ads") - 1);
            }
        });

        db.collection("FavoriteAds").whereEqualTo("id_ads", getIntent().getStringExtra("id")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    db.collection("FavoriteAds").document(document.getId()).delete();
                }
            }
        });

        db.collection("Ads").document(getIntent().getStringExtra("id")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent intent = new Intent(EditHelpPage.this, EditHelp.class);
                startActivity(intent);
            }
        });
    }
}