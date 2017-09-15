package com.example.siwei.s5;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.textView);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message m){
                textView.setText("耗时运算"+m.arg1);
            }
        };

        final Runnable work = new Runnable() {
            @Override
            public void run() {
                int p = 0;
                while (p <= 100){
                    Message m = new Message();
                    m.arg1 = p;
                    handler.sendMessage(m);
                    p+=10;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Message m = handler.obtainMessage();
                m.arg1=-1;
                handler.sendMessage(m);
            }
        };

        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread wt = new Thread(null,work,"wt");
                wt.start();
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("post");
            }
        });
    }
}


