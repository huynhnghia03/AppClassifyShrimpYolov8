package com.example.myyolov8app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myyolov8app.R;
import com.example.myyolov8app.model.PhoTo;

import java.util.List;

public class CustomAdapterPhoto extends PagerAdapter {
private List<PhoTo> listPhoto;

    public CustomAdapterPhoto(List<PhoTo> listPhoto) {
        this.listPhoto = listPhoto;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo,container,false);
        ImageView img = view.findViewById(R.id.it_img_indic);
        PhoTo photo = listPhoto.get(position);
        img.setImageResource(photo.getResourceId());
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if (listPhoto!=null){
            return listPhoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
