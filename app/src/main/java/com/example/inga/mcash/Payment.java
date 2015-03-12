package com.example.inga.mcash;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Inga on 11.03.2015.
 */
public class Payment {

    private ArrayList<Commodity> commoditiesList;
    private float totalAmount;
    private String cashier, billNumber, status;
    private Date date;

    public Payment(ArrayList<Commodity> commoditiesList, float totalAmount, String cashier, String billNumber, String status, Date date) {
        this.commoditiesList = commoditiesList;
        this.totalAmount = totalAmount;
        this.billNumber = billNumber;
        this.date = date;
        this.status = status;
    }
}
