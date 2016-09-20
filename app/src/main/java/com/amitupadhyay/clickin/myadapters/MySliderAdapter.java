package com.amitupadhyay.clickin.myadapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amitupadhyay.clickin.R;

/**
 * Created by aupadhyay on 8/13/16.
 */

public class MySliderAdapter extends PagerAdapter {

    private int []lion_imgs = {R.drawable.lion1, R.drawable.lion2,R.drawable.lion3,R.drawable.lion4,R.drawable.lion5,};
    private LayoutInflater inflater;
    private Context context;


    public MySliderAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return lion_imgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.home_fragment_swipe, container, false);
        ImageView img = (ImageView) v.findViewById(R.id.fragment_home_image_slider);

        img.setImageResource(lion_imgs[position]);

        container.addView(v);

        return v;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.invalidate();

    }

}
