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
public class OthersFragment extends Fragment {

    RecyclerView otherServiceRecyclerView;
    ArrayList<Services> otherServiceList;


    public OthersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_others, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        otherServiceRecyclerView = (RecyclerView) view.findViewById(R.id.otherServiceRecyclerView);
        otherServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        otherServiceList = new ArrayList<>();

        Services obj02 = new Services("Electrician", R.drawable.s02electriciani, "Rs. 149/-", "Any problem related to electricity or electrical appliances. Just clickin!");
        Services obj03 = new Services("Carpentry", R.drawable.s03carpentryi, "Rs. 149/-", "Furniture causing problems? Just Clickin for calling a carpenter.");
        Services obj10 = new Services("Catering Services", R.drawable.s10_catering_servicesi, "Rs 200", "Need catering services for any event? Just clickin!");
        Services obj11 = new Services("Photography", R.drawable.s11_photographyi, "Rs 9*", "Just clickin! We'll frame your precious moments.");
        Services obj14 = new Services("Packers and Movers", R.drawable.s14_packers_and_moversi, "Rs.499/-", "Having problem shifting your house? Don't worry, Just clickin!");
        Services obj21 = new Services("Customised Cakes", R.drawable.s21_customisedcakesi, "Rs.1499/-", "Want to make birthday special?Modify the cake by just clickin!");

        otherServiceList.add(obj02);
        otherServiceList.add(obj03);
        otherServiceList.add(obj10);
        otherServiceList.add(obj11);
        otherServiceList.add(obj14);
        otherServiceList.add(obj21);

        HomeFragmentServicesAdapter homeFragmentServicesAdapter = new HomeFragmentServicesAdapter(R.layout.service_row, getActivity(), otherServiceList);

        otherServiceRecyclerView.setAdapter(homeFragmentServicesAdapter);
    }
}
