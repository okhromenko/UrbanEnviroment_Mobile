package com.example.urbanenviroment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.urbanenviroment.page.help.HelpPage;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Help;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    Context context;
    List<Help> helpList;

    public HelpAdapter(Context context, List<Help> helpList) {
        this.context = context;
        this.helpList = helpList;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View helpItem = LayoutInflater.from(context).inflate(R.layout.help_item, parent, false);
        return new HelpAdapter.HelpViewHolder(helpItem);

    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        int img_org_help_id = context.getResources().getIdentifier(
                helpList.get(position).getImg_org(), "drawable", context.getPackageName());
        holder.img_org_help.setImageResource(img_org_help_id);


        //Drawable drawable = AppCompatResources.getDrawable(context, R.drawable.help_line_item);
        //assert drawable != null;
        //Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        //Drawable mutableDrawable = wrappedDrawable.mutate();

        int color = Color.GRAY;
        Drawable image = ContextCompat.getDrawable(context, R.drawable.img_things_item);

        if (helpList.get(position).getType_help().equals("Еда")) {
            color = ContextCompat.getColor(context, R.color.food);
            image = ContextCompat.getDrawable(context, R.drawable.img_food_item);
        }
            //DrawableCompat.setTint(mutableDrawable, ContextCompat.getColor(context, R.color.food));
        if (helpList.get(position).getType_help().equals("Вещи")){
            color = ContextCompat.getColor(context, R.color.things);
            image = ContextCompat.getDrawable(context, R.drawable.img_things_item);
        }
            //DrawableCompat.setTint(mutableDrawable, ContextCompat.getColor(context, R.color.things));
        if (helpList.get(position).getType_help().equals("Волонтерство")){
            color = ContextCompat.getColor(context, R.color.help);
            image = ContextCompat.getDrawable(context, R.drawable.img_help_item);
        }
            //DrawableCompat.setTint(mutableDrawable, ContextCompat.getColor(context, R.color.help));

        //holder.img_line_help.setImageDrawable(drawable);
        holder.img_item_help.setImageDrawable(image);
        holder.img_line_help.setCardBackgroundColor(color);
        holder.img_box_help.setCardBackgroundColor(color);
        holder.name_org_help.setText(helpList.get(position).getName_org());
        holder.type_ads_help.setText(helpList.get(position).getType_help());
        holder.description_help.setText(helpList.get(position).getDescription());
        holder.status_help.setText(helpList.get(position).getStatus());
        holder.date_help.setText(helpList.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HelpPage.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }

    public static final class HelpViewHolder extends RecyclerView.ViewHolder{

        CardView img_line_help, img_box_help;
        ImageView img_org_help, img_item_help;
        TextView name_org_help, type_ads_help, description_help, status_help, date_help;


        public HelpViewHolder(@NonNull View itemView) {
            super(itemView);
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
