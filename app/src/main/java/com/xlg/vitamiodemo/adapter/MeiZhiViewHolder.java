package com.xlg.vitamiodemo.adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.xlg.vitamiodemo.R;
import com.xlg.vitamiodemo.mode.GanHuo;

public class MeiZhiViewHolder extends BaseViewHolder<GanHuo.Result> {
    private ImageView image;

    public MeiZhiViewHolder(ViewGroup parent) {
        super(parent, R.layout.meizhi_item);
        image = $(R.id.image);
    }

    @Override
    public void setData(GanHuo.Result data) {
        super.setData(data);
        Log.d("23",data.getUrl());
        Glide.with(getContext())
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image);
    }
}
