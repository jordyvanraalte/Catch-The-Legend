package com.whisperict.catchthelegend.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.database.DatabaseManager;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Switch vibrateSwitch;
    private Switch musicSwitch;
    private boolean switchOnOffHeptic;
    public static final String HEPTIC = "HEPTIC";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        vibrateSwitch = (Switch) findViewById(R.id.vibrate_switch);

        vibrateSwitch.setChecked(preferences.getBoolean("VIBRATE_BOOL", true));
        vibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor = preferences.edit();
                editor.putBoolean("VIBRATE_BOOL",b);
                editor.apply();
            }
        });
        musicSwitch = findViewById(R.id.sound_switch);
        musicSwitch.setChecked(preferences.getBoolean("VIBRATE_BOOL", true));

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor = preferences.edit();
                editor.putBoolean("SOUND_BOOL",b);
                editor.apply();
            }
        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.system_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.apply();

        Button reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Executor databaseThread = Executors.newSingleThreadExecutor();
                databaseThread.execute(() -> {
                    DatabaseManager.getInstance(getApplicationContext()).getAppDatabase().legendDao().reset();
                });
            }
        });
    }


}
