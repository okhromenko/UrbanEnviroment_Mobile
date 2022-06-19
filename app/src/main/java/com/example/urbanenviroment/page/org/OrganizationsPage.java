package com.example.urbanenviroment.page.org;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class OrganizationsPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        TextView website_org_org_page = findViewById(R.id.website_org_org_page);
        TextView address_org_org_page = findViewById(R.id.address_org_org_page);
        ImageView img_org_org_page = findViewById(R.id.img_org_org_page);
        ImageButton button_org_edit = findViewById(R.id.button_setting_edit_org);

        LinearLayout layout = findViewById(R.id.layout_user_buttons_org_page);
        if (!(getIntent().getStringExtra("image") == null))
            Picasso.get().load(getIntent().getStringExtra("image")).into(img_org_org_page);

        name_org_org_page.setText(getIntent().getStringExtra("name"));
        email_org_org_page.setText(getIntent().getStringExtra("email"));
        if (!getIntent().getStringExtra("phone").equals("Номер телефона"))
            phone_org_org_page.setText(getIntent().getStringExtra("phone"));
        if (!(getIntent().getStringExtra("description") == null))
            description_animal_page.setText(getIntent().getStringExtra("description"));
        count_animal_org_page.setText(getIntent().getStringExtra("count_animal"));
        count_ads_org_page.setText(getIntent().getStringExtra("count_ads"));
        count_photo_org_page.setText(getIntent().getStringExtra("count_photo"));
        date_reg_org_org.setText(getIntent().getStringExtra("date"));

        if (!getIntent().getStringExtra("address").equals("Адрес")){
            String str = getIntent().getStringExtra("address");
            SpannableString content = new SpannableString(str);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            address_org_org_page.setTextColor(getResources().getColor(R.color.blue_link, getTheme()));
            address_org_org_page.setText(content);
        } else {
            address_org_org_page.setText("Адрес не указан");
            address_org_org_page.setTextColor(getResources().getColor(R.color.dark_gray_2, getTheme()));
        }

        if (!getIntent().getStringExtra("website").equals("Сайт организации")){
            String str = getIntent().getStringExtra("website");
            SpannableString contentlink = new SpannableString(str);
            contentlink.setSpan(new UnderlineSpan(), 0, contentlink.length(), 0);
            website_org_org_page.setText(contentlink);
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (getIntent().getBooleanExtra("is_org", false))
            layout.setVisibility(View.GONE);
        else
            layout.setVisibility(View.VISIBLE);


        if (mAuth.getCurrentUser() != null){

            DocumentReference orgRef = db.collection("User").document(getIntent().getStringExtra("id"));
            orgRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            if (getIntent().getStringExtra("phone").equals("Номер телефона"))
                                phone_org_org_page.setVisibility(View.GONE);
                            else {
                                phone_org_org_page.setVisibility(View.VISIBLE);
                                hidden_other(phone_org_org_page, Boolean.TRUE.equals(document.getBoolean("hidden_phone")));
                            }

                            if (getIntent().getStringExtra("website").equals("Сайт организации"))
                                website_org_org_page.setVisibility(View.GONE);
                            else {
                                website_org_org_page.setVisibility(View.VISIBLE);
                                hidden_other(website_org_org_page, Boolean.TRUE.equals(document.getBoolean("hidden_website")));
                            }

                            hidden_other(email_org_org_page, Boolean.TRUE.equals(document.getBoolean("hidden_email")));

                            if (mAuth.getCurrentUser().getUid().equals(getIntent().getStringExtra("id")))
                                button_org_edit.setVisibility(View.VISIBLE);
                            else button_org_edit.setVisibility(View.GONE);
                        } else {
                            Log.d(TAG, "Данные не найдены");
                        }
                    }
                }
            });

        }
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

    public void profile(View view){
        Intent intent;

        if (mAuth.getCurrentUser() != null)
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
        //Должно открывать диалоговое окно с реквизитами, но я пока его не нарисовала.
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