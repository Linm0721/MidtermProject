package com.example.sam.midterm_project;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

/**
 * Created by sam on 2017/11/25.
 */


public class Romance_of_the_three_Kingdoms_Story extends AppCompatActivity {
    private String[] mItems;//菜单内容
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private  Toolbar toolbar;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romance_of_the_three_kingdoms_story);
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_personal);//按钮图标
        mItems = getResources().getStringArray(R.array.chapters);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());//设置监听
        if (savedInstanceState == null) {
            selectItem(0);//初始化
        }
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void init() {
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)findViewById(R.id.left_drawer);
    }
    //点击菜单项
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            selectItem(position);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void selectItem(int position) {
        //新的碎片view
        Fragment MFragment=new PlanetFragment();
        Bundle args=new Bundle();
        args.putInt(PlanetFragment.ARG_Chapter_NUMBER,position);
        MFragment.setArguments(args);
        //替换内容
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, MFragment).commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    //更改提示栏
    @Override
    public void setTitle(CharSequence title) {
        mTitle=title;
        getSupportActionBar().setTitle(mTitle);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PlanetFragment extends Fragment {
        public static final String ARG_Chapter_NUMBER="chapters";
        public PlanetFragment(){}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView=inflater.inflate(R.layout.story_piece,container,false);
            int i=getArguments().getInt(ARG_Chapter_NUMBER);
            ( (TextView) rootView ).setText(getResources().getStringArray(R.array.content)[i]) ;
            ( (TextView) rootView ).setMovementMethod(ScrollingMovementMethod.getInstance());
            return rootView;
        }
    }
    //侦听打开和关闭事件
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
