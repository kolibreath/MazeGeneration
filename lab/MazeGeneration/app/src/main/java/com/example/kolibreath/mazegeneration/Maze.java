package com.example.kolibreath.mazegeneration;

import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by kolibreath on 17-5-6.
 */

public class Maze {

    /**
     * 这个类会生成一个迷宫，
     * 迷宫的大小是8*8，然后x，y轴和view的轴相平行，其中的x和y坐标分别是方格对应的位置的映射
     * 数字1 表示已经被标记 数字0表示未被标记
     * 坐标的最左上角的各自对应的坐标是(1,1)
     * 注意(1)这个方正的真正大小是10*10 最外层一圈是用来判定这个是否超出边界
     *     (2)randomDirections()给出随机的一个方向 1 2 3 4 分别表示 上下左右
     *     (3)使用的是drawRect()方法绘制迷宫 所以使用一个map集合保存每个小矩形的对角坐标
     *     (4)获取相邻的坐标的机制是：有一个全局变量保存最开始的左上角的点的，和另一个全局变量保存
     *     右下角的点的坐标，随机得到的是那个方向就在x 或者 y上加上100dp
     */
    int mazeGrid[][] = new int[10][10];


    public Maze(int mazeGrid[][]){
        this.mazeGrid = mazeGrid;
        for(int i=0;i<10;i++){
            mazeGrid[0][i] = -1;
            mazeGrid[i][0] = -1;
            mazeGrid[9][i] = -1;
            mazeGrid[i][9] = -1;
            Log.d("location", mazeGrid[0][i]+"  "+mazeGrid[i][0] +"  " +mazeGrid[9][i]
                    +"  "+mazeGrid[i][9]);
        }
    }


    //通过给定的随机值移动坐标并且返回一个新的Rect
    public Rect moveRectWithRandomDir(int randomNumber, int topLeftMostX , int topLeftMostY,
                                  int bottomRightMostX, int bottomRightMostY){
        switch (randomNumber){
            case 1:
                topLeftMostY += 100;
                bottomRightMostY += 100;
                break;
            case 2:
                topLeftMostY -= 100;
                bottomRightMostY -=100;
                break;
            case 3:
                topLeftMostX -= 100;
                bottomRightMostX -= 100;
                break;
            case 4:
                topLeftMostX += 100;
                bottomRightMostX += 100;
                break;
        }
        Rect newRect = new Rect(topLeftMostX,topLeftMostY,bottomRightMostX,bottomRightMostY);
        return newRect;
    }


    //x y 是mazeGrid的坐标
    public void setMarked(int x, int y){
        mazeGrid[x][y] = 1;
        Log.d("coorX", x+"   "+y);
    }


    public boolean ifFinished(int count,int bottomLeftX ) {
        if (count >60&&bottomLeftX==960){
            return true;
        } else {
            return false;
        }
    }


    //设置exit的值为10
    public void setExitMark(int GridX,int GridY){
        mazeGrid[GridX][GridY] = 10;
    }

    //判断这个一个方块是否能涂色 判断是否出界，判断是否已经被涂色
    //还需要判断数组是否越界
    public boolean ifDrawable(int x,int y){
        if(mazeGrid[x][y]==-1||mazeGrid[x][y]==1||x>=9||y>=9){
            return false;
        }else {
            return true;
        }
    }

    public int randomDirections(){
        int directions[] = {1,2,3,4};
        Random random = new Random();
        int direc = directions[random.nextInt(4)];
        return  direc;
    }



}
