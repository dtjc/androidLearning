package com.dt.learning.activities;

import android.graphics.drawable.Animatable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dt.learning.R;

public class SVGActivity extends AppCompatActivity {

    ImageView ivSVG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        ivSVG = (ImageView)findViewById(R.id.image_svg);
//        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable)getDrawable(R.drawable.hexagon_anim);
//        ivSVG.setImageDrawable(drawable);
//        drawable.start();

    }

    public void hexagonClick(View view){
        if (ivSVG.getDrawable() instanceof Animatable){
            Animatable d = (Animatable)ivSVG.getDrawable();
            d.start();
        }

    }
}
