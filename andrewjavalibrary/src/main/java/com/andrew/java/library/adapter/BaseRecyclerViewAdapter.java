package com.andrew.java.library.adapter;

import android.content.Context;

import com.andrew.java.library.BR;

/**
 * author: zhangbin
 * created on: 2021/6/29 15:03
 * description:
 */
public abstract class BaseRecyclerViewAdapter<T> extends AndrewRecyclerViewAdapter<T> {
    public BaseRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected void mOnBindViewHolder(BaseRecyclerViewHolder holder, int position, T t) {
        holder.mBinding.setVariable(BR._all, t);
        holder.mBinding.executePendingBindings();
    }
}
