package com.example.myyolov8app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myyolov8app.api.ApiService;
import com.example.myyolov8app.model.DeleteHis;
import com.example.myyolov8app.model.Info;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoClassoficationActivity extends AppCompatActivity {
TextView rs,tt,time;
Button delete;
ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_classofication);
        rs=findViewById(R.id.dt_result);
        tt = findViewById(R.id.dt_total);
        time = findViewById(R.id.dt_time);
        img = findViewById(R.id.dt_img);
        delete = findViewById(R.id.info_btn_del);
        Info info= (Info) getIntent().getSerializableExtra("OB");
        System.out.println(info);
        rs.setText(info.getShrimpCounts());
        tt.setText(""+info.getCount());
        time.setText(info.getTimestamp().toGMTString());;
        Glide.with(this).load(Constants.URL+info.getImageUrl()).into(img);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Information");
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogconfirm(String.valueOf(info.getId()));
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(Constants.URL+info.getImageUrl());
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void showDialogconfirm( String pst){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có đồng ý xóa không ?");
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                try {
                    delHistory(pst);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Khong thanh cong",Toast.LENGTH_LONG).show();
                }
            }
        });
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog al = b.create();
        al.show();
    }

    private void delHistory(String id){
        ApiService.apiService.deleteHistory(new DeleteHis(id)).enqueue(new Callback<DeleteHis>() {
            @Override
            public void onResponse(Call<DeleteHis> call, Response<DeleteHis> response) {
                System.out.println(response.body());

                Toast.makeText(getApplicationContext()," Thanh cong",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(200, intent); // Set the result code
                finish(); // Finish the activity
                System.out.println("delok");
            }

            @Override
            public void onFailure(Call<DeleteHis> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"" +t,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showImage(String link){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.imge_detail, null);
        dialog.setView(dialogLayout);
        dialog.show();
        System.out.println(dialog.findViewById(R.id.goProDialogImage));
        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
        Glide.with(getApplicationContext()).load(link).into(image);
    }
}