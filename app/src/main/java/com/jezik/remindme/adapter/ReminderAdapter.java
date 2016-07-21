package com.jezik.remindme.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jezik.remindme.AddReminderActivity;
import com.jezik.remindme.Constants;
import com.jezik.remindme.MainActivity;
import com.jezik.remindme.R;
import com.jezik.remindme.ReminderData;
import com.jezik.remindme.database.DbHelper;

import java.util.List;

/**
 * Created by Дмитрий on 04.07.2016.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ListViewHolder> {

    List<ReminderData> dataList;
    Context context;

    DbHelper dbHelper;

    public ReminderAdapter(List<ReminderData> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ReminderAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ReminderAdapter.ListViewHolder holder, int position) {

        final ReminderData reminderData = dataList.get(position);

        final TextView tvHeader = holder.tv_header;
        final TextView tvContent = holder.tv_content;
        TextView tvDate = holder.tv_date;
        final TextView btnTab = holder.btn_tab;
        CheckBox cbDone = holder.cb_done;

        dbHelper = DbHelper.newInstance(context);

        tvHeader.setText(reminderData.getHeader());
        tvContent.setText(reminderData.getContent());
        tvDate.setText(reminderData.getDate());
        btnTab.setText(reminderData.getFlag());

        if (reminderData.getIs_done() == 1) {
            cbDone.setChecked(true);
            cbDone.setClickable(false);
        }

        // set color of Tab Button
        if (reminderData.getFlag().equals(context.getString(R.string.tab_item_ideas))) {
            btnTab.setTextColor(ContextCompat.getColor(context, R.color.tabBlue));
        } else if (reminderData.getFlag().equals(context.getString(R.string.tab_item_todo))) {
            btnTab.setTextColor(ContextCompat.getColor(context, R.color.tabRed));
        } else {
            btnTab.setTextColor(ContextCompat.getColor(context, R.color.tabGreen));
        }

        btnTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnTab.getText().toString().equals(context.getString(R.string.tab_item_ideas))) {
                    MainActivity.viewPager.setCurrentItem(Constants.TAB_IDEAS);
                } else if (btnTab.getText().toString().equals(context.getString(R.string.tab_item_todo))) {
                    MainActivity.viewPager.setCurrentItem(Constants.TAB_TODO);
                } else {
                    MainActivity.viewPager.setCurrentItem(Constants.TAB_BIRTHDAYS);
                }
            }
        });

        holder.btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu_card_view, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.cardMenu_edit:
                                Intent intent = new Intent(context, AddReminderActivity.class);
                                intent.putExtra("header", reminderData.getHeader());
                                intent.putExtra("content", reminderData.getContent());
                                intent.putExtra("date", reminderData.getDate());
                                intent.putExtra("flag", reminderData.getFlag());
                                intent.putExtra("done", reminderData.getIs_done());
                                context.startActivity(intent);
                                break;

                            case R.id.cardMenu_delete:
                                dbHelper.deleteReminder(reminderData.getHeader(), reminderData.getContent(), reminderData.getFlag());
                                dataList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                                MainActivity.viewPager.getAdapter().notifyDataSetChanged();
                                break;
                        }

                        return false;
                    }
                });
            }
        });

        holder.cb_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.doneReminder(reminderData.getHeader(), reminderData.getContent(), reminderData.getFlag());
                notifyDataSetChanged();
                MainActivity.viewPager.getAdapter().notifyDataSetChanged();
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
