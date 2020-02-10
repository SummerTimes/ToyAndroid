package com.kk.app.lib.network.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.kk.app.lib.network.util.CryptoUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


/** 文件下载器，默认支持断点下载
 * @author uis on 2018/5/10.
 */
public class DownloadClient {
    static final String END = ".rf";
    boolean cancel = false;

    public void cancel(){
        this.cancel = true;
    }

    public void download(ExecutorService service, final File parent, final String url, final DownloadCallback callback){
        if(service == null){
            Log.w("download","ExecutorService is null,can't download");
            return;
        }
        service.submit(new Runnable() {
            @Override
            public void run() {
                download(parent,url,callback);
            }
        });
    }

    public static File newFile(File parent, String url){
        String name = CryptoUtil.genMD5Str(url);
        return new File(parent,name);
    }

    public static File realFile(File parent, String url){
        return new File(parent, CryptoUtil.genMD5Str(url) + END);
    }

    public Call newCall(String url, long startPoints){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes=" + startPoints + "-")
                .get()
                .url(url)
                .build();
        return client.newCall(request);
    }

    /**
     * 下载，需要把此任务放入到子线程中
     * @param parent 下载文件夹
     * @param url    下载地址
     */
    public void download(File parent, String url, DownloadCallback callback){
        //真实地址
        File realFile = null;
        FileChannel channel = null;
        try {
            if(TextUtils.isEmpty(url)){
                throw new NullPointerException("download url is null");
            }
            if(parent == null){
                throw new NullPointerException("parent file is null");
            }
            long startPoints = 0;
            //缓存地址
            File file = newFile(parent,url);
            realFile = new File(file.getParent(),file.getName() + END);
            if(!realFile.exists()) {
                if (file.exists()) {
                    startPoints = file.length();
                }
                channel = new RandomAccessFile(file, "rwd").getChannel();
                channel.lock();
                Call call = newCall(url, startPoints);
                ResponseBody body = call.execute().body();
                long currentLen = startPoints;
                long totalLen = body.contentLength();
                long mills = System.currentTimeMillis();
                InputStream in = body.byteStream();
                channel.position(startPoints);
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byte[] buffer = new byte[1024];
                int len;
                while (!cancel && (len = in.read(buffer)) != -1) {
                    byteBuffer.put(buffer,0,len);
                    byteBuffer.flip();
                    channel.write(byteBuffer);
                    byteBuffer.clear();
                    currentLen += len;
                    if ((System.currentTimeMillis()-mills)>300 && callback != null) {
                        int percent = (int) (100 * currentLen / (totalLen+currentLen));
                        callback.onProgress(totalLen, currentLen, percent);
                        mills = System.currentTimeMillis();
                    }
                }
                in.close();
                if(totalLen>=0 && (totalLen+startPoints) == currentLen) {
                    file.renameTo(realFile);
                }
            }
            if (!realFile.exists()) {
                throw new IOException("File isnot exists.");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            if(callback != null){
                callback.onFail(ex,ex.getMessage());
            }
        }finally {
            try{
                channel.close();
            }catch (Exception ex){}
            if(callback != null){
                if(realFile != null && realFile.exists()) {
                    callback.onSuccess(realFile);
                }
            }
        }
    }

    public static boolean copyFile(File resource, File des){
        boolean success = true;
        try {
            FileInputStream fileIn = new FileInputStream(resource);
            RandomAccessFile accessFile = new RandomAccessFile(des, "rwd");
            fileIn.getChannel().transferTo(0, resource.length(), accessFile.getChannel());
        }catch (Exception ex){
            ex.printStackTrace();
            success = false;
        }finally {
            return success;
        }
    }

    public static void saveToSysAlbum(Context context, File file) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
