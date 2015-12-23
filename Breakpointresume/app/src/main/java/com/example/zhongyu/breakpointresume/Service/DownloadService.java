package com.example.zhongyu.breakpointresume.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.zhongyu.breakpointresume.Dao.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhongyu on 12/20/2015.
 */
public class DownloadService extends Service {

    private static final String TAG = "DownloadService";
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/downloads/";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public DownloadService(FileInfo fileInfo){
        InitThread initThread = new InitThread(fileInfo);
        initThread.start();
    }
    static class InitThread extends Thread{
        private FileInfo mFileInfo = null;

        private InitThread(FileInfo fileInfo){
            this.mFileInfo = fileInfo;
        }

        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile randomAccessFile = null;
            try {
                URL url = new URL(mFileInfo.getFileurl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int lenth = -1;
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    lenth = conn.getContentLength();
                }
                if(lenth < 0){
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if(!dir.exists()){
                    dir.mkdir();
                }
                File file = new File(dir,mFileInfo.getTitle());
                randomAccessFile = new RandomAccessFile(file,"rwd");
                randomAccessFile.setLength(lenth);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conn.disconnect();
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}











