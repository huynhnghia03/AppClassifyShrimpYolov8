//package com.example.myyolov8app;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.example.myyolov8app.data_local.DataLocalManager;
//import com.example.myyolov8app.databinding.ActivityHomeBinding;
//import com.example.myyolov8app.model.Users;
//import com.example.myyolov8app.ui.Admin.AllUsersFragment;
//import com.example.myyolov8app.ui.classification.ClassificationFragment;
//import com.example.myyolov8app.ui.home.HomeFragment;
//import com.example.myyolov8app.ui.History.HistoryFragment;
//import com.example.myyolov8app.ui.profile.ChangePassFragment;
//import com.example.myyolov8app.ui.profile.ProfileFragment;
//import com.google.android.material.navigation.NavigationView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.core.view.GravityCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.appcompat.app.AppCompatActivity;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//
//public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//
//    public static final int FRAGMENT_HOME = 0;
//    public static final int FRAGMENT_CLASSIFICATION = 1;
//    public static final int FRAGMENT_HISTORY = 2;
//    private static final int FRAGMENT_PROFILE = 3;
//    private static final int FRAGMENT_CHANGE_PASSWORD = 4;
//    private static final int FRAGMENT_ADMIN = 5;
//
//    private int mCurrentFragment = FRAGMENT_HOME;
//    private AppBarConfiguration mAppBarConfiguration;
//    private ActivityHomeBinding binding;
//
//    Users user;
//    CircleImageView avatar;
//    TextView email;
//    Intent intent;
//    View headerView;
//    NavigationView navigationView;
//    private DrawerLayout drawer;
//
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityHomeBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
////        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//        setSupportActionBar(binding.appBarMain.toolbar);
//         drawer = binding.drawerLayout;
//         avatar=findViewById(R.id.head_img);
//
//        navigationView= binding.navView;
//        headerView = navigationView.getHeaderView(0);
//       email = headerView.findViewById(R.id.head_name);
//       avatar = headerView.findViewById(R.id.head_img);
//
//        user = DataLocalManager.getUser();
//
//        if ( user.getEmail()!=null){
//            System.out.println(user.getEmail());
//            System.out.println(user!=null && user.getAuth()!=null);
//            initialUser();
//        }else{
//            reRenderUI();
//        }
//
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
////        mAppBarConfiguration = new AppBarConfiguration.Builder(
////                R.id.nav_home)
////                .setOpenableLayout(drawer)
////                .build();
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
////        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,binding.appBarMain.toolbar,
//                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//       drawer.addDrawerListener(toggle);
//
//       toggle.syncState();
//       navigationView.setNavigationItemSelectedListener(this);
//        replaceFragment(new HomeFragment());
//        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
//setTitleToolbar();
//
//drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
//    @Override
//    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
////        System.out.println("1");
//    }
//
//    @Override
//    public void onDrawerOpened(@NonNull View drawerView) {
////        System.out.println("2");
////        user = DataLocalManager.getUser();
////        if( user.getEmail()!=null) {
////            initialUser();
////        }else {
////            reRenderUI();
////        }
//    }
//
//    @Override
//    public void onDrawerClosed(@NonNull View drawerView) {
//        System.out.println("3");
//    }
//
//    @Override
//    public void onDrawerStateChanged(int newState) {
//    }
//});
//    }
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
////        System.out.println();
//        if (user.getEmail()==null) {
//            // User is not logged in
//
//            if (id != R.id.nav_home) {
//
//                // Redirect to login if any ite
//                // m other than "Home" is selected
//                intent = new Intent(this, Login.class);
//                startActivity(intent);
//                return false; // Prevent further handling of the menu item
//            } else {
//                System.out.println(user.getEmail());
//                // Handle "Home" selection
//                if (mCurrentFragment != FRAGMENT_HOME) {
//                    replaceFragment(new HomeFragment());
//                    mCurrentFragment = FRAGMENT_HOME;
//                }
//            }
//        } else {
//            // User is logged in
//            if (id == R.id.nav_home) {
//                if (mCurrentFragment != FRAGMENT_HOME) {
//                    replaceFragment(new HomeFragment());
//                    mCurrentFragment = FRAGMENT_HOME;
//                }
//            } else if (id == R.id.nav_admin) {
//                System.out.println("ok");
//                if (mCurrentFragment != FRAGMENT_ADMIN) {
//                    replaceFragment(new AllUsersFragment());
//                    mCurrentFragment = FRAGMENT_ADMIN;
//                }
//            }
//            else if (id == R.id.nav_classification) {
//                if (mCurrentFragment != FRAGMENT_CLASSIFICATION) {
//                    replaceFragment(new ClassificationFragment());
//                    mCurrentFragment = FRAGMENT_CLASSIFICATION;
//                }
//            } else if (id == R.id.nav_history) {
//                if (mCurrentFragment != FRAGMENT_HISTORY) {
//                    replaceFragment(new HistoryFragment());
//                    mCurrentFragment = FRAGMENT_HISTORY;
//                }
//            } else if (id == R.id.nav_profile) {
//                if (mCurrentFragment != FRAGMENT_PROFILE) {
//                    replaceFragment(new ProfileFragment());
//                    mCurrentFragment = FRAGMENT_PROFILE;
//                }
//            } else if (id == R.id.nav_changePass) {
//                if (mCurrentFragment != FRAGMENT_CHANGE_PASSWORD) {
//                    replaceFragment(new ChangePassFragment());
//                    mCurrentFragment = FRAGMENT_CHANGE_PASSWORD;
//                }
//            } else if (id == R.id.nav_login) {
//                intent = new Intent(this, Login.class);
//                startActivity(intent);
//            } else if (id == R.id.nav_logout) {
//                Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();
////                user = null; // Ensure user is logged out
//
//                // Navigate to Home fragment and set the Home menu item as checked
//                user = null;
//                DataLocalManager.setUser(user);
//                replaceFragment(new HomeFragment());
//                mCurrentFragment = FRAGMENT_HOME;
//                navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
//                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
//                navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
//                avatar.setImageResource(R.mipmap.ic_launcher_round);
//                email.setText("User not logged in");
//
//                // Uncheck any other selected item
//                for (int i = 0; i < navigationView.getMenu().size(); i++) {
//                    MenuItem menuItem = navigationView.getMenu().getItem(i);
//                    if (menuItem.getItemId() != R.id.nav_home) {
//                        menuItem.setChecked(false);
//                    }
//                }
//
//                drawer.closeDrawer(GravityCompat.START);
//                return false; // Prevent further handling of the menu item
//            }
//        }
//setTitleToolbar();
//
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//
//    @Override
//    public void onBackPressed() {
//
//       if(drawer.isDrawerOpen(GravityCompat.START))
//        {
//
//            drawer.closeDrawer(GravityCompat.START);
//        }else{
//           super.onBackPressed();
//        }
//    }
//
//    private void initialUser(){
//        if(user.getAdmin()){
//            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(true);
//        }else{
//            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
//        }
//        System.out.println(user);
//        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
//        navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
//        if(user.getAvatar().isEmpty()){
//            Glide.with(this).load("https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745").into(avatar);
//        }else {
//            Glide.with(this).load(Constants.URL+user.getAvatar()).into(avatar);
//        }
//        email.setText(user.getEmail());
//    }
//    private void reRenderUI(){
//        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
//        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
//        avatar.setImageResource(R.mipmap.ic_launcher_round);
//        navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
//        email.setText("User not logged in");
//    }
//
//    public void replaceFragment(Fragment fragment){
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.nav_host_fragment_content_main,fragment);
//        transaction.commit();
//    }
//    public void setCurrentFragment(int fragmentId) {
//        mCurrentFragment = fragmentId;
//        System.out.println(mCurrentFragment);
//    }
//
//    public void setTitleToolbar(){
//        String title = "";
//        switch (mCurrentFragment){
//            case FRAGMENT_HOME:
//                title = getString(R.string.menu_home);
//                break;
//            case FRAGMENT_ADMIN:
//                title = getString(R.string.menu_alluser);
//                break;
//            case FRAGMENT_CLASSIFICATION:
//                title=getString(R.string.menu_classification);
//                break;
//            case FRAGMENT_HISTORY:
//                title=getString(R.string.menu_history);
//                break;
//            case FRAGMENT_PROFILE:
//                title=getString(R.string.menu_profile);
//                break;
//            case FRAGMENT_CHANGE_PASSWORD:
//                title=getString(R.string.menu_changePass);
//                break;
//        }
//        if(getSupportActionBar()!=null){
//            getSupportActionBar().setTitle(title);
//        }
//    }
//
//}
package com.example.myyolov8app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myyolov8app.data_local.DataLocalManager;
import com.example.myyolov8app.databinding.ActivityHomeBinding;
import com.example.myyolov8app.model.Users;
import com.example.myyolov8app.ui.Admin.AllUsersFragment;
import com.example.myyolov8app.ui.classification.ClassificationFragment;
import com.example.myyolov8app.ui.home.HomeFragment;
import com.example.myyolov8app.ui.History.HistoryFragment;
import com.example.myyolov8app.ui.profile.ChangePassFragment;
import com.example.myyolov8app.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_CLASSIFICATION = 1;
    public static final int FRAGMENT_HISTORY = 2;
    public static final int FRAGMENT_PROFILE = 3;
    public static final int FRAGMENT_CHANGE_PASSWORD = 4;
    private static final int FRAGMENT_ADMIN = 5;

    private int mCurrentFragment = FRAGMENT_HOME;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    Users user;
    CircleImageView avatar;
    TextView email;
    Intent intent;
    View headerView;
    NavigationView navigationView;
    private DrawerLayout drawer;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        headerView = navigationView.getHeaderView(0);
        email = headerView.findViewById(R.id.head_name);
        avatar = headerView.findViewById(R.id.head_img);

        user = DataLocalManager.getUser();

        if (user != null && user.getEmail() != null) {
            initialUser();
        } else {
            reRenderUI();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, binding.appBarMain.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        setTitleToolbar();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                // No-op
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                user = DataLocalManager.getUser();
                if (user != null && user.getEmail() != null) {
                    initialUser();
                } else {
                    reRenderUI();
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // No-op
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // No-op
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (user==null || user.getEmail()==null) {
//            // User is not logged in

            if (id != R.id.nav_home) {

                if (id != R.id.nav_home) {
                    System.out.println("ok");
                    navigateToLogin();
                    return false;
                } else {
                    handleNavigationSelection(FRAGMENT_HOME, new HomeFragment());
                }
            }
        } else {
            // User is logged in
            if (id == R.id.nav_home) {
                handleNavigationSelection(FRAGMENT_HOME, new HomeFragment());
            } else if (id == R.id.nav_admin) {
                System.out.println("ok");
                handleNavigationSelection(FRAGMENT_ADMIN, new AllUsersFragment());
            }
            else if (id == R.id.nav_classification) {
                handleNavigationSelection(FRAGMENT_CLASSIFICATION, new ClassificationFragment());
            } else if (id == R.id.nav_history) {
                handleNavigationSelection(FRAGMENT_HISTORY, new HistoryFragment());
            } else if (id == R.id.nav_profile) {
                handleNavigationSelection(FRAGMENT_PROFILE, new ProfileFragment());
            } else if (id == R.id.nav_changePass) {
                handleNavigationSelection(FRAGMENT_CHANGE_PASSWORD, new ChangePassFragment());
            } else if (id == R.id.nav_login) {
                navigateToLogin();
            } else if (id == R.id.nav_logout) {
                handleLogout();
            }
        }

        setTitleToolbar();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        System.out.println("homhomhom");
        moveTaskToBack(true);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
        if (user != null && user.getEmail() != null) {
            System.out.println("okoohome");
            moveTaskToBack(true);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println("okokokok");
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "back press",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }

    private void initialUser() {
        if (user.getAdmin()) {
            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
        }

        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        loadImage(user.getAvatar(), avatar);
        email.setText(user.getEmail());
    }

    private void reRenderUI() {
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
        avatar.setImageResource(R.mipmap.ic_launcher_round);
        email.setText("User not logged in");
    }

    private void handleNavigationSelection(int fragmentId, Fragment fragment) {
        if (mCurrentFragment != fragmentId) {
            replaceFragment(fragment);
            mCurrentFragment = fragmentId;
        }
    }

    private void handleLogout() {
        Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();
        user = null;
        DataLocalManager.setUser(user);
        reRenderUI();
        replaceFragment(new HomeFragment());
        mCurrentFragment = FRAGMENT_HOME;
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        uncheckOtherItems();
    }

    private void navigateToLogin() {
        intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, fragment);
        transaction.commit();
    }

    public void setCurrentFragment(int fragmentId) {
        mCurrentFragment = fragmentId;
    }

    public void setTitleToolbar() {
        String title = "";
        switch (mCurrentFragment) {
            case FRAGMENT_HOME:
                title = getString(R.string.menu_home);
                break;
            case FRAGMENT_ADMIN:
                title = getString(R.string.menu_alluser);
                break;
            case FRAGMENT_CLASSIFICATION:
                title = getString(R.string.menu_classification);
                break;
            case FRAGMENT_HISTORY:
                title = getString(R.string.menu_history);
                break;
            case FRAGMENT_PROFILE:
                title = getString(R.string.menu_profile);
                break;
            case FRAGMENT_CHANGE_PASSWORD:
                title = getString(R.string.menu_changePass);
                break;
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void loadImage(String url, ImageView imageView) {
        if (url.isEmpty()) {
            Glide.with(this).load("https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745").into(imageView);
        } else {
            Glide.with(this).load(Constants.URL + url).into(imageView);
        }
    }

    private void uncheckOtherItems() {
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            MenuItem menuItem = navigationView.getMenu().getItem(i);
            if (menuItem.getItemId() != R.id.nav_home) {
                menuItem.setChecked(false);
            }
        }
    }
}
