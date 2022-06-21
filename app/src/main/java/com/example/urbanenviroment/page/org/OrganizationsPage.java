package com.example.urbanenviroment.page.org;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.settings.SettingProfile;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class OrganizationsPage extends AppCompatActivity {

    private FirebaseUser mAuth;
    private String name, email, phone, description, count_animal, count_ads, count_photo, date_reg,
            website, address, img_org, requisits;
    Dialog dialog_requisites;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations_page);

        dialog_requisites = new Dialog(this);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        LinearLayout layout = findViewById(R.id.layout_user_buttons_org_page);

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
        ImageButton button_org_edit = findViewById(R.id.button_setting_edit_org);

        String userId = getIntent().getStringExtra("id");
        Boolean flagHelpPage = getIntent().getBooleanExtra("flagHelpPage", false);

        DocumentReference orgRef = db.collection("User").document(userId);

        orgRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        if (flagHelpPage){
                            name = document.getString("name");
                            date_reg = new SimpleDateFormat("dd.MM.yyyy").format(document.getDate("reg_date"));
                            address = document.getString("address");
                            description = document.getString("description");
                            phone = document.getString("phone");
                            email = document.getString("email");
                            website = document.getString("website");
                            requisits = document.getString("requisits");

                            count_animal = "";
                            count_photo = "";
                            count_ads = "";

                            if (document.getLong("count_animal") != null)
                                count_animal = document.getLong("count_animal").toString();
                            if (document.getLong("count_ads") != null)
                                count_ads = document.getLong("count_ads").toString();
                            if (document.getLong("count_photo") != null)
                                count_photo = document.getLong("count_photo").toString();

                            img_org = document.getString("image");
                        }
                        else {
                            name = getIntent().getStringExtra("name");
                            date_reg = getIntent().getStringExtra("date");
                            address = getIntent().getStringExtra("address");
                            description = getIntent().getStringExtra("description");
                            phone = getIntent().getStringExtra("phone");
                            email = getIntent().getStringExtra("email");
                            website = getIntent().getStringExtra("website");
                            count_animal = getIntent().getStringExtra("count_animal");
                            count_photo = getIntent().getStringExtra("count_photo");
                            count_ads = getIntent().getStringExtra("count_ads");
                            img_org = getIntent().getStringExtra("image");
                            requisits = getIntent().getStringExtra("requisits");
                        }

                        if (mAuth != null && mAuth.getUid().equals(userId))
                            button_org_edit.setVisibility(View.VISIBLE);
                        else button_org_edit.setVisibility(View.GONE);

                        if (phone.equals("Номер телефона"))
                            phone_org_org_page.setVisibility(View.GONE);
                        else {
                            phone_org_org_page.setVisibility(View.VISIBLE);
                            hidden_other(phone_org_org_page, Boolean.TRUE.equals(document.getBoolean("hidden_phone")));
                        }

                        if (website.equals("Сайт организации"))
                            website_org_org_page.setVisibility(View.GONE);
                        else {
                            website_org_org_page.setVisibility(View.VISIBLE);
                            hidden_other(website_org_org_page, Boolean.TRUE.equals(document.getBoolean("hidden_website")));
                        }

                        hidden_other(email_org_org_page, Boolean.TRUE.equals(document.getBoolean("hidden_email")));

                        if (mAuth == null || getIntent().getBooleanExtra("is_org", false))
                            layout.setVisibility(View.GONE);
                        else
                            layout.setVisibility(View.VISIBLE);
                    }

                    if (img_org != null)
                        Picasso.get().load(img_org).into(img_org_org_page);

                    name_org_org_page.setText(name);
                    email_org_org_page.setText(email);

                    if (!phone.equals("Номер телефона") && !phone.isEmpty())
                        phone_org_org_page.setText(phone);

                    if (description != null && !description.isEmpty())
                        description_animal_page.setText(description);

                    count_animal_org_page.setText(count_animal);
                    count_ads_org_page.setText(count_ads);
                    count_photo_org_page.setText(count_photo);
                    date_reg_org_org.setText(date_reg);

                    if (!address.equals("Адрес") && !address.isEmpty()){
                        SpannableString content = new SpannableString(address);
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        address_org_org_page.setTextColor(getResources().getColor(R.color.blue_link, getTheme()));
                        address_org_org_page.setText(content);
                    } else {
                        address_org_org_page.setText("Адрес не указан");
                        address_org_org_page.setTextColor(getResources().getColor(R.color.dark_gray_2, getTheme()));
                    }

                    if (!website.equals("Сайт организации") && !website.isEmpty()){
                        SpannableString contentlink = new SpannableString(website);
                        contentlink.setSpan(new UnderlineSpan(), 0, contentlink.length(), 0);
                        website_org_org_page.setText(contentlink);
                    }
                }
            }
        });
    }

    public void hidden_other(TextView textView, boolean flag){
        if (flag)
            textView.setVisibility(View.GONE);
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

    public void profile(View view){
        Intent intent;

        if (mAuth != null)
            intent = new Intent(this, ProfileActivityUser.class);
        else
            intent = new Intent(this, RegistrationActivity.class);

        startActivity(intent);
    }

    public void organization(View view){
        Intent intent = new Intent(this, OrganizationsActivity.class);
        startActivity(intent);
    }

    public void requisits(View view){
        dialog_requisites.setContentView(R.layout.dialog_requisites);
        dialog_requisites.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_requisites.show();
        if (requisits != null && !requisits.isEmpty()) {
            TextView requisits_text = dialog_requisites.findViewById(R.id.requisits_text);
            requisits_text.setText(requisits);
        }
    }

    public void statistics(View view){
        Intent intent = new Intent(this, Organization_statistics.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
    }

    public void page_photo_org(View view){
        Intent intent = new Intent(OrganizationsPage.this, HomeActivity.class);
        intent.putExtra("flag_org", true);
        intent.putExtra("id_org", getIntent().getStringExtra("id"));
        startActivity(intent);
    }

    public void page_animal_org(View view){
        Intent intent = new Intent(OrganizationsPage.this, CardsMainActivity.class);
        intent.putExtra("flag_org", true);
        intent.putExtra("id_org", getIntent().getStringExtra("id"));
        startActivity(intent);
    }

    public void page_ads_org(View view){
        Intent intent = new Intent(OrganizationsPage.this, HelpActivity.class);
        intent.putExtra("flag_org", true);
        intent.putExtra("id_org", getIntent().getStringExtra("id"));
        startActivity(intent);
    }

    public void setting(View view){
        Intent intent = new Intent(OrganizationsPage.this, SettingProfile.class);
        intent.putExtra("is_org", true);
        startActivity(intent);
    }


    public void add_org_notification(View view){

    }
}