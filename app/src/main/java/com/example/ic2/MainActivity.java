package com.example.ic2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ic2.fragment.QuickreportFragment;
import com.example.ic2.model.User;
import com.example.ic2.setting.Setting;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    QuickreportFragment quickreportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        quickreportFragment=new QuickreportFragment();
        setFragment(quickreportFragment);
        navigationView.getMenu().getItem(0).setChecked(true);

        initNavigationViewHeader();
    }

    private void initNavigationViewHeader() {
        View header=navigationView.getHeaderView(0);
        TextView tvEmail=header.findViewById(R.id.tvEmail);
        tvEmail.setText(User.getCurrentUser().getEmail());
    }

    private void initView() {

        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigation);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navication_drawer_open,
                R.string.navication_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    public void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();

        drawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.quickReport:setFragment(quickreportFragment);return true;
            case R.id.setting:startActivity(new Intent(MainActivity.this, Setting.class));return true;
            case R.id.archive:startActivity(new Intent(MainActivity.this,ArchiveActivity.class));return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}
