package com.example.inga.mcash;

import java.util.ArrayList;

/**
 * Created by Inga on 11.03.2015.
 */
public class Basket {

    private ArrayList<Commodity> commodities;
    private int totalAmount;

    public Basket(){
        commodities = new ArrayList<Commodity>();
        totalAmount = 0;
    }


    public void addCommodity(Commodity commodity){

            if (commodity.getId()!=0&&commodityAlreadyAdded(commodity)) {
                Commodity existingCommodity = getCommodityById(commodity.getId());
                existingCommodity.setAmount(existingCommodity.getAmount() + 1);
            } else {
                commodity.setAmount(1);
                commodities.add(commodity);
            }

        setTotalAmount();
    }

    public Commodity getCommodityById(long id){
        for(Commodity commodity :commodities) {
            if(commodity.getId()==id){
                return commodity;
            }
        }
        return null;
    }

    public boolean commodityAlreadyAdded(Commodity commodityToAdd){
        for(Commodity commodity :commodities) {
            if(commodity.getId()==commodityToAdd.getId()){
                return true;
            }
        }
        return false;
    }

    public void setTotalAmount(){

        Discount discount= null;
        totalAmount=0;

       for(Commodity com : commodities) {
           if (com instanceof Discount){
               discount = (Discount) com;}
           else{
               totalAmount += com.getPrice()*com.getAmount();}
       }
       if (discount != null){
           discount.calculatePrice(totalAmount);
           totalAmount += discount.getPrice();
       }

    }

    public int getTotalAmount(){
        return totalAmount;
    }

    public ArrayList<Commodity> getCommodities(){
        return commodities;
    }

    public void removeCommodities(){
        commodities.clear();
        totalAmount =0;
    }

    public void deleteCommodity(long id){
        commodities.remove(getCommodityById(id));
        setTotalAmount();
    }

    public int getSize(){
        return commodities.size();
    }
}
