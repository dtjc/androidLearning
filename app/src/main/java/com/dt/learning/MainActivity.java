package com.dt.learning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        图像处理学习
        ImageView imageView=(ImageView)findViewById(R.id.main_iv_test);
        Matrix matrix=new Matrix();
        Bitmap test= BitmapFactory.decodeResource(getResources(),R.drawable.test);
        Camera camera=new Camera();
        camera.rotateY(45);
        camera.getMatrix(matrix);
        matrix.preTranslate(0,-test.getHeight()/2.0f);
        matrix.postScale(2,2);
        matrix.postTranslate(test.getWidth(),test.getHeight()*2f);
        imageView.setImageBitmap(test);
        imageView.setImageMatrix(matrix);
    }
}
