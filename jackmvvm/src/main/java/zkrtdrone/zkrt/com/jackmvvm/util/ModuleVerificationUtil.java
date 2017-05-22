package zkrtdrone.zkrt.com.jackmvvm.util;


import dji.sdk.products.Aircraft;
import dji.sdk.products.HandHeld;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication;

/**
 * Created by jack_xie on 16/1/6.
 */
public class ModuleVerificationUtil {
    public static boolean isProductModuleAvailable() {
        return (null != BaseApplication.getProductInstance());
    }

    public static boolean isAircraft() {
        return BaseApplication.getProductInstance() instanceof Aircraft;
    }

    public static boolean isHandHeld() {
        return BaseApplication.getProductInstance() instanceof HandHeld;
    }

    public static boolean isCameraModuleAvailable() {
        return isProductModuleAvailable() && (null != BaseApplication.getProductInstance().getCamera());
    }

    public static boolean isPlaybackAvailable() {
        return isCameraModuleAvailable() && (null != BaseApplication.getProductInstance()
                                                                         .getCamera()
                                                                         .getPlaybackManager());
    }

    public static boolean isMediaManagerAvailable() {
        return isCameraModuleAvailable() && (null != BaseApplication.getProductInstance()
                                                                         .getCamera()
                                                                         .getMediaManager());
    }

    public static boolean isRemoteControllerAvailable() {
        return isProductModuleAvailable() && isAircraft() && (null != BaseApplication.getAircraftInstance()
                                                                                          .getRemoteController());
    }

    public static boolean isFlightControllerAvailable() {
        return isProductModuleAvailable() && isAircraft() && (null != BaseApplication.getAircraftInstance()
                                                                                          .getFlightController());
    }

    public static boolean isCompassAvailable() {
        return isFlightControllerAvailable() && isAircraft() && (null != BaseApplication.getAircraftInstance()
                                                                                             .getFlightController()
                                                                                             .getCompass());
    }

    public static boolean isOnboardAvailable() {
        return isFlightControllerAvailable() && isAircraft() && BaseApplication.getAircraftInstance().getFlightController().isOnboardSDKDeviceAvailable();
    }

    public static boolean isFlightLimitationAvailable() {
        return isFlightControllerAvailable() && isAircraft();
    }

    public static boolean isGimbalModuleAvailable() {
        return isProductModuleAvailable() && (null != BaseApplication.getProductInstance().getGimbal());
    }

    public static boolean isAirlinkAvailable() {
        return isProductModuleAvailable() && (null != BaseApplication.getProductInstance().getAirLink());
    }

    public static boolean isWiFiLinkAvailable() {
        return isAirlinkAvailable() && (null != BaseApplication.getProductInstance().getAirLink().getWiFiLink());
    }

    public static boolean isLightbridgeLinkAvailable() {
        return isAirlinkAvailable() && (null != BaseApplication.getProductInstance()
                                                                    .getAirLink()
                                                                    .getLightbridgeLink());
    }
}
