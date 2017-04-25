package myself.hello;

import android.database.ContentObserver;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by dnnt9 on 2016/2/4.
 */
public class MyObserver extends ContentObserver {
    TextView textView;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public MyObserver(Handler handler,TextView textView) {
        super(handler);
        setTextView(textView);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        textView.setText("sql has changed");
    }

    public void setTextView(TextView textView){
        this.textView=textView;
    }
}
