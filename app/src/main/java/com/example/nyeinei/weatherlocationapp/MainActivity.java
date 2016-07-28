package com.example.nyeinei.weatherlocationapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar toolbar;
    public static ImageView logo;
    public static TextView title;
    String TAG = "tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logo = (ImageView)toolbar.findViewById(R.id.logo);
        title = (TextView)toolbar.findViewById(R.id.title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        homeFragment home = new homeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container,home,TAG).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction ft;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            homeFragment home = new homeFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(TAG);
            ft.replace(R.id.container,home,TAG).commit();

        }else if(id == R.id.hourly){
            hourlyFragment hourly = new hourlyFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(TAG);
            ft.replace(R.id.container,hourly).commit();
        }else if(id == R.id.daily){
            dailyFragment daily = new dailyFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(TAG);
            ft.replace(R.id.container,daily).commit();
        }else if(id == R.id.map){
            mapFragment mapFragment = new mapFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,mapFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            android.app.FragmentManager fm = getFragmentManager();

            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                super.onBackPressed();
            }
        }


    }

}
