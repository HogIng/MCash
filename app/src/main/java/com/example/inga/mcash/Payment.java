package com.example.inga.mcash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Inga on 11.03.2015.
 */
public class Payment {

    public static final int STATUS_COMPLETED = 0;
    public static final int STATUS_CANCELLED = 1;
    public static final int STATUS_CANCELLATION = 2;
    public static final int STATUS_ORDERED = 3;

    private ArrayList<Commodity> commoditiesList;
    private int totalAmount, status, cashierID;
    private Calendar calendar;
    private int id;


    public Payment() {

    }

    public void setId(int id){
        this.id = id;
    }

    public void setCommoditiesList(ArrayList<Commodity> commoditiesList){
        this.commoditiesList=commoditiesList;
    }

    public void setTotalAmount(int totalAmount){
        this.totalAmount=totalAmount;
    }

    public void setCashier(int cashierID){
        this.cashierID = cashierID;
    }


    public void setCalendar(Calendar calendar){
        this.calendar=calendar;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getTotalAmount(){
        return totalAmount;
    }

    public int getId(){
        return id;
    }

    public int getStatus(){
        return status;
    }

    public int getCashierID(){
        return cashierID;
    }

    public Calendar getCalendar(){
        return calendar;
    }

    public ArrayList<Commodity> getCommodities(){
        return commoditiesList;
    }
  
}
