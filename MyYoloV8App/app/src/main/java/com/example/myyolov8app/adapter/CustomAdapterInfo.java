package com.example.myyolov8app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myyolov8app.Constants;
import com.example.myyolov8app.InfoClassoficationActivity;
import com.example.myyolov8app.R;
import com.example.myyolov8app.model.Info;
import com.example.myyolov8app.ui.History.HistoryFragment;

import java.util.ArrayList;

public class CustomAdapterInfo extends ArrayAdapter<Info> {
    private ArrayList<Info> arrinfo;
    private final Fragment fragment;
    private int lastPosition = -1;
    private HistoryFragment historyFragment;
    public CustomAdapterInfo(Fragment fragment, HistoryFragment historyFragment, ArrayList<Info> arrinfo) {
        super(fragment.getContext(), R.layout.item_img, arrinfo);
        this.fragment=fragment;
        this.arrinfo=arrinfo;
        this.historyFragment = historyFragment;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_img, null, true);
        Info info= arrinfo.get(position);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.info_img);
        Glide.with(fragment).load(Constants.URL+info.getImageUrl()).into(imageView);
        Animation animation = AnimationUtils.loadAnimation(fragment.getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        rowView.startAnimation(animation);
        lastPosition = position;
        Button btnDelete =  rowView.findViewById(R.id.user_btnDel);
        Button btnDetail =  rowView.findViewById(R.id.user_btnDetail);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyFragment.showDialogconfirm(String.valueOf(arrinfo.get(position).getId()));
                System.out.println("Del" + position);
            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(position);
            }
        });
//        subtitleText.setText(subtitle[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(position);
            }
        });

        return rowView;

    };
    public void sendData(int vt){
        Info info = arrinfo.get(vt);
        Intent intent = new Intent(fragment.getActivity(), InfoClassoficationActivity.class);
        intent.putExtra("OB", info);
        fragment.startActivityForResult(intent, 200); // Request code 200
    }
}
