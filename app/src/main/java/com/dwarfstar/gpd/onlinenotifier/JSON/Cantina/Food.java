package com.dwarfstar.gpd.onlinenotifier.JSON.Cantina;

import java.util.List;

public abstract class Food {
    private String mFoodDish;
    private List<Integer> mPrice;


    public String getFoodDish() {
        return mFoodDish;
    }

    public List<Integer> getPrice() {
        return mPrice;
    }
}
