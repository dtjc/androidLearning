package com.dt.learning.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dt.learning.R;

public class ImageHandleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_handle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView=(ImageView)findViewById(R.id.activity_image_handle_iv);
        Matrix matrix=new Matrix();
        Bitmap test= BitmapFactory.decodeResource(getResources(),R.drawable.test);
        Camera camera=new Camera();
        camera.rotateY(45); //3D处理，绕Y轴旋转45度
        camera.getMatrix(matrix);
        matrix.preTranslate(0,-test.getHeight()/2.0f);
        matrix.postScale(2,2);
        matrix.postTranslate(test.getWidth(),test.getHeight()*2f);
        imageView.setImageBitmap(test);
        imageView.setImageMatrix(matrix);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
