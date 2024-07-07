package com.example.myyolov8app.adapter;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myyolov8app.Constants;
import com.example.myyolov8app.DetailUserActivity;
import com.example.myyolov8app.R;
import com.example.myyolov8app.model.Info;
import com.example.myyolov8app.model.Users;
import com.example.myyolov8app.ui.History.HistoryFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapterUsers extends ArrayAdapter<Users> {
private ArrayList<Users> arrUser;
    private final Fragment fragment;
    private int lastPosition = -1;
    private HistoryFragment historyFragment;
    public CustomAdapterUsers(Fragment fragment, ArrayList<Users> arrUser) {
        super(fragment.getContext(), R.layout.item_user, arrUser);
        this.fragment=fragment;
        this.arrUser=arrUser;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_user, null, true);
        Users user= arrUser.get(position);
        CircleImageView imageView =  rowView.findViewById(R.id.it_info_img);
        TextView username = rowView.findViewById(R.id.it_user_email);
        TextView dect = rowView.findViewById(R.id.it_user_dect);
        username.setText(user.getUsername());
        dect.setText(user.getDetected().toString());

//        Glide.with(context).load(info.getImg()).into(imageView);
        if(user.getAvatar().isEmpty()) {
            Glide.with(fragment).load("https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745").into(imageView);
        }else {
            Glide.with(fragment).load(Constants.URL + user.getAvatar()).into(imageView);
        }
        Animation animation = AnimationUtils.loadAnimation(fragment.getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        rowView.startAnimation(animation);
        lastPosition = position;
        Button btnDetail =  rowView.findViewById(R.id.it_user_btnDetail);
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
        Users users = arrUser.get(vt);
        Intent intent = new Intent(fragment.getActivity(), DetailUserActivity.class);
        intent.putExtra("OB", users);
        fragment.startActivityForResult(intent, 200); // Request code 200
    }
}
