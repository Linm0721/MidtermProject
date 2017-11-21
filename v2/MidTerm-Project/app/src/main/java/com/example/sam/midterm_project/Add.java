package com.example.sam.midterm_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.sam.midterm_project.MainActivity.Infos;


public class Add extends AppCompatActivity {


    private TextInputLayout NAME; //名字控件
    private TextInputLayout BIRTH; //生日控件
    private TextInputLayout PLACE; //籍贯控件
    private TextInputLayout OTHER; //其它信息控件

    private EditText EditName; //编辑名字
    private EditText EditBirth; //编辑生日
    private EditText EditPlace; //编辑籍贯
    private EditText EditOther; //编辑其它
    private RadioGroup RG; //性别选项RadioGroup
    private RadioButton MAN; //选项男
    private RadioButton WOMAN; //选项女
    private Button YES; //确认按钮
    private Button CANCEL; //取消按钮
    private ImageView PHOTO; //头像

    private int photochoose=R.mipmap.p0;//选择的头像id
    private RecyclerView photo_recyclerView; //头像选择RecyclerView
    private CommonAdapter photo_commonAdapter; //RecyclerView适配器
    protected List<Map<String, Object>> photolist = new ArrayList<>();//头像队列
    private boolean editmode=false;//编辑模式 初始为false
    private int index=-1; //位置下标
    private String name="" ;//名字
    private String birth=""; //生日
    private String place=""; //籍贯
    private String sex="男"; //性别初始
    private String other=""; //其它信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        NAME = (TextInputLayout)findViewById(R.id.textinputlayout); //名字控件
        BIRTH = (TextInputLayout)findViewById(R.id.textinputlayout1); //生日控件
        PLACE = (TextInputLayout)findViewById(R.id.textinputlayout2); //籍贯控件
        OTHER = (TextInputLayout)findViewById(R.id.textinputlayout3); //其它信息控件
        EditName = (EditText)findViewById(R.id.name); //名字编辑框
        EditBirth = (EditText)findViewById(R.id.birth); //生日编辑框
        EditPlace = (EditText)findViewById(R.id.place); //籍贯编辑框
        EditOther = (EditText)findViewById(R.id.otherinfo); //其他信息编辑框
        RG = (RadioGroup)findViewById(R.id.radioGroup); //选择性别
        MAN = (RadioButton)findViewById(R.id.man); //选项男
        WOMAN = (RadioButton)findViewById(R.id.woman); //选项女
        YES = (Button)findViewById(R.id.yes); //确认按钮
        CANCEL = (Button)findViewById(R.id.cancel); //取消按钮
        PHOTO = (ImageView)findViewById(R.id.imageView); //头像

        Bundle bundle = getIntent().getExtras();
        //bundle为空，属于新建人物的情况
        if(bundle==null){
            Log.e("aa","coming");
        }
        //bundle不为空，属于编辑人物的情况
        else{
            Log.e("aa","no coming");
            editmode = true; //编辑模式为true
            //设置编辑框的内容 和 初始头像
            EditName.setText(bundle.getString("name"));
            EditBirth.setText(bundle.getString("birth"));
            EditPlace.setText(bundle.getString("place"));
            EditOther.setText(bundle.getString("message"));
            PHOTO.setImageResource(bundle.getInt("photo"));
            photochoose = bundle.getInt("photo");
            index = bundle.getInt("index"); //得到该对象在Infos中的位置
            sex = bundle.getString("sex");
            if(sex.equals("男")){
                MAN.setChecked(true);//性别选项初始选择男
            }
            else{
                MAN.setChecked(false);
                WOMAN.setChecked(true);//性别选项初始选择女
            }
        }

        //可供选择的头像列表
        int photoid[]={R.mipmap.p1,R.mipmap.p2,R.mipmap.p3,R.mipmap.p4,R.mipmap.p5,R.mipmap.p6,R.mipmap.p7,R.mipmap.p8,R.mipmap.p9,R.mipmap.p10};
        for(int i=0; i<10; i++){
            Map<String,Object> temp=new LinkedHashMap<>();
            temp.put("photo",photoid[i]);
            photolist.add(temp);
        }
        photo_recyclerView = (RecyclerView)findViewById(R.id.photo_recyclerview);//头像选择列表布局
        photo_recyclerView.setNestedScrollingEnabled(true);
        photo_recyclerView.setLayoutManager(new GridLayoutManager(this,5));//网格布局 5张为一行

