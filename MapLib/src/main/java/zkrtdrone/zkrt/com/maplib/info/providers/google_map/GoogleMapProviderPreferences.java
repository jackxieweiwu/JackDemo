package zkrtdrone.zkrt.com.maplib.info.providers.google_map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;

import zkrtdrone.zkrt.com.maplib.R;
import zkrtdrone.zkrt.com.maplib.info.providers.DPMapProvider;
import zkrtdrone.zkrt.com.maplib.info.providers.MapProviderPreferences;

/**
 * This is the google map provider preferences. It stores and handles all
 * preferences related to google map.
 */
public class GoogleMapProviderPreferences extends MapProviderPreferences {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences_google_map);
		setupPreferences();
	}

	private void setupPreferences() {
		final Context context = getActivity().getApplicationContext();
		final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

		final String mapTypeKey = getString(R.string.pref_map_type_key);
		final Preference mapTypePref = findPreference(mapTypeKey);
		if (mapTypePref != null) {
			mapTypePref.setSummary(sharedPref.getString(mapTypeKey, ""));
			mapTypePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					mapTypePref.setSummary(newValue.toString());
					return true;
				}
			});
		}
	}

	@Override
	public DPMapProvider getMapProvider() {
		return DPMapProvider.DEFAULT_MAP_PROVIDER;
	}
}
