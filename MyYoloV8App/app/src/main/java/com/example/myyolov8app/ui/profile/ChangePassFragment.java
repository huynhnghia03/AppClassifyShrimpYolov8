package com.example.myyolov8app.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myyolov8app.Login;
import com.example.myyolov8app.data_local.DataLocalManager;
import com.example.myyolov8app.databinding.FragmentChangepassBinding;
import com.example.myyolov8app.api.ApiService;
import com.example.myyolov8app.model.ChangePassword;
import com.example.myyolov8app.model.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassFragment extends Fragment {
    EditText oldp, newp, newpc;
    Button save;
    private FragmentChangepassBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChangepassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        oldp = binding.cpOld;
        newp = binding.cpNew;
        newpc = binding.cpNewc;
        save = binding.cpSave;
        save.setEnabled(false);
        newpc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(!oldp.getText().toString().trim().isEmpty() && !newp.getText().toString().trim().isEmpty() && !newpc.getText().toString().trim().isEmpty()){
                    if(newpc.getText().toString().trim().equals(newpc.getText().toString().trim())){
                        save.setEnabled(true);
                    }else {
                        Toast.makeText(getContext(),"New and confirm password are not equal",Toast.LENGTH_LONG).show();
                        save.setEnabled(false);
                    }
                }
                return false;
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangePassword(oldp.getText().toString(),newp.getText().toString());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void saveChangePassword(String oldpass, String newpass ){
        ChangePassword cp = new ChangePassword(oldpass,newpass);
        Users user = DataLocalManager.getUser();
        ApiService.apiService.changePassword(user.getAccess_token(),cp).enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
            ChangePassword rs = response.body();
            if(rs.isSuccess()){
                Toast.makeText(getContext(),"Change Password successful",Toast.LENGTH_LONG).show();
                DataLocalManager.setUser(null);
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(),rs.getError(),Toast.LENGTH_LONG).show();
            }
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
