package zkrtdrone.zkrt.com.maplib.info.providers;

import zkrtdrone.zkrt.com.maplib.info.DPmap;
import zkrtdrone.zkrt.com.maplib.info.providers.BaiduMap.BaiduMapFragment;
import zkrtdrone.zkrt.com.maplib.info.providers.google_map.GoogleMapProviderPreferences;

/**
 * Contains a listing of the various map providers supported, and implemented in
 * DroidPlanner.
 */
public enum DPMapProvider {
	/**
	 * Provide access to google map v2. Requires the google play services.
	 */
/*	GOOGLE_MAP {
		@Override
		public DPMap getMapFragment() {
			return new GoogleMapFragment();
		}

		@Override
		public MapProviderPreferences getMapProviderPreferences() {
			return new GoogleMapProviderPreferences();
		}
	},*/

    /*高德地图 {
        @Override
        public DPmap getMapFragment() {
            return new BaseMyGaoDeMap();
        }

        @Override
        public MapProviderPreferences getMapProviderPreferences() {
            return new GoogleMapProviderPreferences();
        }
    }*/

    百度地图{
        @Override
        public DPmap getMapFragment() {
            return new BaiduMapFragment();
        }

        @Override
        public MapProviderPreferences getMapProviderPreferences() {
            return new GoogleMapProviderPreferences();
        }
    };



	/**
	 * @return the fragment implementing the map.
	 */
	public abstract DPmap getMapFragment();

	/**
	 * @return the set of preferences supported by the map.
	 */
	public abstract MapProviderPreferences getMapProviderPreferences();

	/**
	 * Returns the map type corresponding to the given map name.
	 * 
	 * @param mapName
	 *            name of the map type
	 * @return {@link DPMapProvider} object.
	 */
	public static DPMapProvider getMapProvider(String mapName) {
		if (mapName == null) {
			return null;
		}

		try {
			return DPMapProvider.valueOf(mapName);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * By default, Google Map is the map provider.
	 */
	public static final DPMapProvider DEFAULT_MAP_PROVIDER = 百度地图;
}
