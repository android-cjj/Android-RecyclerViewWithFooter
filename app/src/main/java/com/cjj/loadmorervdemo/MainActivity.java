package com.cjj.loadmorervdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cjj.OnLoadMoreListener;
import com.cjj.RecyclerViewWithFooter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<String> mDatas;
    private RecyclerViewWithFooter mRecyclerViewWithFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        initData();
        mRecyclerViewWithFooter = (RecyclerViewWithFooter) this.findViewById(R.id.rv_load_more);
        mRecyclerViewWithFooter.setAdapter(new DemoRvAdapter(this, mDatas));
        mRecyclerViewWithFooter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                mRecyclerViewWithFooter.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addData(1);
                    }
                }, 3000);
            }
        });




    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            mDatas.add("" + i);
        }
    }

    protected void addData(int k) {
        for (int i = k; i < k + 50; i++) {
            mDatas.add("" + i);
        }

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
            addData(1);
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
