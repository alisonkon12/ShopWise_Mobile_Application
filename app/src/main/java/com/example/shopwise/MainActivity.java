package com.example.shopwise;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;


import com.example.shopwise.home.HomeFragment;
import com.example.shopwise.promotion.PromotionFragment;

public class MainActivity extends AppCompatActivity {


    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new PromotionFragment();
    final Fragment fragment3 = new FavouriteListFragment();
    final FragmentManager fm = getSupportFragmentManager();

    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Home </font>"));


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Home </font>"));
                    active = fragment1;
                    return true;

                case R.id.navigation_promotion:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Promotion </font>"));
                    active = fragment2;
                    return true;

                case R.id.navigation_favorite:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Favourite </font>"));
                    active = fragment3;
                    return true;
            }
            return false;
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, ProfileSettingsActivity.class));
            return true;
        }

//        if (id == R.id.action_notification_settings) {
//            startActivity(new Intent(MainActivity.this, SettingActivity.class));
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
