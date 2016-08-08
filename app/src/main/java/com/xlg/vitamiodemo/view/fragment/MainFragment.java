package com.xlg.vitamiodemo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.xlg.vitamiodemo.R;
import com.xlg.vitamiodemo.activity.GanHuoActivity;
import com.xlg.vitamiodemo.activity.MeiZhiActivity;
import com.xlg.vitamiodemo.activity.VideoActivity;
import com.xlg.vitamiodemo.adapter.GanHuoAdapter;
import com.xlg.vitamiodemo.adapter.MeiZhiAdapter;
import com.xlg.vitamiodemo.adapter.VideoAdapter;
import com.xlg.vitamiodemo.mode.GanHuo;
import com.xlg.vitamiodemo.mode.mVideo;
import com.xlg.vitamiodemo.retrofit.gank.GankRetrofit;
import com.xlg.vitamiodemo.retrofit.gank.GankService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admin on 2016/5/16.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener {

    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private List<GanHuo.Result> ganHuoList;
    private GanHuoAdapter ganHuoAdapter;
    private MeiZhiAdapter meiZhiAdapter;
    private VideoAdapter videoAdapter;

    private boolean isNetWork = true;
    private String title;
    private int page = 1;
    private Handler handler = new Handler();
    private boolean isVideo=false;

    public static MainFragment getInstance(String title){
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        title = bundle.getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        ganHuoList = new ArrayList<>();
        noWIFILayout = (LinearLayout) view.findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view);

        if (title.equals("福利")){
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);

            meiZhiAdapter = new MeiZhiAdapter(getContext());

            dealWithAdapter(meiZhiAdapter);
        }
        else if(title.equals("Android")||title.equals("iOS")){
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ganHuoAdapter = new GanHuoAdapter(getContext());
            //recyclerView.setAdapterWithProgress(ganHuoAdapter);
            dealWithAdapter(ganHuoAdapter);
        }else if(title.equals("休息视频")){
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            videoAdapter=new VideoAdapter(getContext());
            dealAdapter(videoAdapter);
        }

        recyclerView.setRefreshListener(this);
        recyclerView.setRefreshingColor(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorAccent);
        onRefresh();
        //  getVideoData();



    }


    private void dealAdapter(final RecyclerArrayAdapter<mVideo> adapter){
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout, this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("url", adapter.getItem(position).getUrl());
                startActivity(intent);
            }
        });
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<GanHuo.Result> adapter) {
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (title.equals("福利")) {
                    Intent intent = new Intent(getContext(), MeiZhiActivity.class);
                    jumpActivity(intent, adapter, position);
                } else {
                    Intent intent = new Intent(getContext(), GanHuoActivity.class);
                    jumpActivity(intent, adapter, position);
                }
            }
        });
    }

    private void jumpActivity(Intent intent, RecyclerArrayAdapter<GanHuo.Result> adapter, int position) {
        intent.putExtra("desc", adapter.getItem(position).getDesc());
        intent.putExtra("url", adapter.getItem(position).getUrl());
        startActivity(intent);
    }

    private void getData(String type, int count, int page) {
        GankRetrofit.getRetrofits()
                .create(GankService.class)
                .getGanHuo(type, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GanHuo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        noWIFILayout.setVisibility(View.VISIBLE);
                        Snackbar.make(recyclerView, "没网", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                        ganHuoList = ganHuo.getResults();
                        if (title.equals("福利")) {
                            meiZhiAdapter.addAll(ganHuoList);
                        } else if (!title.equals("休息视频")) {
                            ganHuoAdapter.addAll(ganHuoList);
                        }
                    }
                });
    }
    public void getVideoData(){
        if(!isVideo){
            List<mVideo> list=new ArrayList<mVideo>();
            String[] texts = getResources().getStringArray(R.array.video_test);
            for(String textd:texts){
                String[] text=textd.split(" ");
                if(text.length==3) {

                    mVideo temp = new mVideo(text[0], text[1], text[2]);
                    list.add(temp);
                }
            }

            if(videoAdapter!=null)
                videoAdapter.addAll(list);
            isVideo=true;
        }
        videoAdapter.add(null);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (title.equals("福利")) {
                    meiZhiAdapter.clear();
                    getData("福利", 20, 1);
                } else {
                    if (ganHuoAdapter != null) ganHuoAdapter.clear();
                    if (title.equals("Android")) {
                        getData("Android", 20, 1);
                    } else if (title.equals("iOS")) {
                        getData("iOS", 20, 1);
                    }
                    else if (title.equals("休息视频")){
                        //    getData("休息视频",20,1);
                        //Toast.makeText(getActivity(), "已刷新", Toast.LENGTH_SHORT).show();
                        getVideoData();
                    }
                }
                page = 2;
            }
        }, 100);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (title.equals("福利")) {
                    getData("福利", 20, page);
                } else if (title.equals("Android")) {
                    getData("Android", 20, page);
                } else if (title.equals("iOS")) {
                    getData("iOS", 20, page);
                }
                else if (title.equals("休息视频")){
                    // Toast.makeText(getActivity(), "没有更多，敬请期待", Toast.LENGTH_SHORT).show();
                    //  getData("休息视频",20,page);
                    videoAdapter.add(null);
                }
                page++;
            }
        }, 100);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}