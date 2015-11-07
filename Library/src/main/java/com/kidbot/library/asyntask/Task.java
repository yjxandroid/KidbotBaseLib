package com.kidbot.library.asyntask;

import android.app.PendingIntent;

import java.io.Serializable;

/**
 * User: YJX
 * Date: 2015-10-30
 * Time: 21:47
 * 任务基本类
 */
public  class Task implements Serializable,Comparable{
    //任务类型
    private int tasktype;
    //任务id
    private int taskid;
    //任务简要描述
    private String taskdes;
    //任务完成是否广播通知
    private boolean isbrocast;
    //任务时间
    private int taskruntime=TaskRunTime.TASK_ALL;
    //是否重复做某件事
    private boolean isrepeat;
    private int repeattime;
    private PendingIntent pendingIntent;
    private TaskEvent taskEvent;
    //执行时间
    private int delaytime=-1;
    /**
     * 任务标签
     */
    private String tag;

    public Task(int tasktype, int taskid, String taskdes, boolean isbrocast, int taskruntime, boolean isrepeat, int repeattime, TaskEvent taskEvent, int delaytime, String tag) {
        this.tasktype = tasktype;
        this.taskid = taskid;
        this.taskdes = taskdes;
        this.isbrocast = isbrocast;
        this.taskruntime = taskruntime;
        this.isrepeat = isrepeat;
        this.repeattime = repeattime;
        this.taskEvent = taskEvent;
        this.delaytime = delaytime;
        this.tag = tag;
    }

    public Task(int tasktype, int taskid, String taskdes, boolean isbrocast, int taskruntime, TaskEvent taskEvent, int delaytime, String tag) {

        this.tasktype = tasktype;
        this.taskid = taskid;
        this.taskdes = taskdes;
        this.isbrocast = isbrocast;
        this.taskruntime = taskruntime;
        this.taskEvent = taskEvent;
        this.delaytime = delaytime;
        this.tag = tag;
    }

    public Task(int tasktype, int taskid, String taskdes, boolean isbrocast, int taskruntime, boolean isrepeat, TaskEvent taskEvent, int delaytime, String tag) {
        this.tasktype = tasktype;
        this.taskid = taskid;
        this.taskdes = taskdes;
        this.isbrocast = isbrocast;
        this.taskruntime = taskruntime;
        this.isrepeat = isrepeat;
        this.taskEvent = taskEvent;
        this.delaytime = delaytime;
        this.tag = tag;
    }



    public Task( int taskid, int tasktype, String taskdes, boolean isbrocast,int delaytime, TaskEvent taskEvent) {
        this.tasktype = tasktype;
        this.taskid = taskid;
        this.taskdes = taskdes;
        this.isbrocast = isbrocast;
        this.taskEvent = taskEvent;
        this.delaytime=delaytime;
        this.tag="defalut";
    }

    public int getDelaytime() {
        return delaytime;
    }

    public void setDelaytime(int delaytime) {
        this.delaytime = delaytime;
    }

    public int getTasktype() {
        return tasktype;
    }

    public void setTasktype(int tasktype) {
        this.tasktype = tasktype;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getTaskdes() {
        return taskdes;
    }

    public void setTaskdes(String taskdes) {
        this.taskdes = taskdes;
    }

    public boolean isbrocast() {
        return isbrocast;
    }

    public void setIsbrocast(boolean isbrocast) {
        this.isbrocast = isbrocast;
    }

    public TaskEvent getTaskEvent() {
        return taskEvent;
    }

    public void setTaskEvent(TaskEvent taskEvent) {
        this.taskEvent = taskEvent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public int getTaskruntime() {
        return taskruntime;
    }

    public void setTaskruntime(int taskruntime) {
        this.taskruntime = taskruntime;
    }

    public Task() {
    }

    public boolean isrepeat() {
        return isrepeat;
    }

    public void setIsrepeat(boolean isrepeat) {
        this.isrepeat = isrepeat;
    }

    public int getRepeattime() {
        return repeattime;
    }

    public void setRepeattime(int repeattime) {
        this.repeattime = repeattime;
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }


    public static class Builder {
        Task task;
        public Builder() {
            task=new Task();
        }

        public Builder setTaskId(int id){
            task.setTaskid(id);
            return this;
        }
        public Builder setTaskType(int type){
            task.setTasktype(type);
            return this;
        }
        public Builder setTaskDes(String des){
            task.setTaskdes(des);
            return this;
        }
        public Builder setTaskRunTime(int runtime){
            task.setTaskruntime(runtime);
            return this;
        }
        public Builder setTaskEvent(TaskEvent event){
            task.setTaskEvent(event);
            return this;
        }
        public Builder setTaskRepeatTime(int repeattime){
            task.setRepeattime(repeattime);
            return this;
        }
        public Builder setTaskIsRepeat(boolean isrepeat){
            task.setIsrepeat(isrepeat);
            return this;
        }
        public Builder setIsBrocast(boolean isbrocast){
            task.setIsbrocast(isbrocast);
            return this;
        }
        public Builder setTaskPenddingIntent(PendingIntent intent){
            task.setPendingIntent(intent);
            return this;
        }
        public Builder setTaskTag(String tag){
            task.setTag(tag);
            return this;
        }

        public Builder setTaskDelayTime(int delaytime){
            task.setDelaytime(delaytime);
            return this;
        }
        public Task build(){
            return task;
        }
    }
}
