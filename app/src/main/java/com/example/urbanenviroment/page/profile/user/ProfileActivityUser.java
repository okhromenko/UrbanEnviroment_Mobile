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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.profile.org.EditHelp;
import com.example.urbanenviroment.page.profile.org.ProfileActivityOrg;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.settings.SettingProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class ProfileActivityUser extends AppCompatActivity {
    private ProgressDialog progressDialog;

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private FirebaseAuth mAuth;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog(ProfileActivityUser.this);

        TextView name = findViewById(R.id.name_profile);
        TextView email = findViewById(R.id.email_profile);
        TextView date = findViewById(R.id.data_profile);
        ImageView image_user = (ImageView) findViewById(R.id.img_profile_image_user);

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
                        name.setText(document.getString("name"));
                        email.setText(document.getString("email"));
                        String reg_date = new SimpleDateFormat("d.M.y").format(Objects.requireNonNull(document.getDate("reg_date")));
                        date.setText(reg_date);

                        if (mAuth.getCurrentUser().getPhotoUrl() != null){
                            image = Uri.parse(((mAuth.getCurrentUser()).getPhotoUrl()).toString()).toString();
                            Picasso.get().load(image).into(image_user);
                        }

                    } else {
                        Log.d(TAG, "Проблемы при входе, пользователь не найден");
                    }
                } else {
                    Log.d(TAG, "Проблемы при входе ", task.getException());
                }
            }
        });
    }

    public void loading_photo_user(View view){
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

            ImageView imageView = (ImageView) findViewById(R.id.img_profile_image_user);

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
        startActivity(intent);
    }

    public void favorites(View view){
        Intent intent = new Intent(this, FavoritesProfileUserAnimals.class);
        startActivity(intent);
    }

    public void notifications(View view){
        Intent intent = new Intent(this, NotificationsProfileUser.class);
        startActivity(intent);
    }

    public void exit(View view) {
        FirebaseAuth.getInstance().signOut();
        progressDialog.dismiss();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}