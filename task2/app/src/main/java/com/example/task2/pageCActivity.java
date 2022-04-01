package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class pageCActivity extends AppCompatActivity {
    private int x=0,y=0,z=0;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_cactivity);

        textResult=findViewById(R.id.textResult);

        Bundle bundle=getIntent().getExtras();
        x= Integer.parseInt(bundle.getString("numberX"));
        y= Integer.parseInt(bundle.getString("numberY"));
        z=x+y;

        textResult.setText(String.valueOf(x)+"+"+String.valueOf(y)+"="+String.valueOf(z));
    }

}