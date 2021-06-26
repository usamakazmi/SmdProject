package com.example.exampractice;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.exampractice.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;
    private Button nav_h, nav_m, nav_l;
    private TextView drawerProfileName, drawerProfileText, drawerProfileEmail;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                   switch (item.getItemId())
                   {
                       case R.id.nav_home:
                           setFragment(new CategoryFragment());
                           //bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                           return true;

                       case R.id.nav_leaderboard:
                           setFragment(new LeaderboardFragment());
                           //bottomNavigationView.setSelectedItemId(R.id.navigation_leaderboard);

                           return true;

                       case R.id.nav_account:
                           setFragment(new AccountFragment());
                           //bottomNavigationView.setSelectedItemId(R.id.navigation_account);

                           return true;


                   }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        main_frame = findViewById(R.id.main_frame);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        drawerProfileName = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);
        drawerProfileText = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_text_img);
        drawerProfileEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_email);

        String email = DbQuery.myProfile.getEmail();
        //String email = DbQuery.myProfile.getName();
        drawerProfileEmail.setText(email);

        nav_m = navigationView.getHeaderView(0).findViewById(R.id.nav_m);
        nav_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new AccountFragment());
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        nav_h = navigationView.getHeaderView(0).findViewById(R.id.nav_h);
        nav_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new CategoryFragment());
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        nav_l = navigationView.getHeaderView(0).findViewById(R.id.nav_l);
        nav_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new LeaderboardFragment());
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        String name = DbQuery.myProfile.getName();
        drawerProfileName.setText(name);

        drawerProfileText.setText(name.toUpperCase().substring(0,1));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_account, R.id.nav_leaderboard)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setFragment(new CategoryFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(), fragment);
        transaction.commit();
    }



}