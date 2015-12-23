package com.example.zhongyu.breakpointresume;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zhongyu.breakpointresume.Dao.DaoMaster;
import com.example.zhongyu.breakpointresume.Dao.DaoSession;
import com.example.zhongyu.breakpointresume.Dao.FileInfo;
import com.example.zhongyu.breakpointresume.Service.DownloadService;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;


    private Cursor cursor;

    List<FileInfo> fileInfoList;
    RecyclerView recyclerView;
    FileListAdapter fileListAdapter;
    String fileurl = "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk";

    DownloadService downloadService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        initData();
        initView();
    }


    private final void initDao(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"file-db",null);
        db = helper.getWritableDatabase();
        daoSession = daoMaster.newSession();
    }

    public void initData(){
        fileInfoList = new ArrayList<>();
        FileInfo fileInfo = new FileInfo()
                .setTitle("hello")
                .setSchedule(0)
                .setFileurl(fileurl);
        FileInfo fileInfo1 = new FileInfo()
                .setTitle("world")
                .setSchedule(0)
                .setFileurl(fileurl);
        FileInfo fileInfo2 = new FileInfo()
                .setTitle("welcome")
                .setSchedule(0)
                .setFileurl(fileurl);
        fileInfoList.add(fileInfo);
        fileInfoList.add(fileInfo1);
        fileInfoList.add(fileInfo2);

    }
    public void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.tvFile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileListAdapter = new FileListAdapter(this,fileInfoList);
        recyclerView.setAdapter(fileListAdapter);
    }

    public void onEventMainThread(FileEvent event) {
        FileInfo fileInfo = event.getFileInfo();
        switch (event.getFile_state()){
            case FILE_DOWNLOAD:
                Log.i(TAG, "onEventMainThread: ");
                downloadService = new DownloadService(fileInfo);
                break;
            case FILE_SELECT:
                break;
            case FILE_DELETE:
                break;
        }
    }
}
