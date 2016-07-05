package com.jezik.remindme.database;

import android.provider.BaseColumns;

/**
 * Created by Дмитрий on 04.07.2016.
 */
public class Contract {

    public Contract() {
    }

    public static abstract class TableEntry implements BaseColumns {
        // Table name
        public static final String REMIND_TABLE = "remind";

        // Field names
        public static final String HEADER = "header";
        public static final String CONTENT = "content";
        public static final String DATE = "date";
        public static final String FLAG = "flag";
    }
}
