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


    private TextInputLayout NAME;
    private TextInputLayout BIRTH;
    private TextInputLayout PLACE;
    private TextInputLayout OTHER;

    private EditText EditName;
    private EditText EditBirth;
    private EditText EditPlace;
    private EditText EditOther;
    private RadioGroup RG;
    private RadioButton MAN;
    private RadioButton WOMAN;
    private Button YES;
    private Button CANCEL;
    private String sex="男";
    private  ImageView PHOTO;
    private int photochoose=R.mipmap.p0;//选择的头像
    private RecyclerView photo_recyclerView;
    private CommonAdapter photo_commonAdapter;
    protected List<Map<String, Object>> data = new ArrayList<>();
    private boolean editmode=false;
    private int index=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        NAME = (TextInputLayout)findViewById(R.id.textinputlayout);
        BIRTH = (TextInputLayout)findViewById(R.id.textinputlayout1);
        PLACE = (TextInputLayout)findViewById(R.id.textinputlayout2);
        OTHER = (TextInputLayout)findViewById(R.id.textinputlayout3);
        EditName = (EditText)findViewById(R.id.name);
        EditBirth = (EditText)findViewById(R.id.birth);
        EditPlace = (EditText)findViewById(R.id.place);
        EditOther = (EditText)findViewById(R.id.otherinfo);
        RG = (RadioGroup)findViewById(R.id.radioGroup);
        MAN = (RadioButton)findViewById(R.id.man);
        WOMAN = (RadioButton)findViewById(R.id.woman);
        YES = (Button)findViewById(R.id.yes);
        CANCEL = (Button)findViewById(R.id.cancel);
        PHOTO = (ImageView)findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();

        if(bundle==null){
            Log.e("aa","coming");
        }
        else{
            Log.e("aa","no coming");

                Log.e("aa","name right");
            editmode = true;
            EditName.setText(bundle.getString("name"));
            EditBirth.setText(bundle.getString("birth"));
            EditPlace.setText(bundle.getString("place"));
            EditOther.setText(bundle.getString("message"));
            PHOTO.setImageResource(bundle.getInt("photo"));
            photochoose = bundle.getInt("photo");
            index = bundle.getInt("index");
            sex = bundle.getString("sex");
            if(sex.equals("男")){
                MAN.setChecked(true);
            }
            else{
                MAN.setChecked(false);
                WOMAN.setChecked(true);
            }
        }




        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                                          @Override
                                          public void onCheckedChanged(RadioGroup group, int checkedID){

                                              if(checkedID == MAN.getId()){
                                                  sex = "男";
                                              }
                                              else if(checkedID == WOMAN.getId()){
                                                  sex = "女";

                                              }

                                          };
                                      }
        );
        YES.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = EditName.getText().toString();
                String birth = EditBirth.getText().toString();
                String place = EditPlace.getText().toString();
                String other = EditOther.getText().toString();
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

                    if(editmode==false){
                        MainActivity.Infos.add(new Info(name,place,sex,birth,photochoose,other));
                        Map<String,Object> temp=new LinkedHashMap<>();
                        temp.put("name",name);
                        temp.put("background",photochoose);

                        MainActivity.data.add(temp);
                        MainActivity.commonAdapter.notifyItemInserted(MainActivity.data.size()-1);
                    }
                    else{

                        MainActivity.Infos.get(index).setName(name);
                        MainActivity.Infos.get(index).setDate(birth);
                        MainActivity.Infos.get(index).setSex(sex);
                        MainActivity.Infos.get(index).setBackground(photochoose);
                        MainActivity.Infos.get(index).setNative_place(place);
                        MainActivity.Infos.get(index).setMessage(other);
                       MainActivity.data.remove(index);
                        MainActivity.commonAdapter.notifyItemRemoved(index);

                        Map<String,Object> temp=new LinkedHashMap<>();
                        temp.put("name",name);
                        temp.put("background",photochoose);
                        MainActivity.data.add(index,temp);
                        MainActivity.commonAdapter.notifyItemInserted(index);

                        MainActivity.commonAdapter.notifyItemRangeChanged(index,MainActivity.data.size()-index);

                    }

                    Intent intent = new Intent(Add.this,InfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        CANCEL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        int photoid[]={R.mipmap.p1,R.mipmap.p2,R.mipmap.p3,R.mipmap.p4,R.mipmap.p5,R.mipmap.p6,R.mipmap.p7,R.mipmap.p8,R.mipmap.p9,R.mipmap.p10};
        for(int i=0; i<10; i++){
            Map<String,Object> temp=new LinkedHashMap<>();
            temp.put("photo",photoid[i]);

            data.add(temp);
        }
        photo_recyclerView = (RecyclerView)findViewById(R.id.photo_recyclerview);
        photo_recyclerView.setNestedScrollingEnabled(true);
        photo_recyclerView.setLayoutManager(new GridLayoutManager(this,5));

        //重写适配器中的convert方法进行数据绑定
        photo_commonAdapter=new CommonAdapter<Map<String,Object>>(this,R.layout.horizontal_list_item,data) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {

            /*    TextView first = holder.getView(R.id.cycle_and_first＿letter);
                first.setBackgroundResource(s.get("background").hashCode());*/
                ImageView first = holder.getView(R.id.photo);
                first.setImageResource(s.get("photo").hashCode());
            }

        };
        photo_recyclerView.setAdapter(photo_commonAdapter);
        photo_commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            //单击
            public void onClick(int position) {
                photochoose = data.get(position).get("photo").hashCode();
                PHOTO.setImageResource(photochoose);
            }

            @Override
            //长按
            public void onLongClick(int position) {

            }
        });

    }


}
