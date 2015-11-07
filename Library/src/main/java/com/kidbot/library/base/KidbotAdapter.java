package com.kidbot.library.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用的ListView GridView 适配器
 * User: YJX
 * Date: 2015-11-02
 * Time: 17:43
 */
public abstract class KidbotAdapter<T> extends BaseAdapter {
    protected List<T> mLists;
    protected Context context;
    protected LayoutInflater inflater;
    protected int layoutId;

    public KidbotAdapter(List<T> mLists, Context context, int layoutId) {
        this.mLists = mLists;
        this.context = context;
        this.inflater=LayoutInflater.from(this.context);
        this.layoutId = layoutId;
    }

    public KidbotAdapter(List<T> mLists, Context context) {
        this.mLists = mLists;
        this.context = context;

    }

    @Override
    public int getCount() {
        if (mLists==null){
            return 0;
        }
        return mLists.size();
    }

    @Override
    public T getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KidbotViewHolder viewHolder=KidbotViewHolder.get(context,convertView,parent,layoutId);
        onItemView(viewHolder,mLists.get(position));
        return viewHolder.getConvertView();
    }

    public abstract void onItemView(KidbotViewHolder holder,T item);


}
