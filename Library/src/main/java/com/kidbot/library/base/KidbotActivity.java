package com.kidbot.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * 基础activity
 * User: YJX
 * Date: 2015-10-30
 * Time: 19:31
 */
public abstract class KidbotActivity extends AppCompatActivity {
    protected Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadData();
    }

    protected abstract int getLayoutId();
    protected  abstract void initView(Bundle savedInstanceState);


    /***
     * 从缓存载入数据
     * @return 是否载入成功
     */
    @CheckResult
    protected abstract boolean loadDataFromCache();
    /***
     * 从数据库载入数据
     * @return 是否载入成功
     */
    @CheckResult
    protected abstract boolean loadDataFromDb();
    /***
     * 从网络载入数据
     */
    protected abstract void loadDataFromNetwork();


    private void loadData(){
        if (!loadDataFromCache()) {
            if (!loadDataFromDb()) {
                loadDataFromNetwork();
            }
        }
    }

    /***
     * Activity 之间的跳转
     * @param cls 目标activity
     * @param isfinish 是否关闭当前 activity
     */
    protected void jumpAct(@NonNull Class cls ,@NonNull boolean isfinish){
        jumpAct(cls, isfinish,null);
    }

    /***
     * Activity 之间的跳转
     * @param cls 目标activity
     * @param isfinish 是否关闭当前 activity
     * @param bundle 需要传递的值
     */
    protected void jumpAct(@NonNull Class cls,@NonNull boolean isfinish,Bundle bundle){
        Intent intent=new Intent(this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (isfinish){
            this.finish();
        }
    }

    /***
     *  Activity 之间的跳转
     * @param cls 目标activity
     * @param requestCode
     */
    protected void jumpAct(@NonNull Class cls,@NonNull int requestCode){
        Intent intent=new Intent(this,cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * Fragment之间的切换
     * @param from 当前Fragment
     * @param to 目标Fragment
     * @param id 布局
     * @param value tag
     */
     protected void jumpFrag(Fragment from,@NonNull Fragment to,@android.support.annotation.IdRes int id,String value){
         FragmentManager mManager=getSupportFragmentManager();
         if(from==null){
           if(to.isAdded()){
               mManager.beginTransaction().show(to).commitAllowingStateLoss();
           }else{
               mManager.beginTransaction().add(id,to,value).commitAllowingStateLoss();
           }
         }else{
             if(to.isAdded()){
                 mManager.beginTransaction().hide(from).show(to).commitAllowingStateLoss();
             }else{
                 mManager.beginTransaction().hide(from).add(id,to,value).commitAllowingStateLoss();
             }
         }
     }


}
