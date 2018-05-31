package com.dwarfstar.gpd.onlinenotifier.JSON.Cantina;


import java.util.List;

public class Dinner extends Food {

    private String mFoodDish;
    private List<Integer> mPrice;

    public Dinner(String foodDish, List<Integer> price) {
        mFoodDish = foodDish;
        mPrice = price;
    }

}
