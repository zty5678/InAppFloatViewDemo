package com.zty5678.inappfloatview;

import android.content.Context;
import android.util.TypedValue;

public class Utils {
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static int getToolbarHeight(Context context) {

        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            int height = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            return height;
        }
        return 0;
    }

}
