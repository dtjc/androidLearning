package myself.hello;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import java.io.*;
import java.util.*;
import android.app.*;

public class MainActivity extends AppCompatActivity {

    private NumberPicker numberPicker;
    private TextView textView,showMenu,readTextInExter;
    private ImageButton imageButton1,ibtA;
    private PopupWindowParameter parameter;
    private PopupMenu popupMenu;
    private int[] headid;
    final private int REQUEST_BATY=0,RESULT_FROM_BATY=0;
    private ClipDrawable clipDrawable;
    private ImageView chageableDrawable,imageButton;
    private int clipDrawLevel,length,length1;
    private Animation anim;
    private SharedPreferences sharedPreferences;
    final String FILE_NAME="helloTest.txt",EXTERNAL_FILE="/hello.txt";
    private SharedPreferences.Editor editor;
    private EditText saveTextInExter;
    File file;
    AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.err.println("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        saveTextInExter=(EditText)findViewById(R.id.saveEditText);
        readTextInExter=(TextView)findViewById(R.id.readText);

        sharedPreferences=getSharedPreferences("appData", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count", 0);
        count++;
        Toast.makeText(MainActivity.this,"这是第"+count+"次打开此应用",Toast.LENGTH_SHORT).show();
        editor=sharedPreferences.edit();
        editor.putInt("count", count);
        editor.apply();
        length=sharedPreferences.getInt("length",0);

        try {
            file =new File(Environment.getExternalStorageDirectory().getCanonicalPath()+"/hello");
        } catch (IOException e) {
            e.printStackTrace();
        }

        anim= AnimationUtils.loadAnimation(MainActivity.this, R.anim.practces_one);
        chageableDrawable=(ImageView)findViewById(R.id.clipDrawableImageView);
        clipDrawable=(ClipDrawable)chageableDrawable.getDrawable();
        clipDrawLevel=1000;
        clipDrawable.setLevel(clipDrawLevel);

        headid=new int[]{R.drawable.abc,R.drawable.asd,R.drawable.qwe};
        showMenu=(TextView)findViewById(R.id.showMenu);
        registerForContextMenu(showMenu);

        popupMenu=new PopupMenu(MainActivity.this,findViewById(R.id.popupMenu));
        popupMenu.inflate(R.menu.menu_main);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bgblue:
                        textView.setBackgroundColor(Color.BLUE);
                        break;
                    case R.id.bgwhite:
                        textView.setBackgroundColor(Color.WHITE);
                        break;
                    case R.id.bggreen:
                        textView.setBackgroundColor(Color.GREEN);
                        break;
                    case R.id.txtblack:
                        textView.setTextColor(Color.BLACK);
                        break;
                    case R.id.txtred:
                        textView.setTextColor(Color.RED);
                        break;
                    case R.id.txtyellow:
                        textView.setTextColor(Color.YELLOW);
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.popupMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });


        int NM_ID=0X123;
        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent=new Intent(MainActivity.this,BAty.class);
        PendingIntent pi=PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        Notification notify= new Notification.Builder(MainActivity.this)
                .setAutoCancel(true)
                .setTicker("ikm")
                .setSmallIcon(R.drawable.abc)
                .setContentTitle("adc")
                .setContentText("dsfgdsgdgdgd")
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .build();
        nm.notify(NM_ID, notify);

        numberPicker= (NumberPicker) findViewById(R.id.numberPicker);
        textView= (TextView) findViewById(R.id.textView);
        textView.append("0");
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(99);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textView.setText(R.string.choseNumber);
                textView.append(""+numberPicker.getValue());
            }
        });
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast toast=Toast.makeText(MainActivity.this,textView.getText(),Toast.LENGTH_LONG);
                Toast toast = new Toast(MainActivity.this);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                TextView tv = new TextView(MainActivity.this);
                tv.setText(textView.getText());
                toast.setView(tv);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        imageButton =(ImageView)findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.startAnimation(anim);
                Toast toast = Toast.makeText(MainActivity.this, "你是个二百五", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        });


        imageButton1=(ImageButton)findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
                String[] name={"abc","asd","qwe"};
                String[] text={"text1","text2","text3"};

                for(int i=0;i<headid.length;i++){
                    Map<String,Object> item=new HashMap<String, Object>();
                    item.put("header",headid[i]);
                    item.put("name",name[i]);
                    item.put("text",text[i]);
                    listItems.add(item);
                }
                SimpleAdapter simpleAdapter=new SimpleAdapter(MainActivity.this,listItems,R.layout.test,new String[]{"header","name","text"},new int[]{R.id.header,R.id.name,R.id.text});
                final ListView listView=new ListView(MainActivity.this);
                listView.setAdapter(simpleAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        imageButton1.setImageResource(headid[position]);
                        startActivity(new Intent(MainActivity.this, DAty.class));
                    }
                });
                alertDialog = new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("title")
