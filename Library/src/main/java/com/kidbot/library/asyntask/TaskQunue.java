package com.kidbot.library.asyntask;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 任务队列
 * User: YJX
 * Date: 2015-10-30
 * Time: 23:17
 */
public class TaskQunue {
    private PriorityBlockingQueue<Task> taskQunue;
    public TaskQunue(){
        this.taskQunue=new PriorityBlockingQueue<Task>();
    }


    public void addTask(Task task){
        this.taskQunue.add(task);
    }

    public void removeTask(Task task){
        this.taskQunue.remove(task);
    }

    public void clearTask(){
        this.taskQunue.clear();
    }

    public PriorityBlockingQueue<Task> getTaskQunue() {
        return taskQunue;
    }

    public void setTaskQunue(PriorityBlockingQueue<Task> taskQunue) {
        this.taskQunue = taskQunue;
    }

    public Task getTask(int id){
        Task task=null;
        for (Task temp:taskQunue){
            if (temp.getTaskid()==id){
                task=temp;
                break;
            }
        }
        return task;
    }
}
