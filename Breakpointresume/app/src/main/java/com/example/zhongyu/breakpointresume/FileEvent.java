package com.example.zhongyu.breakpointresume;

import com.example.zhongyu.breakpointresume.Dao.FileInfo;

/**
 * Created by zhongyu on 12/20/2015.
 */
public class FileEvent {

    public static enum FILE_STATE{
        FILE_DOWNLOAD,FILE_DELETE,FILE_SELECT,FILE_PAUSE
    }

    public FILE_STATE file_state;

    FileInfo fileInfo;

    public FileEvent(FILE_STATE file_state){
        this.file_state = file_state;
    }

    public FILE_STATE getFile_state(){
        return file_state;
    }
    public FileInfo getFileInfo(){
        return fileInfo;
    }

    public FileEvent setFileInfo( FileInfo fileInfo){
        this.fileInfo = fileInfo;
        return this;
    }

}
