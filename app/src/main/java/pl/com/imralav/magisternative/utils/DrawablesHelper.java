package pl.com.imralav.magisternative.utils;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class DrawablesHelper {
    private static final String ANDROID_DRAWABLE = "@android:drawable/";
    private static final String STATUS_UNKNOWN_ICON_NAME = ANDROID_DRAWABLE + "ic_menu_help";
    private static final String DATA_ACCESSIBLE_ICON_NAME = ANDROID_DRAWABLE + "checkbox_on_background";
    private static final String DATA_INACCESSIBLE_ICON_NAME = ANDROID_DRAWABLE + "ic_secure";
    private static final String DRAWABLE = "drawable";


    public static int getStatusUnknownIconId(Resources resources, String packageName) {
        return resources.getIdentifier(STATUS_UNKNOWN_ICON_NAME, DRAWABLE, packageName);
    }

    public static int getDataAccessibleIconId(Resources resources, String packageName) {
        return resources.getIdentifier(DATA_ACCESSIBLE_ICON_NAME, DRAWABLE, packageName);
    }

    public static int getDataInaccessibleIconId(Resources resources, String packageName) {
        return resources.getIdentifier(DATA_INACCESSIBLE_ICON_NAME, DRAWABLE, packageName);
    }
}
