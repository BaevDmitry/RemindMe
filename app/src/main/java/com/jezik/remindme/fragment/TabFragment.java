package com.jezik.remindme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jezik.remindme.Constants;
import com.jezik.remindme.R;
import com.jezik.remindme.adapter.ReminderAdapter;
import com.jezik.remindme.database.DbHelper;


public class TabFragment extends Fragment {

    static final String ARGUMENT_TAB_NUMBER = "argument tab number";

    private int tabNumber;

    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ReminderAdapter adapter;
    private DbHelper dbHelper;

    public static TabFragment newInstance(int tab) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_TAB_NUMBER, tab);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabNumber = getArguments().getInt(ARGUMENT_TAB_NUMBER);

        dbHelper = DbHelper.newInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tab, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        emptyTextView = (TextView) view.findViewById(R.id.empty_view);

        switch (tabNumber) {
            case Constants.TAB_HISTORY:
                adapter = new ReminderAdapter(dbHelper.getAllReminders(), getContext());
                checkAdapter();

                createRecyclerView();
                break;

            case Constants.TAB_IDEAS:
                adapter = new ReminderAdapter(dbHelper.getIdeasReminders(getContext()), getContext());
                checkAdapter();

                createRecyclerView();
                break;

            case Constants.TAB_TODO:
                adapter = new ReminderAdapter(dbHelper.getTodoReminders(getContext()), getContext());
                checkAdapter();

                createRecyclerView();
                break;

            case Constants.TAB_BIRTHDAYS:
                adapter = new ReminderAdapter(dbHelper.getBirthdaysReminders(getContext()), getContext());
                checkAdapter();

                createRecyclerView();
                break;
        }


        return view;
    }

    private void createRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void checkAdapter() {

        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }



}
