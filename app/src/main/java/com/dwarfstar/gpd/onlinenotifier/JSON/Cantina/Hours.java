package com.dwarfstar.gpd.onlinenotifier.JSON.Cantina;


public class Hours {

    private Boolean mIsOpen;
    private String mMessage;

    public Hours(Boolean isOpen, String message) {
        mIsOpen = isOpen;
        mMessage = message;
    }

    public Boolean getOpen() {
        return mIsOpen;
    }

    public String getMessage() {
        return mMessage;
    }
}
