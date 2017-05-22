package com.study.yaodh.androidstudy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.download.TaskManager;
import com.study.yaodh.androidstudy.download.TaskModel;

import java.io.File;

/**
 * Created by yaodh on 2017/5/17.
 */

public class LiulishuoTaskItemAdapter extends RecyclerView.Adapter<LiulishuoTaskItemAdapter.TaskItemViewHolder>{

    private FileDownloadListener taskDownloadListener = new FileDownloadListener() {
        private TaskItemViewHolder checkCurrentHolder(final BaseDownloadTask task) {
            final TaskItemViewHolder tag = (TaskItemViewHolder) task.getTag();
            if (tag.id != task.getId()) {
                return null;
            }
            return tag;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.pending, soFarBytes
                    , totalBytes);
            tag.taskStatusTv.setText("Status: pending");
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.taskStatusTv.setText("Status: started");
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes
                    , totalBytes);
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes
                    , totalBytes);
            tag.taskStatusTv.setText("Status: connected");
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloaded();
            TaskManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
            tag.taskStatusTv.setText("Status: paused");
            TaskManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                    , task.getLargeFileTotalBytes());
            TaskManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };

    private View.OnClickListener taskActionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() == null) {
                return;
            }
            TaskItemViewHolder holder = (TaskItemViewHolder) v.getTag();
            CharSequence action = ((TextView) v).getText();
            if (action.equals("Pause")) {
                FileDownloader.getImpl().pause(holder.id);
            } else if (action.equals("Start")) {
                final TaskModel model = TaskManager.getImpl().get(holder.position);
                final BaseDownloadTask task = FileDownloader.getImpl().create(model.url)
                        .setPath(model.path)
                        .setCallbackProgressTimes(100)
                        .setListener(taskDownloadListener);
                TaskManager.getImpl().addTaskForViewHolder(task);
                TaskManager.getImpl().updateViewHolder(holder.id, holder);
                task.start();
            } else if (action.equals("Delete")) {
                new File(TaskManager.getImpl().get(holder.position).path).delete();
                holder.taskActionBtn.setEnabled(true);
                holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
            }
        }
    };

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liulishuo_download, parent, false);
        TaskItemViewHolder holder = new TaskItemViewHolder(view);
        holder.taskActionBtn.setOnClickListener(taskActionOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(TaskItemViewHolder holder, int position) {
        final TaskModel model = TaskManager.getImpl().get(position);
        holder.update(model.id, position);
        holder.taskActionBtn.setTag(holder);
        holder.taskNameTv.setText(model.name);
        TaskManager.getImpl().updateViewHolder(holder.id, holder);
        holder.taskActionBtn.setEnabled(true);

        if (TaskManager.getImpl().isReady()) {
            final int status = TaskManager.getImpl().getStatus(model.id, model.path);
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                holder.updateDownloading(status, TaskManager.getImpl().getSofar(model.id)
                        , TaskManager.getImpl().getTotal(model.id));
            } else if (!new File(model.path).exists() &&
                    !new File(FileDownloadUtils.getTempPath(model.path)).exists()) {
                // not exist file
                holder.updateNotDownloaded(status, 0, 0);
            } else if (TaskManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                holder.updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                holder.updateDownloading(status, TaskManager.getImpl().getSofar(model.id)
                        , TaskManager.getImpl().getTotal(model.id));
            } else {
                // not start
                holder.updateNotDownloaded(status, TaskManager.getImpl().getSofar(model.id)
                        , TaskManager.getImpl().getTotal(model.id));
            }
        } else {
            holder.taskStatusTv.setText("Status: loading...");
            holder.taskActionBtn.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return TaskManager.getImpl().getTaskCount();
    }

    public static class TaskItemViewHolder extends RecyclerView.ViewHolder {
        private TextView taskNameTv;
        private TextView taskStatusTv;
        private ProgressBar taskPb;
        private Button taskActionBtn;

        private int position;
        private int id;

        public TaskItemViewHolder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View itemView) {
            taskNameTv = (TextView) itemView.findViewById(R.id.task_name_tv);
            taskStatusTv = (TextView) itemView.findViewById(R.id.task_status_tv);
            taskPb = (ProgressBar) itemView.findViewById(R.id.task_pb);
            taskActionBtn = (Button) itemView.findViewById(R.id.task_action_btn);
        }

        public void update(final int id, final int position) {
            this.id = id;
            this.position = position;
        }

        public void updateDownloaded() {
            taskPb.setMax(1);
            taskPb.setProgress(1);
            taskStatusTv.setText("Status: completed");
            taskActionBtn.setText("Delete");
        }

        public void updateNotDownloaded(final int status, final long sofar, final long total) {
            if (sofar > 0 && total > 0) {
                final float percent = sofar
                        / (float) total;
                taskPb.setMax(100);
                taskPb.setProgress((int) (percent * 100));
            } else {
                taskPb.setMax(1);
                taskPb.setProgress(0);
            }

            switch (status) {
                case FileDownloadStatus.error:
                    taskStatusTv.setText("Status: error");
                    break;
                case FileDownloadStatus.paused:
                    taskStatusTv.setText("Status: paused");
                    break;
                default:
                    taskStatusTv.setText("Status: has not downloaded");
                    break;
            }
            taskActionBtn.setText("Start");
        }

        public void updateDownloading(final int status, final long sofar, final long total) {
            final float percent = sofar
                    / (float) total;
            taskPb.setMax(100);
            taskPb.setProgress((int) (percent * 100));

            switch (status) {
                case FileDownloadStatus.pending:
                    taskStatusTv.setText("Status: pending");
                    break;
                case FileDownloadStatus.started:
                    taskStatusTv.setText("Status: started");
                    break;
                case FileDownloadStatus.connected:
                    taskStatusTv.setText("Status: connected");
                    break;
                case FileDownloadStatus.progress:
                    taskStatusTv.setText("Status: progressing");
                    break;
                default:
                    taskStatusTv.setText("Status: downlonding" + status);
                    break;
            }

            taskActionBtn.setText("Pause");
        }
    }
}