        //重写适配器中的convert方法进行数据绑定
        photo_commonAdapter=new CommonAdapter<Map<String,Object>>(this,R.layout.horizontal_list_item,photolist) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {

                ImageView first = holder.getView(R.id.photo);
                first.setImageResource(s.get("photo").hashCode());
            }

        };
        photo_recyclerView.setAdapter(photo_commonAdapter);
        photo_commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            //单击
            public void onClick(int position) {
                photochoose = photolist.get(position).get("photo").hashCode();//获取图片id
                PHOTO.setImageResource(photochoose);//将大头像设置为单击的头像
            }

            @Override
            //长按
            public void onLongClick(int position) {

            }
        });


        //根据用户性别选择 修改sex
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                                          @Override
           public void onCheckedChanged(RadioGroup group, int checkedID){
                if(checkedID == MAN.getId()){
                       sex = "男";
                 }
                 else if(checkedID == WOMAN.getId()){
                       sex = "女";
                }
           };}
        );
        //点击确认按钮
        YES.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = EditName.getText().toString();
                birth = EditBirth.getText().toString();
                place = EditPlace.getText().toString();
                other = EditOther.getText().toString();
                NAME.setErrorEnabled(false);
                BIRTH.setErrorEnabled(false);
                PLACE.setErrorEnabled(false);
                if(name.isEmpty()){
                    NAME.setErrorEnabled(true);
                    NAME.setError("姓名不能为空");
                }
                else if(birth.isEmpty()){
                    BIRTH.setErrorEnabled(true);
                    BIRTH.setError("生日不能为空");
                }
                else if(place.isEmpty()){
                    PLACE.setErrorEnabled(true);
                    PLACE.setError("籍贯不能为空");
                }
                else{
                    //添加人物模式
                    if(editmode==false){
                        MainActivity.Infos.add(new Info(name,place,sex,birth,photochoose,other)); //添加到Infos
                        Map<String,Object> temp=new LinkedHashMap<>();
                        temp.put("name",name);
                        temp.put("background",photochoose);
                        MainActivity.data.add(temp); //添加到Adapter的人物队列
                        MainActivity.commonAdapter.notifyItemInserted(MainActivity.data.size()-1); //更新Adapter
                    }
                    //编辑人物模式
                    else{
                        //修改相应对象的信息
                        MainActivity.Infos.get(index).setName(name);
                        MainActivity.Infos.get(index).setDate(birth);
                        MainActivity.Infos.get(index).setSex(sex);
                        MainActivity.Infos.get(index).setBackground(photochoose);
                        MainActivity.Infos.get(index).setNative_place(place);
                        MainActivity.Infos.get(index).setMessage(other);
                        //更新收藏队列的头像
                        for(int i=0; i<MainActivity.favor_list.size(); i++){
                            if(MainActivity.favor_list.get(i).get("name").equals(name)){
                                MainActivity.favor_list.get(i).put("background",photochoose);
                                MainActivity.simpleAdapter.notifyDataSetChanged();
                            }
                        }
                        //删除原本data中的对象
                        MainActivity.data.remove(index);
                        MainActivity.commonAdapter.notifyItemRemoved(index);

                        Map<String,Object> temp=new LinkedHashMap<>();
                        temp.put("name",name);
                        temp.put("background",photochoose);

                        //在原位置加上新的对象
                        MainActivity.data.add(index,temp);
                        MainActivity.commonAdapter.notifyItemInserted(index);
                        MainActivity.commonAdapter.notifyItemRangeChanged(index,MainActivity.data.size()-index);

                    }
                    //点击确认后跳转到人物介绍页面，传入名字
                    Intent intent = new Intent(Add.this,InfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