//                        .setMessage("message")
//                        .setIcon(R.drawable.abc);
                        .setView(listView)
                        .setPositiveButton("startB", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivityForResult(new Intent(MainActivity.this,BAty.class),REQUEST_BATY);
                            }
                        })
                        .setNegativeButton("return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog.show();
            }
        });

        parameter=new PopupWindowParameter();
        ibtA= (ImageButton) findViewById(R.id.ibtA);
        ibtA.setOnClickListener(new View.OnClickListener() {
//            PopupWindow popupWindow;

            @Override
            public void onClick(View v) {
                if(parameter.getPopupWindow()!=null){
                    parameter.getPopupWindow().dismiss();
                }
                View root=MainActivity.this.getLayoutInflater().inflate(R.layout.test1,null);
                final PopupWindow popupWindow=new PopupWindow(root,500,500);
                popupWindow.showAsDropDown(findViewById(R.id.ibtA));
                parameter.setPopupWindow(popupWindow);
                root.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add("test1");
        menu.addSubMenu("test2");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.bgblue:
                textView.setBackgroundColor(Color.BLUE);
                break;
            case R.id.bgwhite:
                textView.setBackgroundColor(Color.WHITE);
                break;
            case R.id.bggreen:
                textView.setBackgroundColor(Color.GREEN);
                break;
            case R.id.txtblack:
                textView.setTextColor(Color.BLACK);
                break;
            case R.id.txtred:
                textView.setTextColor(Color.RED);
                break;
            case R.id.txtyellow:
                textView.setTextColor(Color.YELLOW);
                break;


        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.font_10:
                textView.setTextSize(20);
                break;
            case R.id.font_12:
                textView.setTextSize(24);
                break;
            case R.id.font_14:
                textView.setTextSize(28);
                break;
            case R.id.font_16:
                textView.setTextSize(32);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_BATY&&resultCode==RESULT_FROM_BATY){
            Bundle bundle=data.getExtras();
            int position=bundle.getInt("position");
            if (position<headid.length&&position>-1)     imageButton1.setImageResource(headid[position]);
        }
    }

    public void startC(View source){
        startActivity(new Intent(MainActivity.this, CAty.class));
    }

    public void startD(View source){
        Bundle bundle=new Bundle();
        bundle.putCharSequence("text",textView.getText());
        Intent intent=new Intent(MainActivity.this,DAty.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startE(View source){
        Intent startEAtyIntent=new Intent();
        startEAtyIntent.setAction("hello.app.src.main.java.myself.hello.android.intent.action.EATY_ACTION");
        startActivity(startEAtyIntent);
    }

    public void changeLevel(View source){
//        截取片段，0为无，10000为整个片段
        if(clipDrawLevel==10000){
            clipDrawLevel=1000;
        }
        else{
            clipDrawLevel+=1000;
        }
        clipDrawable.setLevel(clipDrawLevel);
    }

    public void saveText(View source){
        try {
            FileOutputStream outputStream=openFileOutput(FILE_NAME,MODE_PRIVATE);
            byte[] writtenContent=textView.getText().toString().getBytes();
            length=writtenContent.length;
            outputStream.write(writtenContent);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readText(View source){
        if (length>0){
            try {
                FileInputStream inputStream=openFileInput(FILE_NAME);
                byte[] readContent=new byte[length];
                inputStream.read(readContent);
                Toast.makeText(MainActivity.this,new String(readContent),Toast.LENGTH_SHORT).show();
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else Toast.makeText(MainActivity.this,"没有保存过的数据",Toast.LENGTH_SHORT).show();
    }

    public void saveInExternal(View source){
        try {


            if (!file.exists()){
                file.mkdir();
            }
            FileWriter writer=new FileWriter(file.getCanonicalPath()+EXTERNAL_FILE);
            String s=saveTextInExter.getText().toString();
            length1=s.length();
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readInExternal(View source){
        char[] txt=new char[length1];
        try{
            FileReader reader=new FileReader(file.getCanonicalPath()+EXTERNAL_FILE);
            reader.read(txt);
            readTextInExter.setText(txt, 0, length1);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.err.println("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.err.println("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.err.println("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.err.println("onResume");
    }

    @Override
    protected void onDestroy() {
        sharedPreferences=getSharedPreferences("appData",MODE_PRIVATE);
        editor.putInt("length",length);
        editor.commit();
        super.onDestroy();
        System.err.println("onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.err.println("onPause");
    }

}