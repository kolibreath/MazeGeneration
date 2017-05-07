package com.example.kolibreath.mazegeneration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by kolibreath on 17-5-6.
 */

public class MazeView extends View{

    public MazeView(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //计数器 只有当标记的方块数目达到40个以上并且达到右侧才可以算完
        int count = 0;
        int mazeGrid[][] = new int[10][10];

        int topLeftX = 160;
        int topLeftY = 160;
        int bottomRightX = 260;
        int bottomRightY = 260;

        //开始点默认的坐标为（1,1）
        int GridX = 1 , GridY =1;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        //绘制的正方形矩阵宽度为100dp
        //用对角线的两个点显示
        canvas.drawRect(160,160,960,960,paint);

        canvas.drawLine(160,160,960,160,paint);
        canvas.drawLine(160,160,160,960,paint);
        canvas.drawLine(160,960,960,960,paint);
        canvas.drawLine(960,160,960,960,paint);

        Paint whitPaint = new Paint();
        whitPaint.setAntiAlias(true);
        whitPaint.setColor(Color.WHITE);
        whitPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(160,160,260,260,whitPaint);

        Maze maze = new Maze(mazeGrid);
        maze.setMarked(GridX, GridY);
        int tempX ,tempY;
        boolean unfinished = maze.ifFinished(count,bottomRightX);
        int randomDirection ;
        boolean whetherDrawable ;
        while(!unfinished){

            randomDirection = maze.randomDirections();
            //检测当前的点的值是否被占用
            whetherDrawable = maze.ifDrawable(processWithGridCoordinates(randomDirection,GridX,GridY)[0],
                    processWithGridCoordinates(randomDirection,GridX,GridY)[1]);

            //如果是false的话就会一直搜索，直到true
            while(!whetherDrawable){
                randomDirection = maze.randomDirections();
                tempX = processWithGridCoordinates(randomDirection,GridX,GridY)[0];
                tempY = processWithGridCoordinates(randomDirection,GridX,GridY)[1];
                whetherDrawable = maze.ifDrawable(tempX,tempY);
            }

            //ifDrawable()判断是否超出了边界，和方块有无标记


            if(whetherDrawable){
                //如果可以的话就在Grid将这个矩阵的坐标随机的方向移动
                //但是 万一1这个移动到了我边界怎么办？？
                //判断 只有移动之后不是边界在同时进行二维数组和坐标移动！
                switch (randomDirection) {
                    case 1:
                        GridY++;
                        if(GridY==9) {
                            GridY--;
                            break;
                        }
                        topLeftY += 100;
                        bottomRightY += 100;
                        break;
                    case 2:
                        GridY--;
                        if(GridY==9) {
                            GridY--;
                            break;
                        }
                        topLeftY -= 100;
                        bottomRightY -=100;
                        break;
                    case 3:
                        GridX--;
                        if(GridX==9) {
                            GridX--;
                            break;
                        }
                        topLeftX -= 100;
                        bottomRightX -= 100;
                        break;
                    case 4:
                        GridX++;
                        if(GridX==9) {
                            GridX--;
                            break;
                        }
                        topLeftX += 100;
                        bottomRightX += 100;
                        break;
                }
            }
                if(GridX==9){
                    GridX--;
                   // GridY--;
                }
                if(GridY==9){
                    GridY--;
                }
                Rect square = new Rect(topLeftX,topLeftY,bottomRightX,bottomRightY);
            //对这个改变之后的方块进行涂色
                canvas.drawRect(square,whitPaint);
                maze.setMarked(GridX,GridY);
                count++;
                unfinished = maze.ifFinished(count,bottomRightX);
            }
        }

    private int[] processWithGridCoordinates(int random,int GridX,int GridY){
        //注意GridX 和 GridY的初始坐标为(1,1)
        int newCoodX = 1;
        int newCoodY = 1;
        int coordinate[] = {newCoodX,newCoodY};
        switch (random){
            case 1:
                coordinate[0] = GridY + 1;
                break;
            case 2:
                coordinate[1] = GridY - 1;
                break;
            case 3:
                coordinate[0]= GridX - 1;
                break;
            case 4:
                coordinate[1] = GridY + 1;
                break;
        }
        return  coordinate;
    }

}