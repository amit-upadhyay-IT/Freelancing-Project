package com.amitupadhyay.clickin.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amitupadhyay.clickin.R;
import com.amitupadhyay.clickin.myadapters.HomeFragmentServicesAdapter;
import com.amitupadhyay.clickin.util.Services;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleServicesFragment extends Fragment {

    RecyclerView vehicalServiceRecyclerView;
    ArrayList<Services> vehicalServiceList;

    public VehicleServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_services, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vehicalServiceRecyclerView = (RecyclerView) view.findViewById(R.id.vehicalServiceRecyclerView);
        vehicalServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        vehicalServiceList = new ArrayList<>();

        Services obj04 = new Services("Car Wash", R.drawable.s04carwashi, "Rs. 199/-", "No need to stand in a queue. We will take care of it! Just clickin.");
        Services obj06 = new Services("Automobile Sevicing", R.drawable.s06automobile_servicesi, "Rs. 210", "Don't have time to get vehicle serviced?Save your time just clickin!");
        Services obj12 = new Services("Self Drive", R.drawable.s12_self_drivei, "Rs. 200", "Want to go on a long drive? Just clickin!");
        Services obj13 = new Services("Rent a Car", R.drawable.s13_rent_a_cari, "Rs. 200", "Want a car to be rented on corporate or house needs? Just clickin!");

        vehicalServiceList.add(obj04);
        vehicalServiceList.add(obj06);
        vehicalServiceList.add(obj12);
        vehicalServiceList.add(obj13);

        HomeFragmentServicesAdapter homeFragmentServicesAdapter = new HomeFragmentServicesAdapter(R.layout.service_row, getActivity(), vehicalServiceList);

        vehicalServiceRecyclerView.setAdapter(homeFragmentServicesAdapter);

    }
}
