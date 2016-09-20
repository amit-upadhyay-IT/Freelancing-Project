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
public class FashionFragment extends Fragment {

    RecyclerView faishonServiceRecyclerView;
    ArrayList<Services> faishonServiceList;


    public FashionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fashion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        faishonServiceRecyclerView = (RecyclerView) view.findViewById(R.id.faishonServiceRecyclerView);
        faishonServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        faishonServiceList = new ArrayList<>();

        Services obj17 = new Services("Sofa Spa", R.drawable.s17_sofa_spai, "Rs 200", "Enjoy sofa spa. Just clickin!");
        Services obj18 = new Services("Salon & Parlour", R.drawable.s18_salon_parlouri, "Rs 200", "Going to dress your hair? Just clickin!");
        Services obj20 = new Services("Customised Apparel", R.drawable.s20_customised_appareli, "Rs.399/-", "Experts to design and print sweatshirt and tees. Just clickin!");

        faishonServiceList.add(obj17);
        faishonServiceList.add(obj18);
        faishonServiceList.add(obj20);

        HomeFragmentServicesAdapter homeFragmentServicesAdapter = new HomeFragmentServicesAdapter(R.layout.service_row, getActivity(), faishonServiceList);

        faishonServiceRecyclerView.setAdapter(homeFragmentServicesAdapter);
    }
}
