package com.example.myyolov8app.ui.History;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import com.example.myyolov8app.Constants;
import com.example.myyolov8app.adapter.CustomAdapterInfo;
import com.example.myyolov8app.R;
import com.example.myyolov8app.data_local.DataLocalManager;
import com.example.myyolov8app.databinding.FragmentHistoryBinding;
import com.example.myyolov8app.api.ApiService;
import com.example.myyolov8app.model.DeleteHis;
import com.example.myyolov8app.model.HistoryData;
import com.example.myyolov8app.model.Info;
import com.example.myyolov8app.model.Users;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {


    private FragmentHistoryBinding binding;
    TextView tvbgS, tvMdS, tvSmS;
    ListView lsv;
    ArrayList<Info> arrinfo;
    CustomAdapterInfo adapter;
    PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);
        tvbgS = binding.tvbgS;
        tvMdS = binding.tvMdS;
        tvSmS = binding.tvSmS;
        lsv = binding.lsvHs;
        pieChart = binding.piechart;
        getDataHistory();

        // Creating a method setData()
        // to set the text in text view and pie chart



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.export_csv, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.export_csv){
            if(arrinfo!=null && arrinfo.size()>0) {
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                HSSFSheet hssfSheet = hssfWorkbook.createSheet("MySheet");
                HSSFRow hssfRow = hssfSheet.createRow(0);
                HSSFCell hssfCell = hssfRow.createCell(0);
                hssfCell.setCellValue("imageUrl");
                hssfCell = hssfRow.createCell(1);
                hssfCell.setCellValue("shrimpCounts");
                hssfCell = hssfRow.createCell(2);
                hssfCell.setCellValue("count");
                hssfCell = hssfRow.createCell(3);
                hssfCell.setCellValue("timestamp");
                hssfCell = hssfRow.createCell(4);
                hssfCell.setCellValue("email");

                for (int j = 0; j < arrinfo.size(); j++) {
                    hssfRow = hssfSheet.createRow(j + 1);
                    hssfCell = hssfRow.createCell(0);
                    hssfCell.setCellValue(Constants.URL + arrinfo.get(j).getImageUrl().toString());
                    hssfCell = hssfRow.createCell(1);
                    hssfCell.setCellValue(arrinfo.get(j).getShrimpCounts().toString());
                    hssfCell = hssfRow.createCell(2);
                    hssfCell.setCellValue(arrinfo.get(j).getCount());
                    hssfCell = hssfRow.createCell(3);
                    hssfCell.setCellValue(arrinfo.get(j).getTimestamp().toString());
                    hssfCell = hssfRow.createCell(4);
                    hssfCell.setCellValue(arrinfo.get(j).getEmail().toString());
                }
                saveDataHistory(hssfWorkbook);
            }else {
                Toast.makeText(getContext(),"No Datas To Export",Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataHistory(){
        Users datauser = DataLocalManager.getUser();
        ApiService.apiService.getHistory(datauser.getAccess_token()).enqueue(new Callback<HistoryData>() {
            @Override
            public void onResponse(Call<HistoryData> call, Response<HistoryData> response) {
                HistoryData hs = response.body();
                if(hs==null){
                    Toast.makeText(getContext(),"Faild the get datas",Toast.LENGTH_LONG).show();
                }else {
                    if (hs.getAuth()){
                        setData(hs);
                        showDataListView(hs.getDatas());
                    }else {
                        Toast.makeText(getContext(),"Please login",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryData> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(getContext(),"Faild the get datas",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setData(HistoryData data)
    {
     tvbgS.setText(""+data.getBig());
     tvMdS.setText(""+data.getMedium());
     tvSmS.setText(""+data.getSmall());

    if(data.getDatas().size()==0){
        pieChart.addPieSlice(
                new PieModel(
                        "No data",
                        0,
                        Color.parseColor("#FFA726")));
        System.out.println("vo 0");
    }else {
    // Set the data and color to the pie chart
    pieChart.addPieSlice(
            new PieModel(
                    "BigShrimp",
                    data.getBig(),
                    Color.parseColor("#FFA726")));
    pieChart.addPieSlice(
            new PieModel(
                    "MediumShrimp",
                    data.getMedium(),
                    Color.parseColor("#66BB6A")));
    pieChart.addPieSlice(
            new PieModel(
                    "SmallShrimp",
                    data.getSmall(),
                    Color.parseColor("#EF5350")));
}
        // To animate the pie chart
        pieChart.startAnimation();
    }
    private void showDataListView(ArrayList<String[]> datas){
        arrinfo  = new ArrayList<>();
        for(int x = 0; x< datas.size();x++){
            arrinfo.add(new Info(Integer.parseInt(datas.get(x)[0]),datas.get(x)[1],
                    datas.get(x)[2],Integer.parseInt(datas.get(x)[3]),
                    new Date(datas.get(x)[4]),datas.get(x)[5]));
        }
        System.out.println(arrinfo);
        adapter = new CustomAdapterInfo(this,this,arrinfo);
        lsv.setAdapter(adapter);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("okokok"+requestCode);
        if (resultCode == 200) {
            getDataHistory();
            pieChart.clearChart();
        }
    }
    public void showDialogconfirm( String pst){
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có đồng ý xóa không ?");
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                try {
                    delHistory(pst);
                }catch (Exception e){
                    Toast.makeText(getContext(),"Khong thanh cong",Toast.LENGTH_LONG).show();
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
    public void delHistory(String id){
        ApiService.apiService.deleteHistory(new DeleteHis(id)).enqueue(new Callback<DeleteHis>() {
            @Override
            public void onResponse(Call<DeleteHis> call, Response<DeleteHis> response) {
                System.out.println(response.body());
                Toast.makeText(getContext()," Thanh cong",Toast.LENGTH_LONG).show();
                getDataHistory();
                pieChart.clearChart();
            }
            @Override
            public void onFailure(Call<DeleteHis> call, Throwable t) {
                Toast.makeText(getContext(),"" +t,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void saveDataHistory(HSSFWorkbook hssfWorkbook) {
        Context context = getContext(); // Get the context
        if (context != null) {
            // Get the download directory
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File fileOutput = new File(downloadsDir, "dataHistories.csv");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
                hssfWorkbook.write(fileOutputStream);
                fileOutputStream.close();
                hssfWorkbook.close();
                createDownloadSuccessNotification(context, fileOutput);
                Toast.makeText(context, "File Created Successfully ", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(context, "File Creation Failed", Toast.LENGTH_LONG).show();
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(getActivity(), "Context Not Available", Toast.LENGTH_LONG).show();
        }
    }
private void createDownloadSuccessNotification(Context context, File fileOutput) {
    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        String channelId = "download_channel";
        CharSequence channelName = "Download Notifications";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setDescription("Notifications for download status");
        notificationManager.createNotificationChannel(channel);
    }

    // Create an intent that opens the file location
    Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", fileOutput);
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setDataAndType(fileUri, "text/csv");
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);

    PendingIntent pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
    );

    // Build the notification
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "download_channel")
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentTitle("Download Complete")
            .setContentText("File Created Successfully")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent);

    // Notify the user
    notificationManager.notify(1, builder.build());
}

}
