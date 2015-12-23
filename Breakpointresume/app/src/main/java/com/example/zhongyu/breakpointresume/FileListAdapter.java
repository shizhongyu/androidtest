package com.example.zhongyu.breakpointresume;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhongyu.breakpointresume.Dao.FileInfo;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zhongyu on 12/20/2015.
 */
public class FileListAdapter extends RecyclerView.Adapter
                            <FileListAdapter.FileListViewHolder> implements View.OnClickListener{

    private static final String TAG = "FileListAdapter";
    private Context mContext = null;
    private List<FileInfo> fileInfoList;

    public FileListAdapter(){

    }

    public FileListAdapter(Context context,List<FileInfo> fileInfoList){
        this.mContext = context;
        this.fileInfoList = fileInfoList;
    }

    @Override
    public FileListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater
               .from(parent.getContext())
               .inflate(R.layout.listitem, parent, false);
        return new FileListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileListViewHolder holder, int position) {
        FileInfo fileInfo = fileInfoList.get(position);

        holder.vTitle.setText(fileInfo.getTitle());
        holder.vSchedule.setProgress((int) fileInfo.getSchedule());

        holder.start_or_pause.setOnClickListener(this);
        holder.itemView.setTag(R.id.tag_object,fileInfo);

    }

    @Override
    public int getItemCount() {
        return fileInfoList == null ? 0 : fileInfoList.size();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.start_pause:
                fileDown(v);
                break;
        }
    }

    public void fileDown(View view){
        ViewGroup container = (ViewGroup) view.getParent();
        FileInfo fileInfo = (FileInfo) container.getTag(R.id.tag_object);
        Button button = (Button) view;
        String text = button.getText().toString().trim();
        String start = mContext.getString(R.string.start);
        String end = mContext.getString(R.string.end);
        if(text.equals(start)){
            EventBus.getDefault()
                    .post(new FileEvent(FileEvent.FILE_STATE.FILE_DOWNLOAD)
                            .setFileInfo(fileInfo));
        }else if(text.equals(end)){
            EventBus.getDefault()
                    .post(new FileEvent(FileEvent.FILE_STATE.FILE_PAUSE)
                            .setFileInfo(fileInfo));
        }
    }
    public static class FileListViewHolder extends RecyclerView.ViewHolder{

        private TextView vTitle;
        private Button start_or_pause;
        private ProgressBar vSchedule;

        public FileListViewHolder(View itemView) {
            super(itemView);
            vTitle = (TextView) itemView.findViewById(R.id.title);
            start_or_pause = (Button) itemView.findViewById(R.id.start_pause);
            vSchedule = (ProgressBar) itemView.findViewById(R.id.schedule);
        }
    }
}
