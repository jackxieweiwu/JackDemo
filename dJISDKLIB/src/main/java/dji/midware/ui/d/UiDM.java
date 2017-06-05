package dji.midware.ui.d;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by jck_xie on 17-6-1.
 */

public class UiDM {
    public static void a(@NonNull View view) {
        if(view.isSelected()) {
            view.setSelected(false);
        } else {
            view.setSelected(true);
        }

    }

    public static void b(@NonNull View view) {
        view.setSelected(false);
    }
}
