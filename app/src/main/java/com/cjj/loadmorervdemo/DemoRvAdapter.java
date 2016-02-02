package com.cjj.loadmorervdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.Inet4Address;
import java.util.List;

public class DemoRvAdapter extends RecyclerView.Adapter<DemoRvAdapter.MyViewHolder> {

    private Context mContext;
    private List<Integer> mDatas;
    public DemoRvAdapter(Context context, List<Integer> mDatas){
        mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_home, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.mImageViewv.setImageResource(mDatas.get(position));
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView mImageViewv;

        public MyViewHolder(View view)
        {
            super(view);
            mImageViewv = (ImageView) view.findViewById(R.id.iv_image);
        }
    }
}