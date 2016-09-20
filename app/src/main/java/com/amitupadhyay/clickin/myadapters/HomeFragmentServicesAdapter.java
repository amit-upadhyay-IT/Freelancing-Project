package com.amitupadhyay.clickin.myadapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amitupadhyay.clickin.R;
import com.amitupadhyay.clickin.ui.PlaceOrderActivity;
import com.amitupadhyay.clickin.util.Services;

import java.util.ArrayList;

/**
 * Created by aupadhyay on 9/9/16.
 */

public class HomeFragmentServicesAdapter extends RecyclerView.Adapter<HomeFragmentServicesAdapter.ServiceViewHolder> {

    private int resource;
    private Context context;
    private ArrayList<Services> serviceList;

    public HomeFragmentServicesAdapter() {
    }

    public HomeFragmentServicesAdapter(int resource, Context context, ArrayList<Services> serviceList) {
        this.resource = resource;
        this.context = context;
        this.serviceList = serviceList;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(resource, parent, false);

        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, final int position) {

        Services obj = serviceList.get(position);

        holder.imageView.setImageResource(obj.getImage());
        holder.descTextView.setText(obj.getDesc());
        holder.priceTextView.setText(obj.getPrice());
        holder.serviceDesc.setText(obj.getServiceDescription());

        holder.serviceRowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Services obj = serviceList.get(position);

                String serviceName = obj.getDesc();
                Toast.makeText(context, serviceName, Toast.LENGTH_SHORT).show();

                Bundle serviceClicked = new Bundle();
                serviceClicked.putString("SERVICE_CLICKED", serviceName);

                Intent intent = new Intent(context, PlaceOrderActivity.class);
                intent.putExtras(serviceClicked);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


    class ServiceViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView priceTextView;
        TextView descTextView;
        TextView serviceDesc;
        LinearLayout serviceRowLinearLayout;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.service_image);
            priceTextView = (TextView) itemView.findViewById(R.id.service_price_text_view);
            descTextView = (TextView) itemView.findViewById(R.id.service_name_textview);
            serviceDesc = (TextView) itemView.findViewById(R.id.service_description);
            serviceRowLinearLayout = (LinearLayout) itemView.findViewById(R.id.serviceRowLinearLayout);

        }
    }
}
