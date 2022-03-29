package com.example.urbanenviroment.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.urbanenviroment.model.Notifications;
import com.example.urbanenviroment.page.help.HelpPage;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    Context context;
    List<Notifications> notificationsList;

    public NotificationsAdapter(Context context, List<Notifications> notificationsList){
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View notificationsItem = LayoutInflater.from(context).inflate(R.layout.notifications_item, parent, false);
        return new NotificationsAdapter.NotificationsViewHolder(notificationsItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        int img_org_notifications_id = context.getResources().getIdentifier(
                notificationsList.get(position).getImg_org(), "drawable", context.getPackageName());
        holder.img_org_notifications.setImageResource(img_org_notifications_id);

        holder.name_org_notifications.setText(notificationsList.get(position).getName_org());
        holder.type_notifications.setText(notificationsList.get(position).getType_notifications());

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
        return notificationsList.size();
    }

    public static final class NotificationsViewHolder extends RecyclerView.ViewHolder{

        ImageView img_org_notifications;
        TextView name_org_notifications, type_notifications;


        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);

            img_org_notifications = itemView.findViewById(R.id.img_org_notifications);
            name_org_notifications = itemView.findViewById(R.id.name_org_notifications);
            type_notifications = itemView.findViewById(R.id.type_notifications);
        }
    }

}
