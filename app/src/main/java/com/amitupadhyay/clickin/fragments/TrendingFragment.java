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
public class TrendingFragment extends Fragment {

    RecyclerView bestServicesRecyclerView;
    ArrayList<Services> bestServicesList;

    public TrendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bestServicesRecyclerView = (RecyclerView) view.findViewById(R.id.bestServicesRecyclerView);
        bestServicesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bestServicesList = new ArrayList<>();

        Services obj02 = new Services("Electrician", R.drawable.s02electriciani, "Rs. 149/-", "Any problem related to electricity or electrical appliances. Just clickin!");
        Services obj03 = new Services("Carpentry", R.drawable.s03carpentryi, "Rs. 149/-", "Furniture causing problems? Just Clickin for calling a carpenter.");
        Services obj05 = new Services("Plumbing", R.drawable.s05plumbingi, "Rs. 149/-", "Exhausted searching for a plumber ? Relax and just clickin!");
        Services obj09 = new Services("Interior Designing", R.drawable.s9_interior_designingi, "Rs. 149/-", "Do you hate the old interior design of your house? Just clickin!");
        Services obj11 = new Services("Photography", R.drawable.s11_photographyi, "Rs 9*", "Just clickin! We'll frame your precious moments.");
        Services obj20 = new Services("Customised Apparel", R.drawable.s20_customised_appareli, "Rs.399/-", "Experts to design and print sweatshirt and tees. Just clickin!");
        Services obj14 = new Services("Packers and Movers", R.drawable.s14_packers_and_moversi, "Rs.499/-", "Having problem shifting your house? Don't worry, Just clickin!");

        bestServicesList.add(obj02);
        bestServicesList.add(obj03);
        bestServicesList.add(obj05);
        bestServicesList.add(obj09);
        bestServicesList.add(obj11);
        bestServicesList.add(obj20);
        bestServicesList.add(obj14);

        HomeFragmentServicesAdapter homeFragmentServicesAdapter = new HomeFragmentServicesAdapter(R.layout.service_row, getActivity(), bestServicesList);

        bestServicesRecyclerView.setAdapter(homeFragmentServicesAdapter);
    }
}
