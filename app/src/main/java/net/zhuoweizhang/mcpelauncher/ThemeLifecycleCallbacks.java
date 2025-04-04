package net.zhuoweizhang.mcpelauncher;

import android.app.Application;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class ThemeLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        Utils.setupTheme(activity, false);
    }

    public void onActivityDestroyed(@NonNull Activity activity) {
    }

    public void onActivityPaused(@NonNull Activity activity) {
    }

    public void onActivityResumed(@NonNull Activity activity) {
    }

    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    public void onActivityStarted(@NonNull Activity activity) {
    }

    public void onActivityStopped(@NonNull Activity activity) {
    }
}
