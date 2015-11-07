package com.kidbot.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基础Fragment
 * User: YJX
 * Date: 2015-10-30
 * Time: 18:54
 */
public abstract class KidbotFragment extends Fragment {
    protected Context frmContext;
    protected  boolean isshowview=false;
    protected  boolean isprepared=false;
    protected  View rootView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        frmContext=getActivity();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getUserVisibleHint()){
            this.isshowview=true;
            this.loadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView=inflater.inflate(getLayoutId(),container,false);
        this.initView();
        isprepared=true;
        loadData();
        return this.rootView;
    }

    /**
     * 得到布局ID
     * @return 布局id
     */
    public abstract int getLayoutId();

    /**
     * 初始化各个控件
     */
    public abstract void initView();

    /***
     * 从缓存载入数据
     * @return 是否载入成功
     */
    @CheckResult
    public abstract boolean loadDataFromCache();
    /***
     * 从数据库载入数据
     * @return 是否载入成功
     */
    @CheckResult
    public abstract boolean loadDataFromDb();
    /***
     * 从网络载入数据
     */
    public abstract void loadDataFromNetwork();

    /***
     * 初始化控件
     * @param id 控件的id
     * @param <T> 控件
     * @return 控件
     */
    protected  <T extends  View> T findView(@android.support.annotation.IdRes int id){
        if (this.rootView==null){
            try {
                throw  new Exception("the rootView is null.should set it");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        T t= (T) this.rootView.findViewById(id);
        return t;
    }

    /**
     * 载入数据
     */
    private void loadData() {
        if (isprepared && isshowview) {
            if (!loadDataFromCache()) {
                if (!loadDataFromDb()) {
                    loadDataFromNetwork();
                }
            }
        }
    }
}
