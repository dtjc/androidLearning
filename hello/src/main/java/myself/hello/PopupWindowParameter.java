package myself.hello;

import android.widget.PopupWindow;

/**
 * Created by dnnt9 on 2016/1/24.
 */
public class PopupWindowParameter {

    protected PopupWindow popupWindow;

    PopupWindowParameter(){
        popupWindow=null;
    }
    protected PopupWindow getPopupWindow(){
        return popupWindow;
    }

    protected void setPopupWindow(PopupWindow popupWindow){
        this.popupWindow = popupWindow;
    }

}
