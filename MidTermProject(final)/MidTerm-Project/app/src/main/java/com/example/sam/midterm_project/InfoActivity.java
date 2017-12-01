package com.example.sam.midterm_project;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sam on 2017/11/20.
 */


public class InfoActivity extends AppCompatActivity {

    private Map<String, Info> str_inf = new HashMap<>();//将名字和人物对象Infos对应起来，给出名字得到对应Info
    private int index;//接收到的名字在Infos中对应的位置
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_info);

        Bundle bundle = getIntent().getExtras();
        String n = bundle.getString("name");//接收name

        for(int i=0; i < MainActivity.Infos.size(); i++) {
            str_inf.put(MainActivity.Infos.get(i).getName(), MainActivity.Infos.get(i)); //将名字映射到对象
            //记录接收到的名字在Infos队列的位置
            if(MainActivity.Infos.get(i).getName().equals(n)){
                index  = i;
            }
        }

        final Info p = str_inf.get(n); //我们得到的任务对象

        ImageView gp = (ImageView)findViewById(R.id.photo); //头像背景
        gp.setImageResource(p.getBackground());//显示头像

        TextView name=(TextView)findViewById(R.id.Name);//名字
        name.setText(p.getName());//显示名字

        TextView native_place=(TextView)findViewById(R.id.native_place);//籍贯
        native_place.setText(p.getNative_place());//显示籍贯

        TextView sex=(TextView)findViewById(R.id.sex);//性别
        sex.setText(p.getSex());//显示性别

        TextView date=(TextView)findViewById(R.id.date);//生日
        date.setText(p.getDate());//显示生日

        TextView clique=(TextView)findViewById(R.id.clique);//主效势力
        clique.setText(p.getClique());//显示主效势力

        TextView message=(TextView)findViewById(R.id.more);//更多信息
        message.setText(p.getMessage());//显示更多信息

        Button back=(Button)findViewById(R.id.Back); //返回图标
        //返回到MainActivity
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(InfoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Button favorbutton=(Button)findViewById(R.id.favor);//收藏图标
        //点击收藏
        favorbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(InfoActivity.this, "角色已添加到收藏", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new EventBusItem(p.getName(), p.getBackground()));//发送EventBus
            }
        });

        //final ImageView editbutton=(ImageView) findViewById(R.id.star);//修改图标
        Button editbutton=(Button)findViewById(R.id.star);//收藏图标
        //点击跳转到Add，传入要编辑的值
        editbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(InfoActivity.this,Add.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",p.getName());
                bundle.putString("birth",p.getDate());
                bundle.putString("place",p.getNative_place());
                bundle.putString("message",p.getMessage());
                bundle.putString("sex",p.getSex());
                bundle.putInt("photo",p.getBackground());
                bundle.putInt("index",index); //发送在Info的位置
                bundle.putString("clique",p.getClique());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

}
