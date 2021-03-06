package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.example.urbanenviroment.page.help.HelpActivity;
import com.example.urbanenviroment.page.help.HelpPage;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Help;
import com.example.urbanenviroment.page.profile.org.EditAnimal;
import com.example.urbanenviroment.page.profile.org.EditAnimalPage;
import com.example.urbanenviroment.page.profile.org.EditHelp;
import com.example.urbanenviroment.page.profile.org.EditHelpPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    private DocumentSnapshot[] currentDocument;
    private FirebaseFirestore db;
    Context context;
    List<Help> helpList;
    int color, image, color_transperent;
    boolean flag;
    boolean is_org;

    public HelpAdapter(Context context, List<Help> helpList, boolean flag) {
        this.context = context;
        this.helpList = helpList;
        this.flag = flag;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (flag){
            View helpItem = LayoutInflater.from(context).inflate(R.layout.help_edit_item, parent, false);
            return new HelpAdapter.HelpViewHolder(helpItem);
        }

        View helpItem = LayoutInflater.from(context).inflate(R.layout.help_item, parent, false);
        return new HelpAdapter.HelpViewHolder(helpItem);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        color = Color.GRAY;
        color_transperent = Color.GRAY;
        image = R.drawable.img_things_item;

        find_type(helpList.get(position).getType_help());

        holder.img_item_help.setImageDrawable(ContextCompat.getDrawable(context, image));

        holder.img_line_help.setCardBackgroundColor(color);
        holder.img_box_help.setCardBackgroundColor(color);
        holder.type_ads_help.setText(helpList.get(position).getType_help());

        if (!flag){
            Picasso.get().load(helpList.get(position).getImg_org()).into(holder.img_org_help);
            holder.name_org_help.setText(helpList.get(position).getName_org());
        }

        String str = helpList.get(position).getDescription();
        if (str.length() > 200) {
            str = str.substring(0,200) + "...";
            holder.description_help.setText(str);
        } else {
            holder.description_help.setText(helpList.get(position).getDescription());
        }

        holder.status_help.setText(helpList.get(position).getStatus());
        holder.date_help.setText(helpList.get(position).getDate_first());

        db = FirebaseFirestore.getInstance();

        db.collection("User").document(helpList.get(position).getId_org()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
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
                        intent.putExtra("userId", helpList.get(position).getId_org());
                        intent.putExtra("image",  image);
                        intent.putExtra("color", color);
                        intent.putExtra("color_transperent", color_transperent);
                        intent.putExtra("is_org", is_org);
                        intent.putExtra("requisits", document.getString("requisits"));
                        context.startActivity(intent);
                    }
                });
            }
        });


        if (mAuth.getCurrentUser() != null){

            currentDocument = new DocumentSnapshot[helpList.size()];

            db.collection("User").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    if ((Boolean) Boolean.TRUE.equals(document.getBoolean("is_org"))){
                        if (!flag)
                            holder.button_favorite_help.setVisibility(View.GONE);
                        is_org = true;
                    }
                    else{
                        if (!flag)
                            holder.button_favorite_help.setVisibility(View.VISIBLE);
                        is_org = false;
                    }
                }
            });

            if (!flag){
                db.collection("FavoriteAds").whereEqualTo("id_ads", helpList.get(position).getId())
                        .whereEqualTo("userId", mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    QuerySnapshot document = task.getResult();
                                    if (document.isEmpty()) {
                                        currentDocument[position] = null;
                                        holder.button_favorite_help.setImageResource(R.drawable.button_favorite);
                                    }
                                    else{
                                        currentDocument[position] = document.getDocuments().get(0);
                                        holder.button_favorite_help.setImageResource(R.drawable.button_favorite_press);
                                    }
                                }
                            }
                        });


                holder.button_favorite_help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentDocument[position] == null) {

                            HashMap<String, Object> favoriteAds = new HashMap<>();

                            favoriteAds.put("id_ads", helpList.get(position).getId());
                            favoriteAds.put("type", helpList.get(position).getType_help());
                            favoriteAds.put("last_date", helpList.get(position).getDate_last());
                            favoriteAds.put("first_date", helpList.get(position).getDate_first());
                            favoriteAds.put("description",  helpList.get(position).getDescription());

                            favoriteAds.put("userId", mAuth.getUid());
                            favoriteAds.put("orgId", helpList.get(position).getId_org());
                            favoriteAds.put("username", helpList.get(position).getName_org());
                            favoriteAds.put("imageOrg", helpList.get(position).getImg_org());


                            db.collection("FavoriteAds").document()
                                    .set(favoriteAds).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @SuppressLint("NotifyDataSetChanged")
                                        @Override
                                        public void onSuccess(Void unused) {
                                            notifyDataSetChanged();
                                        }
                                    });
                        } else {
                            DocumentReference changeRef = db.collection("FavoriteAds").document(currentDocument[position].getId());
                            changeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onSuccess(Void unused) {
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
            }

        }

        if (flag){
            holder.button_setting_help_edit_animal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditHelpPage.class);
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
                    intent.putExtra("is_org", is_org);
                    context.startActivity(intent);
                }
            });

            holder.button_delete_edit_animal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                    db.collection("User").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document_user = task.getResult();
                            db.collection("User").document(mAuth.getUid()).update("count_ads",
                                    document_user.getLong("count_ads") - 1);
                        }
                    });

                    db.collection("FavoriteAds").whereEqualTo("id_ads", helpList.get(position).getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("FavoriteAds").document(document.getId()).delete();
                            }
                        }
                    });

                    db.collection("Ads").document(helpList.get(position).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(context, EditHelp.class);
                            context.startActivity(intent);
                        }
                    });
                }
            });
        }


    }

    public void find_type(String st){
        switch (st) {
            case "??????":
                color = ContextCompat.getColor(context, R.color.food);
                color_transperent = ContextCompat.getColor(context, R.color.food_transperent);
                image = R.drawable.img_food_item;
                break;
            case "????????":
                color = ContextCompat.getColor(context, R.color.things);
                color_transperent = ContextCompat.getColor(context, R.color.things_transperent);
                image = R.drawable.img_things_item;
                break;
            case "????????????????????????":
                color = ContextCompat.getColor(context, R.color.help);
                color_transperent = ContextCompat.getColor(context, R.color.help_transperent);
                image = R.drawable.img_help_item;
                break;
            case "??????????????":
                color = ContextCompat.getColor(context, R.color.money);
                color_transperent = ContextCompat.getColor(context, R.color.money_transperent);
                image = R.drawable.img_money_item;
                break;
            case "????????????":
                color = ContextCompat.getColor(context, R.color.other);
                color_transperent = ContextCompat.getColor(context, R.color.other_transperent);
                image = R.drawable.img_other_item;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }

    public static final class HelpViewHolder extends RecyclerView.ViewHolder{

        ImageButton button_favorite_help, button_setting_help_edit_animal, button_delete_edit_animal;
        CardView img_line_help, img_box_help;
        ImageView img_org_help, img_item_help;
        TextView name_org_help, type_ads_help, description_help, status_help, date_help;


        public HelpViewHolder(@NonNull View itemView) {
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
