package de.christiani.benjamin.wordgen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;


public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String TAG = SettingsFragment.class.getName();
    public static final String KEY_PREF_DICTIONARY = "pref_dictionary";
    public static final String KEY_PREF_LIMIT = "pref_limit";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        updatePreference(findPreference(KEY_PREF_DICTIONARY));
        updatePreference(findPreference(KEY_PREF_LIMIT));
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        this.updatePreference(findPreference(key));
    }

    private void updatePreference(final Preference preference) {
        if(preference == null) return;
        if(preference instanceof ListPreference) {
            final ListPreference listPreference = (ListPreference) preference;
            preference.setSummary(listPreference.getEntry());
            return;
        }
    }
}
