package myself.hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import java.util.*;

public class BAty extends AppCompatActivity {
    private SimpleAdapter adapter;
    private ListView listView;
    private TextView testView;
    final private int RESUIT_TO_MAIN=0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent =getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        testView=(TextView)findViewById(R.id.testView);

        String[] name={"abc","asd","qwe"};
        String[] text={"text1","text2","text3"};
        int[] headid={R.drawable.abc,R.drawable.asd,R.drawable.qwe};
        List <Map<String,Object>> items=new ArrayList<Map<String, Object>>();
        for (int i=0;i<name.length;i++){
            Map<String, Object> map=new HashMap<String,Object>();
            map.put("header",headid[i]);
            map.put("name",name[i]);
            map.put("text",text[i]);
            items.add(map);
        }
        adapter=new SimpleAdapter(this,items,R.layout.test,new String[]{"header","name","text",},new int[]{R.id.header,R.id.name,R.id.text});
        listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                testView.setText("position:"+position+" , id:"+id);
                intent.putExtra("position",position);
//                设置结果码
                BAty.this.setResult(RESUIT_TO_MAIN,intent);
                BAty.this.finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        intent.putExtra("position",-1);
        BAty.this.setResult(RESUIT_TO_MAIN,intent);
        BAty.this.finish();
    }



}
