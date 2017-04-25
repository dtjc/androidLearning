package myself.hello;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DAty extends AppCompatActivity {

    private Button switchFrag;
    private Fragment fragmentOne;
    private Fragment fragmentTwo;
    private FragmentManager fragmentManager;
    private boolean isOne;
    MyDataBase myDBHelper;
    Button insert,query;
    SQLiteDatabase sqldb;
    PopupMenu singleQuery;
    int menuid=-1;
    EditText text,text1,text2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daty);

        textView=(TextView)findViewById(R.id.sqlChange);
        getContentResolver().registerContentObserver(StringsConstants.PEOPLE_URI, true, new MyObserver(new Handler(),textView));

        fragmentManager=getFragmentManager();
//        fragmentOne=fragmentManager.findFragmentById(R.id.fragment);
        fragmentOne=new FragmentOne();
        fragmentTwo=new FragmentTwo();
        fragmentManager.beginTransaction().replace(R.id.datyLayout, fragmentOne).commit();

        isOne=true;

        switchFrag= (Button) findViewById(R.id.switchFragment);
        switchFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOne) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.datyLayout, fragmentTwo)
                            .commit();
                    isOne = false;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.datyLayout, fragmentOne)
                            .commit();
                    isOne = true;
                }
            }
        });

        myDBHelper=new MyDataBase(this,"test_info",1);
        sqldb=myDBHelper.getReadableDatabase();
        insert=(Button)findViewById(R.id.entering);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String age=((EditText)findViewById(R.id.ageEditText)).getText().toString();
                    String name=((EditText)findViewById(R.id.nameEditText)).getText().toString();
                    String birthday=((EditText)findViewById(R.id.birthdayEditText)).getText().toString();
//                    ContentValues values=new ContentValues();
//                    values.put("name",name);
//                    values.put("age",age);
//                    values.put("birthday",birthday);
//                    sqldb.insert("test_info", null, values);
                    sqldb.execSQL("insert into test_info values('"+name+"','"+age+"','"+birthday+"')");
//                    sqldb.execSQL("insert into test_info values('"+name+"','"+age+"','"+birthday+"')");
                    Toast.makeText(DAty.this,"success",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(DAty.this,"请输入正确的值",Toast.LENGTH_SHORT).show();
                }
            }
        });


        query=(Button)findViewById(R.id.queryBtn);
//        query.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor cursor=sqldb.rawQuery("select * from test_info",null);
//                while(cursor.moveToNext()){
//                    System.err.println("name:"+cursor.getString(0)+",age:"+cursor.getString(1)+",bir"+cursor.getString(2));
//                }
//                String[] test=cursor.getColumnNames();
//                for (String s:test){
//                    System.out.println(s);
//                }
//            }
//
//        });


        singleQuery=new PopupMenu(this,query);
        singleQuery.inflate(R.menu.querymenu);
        final AlertDialog.Builder queryDialog=new AlertDialog.Builder(this);
        text=new EditText(this);
        text1=new EditText(this);
        text2=new EditText(this);
        queryDialog.setPositiveButton("yew", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor cursor=null;
//                Bundle data = new Bundle();
//                Intent intent = new Intent(DAty.this, FAty.class);
                if (menuid == 0) {
                    cursor = sqldb.rawQuery("select * from test_info where name='" + text.getText().toString()+"'", null);
//                    data.putSerializable("cusorData", (Serializable) cursor);
//                    intent.putExtras(data);
                } else if (menuid == 1) {
                    cursor = sqldb.rawQuery("select * from test_info where age='" + text1.getText().toString()+"'", null);
//                    data.putSerializable("cusorData", (Serializable) cursor);
//                    intent.putExtras(data);
                } else if (menuid == 2) {
                    cursor = sqldb.rawQuery("select * from test_info where birthday='" + text2.getText().toString()+"'", null);
//                    data.putSerializable("cusorData", (Serializable) cursor);
//                    intent.putExtras(data);

                }
                ArrayList<Map<String,String>> mapArrayList=convertCursorToList(cursor);
                ListView listView=(ListView)findViewById(R.id.datyListView);
                SimpleAdapter adapter=new SimpleAdapter
                        (DAty.this,mapArrayList,R.layout.querylayout,new String[]{"name","age","birthday"},new int[]{R.id.nameList,R.id.ageList,R.id.birList});
                listView.setAdapter(adapter);
//                startActivity(intent);
                dialog.dismiss();
            }
        })
        .setNegativeButton("cencel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        queryDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleQuery.show();
            }
        });
        singleQuery.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.queryByName:
                        queryDialog.setView(text);
                        queryDialog.show();
                        menuid = 0;
                        break;
                    case R.id.queryByAge:
                        queryDialog.setView(text1);
                        queryDialog.show();
                        menuid = 1;
                        break;
                    case R.id.queryByBirthday:
                        queryDialog.setView(text2);
                        queryDialog.show();
                        menuid = 2;
                        break;
                }
                return false;
            }
        });
    }

    public ArrayList<Map<String ,String>> convertCursorToList(Cursor cursor){
        ArrayList<Map<String,String>> mapArrayList=new ArrayList<Map<String, String>>();
        while(cursor.moveToNext()){
            Map<String,String> map=new HashMap<String,String>();
            map.put("name",cursor.getString(0));
            map.put("age",cursor.getString(1));
            map.put("birthday",cursor.getString(2));
            mapArrayList.add(map);
        }
        return mapArrayList;
    }


}
