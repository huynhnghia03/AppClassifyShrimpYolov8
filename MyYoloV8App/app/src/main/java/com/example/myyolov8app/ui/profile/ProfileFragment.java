package com.example.myyolov8app.ui.profile;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myyolov8app.Constants;
import com.example.myyolov8app.R;
import com.example.myyolov8app.data_local.DataLocalManager;
import com.example.myyolov8app.databinding.FragmentProfileBinding;
import com.example.myyolov8app.api.ApiService;
import com.example.myyolov8app.model.RealPathUtil;
import com.example.myyolov8app.model.Users;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

//    ImageView avt;
    CircleImageView avt;
    TextView username, email, time;
    Button choose, change, cancel;
    Users user;
    private InputStream is;
    private Uri muri;
    private Bitmap mBitmap = null;
    Users datauser;
    public static final int RESULT_CANCELED    = 0;
    public static final int RESULT_OK           = -1;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 1;
    private static final int REQUEST_CODE_PERMISSION_STORAGE = 2;
    private FragmentProfileBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user = DataLocalManager.getUser();
        avt = binding.profileImage;
        username=binding.pfName;
        time = binding.pfTime;
        email=binding.pfEmail;
        change = binding.pfChange;
        cancel = binding.pfCancel;
        change.setEnabled(false);
        cancel.setEnabled(false);
        if(user!=null && user.getEmail()!=null){
            showDataUser();
        }
        choose = binding.pfBtnAvt;
        choose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final CharSequence[] options = { "Choose from Photos", "Take Picture", "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("New Test Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Picture")) {
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                startActivityForResult(takePicture, 0);
                            } else {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
                            }
                        }
                        else if (options[item].equals("Choose from Photos")) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , 1);
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveChange(username.getText().toString(), email.getText().toString() );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataUser();
                change.setEnabled(false);
                cancel.setEnabled(false);
            }
        });
        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(!user.getUsername().trim().equals(username.getText().toString().trim()) && !username.getText().toString().isEmpty()){
                    System.out.println(user.getUsername().trim().equals(username.getText().toString().trim()));
                    System.out.println(username.getText().toString().trim()+"ggg");
                    change.setEnabled(true);
                    cancel.setEnabled(true);
                }else {
                    change.setEnabled(false);
                }
                return false;
            }
        });
        avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(Constants.URL+"/"+user.getAvatar());
            }
        });
        return root;
    }
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Camera permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CODE_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                   
                        mBitmap = (Bitmap) data.getExtras().get("data");
                        Matrix matrix = new Matrix();
                        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                        avt.setImageBitmap(mBitmap);
                        muri = getImageUri(getContext(), mBitmap);
                        change.setEnabled(true);
                        cancel.setEnabled(true);
                        try {
                            is = getActivity().getApplicationContext().getContentResolver().openInputStream(muri);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage.toString().contains("image")) {
                            muri=selectedImage;
                            try {
                                is= getActivity().getApplicationContext().getContentResolver().openInputStream(data.getData());
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),selectedImage);
                                avt.setImageBitmap(bitmap);
                                change.setEnabled(true);
                                cancel.setEnabled(true);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else  if (selectedImage.toString().contains("video")) {
                        }
                    }
                    break;
            }
        }

    }
    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }
    private void saveChange(String username, String email) throws IOException {
        MultipartBody.Part multipartAvt=null;
        if(muri!=null) {
            String pathImage = RealPathUtil.getRealPath(getContext(), muri);
            File file = new File(pathImage);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), getBytes(is));
             multipartAvt= MultipartBody.Part.createFormData("File", file.getName(), requestBody);
        }
        RequestBody requestUsername =RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody requestEmail =RequestBody.create(MediaType.parse("multipart/form-data"), email);
        datauser = DataLocalManager.getUser();
        ApiService.apiService.changeProfile(multipartAvt,requestEmail,requestUsername).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users user = response.body();
                DataLocalManager.setUser(user);
                showDataUser();
                Toast.makeText(getContext(),"Change success",Toast.LENGTH_LONG).show();
                change.setEnabled(false);
                cancel.setEnabled(false);
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getContext(),"Change faild",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showDataUser(){
        user = DataLocalManager.getUser();
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        time.setText(new Date(user.getDate()).toLocaleString());
        if(user.getAvatar().isEmpty()){
            Glide.with(this).load("https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745").into(avt);
        }else {
            Glide.with(this).load(Constants.URL+"/"+user.getAvatar()).into(avt);
        }

    }
    private void showImage(String link){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        Glide.with(getContext()).load(link).into(image);

    }
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

}
