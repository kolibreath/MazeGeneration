package com.example.kolibreath.mazegeneration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.Random;

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

        Maze maze = new Maze(mazeGrid);
        maze.setMarked(GridX, GridY);
        int tempX ,tempY;
        boolean unfinished = maze.ifFinished(count,bottomRightX);
        int randomDirection ;
        boolean whetherDrawable ;



        initRect(canvas);
        initWhiteSquare(canvas);


        drawStart(topLeftX,topLeftY,canvas);

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
                Paint whitpaint = new Paint();
                whitpaint.setStyle(Paint.Style.FILL);
                whitpaint.setColor(Color.WHITE);
                canvas.drawRect(square,whitpaint);
                unfinished = maze.ifFinished(count,bottomRightX);
                    maze.setMarked(GridX,GridY);
                count++;
            }


            makeDecetpcive(mazeGrid);
            logMazeGrid(mazeGrid);
        //用这个for循环中的i j 代替坐标！
        decepcivePaint(mazeGrid,canvas);
        Log.d("exit", topLeftX+" " +topLeftY+" " +bottomRightX+" " +bottomRightY);
        drawExit(bottomRightX,bottomRightY,canvas);
        breakThrough(bottomRightX,bottomRightY,canvas,mazeGrid);
        }
        //通过Grid中的坐标去获取真实的坐标（左上角的）右下角的矩形的坐标可以用
        // 这个两个坐标分别加上100


    public void breakThrough(int bottomRightX,int bottomRightY,Canvas canvas,int mazeGrid[][]){
        int coors[] = getGridCoorByCoor(bottomRightX,bottomRightY);
        //这里 得到了 出口处对应的 方格的坐标
        //搜索出口为中心6的坐标是什么
        int upperX,upperY,downwardX,downwardY,faceX,faceY,DupperX,DupperY,DdownX,DdownY;
        upperX  = coors[0] ;
        upperY =  coors[1] - 1;
        downwardX = coors[0];
        downwardY = coors[1] +1;
        faceX = coors[0] - 1;
        faceY = coors[1];
        DupperX = coors[0]-1;
        DupperY  = coors[1] -1;
        DdownX = coors[0] - 1;
        DdownY = coors[1] + 1;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        canvas.drawRect(bottomRightX-100,bottomRightY-100,bottomRightX,bottomRightY,paint);

        breakPrint(coors[0],coors[1],mazeGrid,canvas,paint);
        breakPrint(upperX,upperY,mazeGrid,canvas,paint);
        breakPrint(downwardX,downwardY,mazeGrid,canvas,paint);
        breakPrint(faceX,faceY,mazeGrid,canvas,paint);
        breakPrint(DupperX,DupperY,mazeGrid,canvas,paint);
        breakPrint(DdownX,DdownY,mazeGrid,canvas,paint);
    }

    private void breakPrint(int X,int Y,int mazeGrid[][],Canvas canvas,Paint paint){
        if(mazeGrid[X][Y]==1){
            int coors[] = getCoorByGridCoor(X,Y);
            canvas.drawRect(coors[0],coors[1],coors[0]+100,coors[1]+100,paint);
        }
    }

    //从坐标映射到Grid的坐标 这个坐标是左上角的坐标
    public int[] getGridCoorByCoor(int coorX,int coorY){
        int GridCoors[] = new int[2];
        GridCoors[0] = (coorX - 160)/100 + 1;
        GridCoors[1] = (coorY - 160)/100 + 1;
        return GridCoors;
    }

    public void initWhiteSquare(Canvas canvas){
        Paint whitPaint = new Paint();
        whitPaint.setAntiAlias(true);
        whitPaint.setColor(Color.WHITE);
        whitPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(160,160,260,260,whitPaint);

    }

    public void initRect(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(160,160,960,960,paint);
        canvas.drawLine(160,160,960,160,paint);
        canvas.drawLine(160,160,160,960,paint);
        canvas.drawLine(160,960,960,960,paint);
        canvas.drawLine(960,160,960,960,paint);
    }

    public void drawStart(int topLeftX,int topLeftY,Canvas canvas){
        Paint paint =  new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        canvas.drawLine(topLeftX,topLeftY,topLeftX,topLeftY+100,paint);
    }
    public void drawExit(int bottomRigthX,int bottomRightY,Canvas canvas){
        Paint paint  = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        canvas.drawLine(bottomRigthX,bottomRightY-100,bottomRigthX,bottomRightY,paint);
    }

    public void logMazeGrid(int [][]mazeGrid){
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                Log.d("logMaze",mazeGrid[i][j]+"");
            }
        }
    }
    public void decepcivePaint(int[][] mazeGrid,Canvas canvas){
        int count = 0;
        for(int i =1;i<=8;i++){
            for(int j=1;j<=8;j++) {
                int control = mazeGrid[i][j];
                if (control == 1) {
                    int coors[] = getCoorByGridCoor(i, j);
                    int topleftX = coors[0];
                    int topleftY = coors[1];
                    int bottomrightX = coors[0] + 100;
                    int bottomrightY = coors[1] + 100;
                    Rect rect = new Rect(topleftX,topleftY,bottomrightX,bottomrightY);
                    Paint blackPaint = new Paint();
                    blackPaint.setStyle(Paint.Style.FILL);
                    blackPaint.setAntiAlias(true);
                    blackPaint.setColor(Color.WHITE);
                    canvas.drawRect(rect,blackPaint);
                }
                if(control==-1){
                    int coors[] = getCoorByGridCoor(i, j);
                    int topleftX = coors[0];
                    int topleftY = coors[1];
                    int bottomrightX = coors[0] + 100;
                    int bottomrightY = coors[1] + 100;
                    Rect rect = new Rect(topleftX,topleftY,bottomrightX,bottomrightY);
                    Paint blackPaint = new Paint();
                    blackPaint.setStyle(Paint.Style.FILL);
                    blackPaint.setAntiAlias(true);
                    blackPaint.setColor(Color.BLACK);
                    canvas.drawRect(rect,blackPaint);
                }
            }
        }
    }
        public int[] getCoorByGridCoor(int GridX,int GridY){
            int CoorX = (GridX-1)*100 + 160;
            int CoorY = (GridY-1)*100 + 160;
            int coorS[] = {CoorX,CoorY};
            return coorS;
        }

        //如果同时有四个小正方形组成的大正方形方块的话，就随机在这些方块中隐藏1个
    private void makeDecetpcive(int[][] mazeGrid){
        int count =0;
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++) {
                int a, b, c, d;
                a = mazeGrid[i][j];
                b = mazeGrid[i][j + 1];
                c = mazeGrid[i + 1][j];
                d = mazeGrid[i + 1][j + 1];
                //如果有四个相连的白色
                if (a == 1 && b == 1 && c == 1 && d == 1) {
                    Random random = new Random();
                    int randomN = random.nextInt(2);
                    int randomN2 = random.nextInt(2);
                    mazeGrid[i+randomN][j+randomN2] = -1;
                }
                //如果有四个相连的黑色
                if(a == 0 && b== 0 && c== 0 && d ==0){
                    Random random = new Random();
                    int randomN = random.nextInt(2);
                    int randomN2 = random.nextInt(2);
                    mazeGrid[i+randomN][j+randomN2] = 1;
                    randomN = random.nextInt(2);
                    randomN2 = random.nextInt(2);
                    mazeGrid[i+randomN][i+randomN2] = 1;

                }
            }
        }
    }

    private int[] processWithGridCoordinates(int random,int GridX,int GridY){
        //注意GridX 和 GridY的初始坐标为(1,1)
        int newCoodX = 1;
        int newCoodY = 1;
        int coordinate[] = {newCoodX,newCoodY};
        switch (random){
            case 1:
                coordinate[1] = GridY + 1;
                break;
            case 2:
                coordinate[1] = GridY - 1;
                break;
            case 3:
                coordinate[0]= GridX - 1;
                break;
            case 4:
                coordinate[0] = GridX + 1;
                break;
        }
        return  coordinate;
    }

}