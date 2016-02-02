package com.cjj.loadmorervdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cjj.DefaultFootItem;
import com.cjj.MaterialFootItem;
import com.cjj.OnLoadMoreListener;
import com.cjj.RecyclerViewWithFooter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Integer> mDatas;
    private RecyclerViewWithFooter mRecyclerViewWithFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_blue_light,android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       addData();
                       swipeRefreshLayout.setRefreshing(false);
                   }
               },3000);
            }
        });

        initData();

        mRecyclerViewWithFooter = (RecyclerViewWithFooter) this.findViewById(R.id.rv_load_more);
        mRecyclerViewWithFooter.setAdapter(new DemoRvAdapter(this, mDatas));
//        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
//        mRecyclerViewWithFooter.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new MaterialFootItem());//material 风格
        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        mRecyclerViewWithFooter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                mRecyclerViewWithFooter.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addData();
                    }
                }, 2000);
            }
        });
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(R.mipmap.cat1);
        mDatas.add(R.mipmap.cat2);
        mDatas.add(R.mipmap.cat3);
        mDatas.add(R.mipmap.cjj);
        mDatas.add(R.mipmap.cat1);
        mDatas.add(R.mipmap.cat2);
    }

    protected void addData() {
        mDatas.add(R.mipmap.cat1);
        mDatas.add(R.mipmap.cat2);
        mDatas.add(R.mipmap.cat3);
        mDatas.add(R.mipmap.cjj);
        mDatas.add(R.mipmap.cat1);
        mDatas.add(R.mipmap.cat2);
        mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addMoreAction) {
            mRecyclerViewWithFooter.setLoad();
            addData();
            return true;
        }
        if (id == R.id.endAction) {

            mRecyclerViewWithFooter.setEnd("没有更多数据了");

            return true;
        }
        if (id == R.id.emptyAction) {
            mDatas.clear();
            mRecyclerViewWithFooter.setEmpty("没有数据", R.mipmap.ic_launcher);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
