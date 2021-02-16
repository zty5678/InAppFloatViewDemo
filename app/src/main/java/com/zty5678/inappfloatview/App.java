package com.zty5678.inappfloatview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;

import org.jetbrains.annotations.NotNull;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EasyFloat.init(this, true);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                doOnActivityResume(activity);
            }


            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                doOnActivityStopped(activity);
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    private static final String TAG_FLOAT_VIEW = "TAG_FLOAT_VIEW";
    private static int floatXOffset;
    private static int floatYOffset;
    private static boolean hasDragged = false;
    private void doOnActivityResume(Activity activity) {
        //客服界面不弹出悬浮窗
        if (activity instanceof CustomerServiceActivity) {
            return;
        }
        EasyFloat.Builder builder = EasyFloat.with(activity)
                .setLayout(R.layout.layout_float_window, new OnInvokeView() {
                    @Override
                    public void invoke(View view) {
                        View click_view_float = view.findViewById(R.id.click_view_float);
                        View click_view_close = view.findViewById(R.id.click_view_close);
                        click_view_float.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomerServiceActivity.start(view.getContext());
                            }
                        });
                        click_view_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismissFloatView(activity);
                            }
                        });
                    }
                })
                .setShowPattern(ShowPattern.CURRENT_ACTIVITY)
                .setAnimator(null)
                .setSidePattern(SidePattern.RESULT_HORIZONTAL)
                .setTag(TAG_FLOAT_VIEW);

        if (!hasDragged) {
            builder.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        } else {
            builder.setLocation(floatXOffset, floatYOffset);
        }

        builder.registerCallbacks(new OnFloatCallbacks() {
            @Override
            public void createdResult(boolean b, @org.jetbrains.annotations.Nullable String s, @org.jetbrains.annotations.Nullable View view) {
            }
            @Override
            public void show(@NotNull View view) {
            }
            @Override
            public void hide(@NotNull View view) {
            }
            @Override
            public void dismiss() {
            }
            @Override
            public void touchEvent(@NotNull View view, @NotNull MotionEvent motionEvent) {
            }
            @Override
            public void drag(@NotNull View view, @NotNull MotionEvent motionEvent) {
            }
            @Override
            public void dragEnd(@NotNull View view) {
                hasDragged = true;
                saveFloatViewPosition(activity);
            }
        });

        builder.show();
    }

    private void saveFloatViewPosition(Activity activity) {
        View floatView = EasyFloat.getFloatView(activity, TAG_FLOAT_VIEW);
        if (floatView != null) {
            int[] outLocation = new int[2];
            floatView.getLocationInWindow(outLocation);
            floatXOffset = outLocation[0];
            floatYOffset = outLocation[1] - Utils.getToolbarHeight(floatView.getContext()) - Utils.getStatusBarHeight(activity);
        }
    }

    private void dismissFloatView(Activity activity) {
        EasyFloat.dismiss(activity, TAG_FLOAT_VIEW);
    }

    private void doOnActivityStopped(Activity activity) {
        dismissFloatView(activity);
    }

}
