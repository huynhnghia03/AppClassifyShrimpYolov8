
package com.example.myyolov8app.ui.classification;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myyolov8app.Constants;
import com.example.myyolov8app.R;
import com.example.myyolov8app.data_local.DataLocalManager;
import com.example.myyolov8app.databinding.FragmentClassificationBinding;
import com.example.myyolov8app.api.ApiService;
import com.example.myyolov8app.model.Classify;
import com.example.myyolov8app.model.RealPathUtil;
import com.example.myyolov8app.model.Users;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificationFragment extends Fragment {

    private FragmentClassificationBinding binding;
    private Button mButtonDetect, buttonSelect, buttonLive;
    private InputStream is;
    private Bitmap mBitmap = null;
    private ImageView mImageView;
    private ProgressDialog dialog;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 1;
    private static final int REQUEST_CODE_PERMISSION_STORAGE = 2;
    public static final int RESULT_CANCELED    = 0;
    public static final int RESULT_OK = -1;
    public Uri muri;
    private TextView kind, total, date;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        checkAndRequestPermissions();

        mImageView = binding.imageView;
        mImageView.setImageResource(R.drawable.logo);
        mButtonDetect = binding.detectButton;
        kind = binding.csfKind;
        total = binding.csfTotal;
        date = binding.csfDatetime;
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Detecting...");
        buttonSelect = binding.selectButton;
        buttonLive = binding.liveButton;

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });
        buttonLive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(takePicture, 0);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
                }
            }
        });
        mButtonDetect.setEnabled(true);
        mButtonDetect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    System.out.println(is !=null && muri !=null);
                    System.out.println(is);
                    System.out.println(muri);
                    if(is !=null && muri !=null){
                        classifyImage();
                    }else {
                        Toast.makeText(getContext(), "Please choose image or take a photo!", Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Your onViewCreated code goes here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            mImageView.setImageBitmap(imageBitmap);
                            mButtonDetect.setEnabled(true);
                            muri = getImageUri(getContext(), imageBitmap);
                            try {
                                is = getActivity().getApplicationContext().getContentResolver().openInputStream(muri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("ActivityResult", "No extras found in data");
                        }
                    } else {
                        Log.e("ActivityResult", "Result not OK for request code 0");
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        muri=selectedImage;
                        if (selectedImage != null) {
                            try {
                                is= getActivity().getApplicationContext().getContentResolver().openInputStream(data.getData());
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                                mImageView.setImageBitmap(bitmap);
                                mButtonDetect.setEnabled(true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("ActivityResult", "Selected image URI is null");
                        }
                    } else {
                        Log.e("ActivityResult", "Result not OK for request code 1");
                    }
                    break;
            }
        }
    }

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
        }
    }

    private void showImageSourceDialog() {
        final CharSequence[] options = {"Choose from Photos", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("New Test Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
              if (options[item].equals("Choose from Photos")) {
                  System.out.println("choose");
                  Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                  startActivityForResult(pickPhoto , 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void classifyImage() throws IOException {
        dialog.show();
        String pathImage = RealPathUtil.getRealPath(getContext(), muri);
        File file = new File(pathImage);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), getBytes(is));
        MultipartBody.Part multipartAvt = MultipartBody.Part.createFormData("File", file.getName(), requestBody);
        Users datauser = DataLocalManager.getUser();
        ApiService.apiService.classify(datauser.getAccess_token(), multipartAvt).enqueue(new Callback<Classify>() {
            @Override
            public void onResponse(Call<Classify> call, Response<Classify> response) {
                Classify hs = response.body();
                Glide.with(ClassificationFragment.this).load(Constants.URL + hs.getFilename()).into(mImageView);
                dialog.dismiss();
                mButtonDetect.setEnabled(true);
                String rs ="";
                if(hs.Info.getTotal()>0){
                    Toast.makeText(getContext(), "Detected successfully", Toast.LENGTH_LONG).show();
                    if(hs.Info.getBig()>0){
                        rs+="Big: "+hs.Info.getBig();
                    }
                    if (hs.Info.getMedium()>0) {
                        rs+=", Medium: "+hs.Info.getMedium();
                    }
                    if (hs.Info.getSmall()>0) {
                        rs+=", Small: "+hs.Info.getSmall();
                    }
                    kind.setText(rs);
                    total.setText(""+hs.Info.getTotal());
                    date.setText(new Date(hs.getDate()).toLocaleString());
                }else {
                    Toast.makeText(getContext(), "No shrimps are dectected", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Classify> call, Throwable t) {
                Toast.makeText(getContext(), "Loi", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];
        int len;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
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
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }


}
