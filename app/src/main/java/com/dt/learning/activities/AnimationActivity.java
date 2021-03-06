package com.dt.learning.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.dt.learning.R;
import com.dt.learning.adapter.MyAdapter;

public class AnimationActivity extends AppCompatActivity {
    private RecyclerView rcy;
    private boolean mViewAnimActive;
    private boolean mAttrAnimActive;
    private boolean mSetAnimActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Animation animation=AnimationUtils.loadAnimation(this,R.anim.layout_anim_item);
        LayoutAnimationController controller=new LayoutAnimationController(animation,0.3f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);

        rcy = (RecyclerView)findViewById(R.id.content_animation_rcy_layout_anim);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this){
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                super.onMeasure(recycler, state, widthSpec, heightSpec);
                Log.e("rcy_item_measure","test");
            }
        };
        rcy.setLayoutManager(linearLayoutManager);
        MyAdapter adapter=new MyAdapter();
        rcy.setLayoutAnimation(controller);
        rcy.setAdapter(adapter);
        rcy.startLayoutAnimation();
    }

    public void viewAnimStart(View view){
        if (mViewAnimActive)    return;
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.view_anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mViewAnimActive=true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewAnimActive=false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        view.startAnimation(anim);
    }

    public void attrAnimStart(final View view){
        if (mAttrAnimActive)    return;
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(view,"translationX",0,3000);
        objectAnimator.setDuration(2000);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mAttrAnimActive=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAttrAnimActive=false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAttrAnimActive=false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        objectAnimator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {

                Log.e("animator",String.valueOf(view.getX()));
                return input;
            }
        });
        objectAnimator.start();
    }

    public void setAnimStart(View view){
        if (mSetAnimActive) return;
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view,"translationX",0,500),
                ObjectAnimator.ofFloat(view,"scaleX",0.4f,1f),
                ObjectAnimator.ofFloat(view,"scaleY",0.4f,1f),
                ObjectAnimator.ofFloat(view,"pivotX",0),
                ObjectAnimator.ofFloat(view,"pivotY",0),
                ObjectAnimator.ofFloat(view,"rotationY",0,180),
                ObjectAnimator.ofFloat(view,"alpha",0.25f,1f));
        animatorSet.setDuration(3000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mSetAnimActive=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSetAnimActive=false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mSetAnimActive=false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animatorSet.start();
    }
}
