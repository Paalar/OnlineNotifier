package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;

public class Office {

    private Meetings mMeetings;
    private Servant mServant;
    private Coffee mCoffee;
    private Status mStatus;

    public Office(Meetings meetings, Servant servant, Coffee coffee, Status status) {
        mMeetings = meetings;
        mServant = servant;
        mCoffee = coffee;
        mStatus = status;
    }

    public Meetings getMeetings() {
        return mMeetings;
    }

    public Servant getServant() {
        return mServant;
    }

    public Coffee getCoffee() {
        return mCoffee;
    }

    public Status getStatus() {
        return mStatus;
    }
}
