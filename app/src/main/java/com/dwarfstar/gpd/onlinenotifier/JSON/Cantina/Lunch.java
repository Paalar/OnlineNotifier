package com.dwarfstar.gpd.onlinenotifier.JSON.Cantina;

import java.util.List;

public class Lunch extends Food{

    private String mFoodDish;
    private List<Integer> mPrice;

    public Lunch(String foodDish, List<Integer> price) {
        mFoodDish = foodDish;
        mPrice = price;
    }

}
