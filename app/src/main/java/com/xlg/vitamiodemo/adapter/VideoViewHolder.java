package com.xlg.vitamiodemo.adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xlg.vitamiodemo.R;
import com.xlg.vitamiodemo.mode.mVideo;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Administrator on 2016/8/7.
 */
public class VideoViewHolder extends BaseViewHolder<mVideo>{

    private ImageView imageView;
    private TextView textView;

    public VideoViewHolder(ViewGroup parent) {
        super(parent, R.layout.main_news_item);
        imageView=$(R.id.iv_title);
        textView=$(R.id.tv_title);
    }

    @Override
    public void setData(mVideo data) {
        super.setData(data);
        textView.setText(data.getTitle());
        Log.d("23", "所发生的" + data.getUrl());
        Glide.with(getContext())
                .load(data.getImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

}
