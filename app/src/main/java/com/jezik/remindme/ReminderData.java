package com.jezik.remindme;


import android.app.PendingIntent;

/**
 * Created by Дмитрий on 04.07.2016.
 */
public class ReminderData {

    private String header;
    private String content;
    private String date;
    private String flag;
    private int is_done;

    public ReminderData(String header, String content, String date, String flag, int is_done) {
        this.header = header;
        this.content = content;
        this.date = date;
        this.flag = flag;
        this.is_done = is_done;

    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }
}
