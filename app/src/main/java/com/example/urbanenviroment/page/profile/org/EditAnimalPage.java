package com.example.urbanenviroment.page.profile.org;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditAnimalPage extends AppCompatActivity {

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animal_page);

        TextView text_name_animal_edit_page = (TextView) findViewById(R.id.text_name_animal_edit_page);
        TextView text_kind_animal_edit_page = (TextView) findViewById(R.id.text_kind_animal_edit_page);
        TextView text_species_animal_edit_page = (TextView) findViewById(R.id.text_species_animal_edit_page);

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

    public void change(TextView text, TextView button, LinearLayout layout){
        text.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }

    public void cancel(TextView text, TextView button, LinearLayout layout){
        text.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
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
        TextView textName = (TextView) findViewById(R.id.text_name_animal_edit_page);
        TextView textchange = (TextView) findViewById(R.id.button_change_name_animal_edit_page);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_name_animal_edit_page);

        change(textName, textchange, layout);
    }

    public void cancel_name_animal_edit_page(View view){
        TextView textName = (TextView) findViewById(R.id.text_name_animal_edit_page);
        TextView textchange = (TextView) findViewById(R.id.button_change_name_animal_edit_page);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_name_animal_edit_page);

        cancel(textName, textchange, layout);
        clear(R.id.add_animal_org);
        clear(R.id.change_password_setting_name_animal);
    }

    public void change_kind_animal_edit_page(View view){
        TextView textName = (TextView) findViewById(R.id.text_kind_animal_edit_page);
        TextView textchange = (TextView) findViewById(R.id.button_change_kind_animal_edit_page);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_kind_animal_edit_page);

        change(textName, textchange, layout);
    }

    public void cancel_kind_animal_edit_page(View view){
        TextView textName = (TextView) findViewById(R.id.text_kind_animal_edit_page);
        TextView textchange = (TextView) findViewById(R.id.button_change_kind_animal_edit_page);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_kind_animal_edit_page);

        cancel(textName, textchange, layout);
        clear(R.id.add_kind_org);
        clear(R.id.change_password_setting_kind_animal);
    }

    public void change_species_animal_edit_page(View view){
        TextView textName = (TextView) findViewById(R.id.text_species_animal_edit_page);
        TextView textchange = (TextView) findViewById(R.id.button_change_species_animal_edit_page);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_species_animal_edit_page);

        change(textName, textchange, layout);
    }

    public void cancel_species_animal_edit_page(View view){
        TextView textName = (TextView) findViewById(R.id.text_species_animal_edit_page);
        TextView textchange = (TextView) findViewById(R.id.button_change_species_animal_edit_page);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_change_species_animal_edit_page);

        cancel(textName, textchange, layout);
        clear(R.id.add_species_org);
        clear(R.id.change_password_setting_species_animal);
    }



    public void clear_name(View view){
        clear(R.id.add_animal_org);
    }

    public void clear_name_password(View view){
        clear(R.id.change_password_setting_name_animal);
    }

    public void clear_kind(View view){
        clear(R.id.add_kind_org);
    }

    public void clear_kind_password(View view){
        clear(R.id.change_password_setting_kind_animal);
    }

    public void clear_species(View view){
        clear(R.id.add_species_org);
    }

    public void clear_species_password(View view){
        clear(R.id.change_password_setting_species_animal);
    }

    public void clear_data(View view){
        clear(R.id.add_date_animal);
    }

    public void clear_state(View view){
        clear(R.id.add_state_animal);
    }

    public void clear_description(View view){
        clear(R.id.add_description_animal);
    }
}