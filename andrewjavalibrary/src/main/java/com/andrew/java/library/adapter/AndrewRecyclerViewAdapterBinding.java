package com.andrew.java.library.adapter;

import android.content.Context;

import com.andrew.java.library.BR;

/**
 * author: zhangbin
 * created on: 2021/6/29 15:03
 * description:
 * 参考 binding
 */
public abstract class AndrewRecyclerViewAdapterBinding<T> extends AndrewRecyclerViewAdapter<T> {
    public AndrewRecyclerViewAdapterBinding(Context context) {
        super(context);
    }

    @Override
    protected void mOnBindViewHolder(AndrewRecyclerViewHolder holder, int position, T t) {
        holder.mBinding.setVariable(BR.item, t);
        holder.mBinding.executePendingBindings();
    }
}
