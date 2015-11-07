package com.kidbot.library.base;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用的 ListView 和 Gridview 的 ViewHolder
 * User: YJX
 * Date: 2015-11-03
 * Time: 10:28
 */
public class KidbotViewHolder {
    private SparseArray<View> mViews;
    private LayoutInflater inflater;
    protected View convertView;
    public KidbotViewHolder(Context context, View convertView,ViewGroup parent,int layoutID) {
        this.mViews=new SparseArray<View>();
        this.inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(layoutID, parent, false);
        convertView.setTag(this);
        this.convertView=convertView;
    }

    public static KidbotViewHolder get(Context context, View convertView,ViewGroup parent,@LayoutRes int layoutID){
        if (convertView==null){
            return new KidbotViewHolder(context, convertView, parent, layoutID);
        }
        return (KidbotViewHolder) convertView.getTag();
    }

    public <T extends View> T findView(@android.support.annotation.IdRes int id){
        T t= (T) mViews.get(id);
        if (t!=null){
            return t;
        }
         t= (T) this.convertView.findViewById(id);
        mViews.put(id,t);
        return t;
    }

    public void setTextViewContent(@IdRes int id,String content){
        TextView textView=findView(id);
        textView.setText(content);
    }
    public void setTextViewContent(@IdRes int id,@android.support.annotation.StringRes int strid){
        TextView textView=findView(id);
        textView.setText(strid);
    }

    public void setImageViewSrc(@IdRes int id,@DrawableRes int drawres){
        ImageView iv=findView(id);
        iv.setImageResource(drawres);
    }



    public View getConvertView() {
        return convertView;
    }
}
