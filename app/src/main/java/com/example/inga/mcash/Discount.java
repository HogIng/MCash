package com.example.inga.mcash;

/**
 * Created by Inga on 11.03.2015.
 */
public class Discount extends Commodity {

    private int percent;


    public Discount(int percent,float totalAmount) {
        super();
        this.percent = percent;
        setPrice(totalAmount);
    }

    public void setPrice(float totalAmount) {
        super.setPrice( - (totalAmount * (percent/100)));
    }
}
