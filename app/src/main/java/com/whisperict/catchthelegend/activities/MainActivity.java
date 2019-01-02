package com.whisperict.catchthelegend.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.database.DatabaseManager;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.fragments.CompendiumFragment;
import com.whisperict.catchthelegend.fragments.HelpDialogFragment;
import com.whisperict.catchthelegend.fragments.MapFragment;
import com.whisperict.catchthelegend.fragments.QuestFragment;
import com.whisperict.catchthelegend.managers.apis.legend.OnLegendApiResponseListener;
import com.whisperict.catchthelegend.managers.game.GameManager;
import com.whisperict.catchthelegend.managers.game.GameResponseListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView =  findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MapFragment()).commit();
            navigationView.setCheckedItem(R.id.map);
            toolbar.setTitle(R.string.map);
        }

        //creates a DatabaseManager instance
        DatabaseManager.getInstance(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MapFragment()).commit();
                toolbar.setTitle(R.string.map);
                break;
            case R.id.quest:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new QuestFragment()).commit();
                toolbar.setTitle(R.string.quests);
                break;
            case R.id.compendium:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CompendiumFragment()).commit();
                toolbar.setTitle(R.string.compendium);
                break;

            case R.id.settings:
                Intent intent = new Intent (MainActivity.this, SettingActivity.class);
                //todo putExtra's?
                MainActivity.this.startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;

            case R.id.help:
                DialogFragment dialog = new HelpDialogFragment();
                dialog.show(getSupportFragmentManager(), "help");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
