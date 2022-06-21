package com.example.urbanenviroment.page.profile.org;

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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddAnimal extends AppCompatActivity{

    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;

    private String sex;
    private MaterialEditText name, age, state, kind, species, description;
    private Uri mediaUri;
    private byte[] byteArray;

    private boolean flagCheckup;

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

            ImageView imageView = (ImageView) findViewById(R.id.img_add_photo_animal);

            if (requestCode == RQS_OPEN_IMAGE ||
                    requestCode == RQS_GET_IMAGE ||
                    requestCode == RQS_PICK_IMAGE) {

                imageView.setImageBitmap(null);

                mediaUri = data.getData();

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

    public void button_date(View view){
        age = findViewById(R.id.add_date_animal);
        Calendar calendar_text = Calendar.getInstance();

        int day_first = calendar_text.get(Calendar.DAY_OF_MONTH);
        int month_first = calendar_text.get(Calendar.MONTH);
        int year_first = calendar_text.get(Calendar.YEAR);

        DatePickerDialog dpd = new DatePickerDialog(AddAnimal.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Month, Day;
                Month = Integer.toString(month + 1);
                if (Month.length() == 1) {
                    Month = "0" + Month;
                }
                Day = Integer.toString(dayOfMonth);
                if (Day.length() == 1) {
                    Day = "0" + Day;
                }
                age.setText(Day + "." + Month + "." + year);
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
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        StorageReference storageRef = storage.getReference();
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
                storageRef.child(imageRef.getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Date date_reg = new Date();

                        Map<String, Object> animals = new HashMap<>();
                        animals.put("name", name.getText().toString());
                        animals.put("state",  state.getText().toString());
                        animals.put("species", species.getText().toString());
                        animals.put("description", description.getText().toString());
                        animals.put("age", age.getText().toString());
                        animals.put("sex", sex);
                        animals.put("kind", kind.getText().toString());
                        animals.put("date_reg", date_reg);
                        animals.put("userId", mAuth.getCurrentUser().getUid());
                        animals.put("username", mAuth.getCurrentUser().getDisplayName());
                        animals.put("imageOrg", mAuth.getCurrentUser().getPhotoUrl().toString());
                        animals.put("image", uri.toString());


                        db.collection("Animal").add(animals).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();

                                DocumentReference User = db.collection("User").document(mAuth.getCurrentUser().getUid());

                                User.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        long count_animal = 1;
                                        if (documentSnapshot.getLong("count_animal") != null)
                                            count_animal = documentSnapshot.getLong("count_animal") + 1;

                                        User.update("count_animal", count_animal);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });


        HashMap<String, Object> kindMap = new HashMap<>();
        kindMap.put("name", kind.getText().toString());

        db.collection("AnimalKind").document(kind.getText().toString())
                .set(kindMap).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddAnimal.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Intent intent = new Intent(AddAnimal.this, ProfileActivityUser.class);
        startActivity(intent);
        finish();
    }

    public void btn_save(View view){
        flagCheckup = true;
        boolean flagInput = true;

        name = findViewById(R.id.add_name_animal);
        age = findViewById(R.id.add_date_animal);
        state = findViewById(R.id.add_state_animal);
        kind = findViewById(R.id.add_kind_animal);
        species = findViewById(R.id.add_species_animal);
        description = findViewById(R.id.add_description_animal);

        RadioButton sex_man = findViewById(R.id.button_switch_man);
        RadioButton sex_woman = findViewById(R.id.button_switch_woman);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date date = dateFormat.parse(Objects.requireNonNull(age.getText()).toString());
            Date today = new Date();
            assert date != null;
            age.setText(dateFormat.format(date));
            goneMessage(R.id.error_input_animal_age);
            if (date.before(today)) {
                goneMessage(R.id.wrong_animal_age);
            } else {
                errorMessage(R.id.wrong_animal_age);
                flagInput = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorMessage(R.id.error_input_animal_age);
            flagInput = false;
        }

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

        check(R.id.add_name_animal, R.id.error_animal_name);
        check(R.id.add_date_animal, R.id.error_animal_age);
        check(R.id.add_state_animal, R.id.error_animal_state);
        check(R.id.add_kind_animal, R.id.error_animal_kind);
        check(R.id.add_species_animal, R.id.error_animal_species);
        check(R.id.add_description_animal, R.id.error_animal_description);

        if (flagCheckup && flagInput){
            getParameter();
        } else if (!flagCheckup) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы заполнили не все поля!", Toast.LENGTH_SHORT);
            toast.show();
        } else if (!flagInput) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы неккоректно заполнили поля!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void check(int id_ET, int id_TV) {
        MaterialEditText ET = findViewById(id_ET);
        if ((ET.getText()).toString().equals("")){
            errorMessage(id_TV);
            flagCheckup = false;
        } else {
            goneMessage(id_TV);
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

        Intent intent = new Intent(AddAnimal.this, ProfileActivityUser.class);
        startActivity(intent);
    }

//    private void goNotification(){
//        String topic = "AnimalNotification";
//
//        String userId = OneSignal.getDeviceState().getUserId();
//
//        try {
////            OneSignal.postNotification(new JSONObject("{'contents': {'en':'Новое животное'}, 'include_player_ids': ['" + userId + "']}"),
////                    new OneSignal.PostNotificationResponseHandler() {
////                        @Override
////                        public void onSuccess(JSONObject response) {
////                            Log.i("OneSignalExample", "postNotification Success: " + response.toString());
////                        }
////
////                        @Override
////                        public void onFailure(JSONObject response) {
////                            Log.e("OneSignalExample", "postNotification Failure: " + response.toString());
////                        }
////                    });
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//    }
}