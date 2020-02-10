package com.kk.app.lib.widget.component;

import android.app.Application;

/**
 * @author kk
 * @datetime: 2020/01/16
 * @desc: 初始化组件
 */
public interface IComponentInit {
    /**
     * 初始化Application
     *
     * @param app
     */
    void onInit(Application app);
}
