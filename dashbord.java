package com.example.afinal;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class dashbord extends AppCompatActivity {
    RecyclerView recyclerview_user;
    ImageView empty_imageview;
    TextView no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        recyclerview_user = findViewById(R.id.recyclerview_user);
        empty_imageview = findViewById(R.id.empty_imageviewuser);
        no_data = findViewById(R.id.no_datauser);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        if(timeOfDay >= 8 && timeOfDay < 19)
            findViewById(R.id.dash).setBackgroundResource(R.drawable.good_morning_img);
        else findViewById(R.id.dash).setBackgroundResource(R.drawable.good_night_img);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    final private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                if (item.getItemId()==R.id.nav_home) {

                        selectedFragment = new HomeFragment();}
                else if (item.getItemId()== R.id.nav_favorites){
                        selectedFragment = new FavoriteFragement();}

                else  {
                    selectedFragment = new SearchFragment();
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };
}