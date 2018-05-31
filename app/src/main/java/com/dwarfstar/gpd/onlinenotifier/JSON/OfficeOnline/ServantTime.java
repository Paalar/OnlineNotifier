package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServantTime {

    private String mTimeZone;
    private Calendar mDate;
    private String mPretty;
    private static final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm");

    public ServantTime(String timeZone, Calendar date, String pretty) {
        mTimeZone = timeZone;
        mDate = date;
        mPretty = pretty;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public Calendar getDate() {
        return mDate;
    }

    public String getPretty() {
        return mPretty;
    }

    public String getTime() {
        return mSimpleDateFormat.format(mDate.getTime());
    }
}
