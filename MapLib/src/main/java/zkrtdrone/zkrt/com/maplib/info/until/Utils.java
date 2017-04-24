package zkrtdrone.zkrt.com.maplib.info.until;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

import zkrtdrone.zkrt.com.maplib.info.providers.DPMapProvider;

/**
 * Created by jack_xie on 17-4-24.
 */

public class Utils {
    /**
     * Returns the map provider selected by the user.
     *
     * @param context
     *            application context
     * @return selected map provider
     */
    public static DPMapProvider getMapProvider(Context context) {
        DroidPlannerPrefs prefs = new DroidPlannerPrefs(context);
        final String mapProviderName = prefs.getMapProviderName();

        return mapProviderName == null ? DPMapProvider.DEFAULT_MAP_PROVIDER : DPMapProvider
                .getMapProvider(mapProviderName);
    }

    /**
     * Used to update the user interface language.
     *
     * @param context
     *            Application context
     */
    public static void updateUILanguage(Context context) {
        DroidPlannerPrefs prefs = new DroidPlannerPrefs(context);
        if (prefs.isEnglishDefaultLanguage()) {
            Configuration config = new Configuration();
            config.locale = Locale.ENGLISH;

            final Resources res = context.getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }
}
