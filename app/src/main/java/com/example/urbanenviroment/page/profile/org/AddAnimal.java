package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AddAnimal extends AppCompatActivity {

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private ImageView imageView;
    private String sex;
    private MaterialEditText name, age, state, kind, species, description;
    byte[] byteArray;
    Calendar calendar_text;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_animal);
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

            imageView = (ImageView) findViewById(R.id.img_add_photo_animal);

            if (requestCode == RQS_OPEN_IMAGE ||
                    requestCode == RQS_GET_IMAGE ||
                    requestCode == RQS_PICK_IMAGE) {

                imageView.setImageBitmap(null);

                Uri mediaUri = data.getData();
                String mediaPath = mediaUri.getPath();

                Uri selectedImage = data.getData();
                String file = selectedImage.getPath();

                try {
                    InputStream inputStream = getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    byteArray = stream.toByteArray();

                    imageView.setImageBitmap(bm);

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

    public void button_date(View view){
        age = findViewById(R.id.add_date_animal);
        calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        dpd = new DatePickerDialog(AddAnimal.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                age.setText(dayOfMonth + "." + (month + 1) + "." + year);
            }
        }, year_first, month_first, day_first);
        dpd.show();
    }

    public void clear_name(View view){
        clear(R.id.add_name_animal);
    }

    public void clear_kind(View view){
        clear(R.id.add_kind_animal);
    }

    public void clear_species(View view){
        clear(R.id.add_species_animal);
    }

    public void clear_state(View view){
        clear(R.id.add_state_animal);
    }

    public void clear_description(View view){
        clear(R.id.add_description_animal);
    }

    public void getParameter(){
        ParseObject animal = new ParseObject("Animals");

        ParseFile photo = new ParseFile(byteArray);
        animal.put("name", name.getText().toString());
        animal.put("state",  state.getText().toString());
        animal.put("species", species.getText().toString());
        animal.put("description", description.getText().toString());
        animal.put("age", age.getText().toString());
        animal.put("sex", sex);
        animal.put("image", photo);

        ParseQuery<ParseObject> query_3 = ParseQuery.getQuery("Animal_kind");
        query_3.whereEqualTo("name", kind.getText().toString());
        query_3.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ParseObject id_kind = ParseObject.createWithoutData("Animal_kind", object.getObjectId());
                    animal.put("id_kind", id_kind);

                    ParseObject ptr = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
                    animal.put("id_user", ptr);

                    Intent intent = new Intent(AddAnimal.this, ProfileActivityOrg.class);
                    startActivity(intent);
                    animal.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                            } else {
                                ParseUser.logOut();
                                Toast.makeText(AddAnimal.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    ParseObject animal_kind = new ParseObject("Animal_kind");
                    animal_kind.put("name", kind.getText().toString());
                    animal_kind.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseObject ptr = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
                            animal.put("id_user", ptr);
                            animal.put("id_kind", animal_kind);

                            Intent intent = new Intent(AddAnimal.this, ProfileActivityOrg.class);
                            startActivity(intent);
                            animal.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null) {
                                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        ParseUser.logOut();
                                        Toast.makeText(AddAnimal.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void btn_save(View view){
        Boolean flagCheckup = true;

        name = findViewById(R.id.add_name_animal);
        age = findViewById(R.id.add_date_animal);
        state = findViewById(R.id.add_state_animal);
        kind = findViewById(R.id.add_kind_animal);
        species = findViewById(R.id.add_species_animal);
        description = findViewById(R.id.add_description_animal);

        RadioButton sex_man = findViewById(R.id.button_switch_man);
        RadioButton sex_woman = findViewById(R.id.button_switch_woman);

        if (sex_man.isChecked()){
            goneMessage(R.id.error_animal_sex);
            sex = "Самец";
        }
        else if (sex_woman.isChecked()) {
            goneMessage(R.id.error_animal_sex);
            sex = "Самка";
        }
        else {
            errorMessage(R.id.error_animal_sex);
            flagCheckup = false;
        }

        if (byteArray == null){
            errorMessage(R.id.error_animal_image);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_animal_image);
        }

        if (name.getText().toString().equals("")){
            errorMessage(R.id.error_animal_name);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_animal_name);
        }

        if (age.getText().toString().equals("")){
            errorMessage(R.id.error_animal_age);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_animal_age);
        }

        if (state.getText().toString().equals("")){
            errorMessage(R.id.error_animal_state);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_animal_state);
        }

        if (kind.getText().toString().equals("")){
            errorMessage(R.id.error_animal_kind);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_animal_kind);
        }

        if (species.getText().toString().equals("")){
            errorMessage(R.id.error_animal_species);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_animal_species);
        }

        if (description.getText().toString().equals("")){
            errorMessage(R.id.error_animal_description);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_animal_description);
        }

        if (flagCheckup){
            getParameter();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы заполнили не все поля!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void errorMessage(int id){
        TextView textName = (TextView) findViewById(id);
        textName.setVisibility(View.VISIBLE);
    }

    public void goneMessage(int id){
        TextView textName = findViewById(id);
        textName.setVisibility(View.GONE);
    }

    public void btn_cancel(View view){
        name.setText("");
        age.setText("");
        state.setText("");
        kind.setText("");
        species.setText("");
        description.setText("");

        Intent intent = new Intent(AddAnimal.this, ProfileActivityOrg.class);
        startActivity(intent);
    }
}