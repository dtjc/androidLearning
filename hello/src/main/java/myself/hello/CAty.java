package myself.hello;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class CAty extends LauncherActivity {

    private String[] names={"BAty","DAty"};
    Class<?>[] classes={BAty.class,DAty.class};
    private MyDrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_caty);

        drawView=new MyDrawView(this);
        addContentView(drawView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.activity_caty,R.id.list,names);
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
    }

    @Override
    protected Intent intentForPosition(int position) {
        return new Intent(this,classes[position]);
    }
}

class MyDrawView extends View {

    public Paint paint;
    public Path path;

    public MyDrawView(Context context){
        super(context);
        paint =new Paint();
        path=new Path();
        path.lineTo(100,100);
        path.lineTo(200,100);
        path.lineTo(200,50);
        path.close();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        canvas.drawCircle(400, 400, 200, paint);
        Rect rect=new Rect(200,600,800,900);
        canvas.drawRect(rect,paint);
        canvas.drawArc(new RectF(200, 600, 800, 900), -45, 180, false, paint);
    }

}
