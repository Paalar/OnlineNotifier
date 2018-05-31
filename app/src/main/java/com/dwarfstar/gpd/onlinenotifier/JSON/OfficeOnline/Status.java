package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;


public class Status {

    private boolean mStatus;
    private String mUpdated;


    public Status(boolean status, String updated) {
        mStatus = status;
        mUpdated = updated;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public String getUpdated() {
        return mUpdated;
    }
}
