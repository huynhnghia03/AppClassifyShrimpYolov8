package com.example.myyolov8app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myyolov8app.data_local.DataLocalManager;
import com.example.myyolov8app.api.ApiService;
import com.example.myyolov8app.model.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Register extends AppCompatActivity {
    Button register;
    EditText name, pass, prepass;
    ImageView img;
    TextView lg;
    Intent intent;
    Users userdata;
    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userdata = DataLocalManager.getUser();
        if(userdata!=null && userdata.getEmail()!=null) {
            intent= new Intent(this,Home.class);
            startActivity(intent);
        }else {
            setContentView(R.layout.activity_register);

//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            register = findViewById(R.id.btn_rg);
            name = findViewById(R.id.txtName);
            pass = findViewById(R.id.lg_pass);
            prepass = findViewById(R.id.txtprpass);
            lg = findViewById(R.id.rg_lg);
            img = findViewById(R.id.rg_img);


            lg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (name.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || prepass.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please fill full information", Toast.LENGTH_LONG).show();
                    } else if (!pass.getText().toString().matches(prepass.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_LONG).show();
                    } else {
                        if(isValidEmail(name.getText().toString())){
                            try {
                                sentRegister(name.getText().toString(), pass.getText().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Email address isn't right format", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }

    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void sentRegister(String email, String password){
        Users user = new Users(email,password);
        System.out.println(user.getEmail());
        ApiService.apiService.registerUser(user).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.code()==200){
                userdata = response.body();
                if (userdata.getAuth()){
                    Toast.makeText(getApplicationContext(), "Register successfully",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Email or Password valid",Toast.LENGTH_LONG).show();
                }}
                else{
                    Toast.makeText(getApplicationContext(), "Email or Password valid",Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(getApplicationContext(), "Loi",Toast.LENGTH_LONG).show();
            }
        });
    }


}
