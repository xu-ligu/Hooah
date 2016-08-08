package com.xlg.vitamiodemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.xlg.vitamiodemo.mode.mVideo;
/**
 * Created by Administrator on 2016/8/7.
 */
public class VideoAdapter extends RecyclerArrayAdapter<mVideo> {

    public VideoAdapter(Context context){
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(parent);
    }
}
