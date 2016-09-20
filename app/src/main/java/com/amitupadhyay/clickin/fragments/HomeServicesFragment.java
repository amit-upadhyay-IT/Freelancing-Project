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
public class HomeServicesFragment extends Fragment {

    private RecyclerView home_services_recycler_view;
    private ArrayList<Services> homeServicesList;

    public HomeServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_services, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        home_services_recycler_view = (RecyclerView) view.findViewById(R.id.home_services_recycler_view);
        home_services_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        homeServicesList = new ArrayList<>();

        Services obj01 = new Services("Laundry", R.drawable.s01laundryi, "Rs 200", "Got some dirty clothes to dry clean or wash. Just clickin!");
        Services obj05 = new Services("Plumbing", R.drawable.s05plumbingi, "Rs. 149/-", "Exhausted searching for a plumber ? Relax and just clickin!");
        Services obj07 = new Services("Housemaids", R.drawable.s07housemaidsi, "Rs 100", "For working class getting maids is a big issue. Don't worry, just clickin!");
        Services obj09 = new Services("Interior Designing", R.drawable.s9_interior_designingi, "Rs. 149/-", "Do you hate the old interior design of your house? Just clickin!");
        Services obj14 = new Services("Packers and Movers", R.drawable.s14_packers_and_moversi, "Rs.499/-", "Having problem shifting your house? Don't worry, Just clickin!");
        Services obj16 = new Services("Bathroom Cleaning", R.drawable.s16_bathroom_cleaningi, "Rs 200", "A clean bathroom signifies hygenic family. Just clickin!");

        homeServicesList.add(obj01);
        homeServicesList.add(obj05);
        homeServicesList.add(obj07);
        homeServicesList.add(obj09);
        homeServicesList.add(obj14);
        homeServicesList.add(obj16);

        HomeFragmentServicesAdapter homeFragmentServicesAdapter = new HomeFragmentServicesAdapter(R.layout.service_row, getActivity(), homeServicesList);

        home_services_recycler_view.setAdapter(homeFragmentServicesAdapter);

    }
}
