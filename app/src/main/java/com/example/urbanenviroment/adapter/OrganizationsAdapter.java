package com.example.urbanenviroment.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Organizations;

import java.util.List;

public class OrganizationsAdapter extends RecyclerView.Adapter<OrganizationsAdapter.OrganizationViewHolder> {

    Context context;
    List<Organizations> organizationsList;

    public OrganizationsAdapter(Context context, List<Organizations> helpList) {
        this.context = context;
        this.organizationsList = helpList;
    }

    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orgItem = LayoutInflater.from(context).inflate(R.layout.organization_item, parent, false);
        return new OrganizationsAdapter.OrganizationViewHolder(orgItem);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        int img_org_id = context.getResources().getIdentifier(
                organizationsList.get(position).getImg_org(), "drawable", context.getPackageName());
        holder.img_org_org.setImageResource(img_org_id);

        holder.name_org_org.setText(organizationsList.get(position).getName_org());
        holder.description_org.setText(organizationsList.get(position).getDescription());
        holder.count_animal_org.setText(organizationsList.get(position).getCount_animal());
        holder.date_org.setText(organizationsList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return organizationsList.size();
    }

    public static final class OrganizationViewHolder extends RecyclerView.ViewHolder{

        ImageView img_org_org;
        TextView name_org_org, description_org, count_animal_org, date_org;

        public OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);

            img_org_org = itemView.findViewById(R.id.img_org_org);
            name_org_org = itemView.findViewById(R.id.name_org_org);
            description_org = itemView.findViewById(R.id.description_org);
            count_animal_org = itemView.findViewById(R.id.count_animal_org);
            date_org = itemView.findViewById(R.id.date_org);
        }
    }
}