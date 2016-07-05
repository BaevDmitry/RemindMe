package com.jezik.remindme.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jezik.remindme.R;
import com.jezik.remindme.ReminderData;

import java.util.List;

/**
 * Created by Дмитрий on 04.07.2016.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ListViewHolder> {

    List<ReminderData> dataList;

    public ReminderAdapter(List<ReminderData> dataList){
        this.dataList = dataList;
        //this.listener = (Listener) context;
    }

    @Override
    public ReminderAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReminderAdapter.ListViewHolder holder, int position) {

        holder.tv_header.setText(dataList.get(position).getHeader());
        holder.tv_content.setText(dataList.get(position).getContent());
        holder.tv_date.setText(dataList.get(position).getDate());
        holder.btn_tab.setText(dataList.get(position).getFlag());

        holder.btn_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.cb_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_header;
        TextView tv_content;
        TextView tv_date;
        Button btn_menu;
        TextView btn_tab;
        CheckBox cb_done;

        public ListViewHolder(View itemView) {
            super(itemView);

            tv_header = (TextView) itemView.findViewById(R.id.card_header);
            tv_content = (TextView) itemView.findViewById(R.id.card_content);
            tv_date = (TextView) itemView.findViewById(R.id.card_date);
            btn_menu = (Button) itemView.findViewById(R.id.card_menu);
            btn_tab = (TextView) itemView.findViewById(R.id.card_tab);
            cb_done = (CheckBox) itemView.findViewById(R.id.card_checkbox);
        }
    }
}
