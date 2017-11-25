package com.example.android.ubiquitouslabassistantsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Dheeraj_Kamath on 11/10/2017.
 */

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.MyViewHolder> {

    private Context mContext;
    private List<Appliance> applianceList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, count;
        public ImageView thumbnail;
        List<Appliance> applianceDetails;
        Context context;

        public MyViewHolder(View view, Context context, List<Appliance> appliance) {
            super(view);
            this.applianceDetails = appliance;
            this.context = context;
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Appliance applianceClicked = this.applianceDetails.get(position);
            String ApplianceSelected = this.applianceDetails.get(position).getName();
            Intent intent;
            if(ApplianceSelected.equalsIgnoreCase("Lights")){
                intent = new Intent(this.context, LightsActivity.class);
                intent.putExtra("no_of_devices", applianceClicked.getNoOfDevices());
                intent.putExtra("flat_icon", applianceClicked.getFlatIcon());
            }else if(ApplianceSelected.equalsIgnoreCase("Fans")){
                intent = new Intent(this.context, FansActivity.class);
                intent.putExtra("no_of_devices", applianceClicked.getNoOfDevices());
                intent.putExtra("flat_icon", applianceClicked.getFlatIcon());
            }else if(ApplianceSelected.equalsIgnoreCase("Projector")){
                intent = new Intent(this.context, LightsActivity.class);
                intent.putExtra("no_of_devices", applianceClicked.getNoOfDevices());
                intent.putExtra("flat_icon", applianceClicked.getFlatIcon());
            }else if(ApplianceSelected.equalsIgnoreCase("Curtains")){
                intent = new Intent(this.context, LightsActivity.class);
                intent.putExtra("no_of_devices", applianceClicked.getNoOfDevices());
                intent.putExtra("flat_icon", applianceClicked.getFlatIcon());
            }else{
                intent = new Intent(this.context, ULA_Home.class);
            }

            this.context.startActivity(intent);
        }
    }


    public ApplianceAdapter(Context mContext, List<Appliance> applianceList) {
        this.mContext = mContext;
        this.applianceList = applianceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appliance_single_item, parent, false);

        return new MyViewHolder(itemView, this.mContext, applianceList);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Appliance appliance = applianceList.get(position);
        holder.title.setText(appliance.getName());
        holder.count.setText(appliance.getNoOfDevices() + " Devices");

        // loading album cover using Glide library
        Glide.with(mContext).load(appliance.getFlatIcon()).into(holder.thumbnail);

        /*
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

        */
    }


        /**
         * Showing popup menu when tapping on 3 dots

         private void showPopupMenu(View view) {
         // inflate menu
         PopupMenu popup = new PopupMenu(mContext, view);
         MenuInflater inflater = popup.getMenuInflater();
         inflater.inflate(R.menu.menu_album, popup.getMenu());
         popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
         popup.show();
         }

         /**
         * Click listener for popup menu items
         *
         class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

         public MyMenuItemClickListener() {
         }

         @Override public boolean onMenuItemClick(MenuItem menuItem) {
         switch (menuItem.getItemId()) {
         case R.id.action_add_favourite:
         Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
         return true;
         case R.id.action_play_next:
         Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
         return true;
         default:
         }
         return false;
         }
         }
         */

    public int getItemCount() {
        return applianceList.size();
    }
}
