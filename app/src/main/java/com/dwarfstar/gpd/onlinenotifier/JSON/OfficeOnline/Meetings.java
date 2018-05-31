package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;


import java.util.List;

public class Meetings {

    private String mMessage;
    private boolean mFree;
    private List<Meeting> mMeetings;

    public Meetings(String message, boolean free) {
        mMessage = message;
        mFree = free;
    }

    public Meetings(String message, boolean free, List<Meeting> meetingList) {
        mMessage = message;
        mFree = free;
        mMeetings = meetingList;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isFree() {
        return mFree;
    }

    public List<Meeting> getMeetings() {
        return mMeetings;
    }
}
