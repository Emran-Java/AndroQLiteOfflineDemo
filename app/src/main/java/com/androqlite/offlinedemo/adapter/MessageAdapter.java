package com.androqlite.offlinedemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androqlite.offlinedemo.R;
import com.androqlite.offlinedemo.datamodels.MessageDataModel;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MessageDataModel> mDataList;
    private ListItemClickListener listItemClickListener;

    public MessageAdapter(Context content, ArrayList<MessageDataModel> dataList) {
        this.mContext = content;
        this.mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
        return new ViewHolder(view, listItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(mDataList.get(position).getMTitle()!=null) {
            holder.tvTitle.setText(mDataList.get(position).getMTitle());
        }

        if(mDataList.get(position).getMMessage()!=null){
            holder.tvSubTitle.setText(mDataList.get(position).getMMessage());

        }
    }

    public void setOnItemClickListener(ListItemClickListener onItemClickListener) {
        this.listItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //--------------------------------

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvTitle;
        private TextView tvSubTitle;


        private ListItemClickListener listItemClickListener;

        public ViewHolder(View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            this.listItemClickListener = listItemClickListener;

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubTitle = (TextView) itemView.findViewById(R.id.tvSubTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listItemClickListener!=null){
                listItemClickListener.onItemClick(getLayoutPosition(), v);
            }
        }
    }


    public interface ListItemClickListener {
        void onItemClick(int position, View v);
    }

 }
