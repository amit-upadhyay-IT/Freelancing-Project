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
public class EntertainmentFragment extends Fragment {

    private RecyclerView entertainmentServiceRecyclerView;
    private ArrayList<Services> entertainmentServiceList;

    public EntertainmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entertainment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        entertainmentServiceRecyclerView = (RecyclerView) view.findViewById(R.id.entertainmentServiceRecyclerView);
        entertainmentServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        entertainmentServiceList = new ArrayList<>();

        Services obj08 = new Services("Event Organizing", R.drawable.s08eventorganizingi, "Rs 250", "Planning to throw a party tonight? Don't take the pain just Clcikin!");
        Services obj19 = new Services("Book a DJ", R.drawable.s19_book_dji, "Rs 200", "Want awesome music tonight at a party. Don't woory, Just Clickin!");

        entertainmentServiceList.add(obj08);
        entertainmentServiceList.add(obj19);

        HomeFragmentServicesAdapter homeFragmentServicesAdapter = new HomeFragmentServicesAdapter(R.layout.service_row, getActivity(), entertainmentServiceList);

        entertainmentServiceRecyclerView.setAdapter(homeFragmentServicesAdapter);


    }
}
