package com.example.myyolov8app.ui.Admin;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

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

import com.example.myyolov8app.Constants;
import com.example.myyolov8app.adapter.CustomAdapterUsers;
import com.example.myyolov8app.R;
import com.example.myyolov8app.databinding.FragmentAllUsersBinding;
import com.example.myyolov8app.model.AllUsers;
import com.example.myyolov8app.api.ApiService;
import com.example.myyolov8app.model.Users;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllUsersFragment extends Fragment {
    TextView sumuser, dectuser, undectuser;
    ListView lsv_user;
    ArrayList<Users> arrinusers;
    CustomAdapterUsers adapter;
    PieChart pieChart_user;
        private FragmentAllUsersBinding binding;
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentAllUsersBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            setHasOptionsMenu(true);
            arrinusers = new ArrayList<>();
sumuser=binding.sumUser;
dectuser=binding.dtUser;
undectuser=binding.udUser;
lsv_user=binding.lsvUser;
pieChart_user = binding.userPiechart;
            getIFAllUsers();
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
            if (item.getItemId()==R.id.export_csv){
                if(arrinusers!=null && arrinusers.size()>0) {
                    System.out.println("ok");
                    HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                    HSSFSheet hssfSheet = hssfWorkbook.createSheet("MySheet");
                    HSSFRow hssfRow = hssfSheet.createRow(0);
                    HSSFCell hssfCell = hssfRow.createCell(0);
                    hssfCell.setCellValue("imageURL");
                    hssfCell = hssfRow.createCell(1);
                    hssfCell.setCellValue("Email");
                    hssfCell = hssfRow.createCell(2);
                    hssfCell.setCellValue("Username");
                    hssfCell = hssfRow.createCell(3);
                    hssfCell.setCellValue("Admin");
                    hssfCell = hssfRow.createCell(4);
                    hssfCell.setCellValue("Detected");
                    hssfCell = hssfRow.createCell(5);
                    hssfCell.setCellValue("Joined");

                    for (int j = 0; j < arrinusers.size(); j++) {
                        hssfRow = hssfSheet.createRow(j + 1);
                        hssfCell = hssfRow.createCell(0);
                        hssfCell.setCellValue(Constants.URL + arrinusers.get(j).getAvatar().toString());
                        hssfCell = hssfRow.createCell(1);
                        hssfCell.setCellValue(arrinusers.get(j).getEmail().toString());
                        hssfCell = hssfRow.createCell(2);
                        hssfCell.setCellValue(arrinusers.get(j).getUsername());
                        hssfCell = hssfRow.createCell(3);
                        hssfCell.setCellValue(arrinusers.get(j).getAdmin().toString());
                        hssfCell = hssfRow.createCell(4);
                        hssfCell.setCellValue(arrinusers.get(j).getDetected().toString());
                        hssfCell = hssfRow.createCell(5);
                        hssfCell.setCellValue(new Date(arrinusers.get(j).getDate()).toLocaleString());
                    }
                    saveDataHistory(hssfWorkbook);
                }else {
                    Toast.makeText(getContext(),"No Datas To Export",Toast.LENGTH_LONG).show();
                }
            }
        return super.onOptionsItemSelected(item);
    }

    public void getIFAllUsers(){
//        History hs = new History();
        ApiService.apiService.getAllUser().enqueue(new Callback<AllUsers>() {
            @Override
            public void onResponse(Call<AllUsers> call, Response<AllUsers> response) {
                System.out.println("ok");
                System.out.println(response.body());
                AllUsers ad = response.body();
                if(ad==null){
                    Toast.makeText(getContext(),"Please login again",Toast.LENGTH_LONG).show();
                }else {
                        setData(ad);
                        showDataListView(ad.dataUsers);

                }

//                tvSmS.setText(hs.getSmall());
            }

            @Override
            public void onFailure(Call<AllUsers> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(getContext(),"That bai",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setData(AllUsers data)
    {
        int sum =data.getTotalUsers();
        int dect=data.getTotalHistory();
        int undect = data.getTotalUsers()-data.getTotalHistory();
        sumuser.setText(""+sum);
        dectuser.setText(""+dect);
        undectuser.setText(""+undect);

        if(data==null){
            pieChart_user.addPieSlice(
                    new PieModel(
                            "No data",
                            0,
                            Color.parseColor("#FFA726")));
        }else {
            // Set the data and color to the pie chart
            pieChart_user.addPieSlice(
                    new PieModel(
                            "Detected",
                            dect,
                            Color.parseColor("#FFFFA726")));
            pieChart_user.addPieSlice(
                    new PieModel(
                            "Undetected",
                            undect,
                            Color.parseColor("#FF66BB6A")));
        }
        // To animate the pie chart
        pieChart_user.startAnimation();
    }
    private void showDataListView(List<Users> datas){
//        }
        for(Users user: datas){
            System.out.println(user.getEmail());
            Users u = new Users(user.getEmail(),user.getUsername(),user.getAvatar(),user.getAdmin(),user.getDate(),user.getDetected());
            arrinusers.add(u);
        }

        System.out.println(arrinusers);
        adapter = new CustomAdapterUsers(this,arrinusers);
        lsv_user.setAdapter(adapter);
    }
    private void saveDataHistory(HSSFWorkbook hssfWorkbook) {
        Context context = getContext(); // Get the context
        if (context != null) {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            if (storageManager != null) {
                StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal storage

                File fileOutput = new File(storageVolume.getDirectory().getPath() + "/Download/AllInfoUsers.csv");

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
                    hssfWorkbook.write(fileOutputStream);
                    fileOutputStream.close();
                    hssfWorkbook.close();
                    createDownloadSuccessNotification(context,fileOutput);
                    Toast.makeText(context, "File Created Successfully ", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(context, "File Creation Failed", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(context, "Storage Manager Not Available", Toast.LENGTH_LONG).show();
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

