package com.xlg.vitamiodemo.mode;

/**
 * Created by Administrator on 2016/8/7.
 */
public class mVideo {
    private String url;
    private String image;
    private String title;
    public mVideo(String title,String image,String url){
        this.image=image;
        this.url=url;
        this.title=title;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public void setImage(String image){
        this.image=image;
    }
    public String getUrl(){
        return  url;
    }
    public String getImage(){
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
