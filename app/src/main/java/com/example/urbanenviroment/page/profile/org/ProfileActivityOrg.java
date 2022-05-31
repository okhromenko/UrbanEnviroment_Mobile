package com.example.urbanenviroment.page.profile.org;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.org.OrganizationsPage;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.settings.SettingPageOrg;
import com.example.urbanenviroment.page.profile.settings.SettingProfile;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class ProfileActivityOrg extends AppCompatActivity {

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    String id, name, image, date, address, description, phone, email, website;
    Boolean is_org;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_org);

        progressDialog = new ProgressDialog(ProfileActivityOrg.this);

        TextView name_profile_org = findViewById(R.id.name_profile_org);
        TextView email_profile_org = findViewById(R.id.email_profile_org);
        TextView phone_profile_org = findViewById(R.id.phone_profile_org);
        TextView data_profile_org = findViewById(R.id.data_profile_org);
        ImageView img_profile_image = findViewById(R.id.img_profile_image_org);


        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("User").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        id = document.getId();
                        name = document.getString("name");
                        email = document.getString("email");
                        phone = document.getString("phone");
                        website = document.getString("website");
                        address = document.getString("address");
                        description = document.getString("description");
                        is_org = document.getBoolean("is_org");

                        date = new SimpleDateFormat("d.M.y").format(Objects.requireNonNull(document.getDate("reg_date")));

                        if (mAuth.getCurrentUser().getPhotoUrl() != null){
                            image = Uri.parse(((mAuth.getCurrentUser()).getPhotoUrl()).toString()).toString();
                            Picasso.get().load(image).into(img_profile_image);
                        }

                        name_profile_org.setText(name);
                        email_profile_org.setText(email);
                        phone_profile_org.setText(phone);
                        data_profile_org.setText(date);

                    } else {
                        Log.d(TAG, "Проблемы при входе, пользователь не найден");
                    }
                } else {
                    Log.d(TAG, "Проблемы при входе ", task.getException());
                }
            }
        });
    }

    public void loading_photo_org(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, RQS_OPEN_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            ImageView imageView = (ImageView) findViewById(R.id.img_profile_image_org);

            if (requestCode == RQS_OPEN_IMAGE ||
                    requestCode == RQS_GET_IMAGE ||
                    requestCode == RQS_PICK_IMAGE) {

                imageView.setImageBitmap(null);

                Uri mediaUri = data.getData();

                try {
                    InputStream inputStream = getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                    imageView.setImageBitmap(bm);

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
                            setPhotoUri(mediaUri).build();

                    assert firebaseUser != null;
                    firebaseUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Новое изображение загружено", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
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

    public void settings(View view){
        Intent intent = new Intent(this, SettingProfile.class);
        intent.putExtra("is_org", is_org);
        startActivity(intent);
    }

    public void add_animal_photo_org(View view) {
        Intent intent = new Intent(this, AddPhoto.class);
        startActivity(intent);
    }

    public void delete_photo_animal(View view) {
        Intent intent = new Intent(this, DeletePhoto.class);
        startActivity(intent);
    }

    public void add_animal_description(View view) {
        Intent intent = new Intent(this, AddAnimal.class);
        startActivity(intent);
    }

    public void edit_animal(View view) {
        Intent intent = new Intent(this, EditAnimal.class);
        startActivity(intent);
    }

    public void add_ad_org(View view){
        Intent intent = new Intent(this, AddHelp.class);
        startActivity(intent);
    }

    public void edit_help(View view) {
        Intent intent = new Intent(this, EditHelp.class);
        startActivity(intent);
    }

    public void watch(View view){
        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query_animal = new ParseQuery<>("Animals");
        query_animal.whereEqualTo("id_user", parseUser);
        query_animal.countInBackground(new CountCallback() {
            @Override
            public void done(int query_count_animal, ParseException e) {
                ParseQuery<ParseObject> query_animal = new ParseQuery<>("Ads");
                query_animal.whereEqualTo("id_user", parseUser);
                query_animal.countInBackground(new CountCallback() {
                    @Override
                    public void done(int query_count_ads, ParseException e) {
                        ParseQuery<ParseObject> query_animal = new ParseQuery<>("Collection");
                        query_animal.whereEqualTo("id_user", parseUser);
                        query_animal.countInBackground(new CountCallback() {
                            @Override
                            public void done(int query_count_photo, ParseException e) {
                                Intent intent = new Intent(ProfileActivityOrg.this, OrganizationsPage.class);
                                intent.putExtra("id", id);
                                intent.putExtra("name", name);
                                intent.putExtra("image", image);
                                intent.putExtra("address", address);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                intent.putExtra("description", description);
                                intent.putExtra("count_animal", Integer.toString(query_count_animal));
                                intent.putExtra("count_ads", Integer.toString(query_count_ads));
                                intent.putExtra("count_photo", Integer.toString(query_count_photo));
                                intent.putExtra("date", date);
                                intent.putExtra("website", website);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    public void exit(View view) {
        FirebaseAuth.getInstance().signOut();
        progressDialog.dismiss();

        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
        finish();
    }

}