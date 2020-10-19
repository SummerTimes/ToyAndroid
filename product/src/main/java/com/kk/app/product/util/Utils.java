package com.kk.app.product.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

/**
 * <pre>
 *     author:
 *                                      ___           ___           ___         ___
 *         _____                       /  /\         /__/\         /__/|       /  /\
 *        /  /::\                     /  /::\        \  \:\       |  |:|      /  /:/
 *       /  /:/\:\    ___     ___    /  /:/\:\        \  \:\      |  |:|     /__/::\
 *      /  /:/~/::\  /__/\   /  /\  /  /:/~/::\   _____\__\:\   __|  |:|     \__\/\:\
 *     /__/:/ /:/\:| \  \:\ /  /:/ /__/:/ /:/\:\ /__/::::::::\ /__/\_|:|____    \  \:\
 *     \  \:\/:/~/:/  \  \:\  /:/  \  \:\/:/__\/ \  \:\~~\~~\/ \  \:\/:::::/     \__\:\
 *      \  \::/ /:/    \  \:\/:/    \  \::/       \  \:\  ~~~   \  \::/~~~~      /  /:/
 *       \  \:\/:/      \  \::/      \  \:\        \  \:\        \  \:\         /__/:/
 *        \  \::/        \__\/        \  \:\        \  \:\        \  \:\        \__\/
 *         \__\/                       \__\/         \__\/         \__\/
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : utils about initialization
 * </pre>
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApp;

    private Utils() {
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
        if (sApp == null) {
            throw new NullPointerException("reflect failed.");
        }
        return sApp;
    }

    public interface Consumer<T> {
        void accept(T t);
    }

    public interface Supplier<T> {
        T get();
    }

    public interface Func1<Ret, Par> {
        Ret call(Par param);
    }
}
