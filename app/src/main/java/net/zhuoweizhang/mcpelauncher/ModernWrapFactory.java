package net.zhuoweizhang.mcpelauncher;

import org.mozilla.javascript.*;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

/**
 * Because they are not classic
 * Disables wrapping of Java class objects
 */

public class ModernWrapFactory extends WrapFactory {

    public static final String TAG = "BlockLauncher/ModernWrapFactory";
    public final List<WeakReference<PopupWindow>> popups = new ArrayList<>();
    private static final MyExceptionHandler myExceptionHandler = new MyExceptionHandler();

    private static class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(@NonNull Thread thread, @NonNull Throwable t) {
            ScriptManager.reportScriptError(null, t);
        }
    }
	/*
	@Override
	public Scriptable wrapJavaClass(Context cx, Scriptable scope, Class javaClass) {
		Log.i(TAG, "Wrapping " + javaClass.toString());
		return super.wrapJavaClass(cx, scope, javaClass);
	}
	*/

    @Override
    public Scriptable wrapAsJavaObject(Context cx, Scriptable scope, Object javaObject, Class<?> staticType) {
        if (javaObject instanceof PopupWindow) {
            if (!ScriptManager.isScriptingEnabled()) {
                ((PopupWindow) javaObject).dismiss();
            }
            synchronized (popups) {
                popups.add(new WeakReference<>((PopupWindow) javaObject));
            }
        }
        if (javaObject instanceof Thread) {
            Thread t = (Thread) javaObject;
            Thread.UncaughtExceptionHandler exHandler = t.getUncaughtExceptionHandler();
            if (exHandler == null || exHandler instanceof ThreadGroup) {
                t.setUncaughtExceptionHandler(myExceptionHandler);
            }
        }
        return super.wrapAsJavaObject(cx, scope, javaObject, staticType);
    }

    protected void closePopups(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                synchronized (popups) {
                    for (WeakReference<PopupWindow> ref : popups) {
                        PopupWindow window = ref.get();
                        if (window == null) continue;
                        window.dismiss();
                    }
                    //popups.clear();
                }
            }
        });
    }


}
