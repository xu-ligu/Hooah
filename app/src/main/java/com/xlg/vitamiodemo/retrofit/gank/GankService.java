package com.xlg.vitamiodemo.retrofit.gank;


import com.xlg.vitamiodemo.mode.GanHuo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


//http://gank.io/api/data/福利/1/20/1
public interface GankService {
    @GET("api/data/{type}/{count}/{page}")
    Observable<GanHuo> getGanHuo(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );
}
