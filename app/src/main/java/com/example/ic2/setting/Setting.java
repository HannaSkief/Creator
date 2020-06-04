package com.example.ic2.setting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.example.ic2.R;

public class Setting extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new MyPreference()).commit();
    }

    public static class  MyPreference extends PreferenceFragment{

        Preference location,typeOfIncident;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            location=findPreference(getString(R.string.locations));
            typeOfIncident=findPreference(getString(R.string.types_of_incident));
            location.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    openFragment(new LocationsFragment());
                    return true;
                }
            });
            typeOfIncident.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    openFragment(new TypeOfIncidentFragment());
                    return true;
                }
            });
        }

        private  void openFragment(Fragment fragment){
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content,fragment)
                    .addToBackStack("setting")
                    .commit();

        }
    }




}
