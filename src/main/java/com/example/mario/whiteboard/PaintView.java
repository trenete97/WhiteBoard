package com.example.mario.whiteboard;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class PaintView extends View {
    private Bitmap bitmap;
    private static Canvas canvas;
    private Path penPath;
    private Paint penPaint;
    private Paint canvasPaint;
    private ChatClient client;
    private int X, Y, oldX, oldY;
    String name;

    public PaintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupPainting();
    }

    public void sendClear(){
        client.send("C");
    }

    public void setClient(String name){

        client = new ChatClient(this);
        client.startConnection(name);
    }

    public void clearCanvas() {
        this.canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

  /*  public void paintPoint(Integer x, Integer y) {
        this.canvas.drawPoint(x, y, this.otherPaint);
        invalidate();
    }*/

    protected void setupPainting() {
        this.bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);

        this.penPath =  new Path();

        this.penPaint = new Paint();
        this.penPaint.setColor(-16728065);
        this.penPaint.setAntiAlias(true);
        this.penPaint.setStrokeWidth(15);
        this.penPaint.setStyle(Paint.Style.STROKE);
        this.penPaint.setStrokeJoin(Paint.Join.ROUND);
        this.penPaint.setStrokeCap(Paint.Cap.ROUND);


        this.canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas cvs) {
        cvs.drawBitmap(bitmap, 0, 0, canvasPaint);
        cvs.drawPath(penPath, penPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF touchPoint = new PointF();
        touchPoint.set(event.getX(), event.getY());

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = Math.round(touchPoint.x);
                oldY = Math.round(touchPoint.y);

                this.penPath.moveTo(oldX, oldY);
                break;

            case MotionEvent.ACTION_MOVE:
                this.penPath.lineTo(touchPoint.x, touchPoint.y);
                X = Math.round(touchPoint.x);
                Y = Math.round(touchPoint.y);
                String color = "N";
                System.out.println(penPaint.getColor());
                switch (penPaint.getColor()) {
                    case -12799119:
                        color = "G";
                        break;
                    case -38476:
                        color = "M";
                        break;
                    case -16777216:
                        color = "N";
                        break;
                    case -16728065:
                        color = "B";
                        break;
                    case -1824728:
                        color = "R";
                        break;
                    default:
                        color = "N";
                    case Color.WHITE:
                        color = "E";
                        break;

                }
                Line l = new Line(color, true);
                l.addPoint(oldX, oldY);
                l.addPoint(X,Y);
                this.client.send(l.LineToString());
                oldX=X;
                oldY=Y;
                break;

            case MotionEvent.ACTION_UP:
                this.canvas.drawPath(penPath, penPaint);
                this.penPath.reset();
                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void changeColor(int newColor){

        this.penPaint.setColor(newColor);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

    public void setEraser(){
        this.penPaint.setColor(Color.WHITE);
    }

    public void canvasThicknessChange(int thickness){
        this.penPaint.setStrokeWidth(thickness);
    }

    public String getName() {
        return name;
    }

    public void drawLine(String line_s) {
        Line l = new Line(line_s, false);
        int x1, y1, x2, y2;
        int color = 0;
        switch (l.getColor()){
            case "N":
                color = -16777216;
                break;
            case "G":
                color = -12799119;
                break;
            case "B":
                color = -16728065;
                break;
            case "M":
                color = -38476;
                break;
            case "R":
                color = -1824728;
                break;
            case"E":
                color = Color.WHITE;
        }
        penPaint.setColor(color);
        x1 = l.getPoints().get(0).a;
        y1 = l.getPoints().get(0).b;
        x2 = l.getPoints().get(1).a;
        y2 = l.getPoints().get(1).b;

        canvas.drawLine(x1, y1, x2, y2, penPaint);
        super.invalidate();
    }
}