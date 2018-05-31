package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Meeting {

    private static final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm");
    private String mSummary;
    private String mMessage;
    private String mPretty;
    private Calendar mStartTime;
    private Calendar mEndTime;

    public Meeting(String summary, Calendar startTime, Calendar endTime, String pretty, String message) {
        mSummary = summary;
        mStartTime = startTime;
        mEndTime = endTime;
        mPretty = pretty;
        mMessage = message;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getPretty() {
        return mPretty;
    }

    public Calendar getStartDate() {
        return mStartTime;
    }

    public String getStartTime() {
        return mSimpleDateFormat.format(mStartTime.getTime());
    }

    public Calendar getEndDate() {
        return mEndTime;
    }

    public String getEndTime() {
        return mSimpleDateFormat.format(mEndTime.getTime());
    }


}
