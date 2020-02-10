package com.kk.app.lib.network.download;

import java.io.File;

/**
 * Running in subThread
 * @author uis on 2018/5/11.
 */
public interface DownloadCallback {
    void onSuccess(File file);
    void onFail(Throwable throwable, String error);
    void onProgress(long total, long current, int percent);
}
