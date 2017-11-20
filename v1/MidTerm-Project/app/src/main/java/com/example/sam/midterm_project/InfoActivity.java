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

    private Map<String, Info> str_inf = new HashMap<>();
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_info);

        Bundle bundle = getIntent().getExtras();
        String n = bundle.getString("name");


        for(int i=0; i < MainActivity.Infos.size(); i++) {
            str_inf.put(MainActivity.Infos.get(i).getName(), MainActivity.Infos.get(i));
            if(MainActivity.Infos.get(i).getName().equals(n)){
                 index  = i;
            }
        }


        final Info p = str_inf.get(n);

        ImageView gp = (ImageView)findViewById(R.id.photo);
      /*  if(p.getBackground().equals("1")){ gp.setImageResource(R.drawable.caocao); }
        else if(p.getBackground().equals("2")){ gp.setImageResource(R.drawable.liubei); }
        else if(p.getBackground().equals("3")){ gp.setImageResource(R.drawable.guanyu); }
        else if(p.getBackground().equals("4")){ gp.setImageResource(R.drawable.zhangfei); }
        else if(p.getBackground().equals("5")){ gp.setImageResource(R.drawable.sunshangxiang); }
        else if(p.getBackground().equals("6")){ gp.setImageResource(R.drawable.sunshangxiang); }
        else if(p.getBackground().equals("7")){ gp.setImageResource(R.drawable.sunquan); }
        else if(p.getBackground().equals("8")){ gp.setImageResource(R.drawable.huatuo); }
        else if(p.getBackground().equals("9")){ gp.setImageResource(R.drawable.zhouyu); }
        else if(p.getBackground().equals("10")){ gp.setImageResource(R.drawable.zhugeliang); }
*/
        gp.setImageResource(p.getBackground());

        Button back=(Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(InfoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Button car=(Button)findViewById(R.id.favor);
        car.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(InfoActivity.this, "角色已添加到收藏", Toast.LENGTH_SHORT).show();
                Map<String, Object> t = new LinkedHashMap<>();
                t.put("background", p.getBackground());
                t.put("name", p.getName());

                //MainActivity.data_car.add(t);
                MainActivity.simpleAdapter.notifyDataSetChanged();

                EventBus.getDefault().post(new EventBusItem(p.getName(), p.getBackground()));

            }
        });

        final ImageView star=(ImageView) findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            /*    if (!tag) {
                    star.setImageResource(R.mipmap.full_star);
                    tag = true;
                } else {
                    star.setImageResource(R.mipmap.empty_star);
                    tag = false;
                }*/
                Intent intent = new Intent(InfoActivity.this,Add.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",p.getName());
                bundle.putString("birth",p.getDate());
                bundle.putString("place",p.getNative_place());
                bundle.putString("message",p.getMessage());
                bundle.putString("sex",p.getSex());
                bundle.putInt("photo",p.getBackground());
                bundle.putInt("index",index);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        TextView name=(TextView)findViewById(R.id.Name);
        name.setText(p.getName());

        TextView native_place=(TextView)findViewById(R.id.native_place);
        native_place.setText(p.getNative_place());

        TextView sex=(TextView)findViewById(R.id.sex);
        sex.setText(p.getSex());

        TextView date=(TextView)findViewById(R.id.date);
        date.setText(p.getDate());

        TextView message=(TextView)findViewById(R.id.more);
        message.setText(p.getMessage());




    }



    private boolean tag = false;
}
