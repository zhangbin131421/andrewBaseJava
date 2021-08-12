package com.example.demojava.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andrew.java.library.adapter.AndrewRecyclerViewAdapterBinding;
import com.andrew.java.library.adapter.AndrewRecyclerViewHolder;
import com.example.demojava.R;
import com.orhanobut.logger.Logger;

/**
 * author: zhangbin
 * created on: 2021/7/15 15:10
 * description:
 */
public class PageAdapter extends AndrewRecyclerViewAdapterBinding<Integer> {
    private int maxItem = 8;

    private int clickTag = 1;
    private int tempValue = 0;

    public PageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_page;
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount() > 8) {
            return maxItem;
        }
        return super.getItemCount();
    }

    @Override
    protected void mOnBindViewHolder(AndrewRecyclerViewHolder holder, int currentPosition, Integer itemValue) {
        super.mOnBindViewHolder(holder, currentPosition, itemValue);
        TextView tvPageNumber = holder.itemView.findViewById(R.id.tvPageNumber);
        int maxValue = arrayList.get(arrayList.size() - 1);
        if (arrayList.size() > getItemCount()) {
            if (getItemCount() == 9) {
                switch (currentPosition) {
                    case 0:
                        tvPageNumber.setText("1");
                        tvPageNumber.setTag(1);
                        break;
                    case 1: {
                        int newValue = clickTag - 3;
                        tvPageNumber.setTag(newValue);
                        tvPageNumber.setText("..");
                    }
                    break;
                    case 2: {
                        int newValue = clickTag - 2;
                        tvPageNumber.setTag(newValue);
                        tvPageNumber.setText(String.valueOf(newValue));
                    }
                    break;
                    case 3: {
                        int newValue = clickTag - 1;
                        tvPageNumber.setTag(newValue);
                        tvPageNumber.setText(String.valueOf(newValue));
                    }
                    break;
                    case 4:
                        tvPageNumber.setTag(clickTag);
                        tvPageNumber.setText(String.valueOf(clickTag));
                        break;
                    case 5: {
                        int newValue = clickTag + 1;
                        tvPageNumber.setTag(newValue);
                        tvPageNumber.setText(String.valueOf(newValue));
                    }
                    break;
                    case 6: {
                        {
                            int newValue = clickTag + 2;
                            tvPageNumber.setTag(newValue);
                            tvPageNumber.setText(String.valueOf(newValue));
                        }
                    }
                    break;
                    case 7: {
                        int newValue = clickTag + 3;
                        tvPageNumber.setTag(newValue);
                    }
                    tvPageNumber.setText("..");
                    break;
                    case 8:
                        int max = arrayList.get(arrayList.size() - 1);
                        tvPageNumber.setText(String.valueOf(max));
                        tvPageNumber.setTag(max);
                        break;
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int clickValue = (int) tvPageNumber.getTag();
                        Logger.e("currentPosition = " + currentPosition + " clickValue=" + clickValue);
                        if (clickValue > 4 && clickValue < (maxValue - 3)) {
                        } else {
                            if (clickValue >= (maxValue - 3)) {
                                tempValue = maxValue;
                            }
                            maxItem = 8;
                        }
                        clickTag = clickValue;
                        notifyDataSetChanged();
                    }
                });


            } else if (getItemCount() == 8) {
                switch (currentPosition) {
                    case 0:
                        tvPageNumber.setText(String.valueOf(itemValue));
                        tvPageNumber.setTag(1);
                        break;
                    case 1: {
                        int newValue = itemValue;
                        if (tempValue == maxValue) {
                            newValue = tempValue - (maxItem - currentPosition - 1);
                            tvPageNumber.setText("..");
                        } else {
                            tvPageNumber.setText(String.valueOf(itemValue));
                        }
                        tvPageNumber.setTag(newValue);
                    }
                    break;
                    case 2:
                    case 3:
                    case 4:
                    case 5: {
                        int newValue = itemValue;
                        if (tempValue == maxValue) {
                            newValue = tempValue - (maxItem - currentPosition - 1);
                        }
                        tvPageNumber.setText(String.valueOf(newValue));
                        tvPageNumber.setTag(newValue);
                    }
                    break;

                    case 6:
                        int newValue = itemValue;
                        if (tempValue == maxValue) {
                            newValue = tempValue - (maxItem - currentPosition - 1);
                            tvPageNumber.setText(String.valueOf(newValue));
                        } else {
                            tvPageNumber.setText("..");
                        }
                        tvPageNumber.setTag(newValue);
                        break;
                    case 7:
                        tvPageNumber.setText(String.valueOf(maxValue));
                        tvPageNumber.setTag(maxValue);
                        break;
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Logger.e("currentPosition = " + currentPosition + " tag=" + tvPageNumber.getTag());
                        int clickValue = (int) tvPageNumber.getTag();
                        if (clickValue < 5) {
                            tempValue = 0;
                        } else {
                            if (clickValue < 8) {
                                maxItem = 9;
                                tempValue = clickTag - clickValue;
                            } else {
                                if (maxValue - clickValue > 3) {
                                    if (clickValue != maxValue) {
                                        maxItem = 9;
                                    }
                                    tempValue = clickTag - clickValue;
                                } else {
                                    tempValue = maxValue;
                                }
                            }
                        }
                        clickTag = clickValue;
                        notifyDataSetChanged();
                    }
                });
            } else {
                tvPageNumber.setTag(itemValue);
            }
        } else {
            tvPageNumber.setText(String.valueOf(itemValue));
            tvPageNumber.setTag(itemValue);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTag = (int) tvPageNumber.getTag();
                    notifyDataSetChanged();
                }
            });
        }

        int currentTag = (int) tvPageNumber.getTag();
        if (clickTag == currentTag) {
            tvPageNumber.setSelected(true);
        } else {
            tvPageNumber.setSelected(false);
        }
    }

    private void click(int tag) {
        int maxValue = arrayList.get(arrayList.size() - 1);
        if (arrayList.size() > getItemCount()) {
            if (getItemCount() == 9) {
                if (tag > 4 && tag < (maxValue - 3)) {
                } else {
                    if (tag >= (maxValue - 3)) {
                        tempValue = maxValue;
                    }
                    maxItem = 8;
                }
            } else {
                if (tag < 5) {
                    tempValue = 0;
                } else {
                    if (tag < 8) {
                        maxItem = 9;
                        tempValue = clickTag - tag;
                    } else {
                        if (maxValue - tag > 3) {
                            if (tag != maxValue) {
                                maxItem = 9;
                            }
                            tempValue = clickTag - tag;
                        } else {
                            tempValue = maxValue;
                        }
                    }
                }
            }
        }
        clickTag = tag;
        notifyDataSetChanged();
    }

    public void lastPage() {
        if (clickTag != 1) {
            int tag = clickTag - 1;
            click(tag);
        }

    }

    public void nextPage() {
        if (clickTag != arrayList.get(arrayList.size() - 1)) {
            int tag = clickTag + 1;
            click(tag);
        }
    }

}
