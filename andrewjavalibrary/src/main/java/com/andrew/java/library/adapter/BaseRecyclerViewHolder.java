package com.andrew.java.library.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:43
 * description:
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    ViewDataBinding mBinding;

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public BaseRecyclerViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
}
