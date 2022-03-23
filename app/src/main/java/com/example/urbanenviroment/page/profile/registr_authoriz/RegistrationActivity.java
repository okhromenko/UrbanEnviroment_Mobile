package com.example.urbanenviroment.page.profile.registr_authoriz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.urbanenviroment.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void authorization(View view){
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }
    public void user_register(View view){

        ImageView ivname = findViewById(R.id.imageView4);
        ivname.setImageResource(R.drawable.text_reg_user);

        ImageButton ibu = findViewById(R.id.imageButton);
        ibu.setImageResource(R.drawable.button_user_press);

        ImageButton ibo = findViewById(R.id.imageButton2);
        ibo.setImageResource(R.drawable.button_organization);

        LinearLayout lldata = findViewById(R.id.linearLayoutData);
        lldata.setVisibility(View.VISIBLE);

        com.rengwuxian.materialedittext.MaterialEditText inputname = findViewById(R.id.nameField);
        inputname.setHint("Введите имя пользователя");

    }

    public void org_register(View view){

        ImageView ivname = findViewById(R.id.imageView4);
        ivname.setImageResource(R.drawable.text_reg_org);

        ImageButton ibu = findViewById(R.id.imageButton);
        ibu.setImageResource(R.drawable.button_user);

        ImageButton ibo = findViewById(R.id.imageButton2);
        ibo.setImageResource(R.drawable.button_org_press);

        LinearLayout lldata = findViewById(R.id.linearLayoutData);
        lldata.setVisibility(View.VISIBLE);

        com.rengwuxian.materialedittext.MaterialEditText inputname = findViewById(R.id.nameField);
        inputname.setHint("Введите название организации");

    }
}