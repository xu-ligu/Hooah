package com.xlg.vitamiodemo.adapter;

import android.content.Context;
import android.view.ViewGroup;


import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.xlg.vitamiodemo.mode.GanHuo;

public class MeiZhiAdapter extends RecyclerArrayAdapter<GanHuo.Result> {

    public MeiZhiAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeiZhiViewHolder(parent);
    }
}
