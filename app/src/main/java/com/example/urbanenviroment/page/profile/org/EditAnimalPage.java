package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class EditAnimalPage extends AppCompatActivity {

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private ImageView imageView;

    Calendar calendar_text;
    DatePickerDialog dpd;

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
        TextView text_description_animal_edit_page = (TextView) findViewById(R.id.text_description_animal_edit_page);


        text_name_animal_edit_page.setText(getIntent().getStringExtra("name_animal"));
        text_kind_animal_edit_page.setText(getIntent().getStringExtra("kind_animal"));
        text_species_animal_edit_page.setText(getIntent().getStringExtra("species_animal"));
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

                //display the image
                try {
                    InputStream inputStream = getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    byte[] byteArray = stream.toByteArray();

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

    public void change(FrameLayout frame, LinearLayout layout){
        frame.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    public void cancel(FrameLayout frame, LinearLayout layout){
        frame.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
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


    public void change_name_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_name);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_name);

        change(frame, layout);
    }

    public void cancel_name_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_name);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_name);

        cancel(frame, layout);
        //clear(R.id.add_animal_org);
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
        //clear(R.id.add_kind_org);
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
        //clear(R.id.add_species_org);
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
        //clear(R.id.add_species_org);
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
        //clear(R.id.add_species_org);
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
        //clear(R.id.add_species_org);
    }

    public void change_description_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_description);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_description);

        change(frame, layout);
    }

    public void cancel_description_animal_edit_page(View view){
        FrameLayout frame = (FrameLayout) findViewById(R.id.text_change_animal_description);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_change_animal_description);

        cancel(frame, layout);
        //clear(R.id.add_species_org);
    }

    public void edit_photo(View view){

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
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Animals");
//
//        query.whereEqualTo("objectId", );
//        query.getFirstInBackground(new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException ex) {
//                if (object != null){
//                    object.deleteInBackground();
//
//                    Intent intent = new Intent(context, DeletePhoto.class);
//                    context.startActivity(intent);
//                }
//            }
//        });
    }

}