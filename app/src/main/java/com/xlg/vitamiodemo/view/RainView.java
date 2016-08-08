package com.xlg.vitamiodemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.ArrayList;
/**
 * Created by xlg on 2016/8/6.
 *
 */
public class RainView extends BaseView {

    //    private RainLine rainLine;
    private ArrayList<RainLine> rainLines;
    private Paint paint;

    private static  int RAIN_COUNT = 1000; //雨点个数
    public static boolean needRain=true;

    public RainView(Context context) {
        super(context);
    }

    public RainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void init() {
        super.init();
//        rainLine = new RainLine(windowWidth, windowHeight);
        rainLines = new ArrayList<RainLine>();
        for (int i = 0; i < RAIN_COUNT; i++) {
            rainLines.add(new RainLine(windowWidth, windowHeight));
        }
        paint = new Paint();
        if (paint !=null) {
            paint.setColor(0xffffffff);
        }

    }


    @Override
    protected void drawSub(Canvas canvas) {
        for(RainLine rainLine : rainLines) {
            canvas.drawLine(rainLine.getStartX(), rainLine.getStartY(), rainLine.getStopX(), rainLine.getStopY(), paint);
        }

    }

    @Override
    protected void animLogic() {
        for(RainLine rainLine : rainLines) {
            rainLine.rain();
        }
    }


    @Override
    protected boolean needStopAnimThread() {
        if(needRain){
            for(RainLine rainLine : rainLines) {
                if (rainLine.getStartY() >= getWidth()) {
//                rainLine.resetRandom();
                    rainLine.initRandom();
                    if(!needRain){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    protected void onAnimEnd() {
    }
}
