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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_info);



        for(int i=0; i < MainActivity.Infos.size(); i++) {
            str_inf.put(MainActivity.Infos.get(i).getName(), MainActivity.Infos.get(i));
        }

        Bundle bundle = getIntent().getExtras();
        String n = bundle.getString("name");
        final Info p = str_inf.get(n);

        ImageView gp = (ImageView)findViewById(R.id.photo);
        if(p.getBackground().equals("1")){ gp.setImageResource(R.drawable.caocao); }
        else if(p.getBackground().equals("2")){ gp.setImageResource(R.drawable.liubei); }
        else if(p.getBackground().equals("3")){ gp.setImageResource(R.drawable.guanyu); }
        else if(p.getBackground().equals("4")){ gp.setImageResource(R.drawable.zhangfei); }
        else if(p.getBackground().equals("5")){ gp.setImageResource(R.drawable.sunshangxiang); }
        else if(p.getBackground().equals("6")){ gp.setImageResource(R.drawable.sunshangxiang); }
        else if(p.getBackground().equals("7")){ gp.setImageResource(R.drawable.sunquan); }
        else if(p.getBackground().equals("8")){ gp.setImageResource(R.drawable.huatuo); }
        else if(p.getBackground().equals("9")){ gp.setImageResource(R.drawable.zhouyu); }
        else if(p.getBackground().equals("10")){ gp.setImageResource(R.drawable.zhugeliang); }


        Button back=(Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        Button car=(Button)findViewById(R.id.favor);
        car.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(InfoActivity.this, "角色已添加到收藏", Toast.LENGTH_SHORT).show();
                Map<String, Object> t = new LinkedHashMap<>();
                t.put("cycle", p.get_first_letter());
                t.put("name", p.getName());

                //MainActivity.data_car.add(t);
                MainActivity.simpleAdapter.notifyDataSetChanged();

                EventBus.getDefault().post(new EventBusItem(p.getName(), p.getNative_place()));

            }
        });

        final ImageView star=(ImageView) findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!tag) {
                    star.setImageResource(R.mipmap.full_star);
                    tag = true;
                } else {
                    star.setImageResource(R.mipmap.empty_star);
                    tag = false;
                }
            }
        });


        TextView name=(TextView)findViewById(R.id.Name);
        name.setText(p.getName());

        TextView native_place=(TextView)findViewById(R.id.native_place);
        native_place.setText(p.getNative_place());

        TextView clique=(TextView)findViewById(R.id.clique);
        clique.setText(p.getClique());

        TextView date=(TextView)findViewById(R.id.date);
        date.setText(p.getDate());

        TextView message=(TextView)findViewById(R.id.more);
        message.setText(p.getMessage());

        /*String[] operations1 = new String[]{"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.more, operations1);
        ListView listView1 = (ListView) findViewById(R.id.more);
        listView1.setAdapter(arrayAdapter1);*/

        /*String[] operations2 = new String[]{"一键下单", "分享商品", "不感兴趣", "查看更多商品促销信息"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.more, operations2);
        ListView listView2 = (ListView) findViewById(R.id.listview);
        listView2.setAdapter(arrayAdapter2);*/


    }



    private boolean tag = false;
}
