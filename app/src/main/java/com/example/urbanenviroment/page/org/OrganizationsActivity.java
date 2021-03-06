package com.example.urbanenviroment.page.org;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.OrganizationsAdapter;
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.profile.org.AddHelp;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.Dialog_Search;
import com.example.urbanenviroment.page.profile.registr_authoriz.RegistrationActivity;
import com.example.urbanenviroment.page.profile.user.ProfileActivityUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.loopeer.shadow.ShadowView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class OrganizationsActivity extends AppCompatActivity {

    public int check;
    RecyclerView orgRecycler;
    OrganizationsAdapter orgAdapter;
    List<Organizations> orgList;
    RadioButton decrease, btn_data_reg, btn_count_animal, btn_count_ads;
    Dialog dialog_search;

    class OrgComparator implements Comparator<Organizations> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Organizations o1, Organizations o2) {
            switch (check){
                case 0:
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("d.M.y");

                    Date date_1 = null;
                    Date date_2 = null;
                    try {
                        date_1 = format.parse(o1.getDate());
                        date_2 = format.parse(o2.getDate());
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    return date_1.compareTo(date_2);

                case 1:
                    return o1.getCount_animal().compareTo(o2.getCount_animal());

                case 2:
                    return o1.getCount_ads().compareTo(o2.getCount_ads());

                default:
                    return o1.getCount_photo().compareTo(o2.getCount_photo());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations);
        FrameLayout rectangle = (FrameLayout) findViewById(R.id.rectangle_search_org);

        init();

        dialog_search = new Dialog(this);

        SearchView searchView = (SearchView) findViewById(R.id.search_view_org_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setOrgRecycler(filter(orgList, newText));
                return false;
            }
        });

        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_organization_activity);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    private List<Organizations> filter(List<Organizations> strings, String text){
        ArrayList<Organizations> filterString = new ArrayList<>();

        for (Organizations word: strings){
            String item = word.getName_org().toLowerCase(Locale.ROOT);
            if (item.contains(text.toLowerCase(Locale.ROOT)))
                filterString.add(word);
        }
        return filterString;
    }

    public void init(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        orgList = new ArrayList<>();

        db.collection("User").whereEqualTo("is_org", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        String name = document.getString("name");
                        String date = new SimpleDateFormat("dd.MM.yyyy").format(document.getDate("reg_date"));
                        String address = document.getString("address");
                        String description = document.getString("description");
                        String phone = document.getString("phone");
                        String email = document.getString("email");
                        String website = document.getString("website");
                        String requisits = document.getString("requisits");
                        String count_animal = "";
                        String count_photo = "";
                        String count_ads = "";

                        if (document.getLong("count_animal") != null)
                            count_animal = document.getLong("count_animal").toString();
                        if (document.getLong("count_ads") != null)
                            count_ads = document.getLong("count_ads").toString();
                        if (document.getLong("count_photo") != null)
                            count_photo = document.getLong("count_photo").toString();

                        String image = document.getString("image");

                        orgList.add(new Organizations(id, name, image, phone, address, email, website, description,
                                count_animal, count_ads, count_photo, date, requisits));
                        setOrgRecycler(orgList);
                    }
                }
            }
        });
    }

    private void setOrgRecycler(List<Organizations> orgList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        orgRecycler = findViewById(R.id.OrgRecycler);
        orgRecycler.setLayoutManager(layoutManager);

        orgAdapter = new OrganizationsAdapter(this, orgList);
        orgRecycler.setAdapter(orgAdapter);
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

    public void find(View view){
        FrameLayout rectangle = (FrameLayout) findViewById(R.id.rectangle_search_org);

        if (rectangle.getVisibility() == View.GONE){
            rectangle.setVisibility(View.VISIBLE);
        }
        else{
            rectangle.setVisibility(View.GONE);
        }
    }

    public void sort(View view){

        CardView org_view = this.findViewById(R.id.org_cardview);
        TextView sort_text = this.findViewById(R.id.sort_text);
        ImageView sort_arrow = this.findViewById(R.id.sort_arrow);

        sort_arrow.setColorFilter(getResources().getColor(R.color.white));
        org_view.setCardBackgroundColor(getResources().getColor(R.color.basic_blue));
        sort_text.setTextColor(getResources().getColor(R.color.white));

        dialog_search.setContentView(R.layout.dialog_search);

        dialog_search.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_search.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void accept(View view) {

        CardView org_view = this.findViewById(R.id.org_cardview);
        TextView sort_text = this.findViewById(R.id.sort_text);
        ImageView sort_arrow = this.findViewById(R.id.sort_arrow);

        sort_arrow.setColorFilter(getResources().getColor(R.color.black));
        org_view.setCardBackgroundColor(getResources().getColor(R.color.white));
        sort_text.setTextColor(getResources().getColor(R.color.black));

        dialog_search.dismiss();

        decrease = (RadioButton) dialog_search.findViewById(R.id.button_sort_dialog_decrease);
        btn_data_reg = (RadioButton) dialog_search.findViewById(R.id.button_sort_dialog_data_reg);
        btn_count_animal = (RadioButton) dialog_search.findViewById(R.id.button_sort_dialog_count_animal);
        btn_count_ads = (RadioButton) dialog_search.findViewById(R.id.button_sort_dialog_count_ads);

        if (btn_data_reg.isChecked())
            check = 0;
        else if (btn_count_animal.isChecked())
            check = 1;
        else if (btn_count_ads.isChecked())
            check = 2;
        else
            check = 3;

        if (decrease.isChecked())
            Collections.sort(orgList, new OrgComparator().reversed());
        else
            Collections.sort(orgList, new OrgComparator());

        setOrgRecycler(orgList);
    }

}