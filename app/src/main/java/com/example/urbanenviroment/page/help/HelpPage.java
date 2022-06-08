package com.example.urbanenviroment.page.help;

import androidx.annotation.NonNull;
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
import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.org.EditAnimalPage;
import com.example.urbanenviroment.page.profile.org.EditHelp;
import com.example.urbanenviroment.page.profile.org.EditHelpPage;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class HelpPage extends AppCompatActivity {

    private DocumentSnapshot currentDocument;
    boolean flag = false;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        LinearLayout panel_date = (LinearLayout) findViewById(R.id.panel_date);
        CardView panel_top = (CardView) findViewById(R.id.panel_top);
        CardView panel_text = (CardView) findViewById(R.id.panel_text);
        TextView type_help_page = (TextView) findViewById(R.id.type_help_page);
        ImageView type_img_help_page = (ImageView) findViewById(R.id.type_img_help_page);
        TextView date_help_page = (TextView) findViewById(R.id.date_help_page);
        TextView first_date_help_page = (TextView) findViewById(R.id.first_date_help_page);
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
        date_help_page.setText(getIntent().getStringExtra("date_last_help"));
        first_date_help_page.setText(getIntent().getStringExtra("date_first_help"));
        status_help_page.setText(getIntent().getStringExtra("status_help"));
        description_help_page.setText(getIntent().getStringExtra("description_help"));
        org_help_page.setText(getIntent().getStringExtra("name_org_help"));


        if (mAuth.getCurrentUser() != null) {
            if (getIntent().getBooleanExtra("is_org", false)) {
                button_favorite_help_page.setVisibility(View.GONE);
            } else {
                button_favorite_help_page.setVisibility(View.VISIBLE);
            }


//        ParseQuery<ParseObject> query_ads = ParseQuery.getQuery("Ads");
//        query_ads.whereEqualTo("objectId", getIntent().getStringExtra("id"));
//        query_ads.getFirstInBackground(new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (object != null && parseUser != null){
//                    if (parseUser.getObjectId().equals(object.getParseObject("id_user").getObjectId()))
//                        edit_del_buttons.setVisibility(View.VISIBLE);
//                    else edit_del_buttons.setVisibility(View.GONE);
//                }
//            }
//        });

            db.collection("FavoriteAds").whereEqualTo("id_ads", getIntent().getStringExtra("id"))
                    .whereEqualTo("userId", mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot document = task.getResult();
                                if (document.isEmpty())
                                    button_favorite_help_page.setImageResource(R.drawable.button_favorite);
                                else {
                                    currentDocument = document.getDocuments().get(0);
                                    button_favorite_help_page.setImageResource(R.drawable.button_favorite_press);
                                }
                            }
                        }
                    });

        }

        button_favorite_help_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentDocument == null) {

                    HashMap<String, Object> favoriteAds = new HashMap<>();

                    favoriteAds.put("id_ads", getIntent().getStringExtra("id"));
                    favoriteAds.put("type", getIntent().getStringExtra("type_ads_help"));
                    favoriteAds.put("last_date", getIntent().getStringExtra("date_last_help"));
                    favoriteAds.put("first_date", getIntent().getStringExtra("date_first_help"));
                    favoriteAds.put("description",  getIntent().getStringExtra("description_help"));

                    favoriteAds.put("userId", mAuth.getUid());
                    favoriteAds.put("username", getIntent().getStringExtra("org"));
                    favoriteAds.put("imageOrg", mAuth.getCurrentUser().getPhotoUrl().toString());


                    db.collection("FavoriteAds").document()
                            .set(favoriteAds).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent intent = new Intent(HelpPage.this, HelpPage.class);
                                    intent.putExtra("color_transperent", getIntent().getIntExtra("color_transperent", 0));
                                    intent.putExtra("color", getIntent().getIntExtra("color", 0));
                                    intent.putExtra("type_ads_help", getIntent().getStringExtra("type_ads_help"));
                                    intent.putExtra("image", getIntent().getIntExtra("image", 0));
                                    intent.putExtra("date_last_help", getIntent().getStringExtra("date_last_help"));
                                    intent.putExtra("date_first_help", getIntent().getStringExtra("date_first_help"));
                                    intent.putExtra("status_help", getIntent().getStringExtra("status_help"));
                                    intent.putExtra("description_help", getIntent().getStringExtra("description_help"));
                                    intent.putExtra("name_org_help", getIntent().getStringExtra("name_org_help"));
                                    startActivity(intent);
                                    finish();
                                }
                            });
                } else {
                    DocumentReference changeRef = db.collection("FavoriteAds").document(currentDocument.getId());
                    changeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(HelpPage.this, HelpPage.class);
                            intent.putExtra("color_transperent", getIntent().getIntExtra("color_transperent", 0));
                            intent.putExtra("color", getIntent().getIntExtra("color", 0));
                            intent.putExtra("type_ads_help", getIntent().getStringExtra("type_ads_help"));
                            intent.putExtra("image", getIntent().getIntExtra("image", 0));
                            intent.putExtra("date_last_help", getIntent().getStringExtra("date_last_help"));
                            intent.putExtra("date_first_help", getIntent().getStringExtra("date_first_help"));
                            intent.putExtra("status_help", getIntent().getStringExtra("status_help"));
                            intent.putExtra("description_help", getIntent().getStringExtra("description_help"));
                            intent.putExtra("name_org_help", getIntent().getStringExtra("name_org_help"));
                            startActivity(intent);
                            finish();
                        }
                    });
                }
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

    public void card(View view){
        Intent intent = new Intent(this, CardsMainActivity.class);
        startActivity(intent);
    }

    public void delete_edit_ads(View view){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ads");

        query.whereEqualTo("objectId", getIntent().getStringExtra("id"));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException ex) {
                if (object != null){
                    object.deleteInBackground();

                    Intent intent = new Intent(HelpPage.this, HelpActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void edit_animal_page(View view) {
        Intent intent = new Intent(this, EditHelpPage.class);

        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("type_ads_help", getIntent().getStringExtra("type_ads_help"));
        intent.putExtra("status_help", getIntent().getStringExtra("status_help"));
        intent.putExtra("date_first_help", getIntent().getStringExtra("date_first_help"));
        intent.putExtra("date_last_help", getIntent().getStringExtra("date_last_help"));
        intent.putExtra("description_help", getIntent().getStringExtra("description_help"));
        intent.putExtra("name_org_help", getIntent().getStringExtra("name_org_help"));
        intent.putExtra("image", getIntent().getStringExtra("image"));
        intent.putExtra("color", getIntent().getStringExtra("color"));
        intent.putExtra("color_transperent", getIntent().getStringExtra("color_transperent"));

        startActivity(intent);
    }
}