package com.example.urbanenviroment.page.animals;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.adapter.AnimalPhotoDeleteOrgAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.org.AddAnimal;
import com.example.urbanenviroment.page.profile.org.EditAnimalPage;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AnimalPage extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentSnapshot currentDocument;
    static ImageView image_animal_page;
    static List<Animals> animalsList;
    RecyclerView animalsRecycler;
    AnimalPhotoDeleteOrgAdapter animalsAdapter;
    Boolean flag = false;
    String id, image_animal, date, name_animal, age, state, species, description, sex, name_org, image_org, kind_animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_page);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ImageButton hide_button_animal_page = (ImageButton) findViewById(R.id.hide_button_animal_page);

        TextView kind_animal_page = (TextView) findViewById(R.id.kind_animal_page);
        TextView species_animal_page = (TextView) findViewById(R.id.species_animal_page);
        TextView data_animal_page = (TextView) findViewById(R.id.data_animal_page);
        image_animal_page = (ImageView) findViewById(R.id.image_animal_page);
        TextView name_animal_page = (TextView) findViewById(R.id.name_animal_page);
        TextView description_animal_page = (TextView) findViewById(R.id.description_animal_page);
        TextView sex_animal_page = (TextView) findViewById(R.id.sex_animal_page);
        TextView age_animal_page = (TextView) findViewById(R.id.age_animal_page);
        TextView state_animal_page = (TextView) findViewById(R.id.state_animal_page);
        TextView org_animal_page = (TextView) findViewById(R.id.org_animal_page);
        ImageButton favorite_button_animal = findViewById(R.id.favorite_button_animal);

        LinearLayout edit_del_buttons = findViewById(R.id.edit_delete_buttons);

        kind_animal_page.setText(getIntent().getStringExtra("kind_animal"));
        species_animal_page.setText(getIntent().getStringExtra("species_animal"));
        data_animal_page.setText(getIntent().getStringExtra("reg_date_animal"));
        Picasso.get().load(getIntent().getStringExtra("image_animal")).into(image_animal_page);
        name_animal_page.setText(getIntent().getStringExtra("name_animal"));
        description_animal_page.setText(getIntent().getStringExtra("description_animal"));
        sex_animal_page.setText(getIntent().getStringExtra("sex_animal"));
        state_animal_page.setText(getIntent().getStringExtra("state_animal"));
        org_animal_page.setText(getIntent().getStringExtra("org"));

        init();

        try {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date ageDate = dateFormat.parse(getIntent().getStringExtra("age_animal"));
            Date todayDate = new Date();
            assert ageDate != null;
            long milliseconds = todayDate.getTime() - ageDate.getTime();
            int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
            int month = (int) (days / 30);
            int year = (int) (days / 365);

            if (month == 0) {
                age_animal_page.setText("Меньше месяца");
            }

            else if (year <= 0) {

                String monthS = " месяцев";

                if (month == 1){
                    monthS = " месяц";
                } else if ((month > 1 ) && (month < 5)) {
                    monthS = " месяца";
                }
                age_animal_page.setText(month + monthS);
            } else {
                String yearS = " лет";

                if (year < 5){
                    yearS = " года";
                } else if (year == 1) {
                    yearS = " год";
                }
                age_animal_page.setText(year + yearS);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (mAuth.getCurrentUser() != null){
            if (getIntent().getBooleanExtra("is_org", false)) {
                favorite_button_animal.setVisibility(View.GONE);
            } else {
                favorite_button_animal.setVisibility(View.VISIBLE);
            }
        }


//        ParseQuery<ParseObject> query_animal = ParseQuery.getQuery("Animals");
//        query_animal.whereEqualTo("objectId", getIntent().getStringExtra("id"));
//        query_animal.getFirstInBackground(new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (object != null && parseUser != null){
//                    if (parseUser.getObjectId().equals(object.getParseObject("id_user").getObjectId()))
//                        edit_del_buttons.setVisibility(View.VISIBLE);
//                    else edit_del_buttons.setVisibility(View.GONE);
//                }
//            }
//        });

        db.collection("FavoriteAnimal").whereEqualTo("id_animal", getIntent().getStringExtra("id"))
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot document = task.getResult();
                            if (document.isEmpty())
                                favorite_button_animal.setImageResource(R.drawable.button_favorite);
                            else{
                                currentDocument = document.getDocuments().get(0);
                                favorite_button_animal.setImageResource(R.drawable.button_favorite_press);
                            }
                        }
                    }
                });



        favorite_button_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentDocument == null) {
                    HashMap<String, Object> favoriteAnimal = new HashMap<>();
                    favoriteAnimal.put("id_animal", getIntent().getStringExtra("id"));
                    favoriteAnimal.put("name", getIntent().getStringExtra("name_animal"));
                    favoriteAnimal.put("state", getIntent().getStringExtra("state_animal"));
                    favoriteAnimal.put("species", getIntent().getStringExtra("species_animal"));
                    favoriteAnimal.put("description", getIntent().getStringExtra("description_animal"));
                    favoriteAnimal.put("age", getIntent().getStringExtra("age_animal"));
                    favoriteAnimal.put("sex", getIntent().getStringExtra("sex_animal"));
                    favoriteAnimal.put("kind", getIntent().getStringExtra("kind_animal"));
                    favoriteAnimal.put("date_reg", getIntent().getStringExtra("reg_date_animal"));

                    favoriteAnimal.put("userId", mAuth.getCurrentUser().getUid());
                    favoriteAnimal.put("username", getIntent().getStringExtra("org"));
                    favoriteAnimal.put("imageOrg", mAuth.getCurrentUser().getPhotoUrl().toString());
                    favoriteAnimal.put("image", getIntent().getStringExtra("image_animal"));


                    db.collection("FavoriteAnimal").document()
                            .set(favoriteAnimal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent intent = new Intent(AnimalPage.this, AnimalPage.class);
                                    intent.putExtra("id", getIntent().getStringExtra("id"));
                                    intent.putExtra("name_animal", getIntent().getStringExtra("name_animal"));
                                    intent.putExtra("state_animal", getIntent().getStringExtra("state_animal"));
                                    intent.putExtra("species_animal", getIntent().getStringExtra("species_animal"));
                                    intent.putExtra("description_animal", getIntent().getStringExtra("description_animal"));
                                    intent.putExtra("age_animal", getIntent().getStringExtra("age_animal"));
                                    intent.putExtra("sex_animal", getIntent().getStringExtra("sex_animal"));
                                    intent.putExtra("kind_animal", getIntent().getStringExtra("kind_animal"));
                                    intent.putExtra("reg_date_animal", getIntent().getStringExtra("reg_date_animal"));
                                    intent.putExtra("image_animal", getIntent().getStringExtra("image_animal"));
                                    intent.putExtra("org", getIntent().getStringExtra("org"));
                                    startActivity(intent);
                                    finish();
                                }
                            });
                } else {
                    DocumentReference changeRef = db.collection("FavoriteAnimal").document(currentDocument.getId());
                    changeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(AnimalPage.this, AnimalPage.class);
                            intent.putExtra("id", getIntent().getStringExtra("id"));
                            intent.putExtra("name_animal", getIntent().getStringExtra("name_animal"));
                            intent.putExtra("state_animal", getIntent().getStringExtra("state_animal"));
                            intent.putExtra("species_animal", getIntent().getStringExtra("species_animal"));
                            intent.putExtra("description_animal", getIntent().getStringExtra("description_animal"));
                            intent.putExtra("age_animal", getIntent().getStringExtra("age_animal"));
                            intent.putExtra("sex_animal", getIntent().getStringExtra("sex_animal"));
                            intent.putExtra("kind_animal", getIntent().getStringExtra("kind_animal"));
                            intent.putExtra("reg_date_animal", getIntent().getStringExtra("reg_date_animal"));
                            intent.putExtra("image_animal", getIntent().getStringExtra("image_animal"));
                            intent.putExtra("org", getIntent().getStringExtra("org"));
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }

    public void init(){

        String id_a = getIntent().getStringExtra("id_animal");
        db.collection("Collection").whereEqualTo("id_animal", getIntent().getStringExtra("id_animal")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    animalsList = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        id = document.getId();
                        image_animal = document.getString("image_collection");

                        animalsList.add(new Animals(id, name_org, image_org, name_animal, image_animal,
                                age, state, kind_animal, species, description, sex, date));
                        setAnimalsRecycler(animalsList);

                    }

                    TextView text_no_photo = (TextView) findViewById(R.id.text_no_photo);
                    if (animalsList.isEmpty()) {
                        text_no_photo.setVisibility(View.VISIBLE);
                    } else {
                        text_no_photo.setVisibility(View.GONE);
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void setAnimalsRecycler(List<Animals> animalsList){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        animalsRecycler = findViewById(R.id.RecyclerView_animal_page_photo);
        animalsRecycler.setLayoutManager(gridLayoutManager);

        animalsAdapter = new AnimalPhotoDeleteOrgAdapter(this, animalsList, true);
        animalsRecycler.setAdapter(animalsAdapter);

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

    public void delete_edit_animal(View view){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Animals");

        query.whereEqualTo("objectId", getIntent().getStringExtra("id"));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException ex) {
                if (object != null){
                    object.deleteInBackground();

                    Intent intent = new Intent(AnimalPage.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void edit_animal_page(View view) {
        Intent intent = new Intent(this, EditAnimalPage.class);

        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("kind_animal", getIntent().getStringExtra("kind_animal"));
        intent.putExtra("species_animal", getIntent().getStringExtra("species_animal"));
        intent.putExtra("reg_date_animal", getIntent().getStringExtra("reg_date_animal"));
        intent.putExtra("name_animal", getIntent().getStringExtra("name_animal"));
        intent.putExtra("description_animal", getIntent().getStringExtra("description_animal"));
        intent.putExtra("sex_animal", getIntent().getStringExtra("sex_animal"));
        intent.putExtra("age_animal", getIntent().getStringExtra("age_animal"));
        intent.putExtra("state_animal", getIntent().getStringExtra("state_animal"));
        intent.putExtra("image_animal", getIntent().getStringExtra("image_animal"));
        intent.putExtra("org", getIntent().getStringExtra("org"));

        startActivity(intent);
    }

    public void hide_animal_info(View view) {
        ImageButton hide_button_animal_page = (ImageButton) findViewById(R.id.hide_button_animal_page);

        if (!flag){
            findViewById(R.id.scrollview1).setVisibility(View.GONE);
            hide_button_animal_page.setRotation(180F);
            flag = true;
        } else {
            findViewById(R.id.scrollview1).setVisibility(View.VISIBLE);
            hide_button_animal_page.setRotation(0F);
            flag = false;
        }
    }

    public static void click_image(int position){
        Picasso.get().load(animalsList.get(position).getImg_animal()).into(image_animal_page);
    }
}