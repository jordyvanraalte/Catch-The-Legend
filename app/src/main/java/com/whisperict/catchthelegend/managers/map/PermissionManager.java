package com.whisperict.catchthelegend.managers.map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

public class PermissionManager {
    public static final int REQUEST_PERMISSION_ID = 33812;

    /**
     * Check whether a given permission is granted
     * @param context     The context to check on
     * @param permission  The permission to check
     */
    public static boolean checkPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request given permissions
     * @param activity    The activity
     * @param permissions The permissions to request
     */
    public static void requestPermissions(Activity activity, String... permissions) {
        requestPermissions(activity, REQUEST_PERMISSION_ID, permissions);
    }

    /**
     * Request given permissions
     * @param fragment    The fragment
     * @param permissions The permissions to request
     */
    public static void requestPermissions(Fragment fragment, String... permissions) {
        requestPermissions(fragment, REQUEST_PERMISSION_ID, permissions);
    }

    /**
     * Request given permissions
     * @param fragment    The fragment
     * @param requestCode The request code
     * @param permissions The permissions to request
     */
    public static void requestPermissions(Fragment fragment, int requestCode, String... permissions) {
        fragment.requestPermissions(permissions, requestCode);
    }

    /**
     * Request given permissions
     * @param activity    The activity
     * @param requestCode The request code
     * @param permissions The permissions to request
     */
    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     * Check whether a given permission is granted and if not request it
     * @param fragment   The fragment
     * @param permission The permission to check and request
     */
    public static boolean checkAndRequestPermission(Fragment fragment, String permission) {
        return checkAndRequestPermission(fragment, REQUEST_PERMISSION_ID, permission);
    }

    /**
     * Check whether a given permission is granted and if not request it
     * @param activity   The activity
     * @param permission The permission to check and request
     */
    public static boolean checkAndRequestPermission(Activity activity, String permission) {
        return checkAndRequestPermission(activity, REQUEST_PERMISSION_ID, permission);
    }

    /**
     * Check whether a given permission is granted and if not request it
     * @param fragment   The fragment
     * @param requestCode The request code
     * @param permission The permission to check and request
     */
    public static boolean checkAndRequestPermission(Fragment fragment, int requestCode, String permission) {
        if(!checkPermission(fragment.getContext(), permission)) {
            requestPermissions(fragment, requestCode, permission);
            return false;
        }
        return true;
    }

    /**
     * Check whether a given permission is granted and if not request it
     * @param activity   The activity
     * @param requestCode The request code
     * @param permission The permission to check and request
     */
    public static boolean checkAndRequestPermission(Activity activity, int requestCode, String permission) {
        if(!checkPermission(activity, permission)) {
            requestPermissions(activity, requestCode, permission);
            return false;
        }
        return true;
    }
}
