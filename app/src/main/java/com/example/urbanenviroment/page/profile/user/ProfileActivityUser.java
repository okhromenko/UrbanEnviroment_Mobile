package com.example.urbanenviroment.page.profile.user;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.org.OrganizationsPage;
import com.example.urbanenviroment.page.profile.org.AddAnimal;
import com.example.urbanenviroment.page.profile.org.AddHelp;
import com.example.urbanenviroment.page.profile.org.AddPhoto;
import com.example.urbanenviroment.page.profile.org.DeletePhoto;
import com.example.urbanenviroment.page.profile.org.EditAnimal;
import com.example.urbanenviroment.page.profile.org.EditHelp;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.settings.SettingOther;
import com.example.urbanenviroment.page.profile.settings.SettingProfile;
import com.example.urbanenviroment.page.profile.settings.SettingProfileUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Objects;

public class ProfileActivityUser extends AppCompatActivity {
    private ProgressDialog progressDialog;

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    String id, name, image, date, address, description, phone, email, website, count_animal, count_photo, count_ads;
    Boolean is_org;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog(ProfileActivityUser.this);

        TextView name_profile = findViewById(R.id.name_profile);
        TextView email_profile = findViewById(R.id.email_profile);
        TextView phone_profile = findViewById(R.id.number_profile);
        TextView date_profile = findViewById(R.id.data_profile);
        ImageView image_profile = (ImageView) findViewById(R.id.img_profile_image);

        LinearLayout linear_user = (LinearLayout) findViewById(R.id.linear_user);
        LinearLayout linear_org = (LinearLayout) findViewById(R.id.linear_org);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

                        if (document.getLong("count_animal") != null)
                            count_animal = document.getLong("count_animal").toString();
                        if (document.getLong("count_ads") != null)
                            count_ads = document.getLong("count_ads").toString();
                        if (document.getLong("count_photo") != null)
                            count_photo = document.getLong("count_photo").toString();

                        date = new SimpleDateFormat("dd.MM.yyyy").format(Objects.requireNonNull(document.getDate("reg_date")));

                        if (mAuth.getCurrentUser().getPhotoUrl() != null){
                            image = Uri.parse(((mAuth.getCurrentUser()).getPhotoUrl()).toString()).toString();
                            Picasso.get().load(image).into(image_profile);
                        }

                        if (is_org) {
                            phone_profile.setVisibility(View.VISIBLE);
                            linear_user.setVisibility(View.GONE);
                            linear_org.setVisibility(View.VISIBLE);
                        } else {
                            phone_profile.setVisibility(View.GONE);
                            linear_user.setVisibility(View.VISIBLE);
                            linear_org.setVisibility(View.GONE);
                        }


                        if (phone_profile.getText().toString().equals("?????????? ????????????????"))
                            phone_profile.setVisibility(View.GONE);

                        name_profile.setText(name);
                        email_profile.setText(email);
                        phone_profile.setText(phone);
                        date_profile.setText(date);
                    } else {
                        Log.d(TAG, "???????????????? ?????? ??????????, ???????????????????????? ???? ????????????");
                    }
                } else {
                    Log.d(TAG, "???????????????? ?????? ?????????? ", task.getException());
                }
            }
        });

    }

    public void loading_photo(View view){
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

            ImageView imageView = (ImageView) findViewById(R.id.img_profile_image);

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
                    byte[] byteArray = stream.toByteArray();

                    imageView.setImageBitmap(bm);

                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();


                    StorageReference storageRef = storage.getReference();
                    StorageReference imgRef = storageRef.child(mediaUri.getPath());
                    UploadTask uploadTask = imgRef.putBytes(byteArray);

                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                storageRef.child(imgRef.getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
                                                setPhotoUri(uri).build();

                                        assert firebaseUser != null;
                                        firebaseUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "?????????? ?????????????????????? ??????????????????", Toast.LENGTH_LONG).show();
                                                    DocumentReference changeRef = db.collection("User").document(firebaseUser.getUid());
                                                    changeRef.update("image", mAuth.getCurrentUser().getPhotoUrl().toString());
                                                    edit_fields_image(mAuth.getCurrentUser().getPhotoUrl().toString());
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void edit_fields_image(String img){

        db.collection("Animal").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("Animal").document(document.getId())
                                    .update("imageOrg", img);
                        }
                    }
                });

        db.collection("Ads").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("Ads").document(document.getId())
                                    .update("imageOrg", img);
                        }
                    }
                });

        db.collection("Collection").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("Collection").document(document.getId())
                                    .update("imageOrg", img);
                        }
                    }
                });

        db.collection("FavoriteAds").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("FavoriteAds").document(document.getId())
                                    .update("imageOrg", img);
                        }
                    }
                });

        db.collection("FavoriteAnimal").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("FavoriteAnimal").document(document.getId())
                                    .update("imageOrg", img);
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

    public void profile(View view){
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
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

    public void settings(View view){
        Intent intent;
        intent = new Intent(this, SettingProfile.class);
        intent.putExtra("is_org", is_org);

        startActivity(intent);
    }

    public void favorites(View view){
        Intent intent = new Intent(this, FavoritesProfileUserAnimals.class);
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
        Intent intent = new Intent(ProfileActivityUser.this, OrganizationsPage.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("image", image);
        intent.putExtra("address", address);
        intent.putExtra("email", email);
        intent.putExtra("phone", phone);
        intent.putExtra("description", description);
        intent.putExtra("count_animal", count_animal);
        intent.putExtra("count_ads", count_ads);
        intent.putExtra("count_photo", count_photo);
        intent.putExtra("date", date);
        intent.putExtra("website", website);
        intent.putExtra("is_org", true);
        startActivity(intent);
    }

    public void exit(View view) {
        FirebaseAuth.getInstance().signOut();
        progressDialog.dismiss();

        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
        finish();
    }
}