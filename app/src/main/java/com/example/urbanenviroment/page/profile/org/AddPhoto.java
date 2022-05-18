package com.example.urbanenviroment.page.profile.org;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.AnimalPhotoDeleteOrgAdapter;
import com.example.urbanenviroment.adapter.AnimalsAdapter;
import com.example.urbanenviroment.adapter.CategoryAnimalAdapter;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.parse.FindCallback;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddPhoto extends AppCompatActivity {

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private ImageView imageView;
    private byte[] byteArray;
    private ArrayList<String> name = new ArrayList<>();
    private HashMap<String, ParseObject> animals = new HashMap<>();
    ParseObject id_a;
    public static String name_category;

    RecyclerView categoryRecycler;
    CategoryAnimalAdapter categoryAnimalAdapter;
    List<CategoryAnimals> categoryAnimalsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Animals");
        ParseObject id_user = ParseObject.createWithoutData("_User", parseUser.getObjectId());
        query.whereEqualTo("id_user", id_user);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    add_list(objects);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show();
                }
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.search_view_app_photo);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setCategoryAnimalsRecycler(filter(categoryAnimalsList, newText));
                return false;
            }
        });
    }

    private List<CategoryAnimals> filter(List<CategoryAnimals> strings, String text){
        ArrayList<CategoryAnimals> filterString = new ArrayList<>();

        for (CategoryAnimals word: strings){
            String item = word.getTitle();
            if (item.contains(text) || item.toLowerCase().contains(text) || item.toUpperCase().contains(text))
                filterString.add(word);
        }
        return filterString;
    }

    public void add_list(List<ParseObject> objects){
        categoryAnimalsList = new ArrayList<>();

        for (ParseObject i : objects){
            String name_animal = i.get("name").toString();

            name.add(name_animal);
            animals.put(name_animal, i);

            categoryAnimalsList.add(new CategoryAnimals(name_animal));
            setCategoryAnimalsRecycler(categoryAnimalsList);
        }
    }

    private void setCategoryAnimalsRecycler(List<CategoryAnimals> categoryAnimalsList){
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager (3, LinearLayoutManager.HORIZONTAL);

        categoryRecycler = findViewById(R.id.RecyclerView_category_list);
        categoryRecycler.setLayoutManager(gridLayoutManager);

        categoryAnimalAdapter = new CategoryAnimalAdapter(this, categoryAnimalsList, false, 3);
        categoryRecycler.setAdapter(categoryAnimalAdapter);
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

    public void getParameter() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseObject collection = new ParseObject("Collection");

        id_a = animals.get(name_category);

        ParseFile photo = new ParseFile(byteArray);
        ParseObject ptr = ParseObject.createWithoutData("Animals", id_a.getObjectId());

        collection.put("image", photo);
        collection.put("id_animal", ptr);
        collection.put("id_user", parseUser);

        collection.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddPhoto.this, AddPhoto.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void save(View view) {
        Boolean flagCheckup = true;

        if (byteArray == null){
            errorMessage(R.id.error_photo_image);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_photo_image);
        }

        //Проверка, выбрано ли животное из списка
        if (animals.get(name_category) == null){
            errorMessage(R.id.error_photo_animal);
            flagCheckup = false;
        } else {
            goneMessage(R.id.error_photo_animal);
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
        TextView textName = (TextView) findViewById(id);
        textName.setVisibility(View.GONE);
    }

    public void cancel(View view){
        Intent intent = new Intent(AddPhoto.this, ProfileActivityOrg.class);
        startActivity(intent);
    }

}