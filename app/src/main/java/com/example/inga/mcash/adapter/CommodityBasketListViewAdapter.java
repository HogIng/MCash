package com.example.inga.mcash.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.inga.mcash.Basket;
import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.adapter.CommodityListViewAdapter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Inga on 13.03.2015.
 */
public class CommodityBasketListViewAdapter extends CommodityListViewAdapter {



    private final Basket basket;
    private View viewCommodity;
    private TextView textViewAmount;

    public CommodityBasketListViewAdapter(Context context, int resource, ArrayList<Commodity> commodities, TextView textViewAmount) {
        super(context,resource,commodities);
        basket = LoginActivity.basket;
        this.textViewAmount=textViewAmount;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       viewCommodity = super.getView(position,convertView,parent);

        setButtonListeners(com);

        return viewCommodity;
    }

    public void displayAmount(){
        final NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
        int price = basket.getTotalAmount();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        textViewAmount.setText(numberFormat.format(price2));
    }



    private void setButtonListeners(Commodity com){
        final Commodity commodity =com;
        Button buttonPlus = (Button) viewCommodity.findViewById(R.id.button_plus);
        buttonPlus.setVisibility(View.VISIBLE);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket.getCommodityById(commodity.getId()).setAmount(commodity.getAmount() + 1);
                notifyDataSetChanged();
                basket.setTotalAmount();
                displayAmount();
            }
        });

        Button buttonMinus = (Button) viewCommodity.findViewById(R.id.button_minus);
        buttonMinus.setVisibility(View.VISIBLE);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket.getCommodityById(commodity.getId()).setAmount(commodity.getAmount() - 1);
                if(commodity.getAmount()==0){
                    basket.deleteCommodity(commodity.getId());
                    if(basket.getSize()==0){

                    }
                }
                basket.setTotalAmount();
                displayAmount();
                notifyDataSetChanged();
            }
        });
    }


}