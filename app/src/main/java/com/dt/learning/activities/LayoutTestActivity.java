package com.dt.learning.activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.debug.hv.ViewServer;
import com.dt.learning.R;

import java.util.LinkedList;
import java.util.Queue;

public class LayoutTestActivity extends AppCompatActivity {

    private static final int POPUP_WINDOW_UP = 0;
    private static final int POPUP_WINDOW_DOWN = 1;

    TextView tv;
    TextView tv1;
    TextView abcdTv;
    TextView goneTv;
    String txt;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_layout_test);
        txt = "abcdefg";
        ViewServer.get(this).addWindow(this);
        tv1 = (TextView)findViewById(R.id.testTv);
        tv = (TextView)findViewById(R.id.tvWindowTest);
        btnTest = (Button)findViewById(R.id.btn_test);
        tv1.setText(txt);
        abcdTv = (TextView)findViewById(R.id.abcdtv);
        goneTv = (TextView)findViewById(R.id.gonetv);
        abcdTv.setText(String.valueOf(goneTv.getLayoutParams().height));

    }

    private void showPopupWindow(PopupWindow popupWindow, Queue<GuideItem> queue){
        GuideItem item = queue.poll();
        if (item != null){
            popupWindow.setContentView(item.view);
            View anchor = findViewById(item.anchorId);
            int[] location = new int[2];
            abcdTv.getLocationOnScreen(location);
            location[0] = location[0] + (abcdTv.getWidth() - popupWindow.getWidth())/2;
            location[1] =location[1] - popupWindow.getHeight();
            popupWindow.showAtLocation(tv,Gravity.LEFT|Gravity.TOP,location[0],location[1]);
        }
    }

    PopupWindow popupWindow;

    public void showOnlineOpenGuild() {
        Log.e("LayoutTest","showOnlineOpenGuild");
        final Queue<GuideItem> queue = getGuideQueue();
        popupWindow = new PopupWindow(this);
        float density = getResources().getDisplayMetrics().density;
        int width = (int)(120 * density + 0.5);
        int height = (int)(100 * density + 0.5);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setHeight(height);
        popupWindow.setWidth(width);
        popupWindow.setClippingEnabled(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                showPopupWindow(popupWindow,queue);
            }
        });
        showPopupWindow(popupWindow,queue);
    }


    //引导项
    private Queue<GuideItem> getGuideQueue(){
        Queue<GuideItem> queue = new LinkedList<>();
        float density = getResources().getDisplayMetrics().density;

        ImageView iv = new ImageView(this);
        iv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.drawable1));
        queue.add(new GuideItem(iv,R.id.abcdtv,POPUP_WINDOW_UP));

        LinearLayout lly = new LinearLayout(this);
        lly.setOrientation(LinearLayout.VERTICAL);
        lly.setPadding(0,(int)(45 * density + 0.5f),0,0);
        iv = new ImageView(this);
        iv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_online_close_guild));
        lly.addView(iv,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        queue.add(new GuideItem(lly,R.id.tvWindowTest,POPUP_WINDOW_UP));

        iv = new ImageView(this);
        iv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.drawable3));
        queue.add(new GuideItem(iv,R.id.testTv,POPUP_WINDOW_DOWN));

        return queue;
    }

    class GuideItem{
        /**
         *
         * @param view
         * @param anchorId
         * @param location 0代表在anchorView上面;1代表在anchorView下面;
         */

        public GuideItem(View view,int anchorId, int location) {
            this.view = view;
            this.location = location;
            this.anchorId = anchorId;
        }

        private View view;
        private int anchorId;
        private int location;

    }

    @Override
    protected void onDestroy() {
        Log.e("LayoutTestActivity","onDestroy()");
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void finish() {
        Log.e("LayoutTestActivity","finish()");
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    private int k = 0;

    public void btn(View view){
        this.finish();

    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
