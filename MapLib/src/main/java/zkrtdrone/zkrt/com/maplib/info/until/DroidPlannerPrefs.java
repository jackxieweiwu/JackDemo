package zkrtdrone.zkrt.com.maplib.info.until;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import zkrtdrone.zkrt.com.maplib.R;

/**
 * Created by jack_xie on 17-4-24.
 */

public class DroidPlannerPrefs {

    public static final boolean DEFAULT_PREF_UI_LANGUAGE = false;
    private static final String DEFAULT_TCP_SERVER_IP = "192.168.1.7";
    private static final String DEFAULT_TCP_SERVER_PORT = "26";
    public SharedPreferences prefs;
    private Context context;

    public DroidPlannerPrefs(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getMapProviderName() {
        return prefs.getString(context.getString(R.string.pref_maps_providers_key), null);
    }

    public boolean isEnglishDefaultLanguage() {
        return prefs.getBoolean(context.getString(R.string.pref_ui_language_english_key),
                DEFAULT_PREF_UI_LANGUAGE);
    }
}
