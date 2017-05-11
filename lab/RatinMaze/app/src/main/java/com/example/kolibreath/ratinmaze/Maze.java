package com.example.kolibreath.ratinmaze;

/**
 * Created by kolibreath on 17-5-11.
 */

public class Maze {


    final int N = 5;
    int sol[][] =
            {{0, 0, 0, 0,0},
            {0, 0, 0, 0,0},
            {0, 0, 0, 0,0},
            {0, 0, 0, 0,0},
            {0,0,0,0,0}
    };

    public Maze(int sol[][]){
        this.sol = sol;
    }

    //最上角的点是200,200
    public int[] getTopLeftX(int X,int Y){
        int convertX  =  X*100 + 200;
        int convertY  =  Y*100 + 200;
        int coors[] = {convertX,convertY};
        return coors;
    }


    boolean isSafe(int maze[][], int x, int y)
    {
        // if (x,y outside maze) return false
        return (x >= 0 && x < N && y >= 0 &&
                y < N && maze[x][y] == 1);
    }


    boolean ifMarked(int sol[][],int X,int Y){
        boolean unmarked = true;
        if(sol[X][Y]==1){
            unmarked = false;
        }
        return unmarked;
    }


    boolean solveMaze(int maze[][])
    {

        if (solveMazeUtil(maze, 0, 0, sol) == false)
        {
            System.out.print("Solution doesn't exist");
            return false;
        }

        return true;
    }
    boolean solveMazeUtil(int maze[][], int x, int y,
                          int sol[][])
    {
        if (x == N - 1 && y == N - 1)
        {
            sol[x][y] = 1;
            return true;
        }
        //ifmarkf当检测的值为1 return false 为 0的时候return true
        if (isSafe(maze, x, y) == true&&ifMarked(sol,x,y)==true)
        {
            // mark x,y as part of solution path
            sol[x][y] = 1;
            if (solveMazeUtil(maze, x - 1, y, sol))
                return true;
            if (solveMazeUtil(maze, x, y - 1, sol))
                return true;

            if (solveMazeUtil(maze, x + 1, y, sol))
                return true;
            if (solveMazeUtil(maze, x, y + 1, sol))
                return true;
            sol[x][y] = 0;
            return false;
        }

        return false;
    }
}
