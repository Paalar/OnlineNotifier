package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;

public class ServantPerson {

    private String mName;
    private ServantTime mStart, mEnd;
    private String mPretty;

    public ServantPerson(String summary, ServantTime start, ServantTime end, String pretty) {
        mName = summary;
        mStart = start;
        mEnd = end;
        mPretty = pretty;
    }

    public String getName() {
        return mName;
    }

    public ServantTime getStart() {
        return mStart;
    }

    public ServantTime getEnd() {
        return mEnd;
    }

    public String getPretty() {
        return mPretty;
    }


}
