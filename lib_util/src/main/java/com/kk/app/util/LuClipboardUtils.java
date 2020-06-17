package com.kk.app.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/25
 *     desc  : 剪贴板相关工具类
 * </pre>
 */
public final class LuClipboardUtils {

    private LuClipboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 复制文本到剪贴板
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void copyText(Context context, final CharSequence text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("text", text));
    }

    /**
     * 获取剪贴板的文本
     *
     * @param context 上下文
     * @return 剪贴板的文本
     */
    public static CharSequence getText(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(context);
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     *
     * @param context 上下文
     * @param uri     uri
     */
    public static void copyUri(Context context, final Uri uri) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newUri(context.getContentResolver(), "uri", uri));
    }

    /**
     * 获取剪贴板的uri
     *
     * @param context 上下文
     * @return 剪贴板的uri
     */
    public static Uri getUri(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     *
     * @param context 上下文
     * @param intent  意图
     */
    public static void copyIntent(Context context, final Intent intent) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @param context 上下文
     * @return 剪贴板的意图
     */
    public static Intent getIntent(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }
}
