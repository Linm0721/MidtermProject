package com.example.sam.midterm_project;

import android.widget.Button;
import android.widget.TextView;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by win on 2017/11/25.
 */

public class game extends AppCompatActivity{
    private String [] names = {"曹操","刘备","关羽","张飞","貂蝉","孙尚香","孙权","华佗","周瑜","诸葛亮"};
    private int [] imageid = {R.mipmap.caocao,R.mipmap.liubei,R.mipmap.guanyu,R.mipmap.zhangfei,R.mipmap.diaochan,R.mipmap.sunshangxiang,R.mipmap.sunquan,R.mipmap.huatuo,R.mipmap.zhouyu,R.mipmap.zhugeliang};
    private TextView name1Text;
    private TextView name2Text;
    private TextView attack1Text;
    private TextView attack2Text;
    private TextView defense1Text;
    private TextView defense2Text;
    private ImageView imageView1;
    private ImageView imageView2;
    private Button YES; //确认按钮
    private Button CANCEL; //取消按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        name1Text =(TextView) findViewById(R.id.name1);
        name2Text =(TextView) findViewById(R.id.name2);
        attack1Text =(TextView) findViewById(R.id.attack1);
        attack2Text =(TextView) findViewById(R.id.attack2);
        defense1Text=(TextView) findViewById(R.id.defense1);
        defense2Text=(TextView) findViewById(R.id.defense2);
        imageView1 = (ImageView)findViewById(R.id.view1);
        imageView2 = (ImageView)findViewById(R.id.view2);
        YES = (Button)findViewById(R.id.yes);
        CANCEL = (Button)findViewById(R.id.cancel);


        Random random = new Random(); //定义一个随机数的类
        int index1=random.nextInt(10);
        int index2=random.nextInt(10);
        name1Text.setText("姓名：  "+names[index1]);
        name2Text.setText("姓名：  "+names[index2]);
        final int i3=random.nextInt(100);
        final int i4=random.nextInt(100);
        final int i5=random.nextInt(100);
        final int i6=random.nextInt(100);


        imageView1.setImageResource(imageid[index1]);
        imageView2.setImageResource(imageid[index2]);
        String s1;
        s1=String.valueOf(i3);
        attack1Text.setText("攻击：  "+s1);

        String s2;
        s2=String.valueOf(i4);
        attack2Text.setText("攻击：  "+s2);

        String s3;
        s3=String.valueOf(i5);
        defense1Text.setText("防御：  "+s3);

        String s4;
        s4=String.valueOf(i6);
        defense2Text.setText("防御：  "+s4);

        //点击确认按钮
        YES.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (i3+i5>i4+i6){
                    Toast.makeText(game.this, "恭喜，您的武将打败了对方武将", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(game.this, "很遗憾，您的武将被打败了", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //取消，结束该页面
        CANCEL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

    }

}
