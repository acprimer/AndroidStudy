package com.study.yaodh.androidstudy.download;

import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.study.yaodh.androidstudy.StudyApplication;
import com.study.yaodh.androidstudy.activity.LiulishuoDownloadActivity;
import com.study.yaodh.androidstudy.adapter.LiulishuoTaskItemAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by yaodh on 2017/5/18.
 */

public class TaskManager {
    private static final TaskManager instance = new TaskManager();

    public static TaskManager getImpl() {
        return instance;
    }

    private TasksManagerDBController dbController;
    private List<TaskModel> models;
    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

    private TaskManager() {
        dbController = new TasksManagerDBController(StudyApplication.getInstance());
        models = dbController.getAllTasks();

        if (models == null || models.size() == 0) {
            for (int i = 0; i < URLConstant.urls.length; i++) {
                String url = URLConstant.urls[i];
                String path = FileDownloadUtils.getDefaultSaveFilePath(url);
                final TaskModel model = dbController.addTask(url, path);
                if (model != null) {
                    models.add(model);
                }
            }
        }
    }

    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    private FileDownloadConnectListener listener;

    private void registerServiceConnectionListener(final WeakReference<LiulishuoDownloadActivity> activityWeakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {
            @Override
            public void connected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }
                activityWeakReference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }
                activityWeakReference.get().postNotifyDataChanged();
            }
        };
        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }

    public void onCreate(final WeakReference<LiulishuoDownloadActivity> activityWeakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(activityWeakReference);
        }
    }

    public void updateViewHolder(final int id, final LiulishuoTaskItemAdapter.TaskItemViewHolder holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }
        task.setTag(holder);
    }

    public void releaseTask() {
        taskSparseArray.clear();
    }

    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }

    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    public TaskModel get(final int position) {
        return models.get(position);
    }

    public TaskModel getById(final int id) {
        for (TaskModel model : models) {
            if (model.id == id) {
                return model;
            }
        }
        return null;
    }

    public boolean isDownloaded(final int status) {
        return status == FileDownloadStatus.completed;
    }

    public int getStatus(final int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    public long getTotal(final int id) {
        return FileDownloader.getImpl().getTotal(id);
    }

    public long getSofar(final int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }

    public int getTaskCount() {
        return models.size();
    }
}
