package com.example.sam.midterm_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import com.example.sam.midterm_project.CommonAdapter;
import com.example.sam.midterm_project.InfoActivity;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

/**
 * Created by sam on 2017/10/24.
 */

public class MainActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;//人物列表 Recylerview
    public static CommonAdapter commonAdapter;//Recyclerview适配器
    public static SimpleAdapter simpleAdapter;//收藏列表 listview适配器
    public static ListView listView ;//收藏列表 ListView
    public static List<Map<String, Object>> data = new ArrayList<>();//人物列表队列
    private boolean tag = false;//false表示处于人物列表，true表示处于收藏列表
    //收藏队列
    public static List<Map<String, Object>> favor_list = new ArrayList<Map<String, Object>>() {
        {
            Map<String, Object> t = new LinkedHashMap<>();
            t.put("background", R.mipmap.sanguo);
            t.put("name", "人物姓名");
            add(t);
        }
    };
    //人物对象队列
    public static List<Info> Infos = new ArrayList<Info>() {{
       add(new Info("曹操", "籍贯：豫州沛国谯（安徽亳州市亳县）", "男", "生卒（155 - 220）",R.mipmap.caocao,"曹操是西园八校尉之一，曾只身行刺董卓，" +
                     "失败后和袁绍共同联合天下诸侯讨伐董卓，后独自发展自身势力，一生中先后战胜了袁术、吕布、张绣、袁绍、刘表、张鲁、" +
                     "马超等割据势力，统一了北方。但是在南下讨伐江东的战役中，曹操在赤壁惨败。后来在和蜀汉的汉中争夺战中，曹操再次无功而返。" +
                     "曹操一生未称帝，他病死后，曹丕继位后不久称帝，追封曹操为魏武皇帝。"));

        add(new Info("刘备", "籍贯：幽州涿郡涿（河北保定市涿州）", "男", "生卒（161 - 223）",R.mipmap.liubei,"刘备，蜀汉的开国皇帝，汉景帝之子中山靖王" +
                     "刘胜的后代。刘备少年孤贫，以贩鞋织草席为生。黄巾起义时，刘备与关羽、张飞桃园结义，成为异姓兄弟，一同剿除黄巾，有功，" +
                     "任安喜县尉，不久辞官；董卓乱政之际，刘备随公孙瓒讨伐董卓，三人在虎牢关战败吕布。后诸侯割据，刘备势力弱小，经常寄人篱下，" +
                     "先后投靠过公孙瓒、曹操、袁绍、刘表等人，几经波折，却仍无自己的地盘。赤壁之战前夕，刘备在荆州三顾茅庐，请诸葛亮出山辅助，" +
                     "在赤壁之战中，联合孙权打败曹操，奠定了三分天下的基础。刘备在诸葛亮的帮助下占领荆州，不久又进兵益州，夺取汉中，建立了横跨荆益两州的政权。" +
                     "后关羽战死，荆州被孙权夺取，刘备大怒，于称帝后伐吴，在夷陵之战中为陆逊用火攻打得大败，不久病逝于白帝城，临终托孤于诸葛亮。"));

        add(new Info("关羽", "籍贯：司隶河东郡解（山西运城市临猗县西南）", "男", "生卒（？ - 219）",R.mipmap.guanyu,"因本处势豪倚势凌人，关羽杀之而逃难江湖。" +
                     "闻涿县招军破贼，特来应募。与刘备、张飞桃园结义，羽居其次。使八十二斤青龙偃月刀随刘备东征西讨。虎牢关温酒斩华雄，屯土山降汉不降曹。" +
                     "为报恩斩颜良、诛文丑，解曹操白马之围。后得知刘备音信，过五关斩六将，千里寻兄。刘备平定益州后，封关羽为五虎大将之首，督荆州事。羽起军攻曹，" +
                     "放水淹七军，威震华夏。围樊城右臂中箭，幸得华佗医治，刮骨疗伤。但未曾提防东吴袭荆州，关羽父子败走麦城，突围中被捕，不屈遭害。"));

        add(new Info("张飞", "籍贯：幽州涿郡（河北保定市涿州）", "男", "生卒（？ - 221）",R.mipmap.zhangfei,"与刘备和关羽桃园结义，张飞居第三。随刘备征讨黄巾，刘备终因功被" +
                     "朝廷受予平原相，后张飞鞭挞欲受赂的督邮。十八路诸侯讨董时，三英战吕布，其勇为世人所知。曹操以二虎竞食之计迫刘备讨袁术，刘备以张飞守徐州，诫禁酒，" +
                     "但还是因此而鞭打曹豹招致吕布东袭。刘备反曹后，反用劫寨计擒曹将刘岱，为刘备所赞。徐州终为曹操所破，张飞与刘备失散，占据古城。误以为降汉的关羽投敌，" +
                     "差点一矛将其杀掉。曹操降荊州后引骑追击，刘备败逃，张飞引二十余骑，立马于长阪桥，吓退曹军数十里。庞统死后刘备召其入蜀，张飞率军沿江而上，智擒巴郡太守" +
                     "严颜并生获之，张飞壮而释放。于葭萌关和马超战至夜间，双方点灯，终大战数百回合。瓦口关之战时扮作醉酒，智破张郃。后封为蜀汉五虎大将。及关羽卒，" +
                     "张飞悲痛万分，每日饮酒鞭打部下，导致为帐下将张达、范强所杀，他们持其首顺流而奔孙权。"));

        add(new Info("貂蝉", "籍贯：无", "女", "生卒 无",R.mipmap.diaochan,"舍身报国的可敬女子，她为了挽救天下黎民，为了推翻权臣董卓的荒淫统治，受王允所托，上演了可歌可泣的连环计" +
                     "（连环美人计），周旋于两个男人之间，成功的离间了董卓和吕布，最终吕布将董卓杀死，结束了董卓专权的黑暗时期。"));

        add(new Info("孙尚香", "籍贯：扬州会稽郡诸暨（浙江诸暨市）", "女", "生卒（？ - ？）",R.mipmap.sunshangxiang,"孙尚香之名为日后戏曲所创作出来的。刘备向东吴借荆州不还，鲁肃身负关系；" +
                     "周瑜一为救友，二为国计，于是上书孙权，教使「美人计」，进妹予刘备为夫人，诱其丧志而疏远属下。孙夫人才捷刚猛，有诸兄之风，身边侍婢百余人，皆亲自执刀侍立。" +
                     "不料在诸葛亮的锦囊妙计安排下，假婚成真姻；后来夫人更助刘备返蜀，于路上怒斥追袭的吴将。后刘备入益州，使赵云领留营司马，留守荆州。此时孙权闻知刘备西征，" +
                     "于是遣周善引领舟船以迎孙夫人，而夫人带着后主刘禅回吴，幸得赵云与张飞勒兵截江，方重夺刘禅。彝陵之战，刘备战败，有讹言传入吴中，道刘备已死，孙夫人伤心不已，" +
                     "望西痛哭，投江而死。后人为其立庙，号曰「枭姬庙」。"));

        add(new Info("孙权", "籍贯：司隶河东郡解（山西运城市临猗县西南）", "男", "生卒（？ - 219）",R.mipmap.sunquan,"孙权19岁就继承了其兄孙策之位，力据江东，击败了黄祖。后东吴联合刘备，" +
                     "在赤壁大战击溃了曹操军。东吴后来又和曹操军在合肥附近鏖战，并从刘备手中夺回荆州、杀死关羽、大破刘备的讨伐军。曹丕称帝后孙权先向北方称臣，后自己建吴称帝，" +
                     "迁都建业。"));

        add(new Info("华佗", "籍贯：豫州沛国谯（安徽亳州市亳县）", "男", "生卒（？ - ？）",R.mipmap.huatuo,
                     "人称神医，传有百岁。以麻沸散为麻醉药施行手术。曾经救重伤的周泰、为关羽刮骨疗毒。后被怀疑有暗杀曹操的企图，" +
                     "死在狱中。托付给狱吏的医学书《青囊书》也被烧掉大半。"));

        add(new Info("周瑜","籍贯：司隶河东郡解（山西运城市临猗县西南）", "男", "生卒（？ - 219）",R.mipmap.zhouyu,"偏将军、南郡太守。自幼与孙策交好，策离袁术讨江东，瑜引兵从之。" +
                     "为中郎将，孙策相待甚厚，又同娶二乔。策临终，嘱弟权曰：“外事不决，可问周瑜”。瑜奔丧还吴，与张昭共佐权，并荐鲁肃等，掌军政大事。赤壁战前，瑜自鄱阳归。" +
                     "力主战曹，后于群英会戏蒋干、怒打黄盖行诈降计、后火烧曹军，大败之。后下南郡与曹仁相持，中箭负伤，与诸葛亮较智斗，定假涂灭虢等计，皆为亮破，后气死于巴陵，" +
                     "年三十六岁。临终，上书荐鲁肃代其位，权为其素服吊丧。"));
        add(new Info("诸葛亮", "籍贯：司隶河东郡解（山西运城市临猗县西南）", "男", "生卒（？ - 219）",R.mipmap.zhugeliang,
                     "人称卧龙先生，有经天纬地之才，鬼神不测之机。刘皇叔三顾茅庐，遂允出山相助。曾舌战群儒、借东风、" +
                     "智算华容、三气周瑜，辅佐刘备于赤壁之战大败曹操，更取得荆州为基本。后奉命率军入川，于定军山智激老黄忠，" +
                     "斩杀夏侯渊，败走曹操，夺取汉中。刘备伐吴失败，受遗诏托孤，安居平五路，七纵平蛮，六出祁山，鞠躬尽瘁，死而后已。" +
                     "其手摇羽扇，运筹帷幄的潇洒形象，千百年来已成为人们心中“智慧”的代名词。"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);//接收收藏信息，更新收藏列表listview

        //为每一项数据创建一个对象，并添加在List中
        //data队列中 有name和background
        String[] name=new String[Infos.size()];
        for(int i=0;i<Infos.size();i++){
            String x=Infos.get(i).getName();
            name[i]=x;
        }
        int[] background = new int[Infos.size()];
        for(int i=0;i<Infos.size();i++){
            int x=Infos.get(i).getBackground();
            background[i]=x;
        }
        for(int i=0;i<Infos.size();i++){
            Map<String,Object> temp=new LinkedHashMap<>();
            temp.put("name",name[i]);
            temp.put("background",background[i]);
            data.add(temp);
        }

        recyclerView = (RecyclerView)findViewById(R.id.character_list_recyclerview);//找到相应布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//设置为线性布局

        //重写适配器中的convert方法进行数据绑定
        commonAdapter=new CommonAdapter<Map<String,Object>>(this,R.layout.character_item,data) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                TextView name = holder.getView(R.id.character_name);
                name.setText(s.get("name").toString());
                ImageView first = holder.getView(R.id.cycle_and_first＿letter);
                first.setImageResource(s.get("background").hashCode());
            }

        };

        //recyclerView.setAdapter(commonAdapter);
        //设置动画
        ScaleInAnimationAdapter animationAdapter=new ScaleInAnimationAdapter(commonAdapter);
        animationAdapter.setDuration(1000);
        recyclerView.setAdapter(animationAdapter);
        recyclerView.setItemAnimator(new OvershootInLeftAnimator());

        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            //单击RecyclerView的对象，传递name给InfoActivity，实现跳转
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",data.get(position).get("name").toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            //长按RecyclerView的对象，删除
            public void onLongClick(int position) {
                if(position<Infos.size()){
                    commonAdapter.removeItem(position);//适配器相应删除
                    Log.e("data",data.get(3).get("name").toString());
                    Infos.remove(position);//人物队列相应删除
                    Log.e("Info",Infos.get(3).getName());
                    Toast.makeText(MainActivity.this, "移除第"+(position+1)+"个角色", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //收藏列表
        listView = (ListView)findViewById(R.id.favorlist);//布局
        simpleAdapter = new SimpleAdapter(this, favor_list, R.layout.character_item, new String[]{"background", "name"},
                new int[]{R.id.cycle_and_first＿letter, R.id.character_name});
        listView.setAdapter(simpleAdapter);//设置适配器

        //单击收藏列表中的角色，发送name给InfoActivity，跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    Bundle bundle = new Bundle();
                    String personname = favor_list.get(i).get("name").toString();
                    boolean flag = false;
                    for(int j=0; j<Infos.size(); j++){
                        if(personname.equals(Infos.get(j).getName())){ //如果在Infos中找到对象名
                            flag = true;
                            break;
                        }
                    }
                    if(flag==true){//找到相应对象，跳转
                        bundle.putString("name",personname);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{ //名字已修改或删除
                        Toast.makeText(MainActivity.this, "该对象名已修改或不存在", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //长按收藏列表中的角色 删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0){
                    AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                    message.setTitle("移除角色");
                    message.setMessage("从列表移除"+favor_list.get(position).get("name").toString());
                    message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            favor_list.remove(position);
                            simpleAdapter.notifyDataSetChanged();
                        }
                    });
                    message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    message.create().show();
                }
                return true;
            }
        });


        listView.setVisibility(View.GONE);//初始化收藏界面不显示
        final FloatingActionButton convert= (FloatingActionButton)findViewById(R.id.myfavor);//人物列表和收藏界面切换的按钮

        convert.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //当前是人物列表界面
                if(!tag) {
                    convert.setImageResource(R.mipmap.book);//切换为书的图标
                    tag = true;
                    listView.setVisibility(View.VISIBLE);//显示收藏界面
                    recyclerView.setVisibility(View.GONE);//隐藏人物列表
                }
                //当前是收藏界面
                else {
                    convert.setImageResource(R.mipmap.favor);//切换为收藏图标
                    tag = false;
                    listView.setVisibility(View.GONE);//隐藏收藏界面
                    recyclerView.setVisibility(View.VISIBLE);//显示人物列表
                }
            }

        });
        final FloatingActionButton addbutton= (FloatingActionButton)findViewById(R.id.add);//添加按钮
        //单击添加按钮，跳转到添加人物界面
        addbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,Add.class);
                startActivity(intent);
            }

        });
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    //EventBus实现人物界面点击收藏会添加到收藏夹
    public void EvenBusMainThread(EventBusItem event){
        Map<String,Object> temp = new LinkedHashMap<>();
        temp.put("background",event.get_background());
        temp.put("name",event.get_name());
        favor_list.add(temp);
    }
    //注销EventBus
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}