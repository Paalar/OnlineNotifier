package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;


import java.util.List;

public class Servant {

    private boolean mIfResponsible;
    private String mPersonResponsible;
    private List<ServantPerson> mServantsList;

    public Servant(boolean ifResponsible, String message, List<ServantPerson> servantPersonList) {
        mIfResponsible = ifResponsible;
        mPersonResponsible = message;
        mServantsList = servantPersonList;
    }

    public boolean isIfResponsible() {
        return mIfResponsible;
    }

    public String getPersonResponsible() {
        return mPersonResponsible;
    }

    public List<ServantPerson> getServantsList() {
        return mServantsList;
    }
}
