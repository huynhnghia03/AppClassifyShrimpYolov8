//package com.example.myyolov8app;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.NotificationCompat;
//
//import android.annotation.SuppressLint;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.storage.StorageManager;
//import android.os.storage.StorageVolume;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.example.myyolov8app.model.AdminUser;
//import com.example.myyolov8app.api.ApiService;
//import com.example.myyolov8app.model.HistoryData;
//import com.example.myyolov8app.model.Users;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.eazegraph.lib.charts.PieChart;
//import org.eazegraph.lib.models.PieModel;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.Date;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DetailUserActivity extends AppCompatActivity {
//    TextView username, email, time, admin, dectected, sumimgs, sumsh, bigs, meds, sms;
//    Toolbar tb;
//    CircleImageView img;
//    PieChart pieChart_user_dt;
//    Users users;
//    AdminUser ad;
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_user);
//
//        username = findViewById(R.id.ad_us);
//        email = findViewById(R.id.ad_email);
//        time = findViewById(R.id.ad_time);
//        admin = findViewById(R.id.ad_admin);
//        sumimgs = findViewById(R.id.ad_sumImgs);
//        sumsh = findViewById(R.id.ad_sumSh);
//        bigs = findViewById(R.id.ad_BS);
//        meds = findViewById(R.id.ad_MS);
//        sms = findViewById(R.id.ad_SS);
//        img = findViewById(R.id.dt_image);
//        dectected = findViewById(R.id.ad_dectected);
//        pieChart_user_dt = findViewById(R.id.ad_piechart);
//        users = (Users) getIntent().getSerializableExtra("OB");
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setTitle("Information Of User");
//        }
////        showData();
//        getDetailUsers();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.export_csv, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        } else if (item.getItemId() == R.id.export_csv) {
//            if (ad != null && ad.getDataHistories().size() > 0) {
//                System.out.println("ok");
//                int count = 0;
//                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//                HSSFSheet hssfSheet = hssfWorkbook.createSheet("MySheet");
//                HSSFRow hssfRow = hssfSheet.createRow(0);
//                HSSFCell hssfCell = hssfRow.createCell(0);
//                hssfCell.setCellValue("imageUrl");
//                hssfCell = hssfRow.createCell(1);
//                hssfCell.setCellValue("shrimpCounts");
//                hssfCell = hssfRow.createCell(2);
//                hssfCell.setCellValue("count");
//                hssfCell = hssfRow.createCell(3);
//                hssfCell.setCellValue("timestamp");
//                hssfCell = hssfRow.createCell(4);
//                hssfCell.setCellValue("email");
//
//                for (int j = 0; j < ad.getDataHistories().size(); j++) {
//                    count = j;
//                    hssfRow = hssfSheet.createRow(j + 1);
//                    hssfCell = hssfRow.createCell(0);
//                    hssfCell.setCellValue(Constants.URL + ad.getDataHistories().get(j)[1].toString());
//                    hssfCell = hssfRow.createCell(1);
//                    hssfCell.setCellValue(ad.getDataHistories().get(j)[2].toString());
//                    hssfCell = hssfRow.createCell(2);
//                    hssfCell.setCellValue(ad.getDataHistories().get(j)[4].toString());
//                    hssfCell = hssfRow.createCell(3);
//                    hssfCell.setCellValue(new Date(ad.getDataHistories().get(j)[4].toString()).toLocaleString());
//                    hssfCell = hssfRow.createCell(4);
//                    hssfCell.setCellValue(ad.getDataHistories().get(j)[5].toString());
//                }
//                hssfRow = hssfSheet.createRow(count + 3);
//                hssfCell = hssfRow.createCell(1);
//                hssfCell.setCellValue("TotalShrimps");
//                hssfCell = hssfRow.createCell(2);
//                hssfCell.setCellValue("TotalBigShrimps");
//                hssfCell = hssfRow.createCell(3);
//                hssfCell.setCellValue("TotaMedimShrimps");
//                hssfCell = hssfRow.createCell(4);
//                hssfCell.setCellValue("TotalSmallShrimps");
//                hssfRow = hssfSheet.createRow(count + 4);
//                hssfCell = hssfRow.createCell(1);
//                hssfCell.setCellValue(ad.getDatas().getTotal());
//                hssfCell = hssfRow.createCell(2);
//                hssfCell.setCellValue(ad.getDatas().getBig());
//                hssfCell = hssfRow.createCell(3);
//                hssfCell.setCellValue(ad.getDatas().getMedium());
//                hssfCell = hssfRow.createCell(4);
//                hssfCell.setCellValue(ad.getDatas().getSmall());
//
//                saveDataUserDetial(hssfWorkbook);
//            } else {
//                Toast.makeText(getApplicationContext(), "No Datas To Export", Toast.LENGTH_LONG).show();
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void getDetailUsers() {
////        History hs = new History();
//        ApiService.apiService.getDetailUser(users.getEmail()).enqueue(new Callback<AdminUser>() {
//            @Override
//            public void onResponse(Call<AdminUser> call, Response<AdminUser> response) {
//                System.out.println("ok");
//                System.out.println(response.body());
//                ad = response.body();
//                if (ad == null) {
//                    Toast.makeText(getApplicationContext(), "Please login again", Toast.LENGTH_LONG).show();
//                } else {
////                        setData(ad.dataUsers);
////                    showData(ad.dataUsers);
//                    showData();
//                    setDataPiechart(ad.getDatas());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AdminUser> call, Throwable t) {
//                System.out.println(t);
//                Toast.makeText(getApplicationContext(), "That bai", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void showData() {
//        username.setText(users.getUsername());
//        email.setText(users.getEmail());
//        time.setText(new Date(users.getDate()).toLocaleString());
//        admin.setText(users.getAdmin().toString());
//        dectected.setText(users.getDetected().toString());
//        if (users.getAvatar().isEmpty()) {
//            Glide.with(this).load("https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745").into(img);
//        } else {
//            Glide.with(this).load(Constants.URL + "/" + users.getAvatar().toString()).into(img);
//        }
//
//
//    }
//
//    private void setDataPiechart(HistoryData data) {
//        sumimgs.setText("" + data.getSumImgs());
//        sumsh.setText("" + data.getTotal());
//        bigs.setText("" + data.getBig());
//        meds.setText("" + data.getMedium());
//        sms.setText("" + data.getSmall());
//
//        if (data.getTotal() == 0) {
//            pieChart_user_dt.addPieSlice(
//                    new PieModel(
//                            "No data",
//                            0,
//                            Color.parseColor("#FFA739")));
//        } else {
//            // Set the data and color to the pie chart
//            pieChart_user_dt.addPieSlice(
//                    new PieModel(
//                            "BigShrimp",
//                            data.getBig(),
//                            Color.parseColor("#FFFFA726")));
//            pieChart_user_dt.addPieSlice(
//                    new PieModel(
//                            "MediumShrimp",
//                            data.getMedium(),
//                            Color.parseColor("#FF66BB6A")));
//            pieChart_user_dt.addPieSlice(
//                    new PieModel(
//                            "SmallShrimp",
//                            data.getSmall(),
//                            Color.parseColor("#FFEF5350")));
//        }
//        // To animate the pie chart
//        pieChart_user_dt.startAnimation();
//    }
//
//    private void saveDataUserDetial(HSSFWorkbook hssfWorkbook) {
//        Context context = getApplicationContext(); // Get the context
//        if (context != null) {
//            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
//            if (storageManager != null) {
//                StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal storage
//
//                File fileOutput = new File(storageVolume.getDirectory().getPath() + "/Download/DetailUser_" + users.getEmail() + ".xls");
//
//                try {
//                    FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
//                    hssfWorkbook.write(fileOutputStream);
//                    fileOutputStream.close();
//                    hssfWorkbook.close();
//                    createDownloadSuccessNotification(context);
//                    Toast.makeText(context, "File Created Successfully "+fileOutput, Toast.LENGTH_LONG).show();
//
//                } catch (Exception e) {
//                    Toast.makeText(context, "File Creation Failed", Toast.LENGTH_LONG).show();
//                    throw new RuntimeException(e);
//                }
//            } else {
//                Toast.makeText(context, "Storage Manager Not Available", Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Toast.makeText(getApplicationContext(), "Context Not Available", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void createDownloadSuccessNotification(Context context) {
//        // Create a NotificationManager
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
//            }
//        }
//        // Create a NotificationChannel if API level is 26 or higher
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "download_channel";
//            CharSequence channelName = "Download Notifications";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
//            channel.setDescription("Notifications for download status");
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        // Create the notification
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "download_channel")
//                .setSmallIcon(android.R.drawable.stat_sys_download_done) // Set the download success icon
//                .setContentTitle("Download Complete")
//                .setContentText("File Created Successfully")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true); // Dismiss the notification after the user touches it
//
//        // Notify the user
//        notificationManager.notify(1, builder.build());
//    }
//
//
//    private void checkAndRequestNotificationPermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
//            }
//        }
//    }
//}
package com.example.myyolov8app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myyolov8app.model.AdminUser;
import com.example.myyolov8app.api.ApiService;
import com.example.myyolov8app.model.HistoryData;
import com.example.myyolov8app.model.Users;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {
    TextView username, email, time, admin, detected, sumimgs, sumsh, bigs, meds, sms;
    Toolbar tb;
    CircleImageView img;
    PieChart pieChart_user_dt;
    Users users;
    AdminUser ad;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
//        checkAndRequestNotificationPermissions();
        username = findViewById(R.id.ad_us);
        email = findViewById(R.id.ad_email);
        time = findViewById(R.id.ad_time);
        admin = findViewById(R.id.ad_admin);
        sumimgs = findViewById(R.id.ad_sumImgs);
        sumsh = findViewById(R.id.ad_sumSh);
        bigs = findViewById(R.id.ad_BS);
        meds = findViewById(R.id.ad_MS);
        sms = findViewById(R.id.ad_SS);
        img = findViewById(R.id.dt_image);
        detected = findViewById(R.id.ad_dectected);
        pieChart_user_dt = findViewById(R.id.ad_piechart);
        users = (Users) getIntent().getSerializableExtra("OB");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Information Of User");
        }
        getDetailUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.export_csv, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.export_csv) {
            if (ad != null && ad.getDataHistories().size() > 0) {
                exportUserDataToExcel();
            } else {
                Toast.makeText(getApplicationContext(), "No Data To Export", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDetailUsers() {
        ApiService.apiService.getDetailUser(users.getEmail()).enqueue(new Callback<AdminUser>() {
            @Override
            public void onResponse(Call<AdminUser> call, Response<AdminUser> response) {
                ad = response.body();
                if (ad == null) {
                    Toast.makeText(getApplicationContext(), "Please login again", Toast.LENGTH_LONG).show();
                } else {
                    showData();
                    setDataPiechart(ad.getDatas());
                }
            }

            @Override
            public void onFailure(Call<AdminUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to load data", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showData() {
        username.setText(users.getUsername());
        email.setText(users.getEmail());
        time.setText(new Date(users.getDate()).toLocaleString());
        admin.setText(users.getAdmin().toString());
        detected.setText(users.getDetected().toString());
        String avatarUrl = users.getAvatar().isEmpty() ?
                "https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745" :
                Constants.URL + "/" + users.getAvatar();
        Glide.with(this).load(avatarUrl).into(img);
    }

    private void setDataPiechart(HistoryData data) {
        sumimgs.setText(String.valueOf(data.getSumImgs()));
        sumsh.setText(String.valueOf(data.getTotal()));
        bigs.setText(String.valueOf(data.getBig()));
        meds.setText(String.valueOf(data.getMedium()));
        sms.setText(String.valueOf(data.getSmall()));

        pieChart_user_dt.clearChart();

        if (data.getTotal() == 0) {
            pieChart_user_dt.addPieSlice(new PieModel("No data", 0, Color.parseColor("#FFA739")));
        } else {
            pieChart_user_dt.addPieSlice(new PieModel("BigShrimp", data.getBig(), Color.parseColor("#FFFFA726")));
            pieChart_user_dt.addPieSlice(new PieModel("MediumShrimp", data.getMedium(), Color.parseColor("#FF66BB6A")));
            pieChart_user_dt.addPieSlice(new PieModel("SmallShrimp", data.getSmall(), Color.parseColor("#FFEF5350")));
        }
        pieChart_user_dt.startAnimation();
    }

    private void exportUserDataToExcel() {
        HSSFWorkbook hssfWorkbook = createExcelWorkbook();
        saveExcelFile(hssfWorkbook);
    }

    private HSSFWorkbook createExcelWorkbook() {
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

        for (int j = 0; j < ad.getDataHistories().size(); j++) {
            hssfRow = hssfSheet.createRow(j + 1);
            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(Constants.URL + ad.getDataHistories().get(j)[1].toString());
            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(ad.getDataHistories().get(j)[2].toString());
            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(ad.getDataHistories().get(j)[4].toString());
            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(new Date(ad.getDataHistories().get(j)[4].toString()).toLocaleString());
            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(ad.getDataHistories().get(j)[5].toString());
        }

        HSSFRow summaryRow = hssfSheet.createRow(ad.getDataHistories().size() + 3);
        summaryRow.createCell(1).setCellValue("TotalShrimps");
        summaryRow.createCell(2).setCellValue("TotalBigShrimps");
        summaryRow.createCell(3).setCellValue("TotalMediumShrimps");
        summaryRow.createCell(4).setCellValue("TotalSmallShrimps");

        summaryRow = hssfSheet.createRow(ad.getDataHistories().size() + 4);
        summaryRow.createCell(1).setCellValue(ad.getDatas().getTotal());
        summaryRow.createCell(2).setCellValue(ad.getDatas().getBig());
        summaryRow.createCell(3).setCellValue(ad.getDatas().getMedium());
        summaryRow.createCell(4).setCellValue(ad.getDatas().getSmall());

        return hssfWorkbook;
    }

//    private void saveExcelFile(HSSFWorkbook hssfWorkbook) {
//        Context context = getApplicationContext();
//        if (context != null) {
//            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
//            if (storageManager != null) {
//                StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal storage
//                File fileOutput = new File(storageVolume.getDirectory().getPath() + "/Download/DetailUser_" + users.getEmail() + ".xls");
//
//                try (FileOutputStream fileOutputStream = new FileOutputStream(fileOutput)) {
//                    hssfWorkbook.write(fileOutputStream);
//                    hssfWorkbook.close();
//                    createDownloadSuccessNotification(context);
//                    Toast.makeText(context, "File Created Successfully " + fileOutput, Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(context, "File Creation Failed", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            } else {
//                Toast.makeText(context, "Storage Manager Not Available", Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Toast.makeText(getApplicationContext(), "Context Not Available", Toast.LENGTH_LONG).show();
//        }
//    }
private void saveExcelFile(HSSFWorkbook hssfWorkbook) {
    Context context = getApplicationContext();
    if (context != null) {
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        if (storageManager != null) {
            StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal storage
            File fileOutput = new File(storageVolume.getDirectory().getPath() + "/Download/DetailUser_" + users.getEmail() + ".csv");

            try (FileOutputStream fileOutputStream = new FileOutputStream(fileOutput)) {
                hssfWorkbook.write(fileOutputStream);
                hssfWorkbook.close();
                createDownloadSuccessNotification(context, fileOutput);
                Toast.makeText(context, "File Created Successfully ", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, "File Creation Failed", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Storage Manager Not Available", Toast.LENGTH_LONG).show();
        }
    } else {
        Toast.makeText(getApplicationContext(), "Context Not Available", Toast.LENGTH_LONG).show();
    }
}

    private void createDownloadSuccessNotification(Context context, File fileOutput) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
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

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

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

    // Check permissions if needed (for API level 33 and higher)
    private void checkAndRequestNotificationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }
}
