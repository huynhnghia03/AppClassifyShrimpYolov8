package com.example.myyolov8app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myyolov8app.Home;
import com.example.myyolov8app.Login;
import com.example.myyolov8app.R;
import com.example.myyolov8app.adapter.CustomAdapterPhoto;
import com.example.myyolov8app.data_local.DataLocalManager;
import com.example.myyolov8app.databinding.FragmentHomeBinding;
import com.example.myyolov8app.model.PhoTo;
import com.example.myyolov8app.model.Users;
import com.example.myyolov8app.ui.History.HistoryFragment;
import com.example.myyolov8app.ui.classification.ClassificationFragment;
import com.example.myyolov8app.ui.profile.ChangePassFragment;
import com.example.myyolov8app.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Intent intent;
    public  Home home;
    Users user;
    private ImageView img_dectection, img_history, profile, cpass;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private Handler mhandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager.getCurrentItem() == getListPhoto().size()-1){
                viewPager.setCurrentItem(0);
            }else {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        img_dectection = binding.imgClassify;
        img_history = binding.imgHistory;
        profile = binding.imgProfile;
        cpass=binding.imgCpassw;
        viewPager = binding.viewPage;
        circleIndicator =binding.indicator;
        user = DataLocalManager.getUser();
        home = (Home) getActivity();
        img_dectection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentLogin(new ClassificationFragment(), R.id.nav_classification, home.FRAGMENT_CLASSIFICATION);
            }
        });
        img_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             sentLogin(new HistoryFragment(), R.id.nav_history, home.FRAGMENT_HISTORY);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentLogin(new ProfileFragment(), R.id.nav_profile, home.FRAGMENT_PROFILE);
            }
        });
        cpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentLogin(new ChangePassFragment(), R.id.nav_changePass,home.FRAGMENT_CHANGE_PASSWORD);
            }
        });
        CustomAdapterPhoto adapter = new CustomAdapterPhoto(getListPhoto());
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        mhandler.postDelayed(runnable,3000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mhandler.removeCallbacks(runnable);
                mhandler.postDelayed(runnable,3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return root;
    }
    private  void sentLogin( Fragment fragment, int id, int pst){
        if (user != null && user.getEmail()!=null) {
            System.out.println("okoko");
            navigateToFragment(fragment,id,pst);
        } else {
            startActivity(new Intent(getContext(), Login.class));
        }
    }
    private List<PhoTo> getListPhoto(){
        List<PhoTo> listPhotos = new ArrayList<>();
        listPhotos.add(new PhoTo(R.drawable.b4));
        listPhotos.add(new PhoTo(R.drawable.b3));
        listPhotos.add(new PhoTo(R.drawable.m4));
        listPhotos.add(new PhoTo(R.drawable.h5));
        listPhotos.add(new PhoTo(R.drawable.m6));

        return listPhotos;

    }
    private void navigateToFragment(Fragment fragment, int navItemId, int fragmentId) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();

        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(navItemId).setChecked(true);

        if (home != null) {
            System.out.println("vo");
            home.setCurrentFragment(fragmentId);
            home.setTitleToolbar();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mhandler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mhandler.postDelayed(runnable,3000);
    }
}