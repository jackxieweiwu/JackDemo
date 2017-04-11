package zkrtdrone.zkrt.com.jackmvvm.mvvm.permission;

/**
 * Created by jack_xie on 17-4-9.
 *  权限回调
 */

public interface OnPermissionCallback {
    public static final int PERMISSION_ALERT_WINDOW = 0xad1;
    public static final int PERMISSION_WRITE_SETTING = 0xad2;

    public void onSuccess(String... permissions);

    public void onFail(String... permissions);
}
