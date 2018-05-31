package com.dwarfstar.gpd.onlinenotifier.JSON.Cantina;


import java.util.List;

public class Cantina {

    private String mCantinaName;
    private Hours mHours;
    private List<Lunch> mLunches;
    private List<Dinner> mDinners;

    public Cantina(String cantinaName, Hours hours, List<Lunch> lunches, List<Dinner> dinners) {
        mCantinaName = cantinaName;
        mHours = hours;
        mLunches = lunches;
        mDinners = dinners;
    }

    public String getCantinaName() {
        return mCantinaName;
    }

    public Hours getHours() {
        return mHours;
    }

    public List<Lunch> getLunches() {
        return mLunches;
    }

    public List<Dinner> getDinners() {
        return mDinners;
    }
}
