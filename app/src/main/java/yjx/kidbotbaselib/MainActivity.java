package yjx.kidbotbaselib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kidbot.library.Log.L;
import com.kidbot.library.asyntask.Task;
import com.kidbot.library.asyntask.TaskEvent;
import com.kidbot.library.asyntask.TaskRunTime;
import com.kidbot.library.asyntask.TaskType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       final Task task=new Task.Builder().setTaskId(1).setIsBrocast(true).
                setTaskDelayTime(5000).setTaskType(TaskType.THREAD_BACKGROUND).setTaskIsRepeat(false).
                setTaskRunTime(TaskRunTime.TASK_ALL).setTaskEvent(new TaskEvent() {
            @Override
            protected boolean run() {
                Log.e(getPackageName(),"测试一下");
                return false;
            }

            @Override
            protected void success() {
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void failer() {
                Toast.makeText(MainActivity.this,"Failer",Toast.LENGTH_SHORT).show();
            }
        }).build();
       final Task task2=new Task.Builder().setTaskId(2).setIsBrocast(true).
                setTaskDelayTime(0).setTaskType(TaskType.THREAD_MAIN_UI).setTaskIsRepeat(false).
                setTaskRunTime(TaskRunTime.TASK_ALL).setTaskEvent(new TaskEvent() {
            @Override
            protected boolean run() {
                Log.e(getPackageName(), "测试一下2");
                return true;
            }

            @Override
            protected void success() {
                Toast.makeText(MainActivity.this,"Success2",Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void failer() {
                Toast.makeText(MainActivity.this,"Failer2",Toast.LENGTH_SHORT).show();
            }
        }).build();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TaskManager.getInstance(getApplicationContext()).addTask(task);
//                TaskManager.getInstance(getApplicationContext()).addTask(task2);
                Person p=new Person(22,"yjx");
//                L.obj(p);
                L.json("{'a':'b','c':{'aa':234,'dd':{'az':12}}}");
            }
        });
        findViewById(R.id.btn_add2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TaskManager.getInstance(getApplicationContext()).addTask(task);
//                TaskManager.getInstance(getApplicationContext()).addTask(task2);
            }
        });
        Intent intent=new Intent(this,KidbotHttpServer.class);
        this.startService(intent);
    }

}
