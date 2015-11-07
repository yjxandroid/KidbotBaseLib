package com.kidbot.library.asyntask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.kidbot.library.utils.NetUtils;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务服务
 * User: YJX
 * Date: 2015-11-02
 * Time: 09:53
 */
public final class TaskService extends Service {
    public static final int TASK_SUCCESS=1;
    public static final int TASK_FAILER=-1;

    public static final String STATUS_CHANGED = "phone_net_status_changed";
    public static final String TASK_ACTION="com.kidbot.task.action";
    public static final String TASKID = "taskid";
    private AlarmManager alarmManager;
    private Calendar calendar;
    private TaskQunue mTaskQunue;
    private ExecutorService executorService;
    private TaskReceiver taskReceiver;
    public static int THREADPOOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, TaskService.class);
        return intent;
    }

    public static Intent startIntent(Context context, boolean flag) {
        Intent intent = new Intent(context, TaskService.class);
        intent.putExtra(STATUS_CHANGED, flag);
        return intent;
    }

    public static Intent startIntent(Context context, int flag) {
        Intent intent = new Intent(context, TaskService.class);
        intent.putExtra(TASKID, flag);
        return intent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new TaskBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mTaskQunue = new TaskQunue();
        this.executorService = Executors.newFixedThreadPool(THREADPOOL_SIZE);
        this.alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(System.currentTimeMillis());
        taskReceiver = new TaskReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(TASK_ACTION);
        this.registerReceiver(taskReceiver, filter);

        for (Task task:TaskManager.getInstance(this).getmTaskQunue().getTaskQunue()){
            this.mTaskQunue.addTask(task);
            excuteTask(task);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null)
            return START_STICKY;
        if (intent.hasExtra(STATUS_CHANGED)) {
            boolean flag = intent.getBooleanExtra(STATUS_CHANGED, false);
            if (flag) {
                if (NetUtils.isNet3G(this)) {
                    for (Task task :mTaskQunue.getTaskQunue()){
                        if (task.getTaskruntime()==TaskRunTime.TASK_3G){
                                excuteTask(task);
                        }
                    }
                }
                if (NetUtils.isNetWifi(this)) {
                    for (Task task :mTaskQunue.getTaskQunue()){
                        if (task.getTaskruntime()==TaskRunTime.TASK_WIFI){
                            excuteTask(task);
                        }
                    }
                }
            }
        }
        if (intent.hasExtra(TASKID)) {
            final int taskid = intent.getIntExtra(TASKID,-1);
            if (taskid==-1){
                return START_STICKY;
            }
            final  Task task=this.mTaskQunue.getTask(taskid);
            if (task==null){
                return START_STICKY;
            }
            switch (task.getTasktype()){
                case TaskType.THREAD_BACKGROUND:
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            if(task.getTaskEvent().run()){
                                if (task.isbrocast()) {
//                                    task.getTaskEvent().success();
                                    Message msg=TaskManager.getInstance(TaskService.this).getTaskHandler().obtainMessage();
                                    msg.what=TASK_SUCCESS;
                                    msg.obj=task;
                                    TaskManager.getInstance(TaskService.this).getTaskHandler().sendMessage(msg);
                                }
                            }else{
                                if (task.isbrocast()) {
//                                    task.getTaskEvent().failer();
                                    Message msg=TaskManager.getInstance(TaskService.this).getTaskHandler().obtainMessage();
                                    msg.what=TASK_FAILER;
                                    msg.obj=task;
                                    TaskManager.getInstance(TaskService.this).getTaskHandler().sendMessage(msg);
                                }

                            }
                            if (!task.isrepeat()) {
                                if (task.getPendingIntent()!=null){
                                    alarmManager.cancel(task.getPendingIntent());
                                }
                                mTaskQunue.removeTask(task);
                            }

                        }
                    });
                    break;
                case TaskType.THREAD_MAIN_UI:
                    if(task.getTaskEvent().run()){
                        if (task.isbrocast()) {
                            task.getTaskEvent().success();
                        }
                    }else{
                        if (task.isbrocast()) {
                            task.getTaskEvent().failer();
                        }

                    }
                    if (!task.isrepeat()) {
                        mTaskQunue.removeTask(task);
                        if (task.getPendingIntent()!=null){
                            this.alarmManager.cancel(task.getPendingIntent());
                        }
                    }
                    break;
            }
        }

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (taskReceiver != null) {
            unregisterReceiver(taskReceiver);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * 添加任务并执行
     * @param task 任务
     */
    public void addTask(final Task task) {
        boolean isrepeat=false;
        for (Task temp:mTaskQunue.getTaskQunue()){
            if (task.getTaskid()==temp.getTaskid()){
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
        this.mTaskQunue.addTask(task);
        excuteTask(task);
    }

    /**
     * 任务执行器
     * @param task 任务
     */
    private void excuteTask(final Task task) {
        //立即执行
        if (task.getDelaytime() <= 0 && task.getTaskruntime() == TaskRunTime.TASK_ALL && !task.isrepeat()) {
            switch (task.getTasktype()){
                case TaskType.THREAD_BACKGROUND:
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            if(task.getTaskEvent().run()){
                                if (task.isbrocast()) {
//                                    task.getTaskEvent().success();

                                    Message msg=TaskManager.getInstance(TaskService.this).getTaskHandler().obtainMessage();
                                    msg.what=TASK_SUCCESS;
                                    msg.obj=task;
                                    TaskManager.getInstance(TaskService.this).getTaskHandler().sendMessage(msg);
                                }
                            }else{
                                if (task.isbrocast()) {
//                                    task.getTaskEvent().failer();
                                    Message msg=TaskManager.getInstance(TaskService.this).getTaskHandler().obtainMessage();
                                    msg.what=TASK_FAILER;
                                    msg.obj=task;
                                    TaskManager.getInstance(TaskService.this).getTaskHandler().sendMessage(msg);
                                }

                            }
                            if (!task.isrepeat()) {
                                mTaskQunue.removeTask(task);
                                if (task.getPendingIntent()!=null){
                                    alarmManager.cancel(task.getPendingIntent());
                                }
                            }
                        }
                    });
                    break;
                case TaskType.THREAD_MAIN_UI:
                    if(task.getTaskEvent().run()){
                        if (task.isbrocast()) {
                            task.getTaskEvent().success();
                        }
                    }else{
                        if (task.isbrocast()) {
                            task.getTaskEvent().failer();
                        }

                    }

                    if (!task.isrepeat()) {
                        mTaskQunue.removeTask(task);
                        if (task.getPendingIntent() != null){
                            this.alarmManager.cancel(task.getPendingIntent());
                        }
                    }
                    break;
            }
        }
        //立即执行且重复
        if (task.getDelaytime() <= 0 && task.getTaskruntime() == TaskRunTime.TASK_ALL && task.isrepeat()) {
            Intent intent = new Intent(TASK_ACTION);
            intent.putExtra(TASKID, task.getTaskid());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 200, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            task.setPendingIntent(pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), task.getRepeattime(), pendingIntent);
        }

        //延后执行
        if (task.getTaskruntime() == TaskRunTime.TASK_ALL && task.getDelaytime() > 0) {
            Intent intent = new Intent(TASK_ACTION);
            intent.putExtra(TASKID, task.getTaskid());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 200, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            task.setPendingIntent(pendingIntent);
            long time=System.currentTimeMillis()+task.getDelaytime();
            this.alarmManager.cancel(pendingIntent);
            if (Build.VERSION.SDK_INT >= 19) {
                if (task.isrepeat()) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, task.getRepeattime(), pendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
            } else {
                if (task.isrepeat()) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, task.getRepeattime(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
            }
        }
    }

    /***
     * 停止所有的任务
     */
    public void cancleAllTask() {
        for (Task task : this.mTaskQunue.getTaskQunue()) {
            if (task.getPendingIntent() != null) {
                this.alarmManager.cancel(task.getPendingIntent());
            }
        }
    }

    /**
     * 停止任务
     */
    public void cancleTask(Task task) {
        if (task.getPendingIntent() != null) {
            this.alarmManager.cancel(task.getPendingIntent());
        }
    }

    /**
     * binder 用来调用 Service内部的方法
     */
    public  class TaskBinder extends Binder {

        public void schedule(Task task) {
                addTask(task);
        }

        public void removeTask(Task task) {
cancleTask(task);
        }

        public void cancleAll() {
            cancleAllTask();
        }

    }


}
