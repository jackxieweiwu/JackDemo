package zkrtdrone.zkrt.com.maplib.info;

import android.content.res.Resources;
import android.graphics.Bitmap;

/**
 * Created by jack_xie on 17-5-11.
 */

public class DroneMarker extends MarkerInfo.SimpleMarkerInfo {



    @Override
    public float getAnchorU() {
        return 0.5f;
    }

    @Override
    public float getAnchorV() {
        return 0.5f;
    }

    @Override
    public Bitmap getIcon(Resources res) {
        return null;//BitmapFactory.decodeResource(res, R.drawable.quad_disconnect);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public boolean isFlat() {
        return true;
    }
}
