package com.kidbot.library.asyntask;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 任务处理器
 * User: YJX
 * Date: 2015-10-31
 * Time: 20:38
 */
public class TaskHandler extends Handler {

    public TaskHandler(Looper mainLooper) {
        super(mainLooper);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Task task= (Task) msg.obj;
        if (task==null)return;
        switch (msg.what){
            case TaskService.TASK_SUCCESS:
                task.getTaskEvent().success();
                break;
            case TaskService.TASK_FAILER:
                task.getTaskEvent().failer();
                break;
        }
    }
}
