package com.example.android.ubiquitouslabassistantsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
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

            switch(position){
                case 0:
                    intent = new Intent(this.context, LightsActivity.class);
                    break;
                case 1:
                    intent = new Intent(this.context, FansActivity.class);
                    break;
                case 2:
                    intent = new Intent(this.context, CurtainsActivity.class);
                    break;
                case 3:
                    intent = new Intent(this.context, ProjectorActivity.class);
                    break;
                default:
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
    }

    public int getItemCount() {
        return applianceList.size();
    }
}
