package com.kidbot.library.asyntask;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 任务管理器
 * User: YJX
 * Date: 2015-10-30
 * Time: 21:49
 */
public  final class TaskManager {

    private static TaskManager instance;
    //任务执行器
    private Context mContext;
    private TaskQunue mTaskQunue;

    private TaskService.TaskBinder mTaskBinder;
    private TaskHandler taskHandler;
    private TaskManager(Context context){
        this.mContext=context;
        taskHandler=new TaskHandler(context.getMainLooper());
        System.out.print(0);
        mTaskQunue=new TaskQunue();
        context.getApplicationContext().bindService(TaskService.startIntent(this.mContext), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                    TaskManager.this.mTaskBinder= (TaskService.TaskBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                TaskManager.this.mTaskBinder=null;

            }
        },Context.BIND_AUTO_CREATE);
    }
    //单例模式
    public static TaskManager getInstance(Context context){
        if (instance==null){
            synchronized (TaskManager.class){
                if (instance==null){
                    instance=new TaskManager(context);
                }
            }
        }
        return instance;
    }


    public void addTask(Task task){
        if (task==null){
            return;
        }
        boolean isrepeat=false;
        if (mTaskBinder==null)
        {   for (Task temp:mTaskQunue.getTaskQunue()){
                if (temp.getTaskid()==task.getTaskid()){
                    isrepeat=true;
                    break;
                }
            }
            if (isrepeat){
                try {
                    throw  new Exception("taskid do not repeat");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            mTaskQunue.addTask(task);
        }else {
            mTaskBinder.schedule(task);
        }
    }

    public TaskQunue getmTaskQunue() {
        return mTaskQunue;
    }

    public TaskHandler getTaskHandler() {
        return taskHandler;
    }


    public void cancleTask(Task task){
        if (mTaskBinder!=null){
            mTaskBinder.removeTask(task);
        }
    }
}
