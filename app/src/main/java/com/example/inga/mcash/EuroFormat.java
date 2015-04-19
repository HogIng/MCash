package com.example.inga.mcash;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by Inga on 22.03.2015.
 */
public class EuroFormat {

    public CharSequence formatPrice(int price){
        NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        return numberFormat.format(price2);
    }

}
