package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.example.urbanenviroment.page.org.OrganizationsActivity;
import com.example.urbanenviroment.page.org.OrganizationsPage;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Organizations;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso.get().load(organizationsList.get(position).getImg_org()).into(holder.img_org_org);

        holder.name_org_org.setText(organizationsList.get(position).getName_org());
        holder.address_org.setText(organizationsList.get(position).getAddress());
        holder.description_org.setText(organizationsList.get(position).getDescription());
        holder.count_animal_org.setText(organizationsList.get(position).getCount_animal());
        holder.count_ads_org.setText(organizationsList.get(position).getCount_ads());
        holder.count_photo_org.setText(organizationsList.get(position).getCount_photo());
        holder.date_org.setText(organizationsList.get(position).getDate());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteOrganization");

        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseObject id_org = ParseObject.createWithoutData("Organization", organizationsList.get(position).getId());

        if ((Boolean) parseUser.get("is_org")) {
            holder.button_favorite_org.setVisibility(View.GONE);
        } else {
            holder.button_favorite_org.setVisibility(View.VISIBLE);
        }

        query.whereEqualTo("id_org", id_org);
        query.whereEqualTo("id_user", parseUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null)
                    holder.button_favorite_org.setImageResource(R.drawable.button_favorite_press);
                else
                    holder.button_favorite_org.setImageResource(R.drawable.button_favorite);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrganizationsPage.class);
                intent.putExtra("id", organizationsList.get(position).getId());
                intent.putExtra("name", organizationsList.get(position).getName_org());
                intent.putExtra("image", organizationsList.get(position).getImg_org());
                intent.putExtra("address", organizationsList.get(position).getAddress());
                intent.putExtra("email", organizationsList.get(position).getEmail());
                intent.putExtra("phone", organizationsList.get(position).getPhone());
                intent.putExtra("description", organizationsList.get(position).getDescription());
                intent.putExtra("count_animal", organizationsList.get(position).getCount_animal());
                intent.putExtra("count_ads", organizationsList.get(position).getCount_ads());
                intent.putExtra("count_photo", organizationsList.get(position).getCount_photo());
                intent.putExtra("date", organizationsList.get(position).getDate());
                context.startActivity(intent);
            }
        });

        holder.button_favorite_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteOrganization");

                ParseUser parseUser = ParseUser.getCurrentUser();
                ParseObject id_org = ParseObject.createWithoutData("Organization", organizationsList.get(position).getId());

                query.whereEqualTo("id_org", id_org);
                query.whereEqualTo("id_user", parseUser);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object == null){
                            holder.button_favorite_org.setImageResource(R.drawable.button_favorite_press);

                            ParseObject favorite_org = new ParseObject("FavoriteOrganization");
                            favorite_org.put("id_user", parseUser);
                            favorite_org.put("id_org", id_org);

                            favorite_org.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null){
                                        Intent intent = new Intent(context, OrganizationsActivity.class);
                                        context.startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            holder.button_favorite_org.setImageResource(R.drawable.button_favorite);
                            object.deleteInBackground();
                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return organizationsList.size();
    }

    public static final class OrganizationViewHolder extends RecyclerView.ViewHolder{

        ImageButton button_favorite_org;
        ImageView img_org_org;
        TextView name_org_org, address_org, description_org, count_animal_org, count_ads_org,
                count_photo_org, date_org;

        public OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);

            button_favorite_org = itemView.findViewById(R.id.button_favorite_org);
            img_org_org = itemView.findViewById(R.id.img_org_org);
            name_org_org = itemView.findViewById(R.id.name_org_org);
            address_org = itemView.findViewById(R.id.address_org_org);
            description_org = itemView.findViewById(R.id.description_org);
            count_animal_org = itemView.findViewById(R.id.count_animal_org);
            count_ads_org = itemView.findViewById(R.id.count_ads_org);
            count_photo_org = itemView.findViewById(R.id.count_photo_org);
            date_org = itemView.findViewById(R.id.date_org);
        }
    }
}
