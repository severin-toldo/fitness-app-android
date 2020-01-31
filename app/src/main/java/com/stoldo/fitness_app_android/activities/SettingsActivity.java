package com.stoldo.fitness_app_android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.util.LocaleManager;

import static android.content.pm.PackageManager.GET_META_DATA;

// TODO java doc to class and all not overriding methods --> Stefano
public class SettingsActivity extends PreferenceActivity {

    private static SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetTitles();

        setTitle(R.string.title_activity_settings);
        addPreferencesFromResource(R.xml.root_preferences);

        if(preferenceChangeListener == null) {
            preferenceChangeListener = this::onSharedPreferenceChanged;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            preferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        }
    }

    //Für Sprache anwenden bei ännderungen
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    protected void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setNewLocale(Activity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        this.finish();
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String lang = sharedPreferences.getString(key, "nothing");
        if(lang != "nothing"){
            setNewLocale(this, lang);
        }
    }
}