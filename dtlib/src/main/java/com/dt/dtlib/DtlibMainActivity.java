package com.dt.dtlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DtlibMainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtlib_main);
        editText =(EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                Intent intent = new Intent();
                if (str.equals("")){
                    intent.putExtra("btnName",getPackageName());
                    setResult(0,intent);
                    finish();
                }else {
                    intent.putExtra("btnName",str);
                    setResult(1,intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(0);
        super.onBackPressed();
    }
}
