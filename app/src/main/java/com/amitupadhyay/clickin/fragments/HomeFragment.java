package com.amitupadhyay.clickin.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amitupadhyay.clickin.R;
import com.amitupadhyay.clickin.myadapters.HomeFragmentServicesAdapter;
import com.amitupadhyay.clickin.myadapters.MySliderAdapter;
import com.amitupadhyay.clickin.util.Services;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.amitupadhyay.clickin.R.id.services_list;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Services> serviceList;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MySliderAdapter adapter;
        final ViewPager viewPager;

        viewPager = (ViewPager) view.findViewById(R.id.fragment_home_view_pager);

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            int currentPage = viewPager.getCurrentItem();
            int NUM_PAGES = 6;

            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 7000); //500 is the delay before this task begins, and 7000 is the time interval for which one image will persist

        adapter = new MySliderAdapter(getActivity());
        viewPager.setAdapter(adapter);

        recyclerView = (RecyclerView) view.findViewById(services_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        serviceList = new ArrayList<>(21);

        Services obj01 = new Services("Laundry", R.drawable.s01laundryi, "Rs 200/-", "Got some dirty clothes to dry clean or wash. Just clickin!");
        Services obj02 = new Services("Electrician", R.drawable.s02electriciani, "Rs. 149/-", "Any problem related to electricity or electrical appliances. Just clickin!");
        Services obj03 = new Services("Carpentry", R.drawable.s03carpentryi, "Rs. 149/-", "Furniture causing problems? Just Clickin for calling a carpenter.");
        Services obj04 = new Services("Car Wash", R.drawable.s04carwashi, "Rs. 199/-", "No need to stand in a queue. We will take care of it! Just clickin.");
        Services obj05 = new Services("Plumbing", R.drawable.s05plumbingi, "Rs. 149/-", "Exhausted searching for a plumber ? Relax and just clickin!");
        Services obj06 = new Services("Automobile Sevicing", R.drawable.s06automobile_servicesi, "Rs 200", "Don't have time to get vehicle serviced?Save your time just clickin!");
        Services obj07 = new Services("Housemaids", R.drawable.s07housemaidsi, "Rs 100", "For working class getting maids is a big issue. Don't worry, just clickin!");
        Services obj08 = new Services("Event Organizing", R.drawable.s08eventorganizingi, "Rs 250", "Planning to throw a party tonight? Don't take the pain just Clcikin!");
        Services obj09 = new Services("Interior Designing", R.drawable.s9_interior_designingi, "Rs. 149/-", "Do you hate the old interior design of your house? Just clickin!");
        Services obj10 = new Services("Catering Services", R.drawable.s10_catering_servicesi, "Rs 200", "Need catering services for any event? Just clickin!");
        Services obj11 = new Services("Photography", R.drawable.s11_photographyi, "Rs 9*", "Just clickin! We'll frame your precious moments.");
        Services obj12 = new Services("Self Drive", R.drawable.s12_self_drivei, "Rs 200", "Want to go on a long drive? Just clickin!");
        Services obj13 = new Services("Rent a Car", R.drawable.s13_rent_a_cari, "Rs 200", "Want a car to be rented on corporate or house needs? Just clickin!");
        Services obj14 = new Services("Packers and Movers", R.drawable.s14_packers_and_moversi, "Rs.499/-", "Having problem shifting your house? Don't worry, Just clickin!");
        Services obj15 = new Services("Travel Packages", R.drawable.s15_travel_packagesi, "Rs 200", "Want to go for a break? Just clickin and get handsome packages.");
        Services obj16 = new Services("Bathroom Cleaning", R.drawable.s16_bathroom_cleaningi, "Rs 200", "A clean bathroom signifies hygenic family. Just clickin!");
        Services obj17 = new Services("Sofa Spa", R.drawable.s17_sofa_spai, "Rs 200", "Enjoy sofa spa. Just clickin!");
        Services obj18 = new Services("Salon & Parlour", R.drawable.s18_salon_parlouri, "Rs 200", "Going to dress your hair? Just clickin!");
        Services obj19 = new Services("Book a DJ", R.drawable.s19_book_dji, "Rs 200", "Want awesome music tonight at a party. Don't woory, Just Clickin!");
        Services obj20 = new Services("Customised Apparel", R.drawable.s20_customised_appareli, "Rs.399/-", "Experts to design and print sweatshirt and tees. Just clickin!");
        Services obj21 = new Services("Customised Cakes", R.drawable.s21_customisedcakesi, "Rs.1499/-", "Want to make birthday special?Modify the cake by just clickin!");

        serviceList.add(obj01);
        serviceList.add(obj02);
        serviceList.add(obj03);
        serviceList.add(obj04);
        serviceList.add(obj05);
        serviceList.add(obj06);
        serviceList.add(obj07);
        serviceList.add(obj08);
        serviceList.add(obj09);
        serviceList.add(obj10);
        serviceList.add(obj11);
        serviceList.add(obj12);
        serviceList.add(obj13);
        serviceList.add(obj14);
        serviceList.add(obj15);
        serviceList.add(obj16);
        serviceList.add(obj17);
        serviceList.add(obj18);
        serviceList.add(obj19);
        serviceList.add(obj20);
        serviceList.add(obj21);

        HomeFragmentServicesAdapter homeFragmentServicesAdapter = new HomeFragmentServicesAdapter(R.layout.service_row, getActivity(), serviceList);

        recyclerView.setAdapter(homeFragmentServicesAdapter);
/*
        mDatabase = FirebaseDatabase.getInstance().getReference().child("AllServices");
        mDatabase.keepSynced(true);

        services_list = (RecyclerView) view.findViewById(R.id.services_list);
        services_list.setHasFixedSize(true);
        services_list.setLayoutManager(new LinearLayoutManager(getContext()));

*/

    }

    @Override
    public void onStart() {
        super.onStart();

/*        FirebaseRecyclerAdapter<Services, ServiceViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Services, ServiceViewHolder>(
                Services.class,
                R.layout.service_row,
                ServiceViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ServiceViewHolder viewHolder, Services model, final int position) {

                viewHolder.setServiceName(model.getName());
                viewHolder.setServicePrice(model.getPrice());
                viewHolder.setImage(getContext(), model.getImageUrl());

                viewHolder.service_name_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Clicked on Service Name "+(position+1), Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.service_price_text_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Clicked on Service Price "+(position+1), Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.service_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Clicked on Service Image "+(position+1), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

        services_list.setAdapter(firebaseRecyclerAdapter);*/
    }

/*    public static class ServiceViewHolder extends RecyclerView.ViewHolder
    {

        View mView;
        TextView service_name_textview;
        TextView service_price_text_view;
        ImageView service_image;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setServiceName(String title)
        {
            service_name_textview = (TextView) mView.findViewById(R.id.service_name_textview);
            service_name_textview.setText(title);
        }

        public void setServicePrice(String desc)
        {
            service_price_text_view = (TextView) mView.findViewById(R.id.service_price_text_view);
            service_price_text_view.setText(desc);
        }

        public void setImage(final Context ctx, final String image)
        {
            service_image = (ImageView) mView.findViewById(R.id.service_image);

            //Picasso.with(ctx).load(image).into(post_image);

            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(service_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(service_image);
                }
            });
        }


    }*/
}
