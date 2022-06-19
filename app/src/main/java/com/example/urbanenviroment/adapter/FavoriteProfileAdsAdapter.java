package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.help.HelpPage;
import com.example.urbanenviroment.page.profile.org.EditHelpPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class FavoriteProfileAdsAdapter extends RecyclerView.Adapter<FavoriteProfileAdsAdapter.FavoriteProfileAdsViewHolder> {

    private DocumentSnapshot[] currentDocument;
    private FirebaseFirestore db;
    Context context;
    List<Help> helpList;
    int color, image, color_transperent;
    boolean is_org;

    public FavoriteProfileAdsAdapter(Context context, List<Help> helpList) {
        this.context = context;
        this.helpList = helpList;
    }

    @NonNull
    @Override
    public FavoriteProfileAdsAdapter.FavoriteProfileAdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View helpItem = LayoutInflater.from(context).inflate(R.layout.help_item, parent, false);
        return new FavoriteProfileAdsAdapter.FavoriteProfileAdsViewHolder(helpItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteProfileAdsAdapter.FavoriteProfileAdsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        color = Color.GRAY;
        color_transperent = Color.GRAY;
        image = R.drawable.img_things_item;

        find_type(helpList.get(position).getType_help());

        holder.img_item_help.setImageDrawable(ContextCompat.getDrawable(context, image));

        holder.img_line_help.setCardBackgroundColor(color);
        holder.img_box_help.setCardBackgroundColor(color);
        holder.type_ads_help.setText(helpList.get(position).getType_help());

        Picasso.get().load(helpList.get(position).getImg_org()).into(holder.img_org_help);
        holder.name_org_help.setText(helpList.get(position).getName_org());
        holder.button_favorite_help.setImageResource(R.drawable.button_favorite_press);


        String str = helpList.get(position).getDescription();
        if (str.length() > 200) {
            str = str.substring(0,200) + "...";
            holder.description_help.setText(str);
        } else {
            holder.description_help.setText(helpList.get(position).getDescription());
        }

        holder.status_help.setText(helpList.get(position).getStatus());
        holder.date_help.setText(helpList.get(position).getDate_first());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HelpPage.class);

                find_type(helpList.get(position).getType_help());

                intent.putExtra("id", helpList.get(position).getId());
                intent.putExtra("type_ads_help", helpList.get(position).getType_help());
                intent.putExtra("status_help", helpList.get(position).getStatus());
                intent.putExtra("date_first_help", helpList.get(position).getDate_first());
                intent.putExtra("date_last_help", helpList.get(position).getDate_last());
                intent.putExtra("description_help", helpList.get(position).getDescription());
                intent.putExtra("name_org_help", helpList.get(position).getName_org());
                intent.putExtra("image", image);
                intent.putExtra("color", color);
                intent.putExtra("color_transperent", color_transperent);

                context.startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null){

            currentDocument = new DocumentSnapshot[helpList.size()];

            db.collection("User").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    if ((Boolean) Boolean.TRUE.equals(document.getBoolean("is_org"))){
                        holder.button_favorite_help.setVisibility(View.GONE);
                        is_org = true;
                    }
                    else{
                        holder.button_favorite_help.setVisibility(View.VISIBLE);
                        is_org = false;
                    }
                }
            });


            holder.button_favorite_help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.collection("FavoriteAds").document(helpList.get(position).getId()).delete();
                    helpList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

    }

    public void find_type(String st){
        switch (st) {
            case "Еда":
                color = ContextCompat.getColor(context, R.color.food);
                color_transperent = ContextCompat.getColor(context, R.color.food_transperent);
                image = R.drawable.img_food_item;
                break;
            case "Вещи":
                color = ContextCompat.getColor(context, R.color.things);
                color_transperent = ContextCompat.getColor(context, R.color.things_transperent);
                image = R.drawable.img_things_item;
                break;
            case "Волонтерство":
                color = ContextCompat.getColor(context, R.color.help);
                color_transperent = ContextCompat.getColor(context, R.color.help_transperent);
                image = R.drawable.img_help_item;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }

    public static final class FavoriteProfileAdsViewHolder extends RecyclerView.ViewHolder{

        ImageButton button_favorite_help, button_setting_help_edit_animal, button_delete_edit_animal;
        CardView img_line_help, img_box_help;
        ImageView img_org_help, img_item_help;
        TextView name_org_help, type_ads_help, description_help, status_help, date_help;


        public FavoriteProfileAdsViewHolder(@NonNull View itemView) {
            super(itemView);

            button_setting_help_edit_animal = itemView.findViewById(R.id.button_setting_help_edit_animal);
            button_delete_edit_animal = itemView.findViewById(R.id.button_delete_edit_animal);
            button_favorite_help = itemView.findViewById(R.id.button_favorite_help);
            img_org_help = itemView.findViewById(R.id.img_org_help);
            name_org_help = itemView.findViewById(R.id.name_org_help);
            type_ads_help = itemView.findViewById(R.id.type_ads_help);
            description_help = itemView.findViewById(R.id.description_help);
            status_help = itemView.findViewById(R.id.status_help);
            date_help = itemView.findViewById(R.id.date_help);
            img_line_help = itemView.findViewById(R.id.ads_line_color);
            img_box_help = itemView.findViewById(R.id.ads_box_color);
            img_item_help = itemView.findViewById(R.id.type_ads_img);
        }
    }
}
