package tw.adouble.app.helloworld.myviewtest170812004;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

//自訂View
public class MyView extends View{
    private LinkedList<LinkedList<Point>> lines;
    private Paint paint;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.GREEN);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);

        lines = new LinkedList<>();
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("brad", "onClick");
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchDown(event.getX(), event.getY());
            //Log.i("brad", "Down: " + event.getX() + " x " + event.getY());
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            touchMove(event.getX(), event.getY());
            //Log.i("brad", "Move: " + event.getX() + " x " + event.getY());
        }
        return true;//super.onTouchEvent(event); =>點下放開均觸發onTouch 放開後由super.onTouchEvent(event)觸發onClick
                      //return true => 持續偵測 點下放開均觸發onTouch 放開後不會觸發onClick
                      //return false => 不持續偵測 僅點下觸發onTouch


    }

    private void touchDown(float x, float y){
        LinkedList<Point> line = new LinkedList<>();
        Point p = new Point();
        p.x=x; p.y=y;
        line.add(p);
        lines.add(line);
    }

    private void touchMove(float x, float y){
        LinkedList<Point> line = lines.getLast();
        Point p = new Point();
        p.x=x; p.y=y;
        line.add(p);
        invalidate();
    }

    public void clear(){
        lines.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(LinkedList<Point> line: lines){
            for(int i=1; i<line.size(); i++){
                Point p0 = line.get(i-1);
                Point p1 = line.get(i);
                canvas.drawLine(p0.x,p0.y,p1.x,p1.y,paint);
            }
        }

        canvas.drawLine(0, 0, 200, 200, paint);
    }

    public void setColor(int color){
        paint.setColor(color);
        invalidate();
    }

    public int getColor(){
        return paint.getColor();
    }

    private class Point {
        float x, y;

    }
}
