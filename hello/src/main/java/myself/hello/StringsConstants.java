package myself.hello;

import android.net.Uri;

/**
 * Created by dnnt9 on 2016/2/3.
 */
public class StringsConstants {

    public static final String PROTOCOL="content://";
    public static final String HELLO_AUTHORITY="com.jucheng.learningapp";
    public static final Uri PERSON_URI= Uri.parse(PROTOCOL+HELLO_AUTHORITY+"/person");
    public static final Uri PEOPLE_URI=Uri.parse(PROTOCOL+HELLO_AUTHORITY+"/people");

}
