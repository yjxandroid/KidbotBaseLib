package com.kidbot.library.asyntask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * User: YJX
 * Date: 2015-11-02
 * Time: 13:38
 */
public class TaskReceiver extends BroadcastReceiver {
    public static final String TASK_ACTION="com.kidbot.task.action";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            context.startService(TaskService.startIntent(context,true));
        }
        if (TASK_ACTION.equals(action)){
            int taskid=intent.getIntExtra(TaskService.TASKID,-1);
            System.out.print(taskid);
            context.startService(TaskService.startIntent(context,taskid));
        }
    }
}
