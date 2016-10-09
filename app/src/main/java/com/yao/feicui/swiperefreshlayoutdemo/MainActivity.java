package com.yao.feicui.swiperefreshlayoutdemo;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;
    private List<String>mData;
    private ArrayAdapter<String>mAdapter;

    private MediaPlayer mPlayer;//实例化媒体播放器类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView= (ListView) findViewById(R.id.listView);
        mData=new ArrayList<>();
        for (int i = 'A';i <='z' ; i++) {
           mData.add(""+(char)i);
        }
        mAdapter=new ArrayAdapter<>(
                getApplicationContext(),android.R.layout.simple_list_item_1,mData);
        mListView.setAdapter(mAdapter);
        //下拉刷新部分
        mRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        //设置卷内颜色
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                                                android.R.color.holo_green_light,
                                                android.R.color.holo_orange_light,
                                                android.R.color.holo_red_light);
        //设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            TextView tv;
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    mData.add(0,"yao"+new Random().nextInt());
                        mAdapter.notifyDataSetChanged();
                        //停止刷新动画
                        mRefreshLayout.setRefreshing(false);
                        playSounds();
                    }
                },2000);

            }
        });
    }

    //播放声音文件
    private void playSounds() {
        try {
            AssetFileDescriptor file = getAssets().openFd("1.mp3");
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(file.getFileDescriptor());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
