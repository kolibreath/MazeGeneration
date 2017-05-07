package com.example.kolibreath.mazegeneration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    //  canvas.drawRect(160,160,960,960,paint); 绘制的左上角的坐标是(169,160) (960,960)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MazeView(this));
    }
}
