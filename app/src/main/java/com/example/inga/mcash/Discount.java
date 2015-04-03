package com.example.inga.mcash;

/**
 * Created by Inga on 11.03.2015.
 */
public class Discount extends Commodity {

    private int percentage;


    public Discount() {
        super();
    }

    public void calculatePrice(int totalAmount) {
        float factor = percentage/100f;
        super.setPrice((int) - (totalAmount * (factor)));
    }

    public void setPercentage(int percentage){
        this.percentage=percentage;
    }

    public int getPercentage(){
        return percentage;
    }


}
