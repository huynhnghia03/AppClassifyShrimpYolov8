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

public class Login extends AppCompatActivity {
Button login;
EditText email, password;
ImageView img;
TextView rg;
    Intent intent;
    Users userData;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userData= DataLocalManager.getUser();;
        if(userData!=null &&  userData.getEmail()!=null) {
            intent= new Intent(this,Home.class);
            startActivity(intent);
            finish();
            return;
        }else {
            setContentView(R.layout.activity_login);
            login = findViewById(R.id.btn_lg);
            email = findViewById(R.id.lg_email);
            password = findViewById(R.id.lg_pass);
            rg = findViewById(R.id.lg_rg);
            img = findViewById(R.id.lg_img);
            rg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValidEmail(email.getText().toString())&&!email.getText().toString().isEmpty()) {
                        sentLogin(email.getText().toString(), password.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "Email address isn't right format", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void sentLogin(String email, String pass){
        Users user = new Users(email,pass);
        System.out.println(user.getEmail());
        ApiService.apiService.loginUser(user).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                 userData = response.body();
                 if(response.code()==200){
                 if (userData.getAuth()){
                     DataLocalManager.setUser(userData);
                     Toast.makeText(getApplicationContext(), "Login successfully",Toast.LENGTH_LONG).show();
                     finish();
                 }else {
                     Toast.makeText(getApplicationContext(), "Email or Password invalid",Toast.LENGTH_LONG).show();
                 }}else {
                     Toast.makeText(getApplicationContext(), "Email or Password invalid",Toast.LENGTH_LONG).show();
                 }
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(getApplicationContext(),"That bai",Toast.LENGTH_LONG).show();
            }
        });
    }
//

}