package com.example.urbanenviroment.page.profile.org;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.example.urbanenviroment.page.profile.settings.SettingPageOrg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class EditAnimalPage extends AppCompatActivity {

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private ImageView imageView;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    Calendar calendar_text;
    DatePickerDialog dpd;
    String description;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animal_page);

        TextView text_name_animal_edit_page = (TextView) findViewById(R.id.text_name_animal_edit_page);
        TextView text_kind_animal_edit_page = (TextView) findViewById(R.id.text_kind_animal_edit_page);
        TextView text_species_animal_edit_page = (TextView) findViewById(R.id.text_species_animal_edit_page);
        TextView text_sex_animal_edit_page = (TextView) findViewById(R.id.text_sex_animal_edit_page);
        TextView text_state_animal_edit_page = (TextView) findViewById(R.id.text_state_animal_edit_page);
        TextView text_age_animal_edit_page = (TextView) findViewById(R.id.text_age_animal_edit_page);
        EditText text_edit_description = (EditText) findViewById(R.id.text_edit_description);
        ImageView img_main_animal_photo = findViewById(R.id.img_main_animal_photo);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        if (getIntent().getStringExtra("id") != null){
            DocumentReference docRef = db.collection("Animal").document(getIntent().getStringExtra("id"));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            text_name_animal_edit_page.setText(document.getString("name"));
                            text_kind_animal_edit_page.setText(document.getString("kind"));
                            text_species_animal_edit_page.setText(document.getString("species"));
                            text_sex_animal_edit_page.setText(document.getString("sex"));
                            text_state_animal_edit_page.setText(document.getString("state"));
                            text_age_animal_edit_page.setText(document.getString("age"));
                            description = document.getString("description");
                            text_edit_description.setText(description);

                            storageRef.child(document.getString("image")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Picasso.get().load(Uri.parse(uri.toString())).into(img_main_animal_photo);
                                }
                            });
                        } else {
                            Log.d(TAG, "Данные не найдены");
                        }
                    }
                }
            });
        }
        else{
            text_name_animal_edit_page.setText(getIntent().getStringExtra("name_animal"));
            text_kind_animal_edit_page.setText(getIntent().getStringExtra("kind_animal"));
            text_species_animal_edit_page.setText(getIntent().getStringExtra("species_animal"));
            text_sex_animal_edit_page.setText(getIntent().getStringExtra("sex_animal"));
            text_state_animal_edit_page.setText(getIntent().getStringExtra("state_animal"));
            text_age_animal_edit_page.setText(getIntent().getStringExtra("age_animal"));
            text_edit_description.setText(getIntent().getStringExtra("description_animal"));
            description = getIntent().getStringExtra("description_animal");
            Picasso.get().load(getIntent().getStringExtra("image_animal")).into(img_main_animal_photo);
        }
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

            imageView = (ImageView) findViewById(R.id.img_main_animal_photo);

            if (requestCode == RQS_OPEN_IMAGE ||
                    requestCode == RQS_GET_IMAGE ||
                    requestCode == RQS_PICK_IMAGE) {

                imageView.setImageBitmap(null);

                Uri mediaUri = data.getData();

                //display the image
                try {
                    InputStream inputStream = getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    byte[] byteArray = stream.toByteArray();

                    imageView.setImageBitmap(bm);

                    StorageReference imageRef = storageRef.child(mediaUri.getPath());
                    UploadTask uploadTask = imageRef.putBytes(byteArray);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), "Небольшие проблемы с загрузкой", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            save("image", imageRef.getPath());
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clear(int id){
        TextView textName = (TextView) findViewById(id);
        textName.setText("");
    }

    public void change(FrameLayout frame, LinearLayout layout){
        frame.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    public void cancel(FrameLayout frame, LinearLayout layout){
        frame.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
    }

    public void clear_radio(int id){
        RadioButton RB = (RadioButton) findViewById(id);
        RB.setChecked(false);
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

    public void save(String to, String what){
        id = getIntent().getStringExtra("id");

        DocumentReference changeRef = db.collection("Animal").document(id);

        changeRef.update(to, Objects.requireNonNull(what)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditAnimalPage.this, EditAnimalPage.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),  "Что-то пошло не так", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void save_name_animal_edit_page(View view){
        MaterialEditText name = findViewById(R.id.add_animal_org);
        save("name", Objects.requireNonNull(name.getText()).toString());
    }

    public void save_kind_animal_edit_page(View view){
        MaterialEditText kind = findViewById(R.id.add_kind_org);
        save("kind", Objects.requireNonNull(kind.getText()).toString());

        HashMap<String, Object> kindMap = new HashMap<>();
        kindMap.put("name", kind.getText().toString());

        db.collection("AnimalKind").document(kind.getText().toString()).set(kindMap);
    }

    public void save_species_animal_edit_page(View view){
        MaterialEditText species = findViewById(R.id.add_species_org);
        save("species", Objects.requireNonNull(species.getText()).toString());
    }

    public void save_sex_animal_edit_page(View view){
        RadioButton sex = findViewById(R.id.button_switch_man);
        String sex_string;

        if (!sex.isChecked())
            sex_string = "Самка";
        else sex_string = "Самец";

        save("sex", sex_string);
    }

    public void save_state_animal_edit_page(View view){
        MaterialEditText state = findViewById(R.id.add_state_animal);
        save("state", Objects.requireNonNull(state.getText()).toString());
    }

    public void save_age_animal_edit_page(View view){
        MaterialEditText age = findViewById(R.id.change_date_animal);
        save("age", Objects.requireNonNull(age.getText()).toString());
    }

    public void save_description_animal_edit_page(View view){
        MaterialEditText description = findViewById(R.id.text_edit_description);
        save("description", Objects.requireNonNull(description.getText()).toString());
    }

    public void change_name_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_name);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_name);

        change(frame, layout);
    }

    public void cancel_name_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_name);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_name);

        cancel(frame, layout);
        clear(R.id.add_animal_org);
    }

    public void change_kind_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_kind_animal);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_kind_animal);

        change(frame, layout);
    }

    public void cancel_kind_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_kind_animal);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_kind_animal);

        cancel(frame, layout);
        clear(R.id.add_kind_org);
    }

    public void change_species_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_species_animal);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_species_animal);

        change(frame, layout);
    }

    public void cancel_species_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_species_animal);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_species_animal);

        cancel(frame, layout);
        clear(R.id.add_species_org);
    }

    public void change_sex_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_sex);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_sex);

        change(frame, layout);
    }

    public void cancel_sex_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_sex);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_sex);

        cancel(frame, layout);
        clear_radio(R.id.button_switch_man);
        clear_radio(R.id.button_switch_woman);
    }

    public void change_state_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_state);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_state);

        change(frame, layout);
    }

    public void cancel_state_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_state);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_state);

        cancel(frame, layout);
        clear(R.id.add_state_animal);
    }

    public void change_age_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_age);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_age);

        change(frame, layout);
    }

    public void button_date(View view){
        MaterialEditText age = (MaterialEditText) findViewById(R.id.change_date_animal);
        calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        dpd = new DatePickerDialog(EditAnimalPage.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                age.setText(dayOfMonth + "." + (month + 1) + "." + year);
            }
        }, year_first, month_first, day_first);
        dpd.show();
    }

    public void cancel_age_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_age);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_age);

        cancel(frame, layout);
        clear(R.id.change_date_animal);
    }

    public void cancel_description_animal_edit_page(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_description);
        EditText desc = (EditText) findViewById(R.id.text_edit_description);
        desc.setText(description);
    }

    public void clear_name(View view){
        clear(R.id.add_animal_org);
    }

    public void clear_kind(View view){
        clear(R.id.add_kind_org);
    }

    public void clear_species(View view){
        clear(R.id.add_species_org);
    }

    public void clear_state(View view){
        clear(R.id.add_state_animal);
    }

    public void delete_animal(View view){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Animals");

        query.whereEqualTo("objectId", getIntent().getStringExtra("id"));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException ex) {
                if (object != null){
                    object.deleteInBackground();

                    Intent intent = new Intent(EditAnimalPage.this, EditAnimal.class);
                    startActivity(intent);
                }
            }
        });
    }

}