package com.andrew.java.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrew.java.library.listener.AndrewClickListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:46
 * description:
 *
 * @author Andrew
 */
public abstract class AndrewRecyclerViewAdapter<T> extends RecyclerView.Adapter<AndrewRecyclerViewHolder> {

    protected abstract int layoutId();

    protected abstract void mOnBindViewHolder(AndrewRecyclerViewHolder holder, int position, T t);

    protected Context mContext;

    protected List<T> arrayList = new ArrayList<>();
    protected int clickPosition = -1;

    private AndrewClickListener<T> mItemClickListener;
    private AndrewClickListener<T> mItemLongClickListener;

    public AndrewRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public AndrewRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AndrewRecyclerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AndrewRecyclerViewHolder holder, final int position) {
        final T t = arrayList.get(position);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPosition = position;
                    if (mItemClickListener != null) {
                        mItemClickListener.onClick(t);
                    }
                }
            });
        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickPosition = position;
                    if (mItemLongClickListener != null) {
                        mItemLongClickListener.onClick(t);
                    }
                    return true;
                }
            });
        }
        mOnBindViewHolder(holder, position, t);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public T getItem(int position) {
        return arrayList.get(position);
    }

    public void mNotifyDataSetChanged() {
        notifyDataSetChanged();
    }

    public void mNotifyItemChanged(int position) {
        notifyItemChanged(position);
    }

    public void mNotifyItemChanged(int position, @Nullable Object payload) {
        notifyItemChanged(position, payload);
    }

    public void clear() {
        arrayList.clear();
    }

    public void clearNotify() {
        arrayList.clear();
        mNotifyDataSetChanged();
    }

    public void add(T t) {
        arrayList.add(t);
    }

    public void addNotify(T t) {
        add(t);
        mNotifyDataSetChanged();
    }

    public void addHead(T t) {
        arrayList.add(0, t);
    }

    public void addHeadNotify(T t) {
        addHead(t);
        mNotifyDataSetChanged();
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
        addAll(list, clearOldData);
        mNotifyDataSetChanged();
    }

    public boolean addAllHead(List<T> list) {
        if (list != null) {
            return arrayList.addAll(0, list);
        }
        return false;
    }

    public boolean deleteNotify() {
        if (clickPosition > -1 && clickPosition < getItemCount()) {
            arrayList.remove(clickPosition);
            clickPosition = -1;
            mNotifyDataSetChanged();
            return true;
        } else {
            Logger.e("删除失败clickPosition=" + clickPosition);
            return false;
        }
    }

    public void updatePosition(int position, T t) {
        arrayList.set(position, t);
    }

    public void updatePositionNotify(int position, T t) {
        arrayList.set(position, t);
        mNotifyDataSetChanged();
    }

    public void setItemClickListener(AndrewClickListener<T> mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setItemLongClickListener(AndrewClickListener<T> mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }
}
