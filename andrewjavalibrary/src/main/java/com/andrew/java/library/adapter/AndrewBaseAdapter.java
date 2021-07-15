package com.andrew.java.library.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:32
 * description:
 */
public abstract class AndrewBaseAdapter<T> extends BaseAdapter {
    private List<T> arrayList = new ArrayList<>();
    protected int clickPosition = -1;

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public T getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void clear() {
        arrayList.clear();
    }

    public void add(T t) {
        arrayList.add(t);
    }

    public void addNotify(T t) {
        arrayList.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list, boolean clearOldData) {
        if (list != null) {
            if (clearOldData) {
                arrayList.clear();
            }
            arrayList.addAll(list);
        }
    }

    public void addAllNotify(List<T> list, boolean clearOldData) {
        if (list != null) {
            if (clearOldData) {
                arrayList.clear();
            }
            arrayList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public boolean addAllHead(List<T> list) {
        if (list != null) {
            return arrayList.addAll(0, list);
        }
        return false;
    }

    public void deleteNotify() {
        if (clickPosition > -1 && clickPosition < getCount()) {
            arrayList.remove(clickPosition);
            clickPosition = -1;
            notifyDataSetChanged();
        } else {
            Logger.e("删除失败clickPosition=" + clickPosition);
        }
    }

}
