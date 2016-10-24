package com.jezik.remindme;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jezik.remindme.database.DbHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddReminderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_header;
    private EditText et_content;
    private Spinner sp_category;
    private static TextView tv_date;
    private Button btn_ok;

    private String item_header;
    private String item_content;
    private String item_date;
    private String item_category;

    private DbHelper dbHelper;
    ReminderData item;

    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        initViews();
        initToolbar();
        dbHelper = DbHelper.newInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            final String header = extras.getString("header");
            final String content = extras.getString("content");
            String date = extras.getString("date");
            final String flag = extras.getString("flag");
            final int done = extras.getInt("done");

            et_header.setText(header);
            et_content.setText(content);
            tv_date.setText(date);

            if (flag.equals(getResources().getString(R.string.tab_item_ideas))) {
                sp_category.setSelection(0);
            } else if (flag.equals(getResources().getString(R.string.tab_item_todo))) {
                sp_category.setSelection(1);
            } else {
                sp_category.setSelection(2);
            }

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initStrings();

                    if (item_header.trim().isEmpty()) {
                        et_header.setError(getString(R.string.header_error));
                    } else if (item_date.isEmpty()) {
                        tv_date.setError(getString(R.string.date_error));
                    } else {
                        item = new ReminderData(item_header, item_content, item_date, item_category, done);
                        dbHelper.updateReminder(header, content, flag, item);
                        startAlarm();

                        finish();
                    }
                }
            });

        } else {

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initStrings();

                    if (item_header.trim().isEmpty()) {
                        et_header.setError(getString(R.string.header_error));
                    } else if (item_date.isEmpty()) {
                        tv_date.setError(getString(R.string.date_error));
                    } else {
                        item = new ReminderData(item_header, item_content, item_date, item_category, 0);
                        dbHelper.insertReminderItem(item);
                        startAlarm();
                        finish();
                    }
                }
            });
        }

    }

    // Init all views in Activity
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.add_reminder_toolbar);

        et_header = (EditText) findViewById(R.id.new_header);
        et_content = (EditText) findViewById(R.id.new_content);
        tv_date = (TextView) findViewById(R.id.new_date);
        sp_category = (Spinner) findViewById(R.id.new_spinner);
        btn_ok = (Button) findViewById(R.id.new_button);
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    // Init all fields (if exist) of new reminder
    private void initStrings() {
        item_header = et_header.getText().toString();
        item_content = et_content.getText().toString();
        item_category = sp_category.getSelectedItem().toString();
        item_date = tv_date.getText().toString();
    }

    // Sets AlarmManager for Notification Service
    private void startAlarm() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        String dateStr = tv_date.getText().toString();
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        try {
            Date date = formatter.parse(dateStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long when = calendar.getTimeInMillis();
        Intent intent = new Intent(this, ReminderService.class);
        intent.putExtra("header", et_header.getText().toString());
        intent.putExtra("content", et_content.getText().toString());

        Uri uri = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME));
        intent.setData(uri);

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC, when, pendingIntent);
    }

    private void stopAlarm() {
        Intent intent = new Intent(this, ReminderService.class);
        intent.putExtra("header", et_header.getText().toString());
        intent.putExtra("content", et_content.getText().toString());

        Uri uri = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME));
        intent.setData(uri);

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


    // Show DatePickerDialog for the date of reminder
    public void showDateDialog(View view) {
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
            Calendar calendar = new GregorianCalendar(year, month, day);
            tv_date.setText(df.format(calendar.getTime()));
        }
    }
}
