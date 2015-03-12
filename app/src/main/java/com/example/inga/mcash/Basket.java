package com.example.inga.mcash;

import java.util.ArrayList;

/**
 * Created by Inga on 11.03.2015.
 */
public class Basket {

    private ArrayList<Commodity> commodities;
    private float totalAmount;

    public Basket(){
        commodities = new ArrayList<Commodity>();
        totalAmount = 0;
    }


    public void addCommodity(Commodity commodity){
        commodities.add(commodity);
        setTotalAmount();
    }

    public void setTotalAmount(){
        Discount discount= null;
       for(Commodity com :commodities) {
           if (com instanceof Discount) discount = (Discount) com;
           else totalAmount += com.getPrice();

       }
       if (discount != null){
           totalAmount += discount.getPrice();
       }
        //round amount
        totalAmount = Math.round(totalAmount *100.0f)/100.0f;
    }

    public float getTotalAmount(){
        return totalAmount;
    }

    public ArrayList<Commodity> getCommodities(){
        return commodities;
    }
}
