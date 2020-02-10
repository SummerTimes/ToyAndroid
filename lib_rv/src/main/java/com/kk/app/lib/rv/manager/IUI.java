package com.kk.app.lib.rv.manager;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public interface IUI {

    void onCombineRequestInflateUI(IFloorCombine combine);


    void runOnUiThread(Runnable runnable);
}
