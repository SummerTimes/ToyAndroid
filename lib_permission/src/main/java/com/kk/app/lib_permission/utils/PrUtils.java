package com.kk.app.lib_permission.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

public final class PrUtils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApp;

    private PrUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of UtilsFileProvider.</p>
     *
     * @param app application
     */
    public static void init(final Application app) {
        if (app == null) {
            Log.e("Utils", "app is null.");
            return;
        }
        if (sApp == null) {
            sApp = app;
            return;
        }
        if (sApp.equals(app)) {
            return;
        }
        sApp = app;
    }

    /**
     * Return the Application object.
     * <p>Main process get app by UtilsFileProvider,
     * and other process get app by reflect.</p>
     *
     * @return the Application object
     */
    public static Application getApp() {
        if (sApp != null) {
            return sApp;
        }
        throw new NullPointerException("reflect failed.");
    }


    public interface Consumer<T> {
        void accept(T t);
    }

}
