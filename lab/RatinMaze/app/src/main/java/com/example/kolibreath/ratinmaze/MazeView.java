package com.example.kolibreath.ratinmaze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by kolibreath on 17-5-11.
 */

public class MazeView extends View {

    int sol[][] =  {{0, 0, 0, 0,0},
            {0, 0, 0, 0,0},
            {0, 0, 0, 0,0},
            {0, 0, 0, 0,0},
            {0,0,0,0,0}
    };

    int mazeGrid[][] =
            {{1,0, 1, 0, 0},
                    {1,0 ,0, 0, 1},
                    {1,0, 0, 0,1},
                    {1,1 ,1,1,1},
                    {0,0,0,0,1}
            };

    //待解决的maze
    public MazeView(Context context){
        super(context);
    }
    private void initOuterlines(Canvas canvas){
        int startX = 200;
        int startY = 200;
        int stopX  = 700;
        int stopY  = 200;


        Paint line = new Paint();
        line.setStyle(Paint.Style.STROKE);
        line.setStrokeWidth(5);

        canvas.drawLine(startX,startY,stopX,stopY,line);
        startX = 200;
        startY = 300;
        stopX = 200;
        stopY = 700;
        canvas.drawLine(startX,startY,stopX,stopY,line);
        startX = 200;
        startY = 700;
        stopX = 700;
        stopY = 700;
        canvas.drawLine(startX,startY,stopX,stopY,line);
        startX = 700;
        startY = 200;
        stopX = 700;
        stopY = 600;
        canvas.drawLine(startX,startY,stopX,stopY,line);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Maze maze = new Maze(sol);
        maze.solveMaze(mazeGrid);

        //sol中1是路径涂色为白 sol中0是路障涂色为黑
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);


        Paint black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        initOuterlines(canvas);

        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(sol[i][j]==1){
                    int top[] = maze.getTopLeftX(i,j);
                    Rect rect = new Rect(top[0],top[1],top[0]+100,top[1]+100);
                    canvas.drawRect(rect,black);
                }
            }
        }

    }
}
