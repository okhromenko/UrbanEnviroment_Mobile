package com.example.urbanenviroment.page.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.animals.HomeActivity;
import com.example.urbanenviroment.page.map.MapActivity;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.adapter.OrganizationsAdapter;
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.profile.org.AddHelp;
import com.example.urbanenviroment.page.profile.registr_authoriz.AuthorizationActivity;
import com.example.urbanenviroment.page.Dialog_Search;
import com.loopeer.shadow.ShadowView;

import java.util.ArrayList;
import java.util.List;

public class OrganizationsActivity extends AppCompatActivity {

    RecyclerView orgRecycler;
    OrganizationsAdapter orgAdapter;

    //Dialog_Search dialog_search;
    Dialog dialog_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations);

        List<Organizations> orgList = new ArrayList<>();
        orgList.add(new Organizations(1, "Заповедный край", "img_org", "+79841887843",
                "Державина 19А", "Описание", "4", "3", "43",
                "03.03.2022"));
        orgList.add(new Organizations(2, "Заповедный край", "img_org", "+79841887843",
                "Державина 19А", "Описание", "4", "3", "43",
                "03.03.2022"));
        orgList.add(new Organizations(3, "Заповедный край", "img_org", "+79841887843",
                "Державина 19А", "Описание", "4", "3", "43",
                "03.03.2022"));
        orgList.add(new Organizations(4, "Заповедный край", "img_org", "+79841887843",
                "Державина 19А", "Описание", "4", "3", "43",
                "03.03.2022"));
        orgList.add(new Organizations(5, "Заповедный край", "img_org", "+79841887843",
                "Державина 19А", "Описание", "4", "3", "43",
                "03.03.2022"));
        orgList.add(new Organizations(6, "Заповедный край", "img_org", "+79841887843",
                "Державина 19А", "Описание", "4", "3", "43",
                "03.03.2022"));
        orgList.add(new Organizations(7, "Заповедный край", "img_org", "+79841887843",
                "Державина 19А", "Описание", "4", "3", "43",
                "03.03.2022"));

        setOrgRecycler(orgList);

        dialog_search = new Dialog(this);
        //dialog_search = new Dialog_Search();
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

        //dialog_search.show(getSupportFragmentManager(), "fragment");
    }

    public void accept(View view) {

        CardView org_view = this.findViewById(R.id.org_cardview);
        TextView sort_text = this.findViewById(R.id.sort_text);
        ImageView sort_arrow = this.findViewById(R.id.sort_arrow);

        sort_arrow.setColorFilter(getResources().getColor(R.color.black));
        org_view.setCardBackgroundColor(getResources().getColor(R.color.white));
        sort_text.setTextColor(getResources().getColor(R.color.black));

        dialog_search.dismiss();
    }

}