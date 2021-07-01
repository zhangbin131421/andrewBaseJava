package com.andrew.java.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.java.library.listener.MyOnClickListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:46
 * description:
 */
public abstract class AndrewRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    abstract int layoutId();

    protected abstract void mOnBindViewHolder(BaseRecyclerViewHolder holder, int position, T t);

    private Context mContext;

    private List<T> arrayList = new ArrayList<>();
    protected int clickPosition = -1;

    MyOnClickListener<T> mItemClickListener;
    MyOnClickListener<T> mItemLongClickListener;

    public AndrewRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, final int position) {
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

    T getItem(int position) {
        return arrayList.get(position);
    }

    void clear() {
        arrayList.clear();
    }

    void clearNotify() {
        arrayList.clear();
        notifyDataSetChanged();

    }

    void add(T t) {
        arrayList.add(t);
    }

    void addNotify(T t) {
        arrayList.add(t);
        notifyDataSetChanged();
    }

    void addAll(List<T> list, boolean clearOldData) {
        if (list != null) {
            if (clearOldData) {
                arrayList.clear();
            }
            arrayList.addAll(list);
        }
    }

    void addAllNotify(List<T> list, boolean clearOldData) {
        if (list != null) {
            if (clearOldData) {
                arrayList.clear();
            }
            arrayList.addAll(list);
            notifyDataSetChanged();
        }
    }

    boolean addAllHead(List<T> list) {
        if (list != null) {
            return arrayList.addAll(0, list);
        }
        return false;
    }

    void deleteNotify() {
        if (clickPosition > -1 && clickPosition < getItemCount()) {
            arrayList.remove(clickPosition);
            clickPosition = -1;
            notifyDataSetChanged();
        } else {
            Logger.e("删除失败clickPosition=" + clickPosition);
        }
    }

    void updatePosition(int position, T t) {
        arrayList.set(position, t);
    }

    void updatePositionNotify(int position, T t) {
        arrayList.set(position, t);
        notifyDataSetChanged();
    }
}
