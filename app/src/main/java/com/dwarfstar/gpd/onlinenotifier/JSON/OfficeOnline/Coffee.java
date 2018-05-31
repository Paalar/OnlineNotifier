package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Coffee {

    private Calendar mDate;
    private int mPots;
    private static final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm");

    public Coffee(Calendar date, int pots) {
        mDate = date;
        mPots = pots;
    }


    public Calendar getDate() {
        return mDate;
    }

    public String getTime() {
        return mSimpleDateFormat.format(mDate.getTime());
    }

    public int getPots() {
        return mPots;
    }
}
