package com.example.inga.mcash.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.inga.mcash.Basket;
import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.LoginActivity;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Inga on 13.03.2015.
 */
public  class CommodityBasketListViewAdapter extends CommodityListViewAdapter {


    private final Basket basket;
    private View viewCommodity;
    private TextView textViewAmount;
    private FrameLayout emptyMessageLayout;
    private Button paymentButton;
    private Button orderButton;

    public CommodityBasketListViewAdapter(Context context, int resource, ArrayList<Commodity> commodities, TextView textViewAmount, FrameLayout emptyMessageLayout,Button paymentButton,Button orderButton) {
        super(context, resource, commodities);
        basket = LoginActivity.basket;
        this.textViewAmount = textViewAmount;
        this.emptyMessageLayout=emptyMessageLayout;
        this.paymentButton=paymentButton;
        this.orderButton=orderButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewCommodity = super.getView(position, convertView, parent);
        setButtonListeners(com);
        return viewCommodity;
    }

    public void displayAmount() {
        final NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
        int price = basket.getTotalAmount();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        textViewAmount.setText(numberFormat.format(price2));
    }


    private void setButtonListeners(final Commodity com) {
        Button buttonPlus = (Button) viewCommodity.findViewById(R.id.button_plus);
        buttonPlus.setVisibility(View.VISIBLE);
        if(!(com instanceof Discount)) {
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    com.setAmount(com.getAmount()+1);
                    notifyDataSetChanged();
               }
            });
        }
        else{
            buttonPlus.setTextColor(context.getResources().getColor(R.color.grey_light));
            buttonPlus.setClickable(false);
        }
        Button buttonMinus = (Button) viewCommodity.findViewById(R.id.button_minus);
        buttonMinus.setVisibility(View.VISIBLE);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.setAmount(com.getAmount()-1);
                if (com.getAmount() == 0) {
                    remove(com);
                }
                notifyDataSetChanged();
            }
        });
    }

    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        if(getCount()>0){
            emptyMessageLayout.setVisibility(View.GONE);
            paymentButton.setClickable(true);
            orderButton.setClickable(true);
        }
        else{
            emptyMessageLayout.setVisibility(View.VISIBLE);
            paymentButton.setClickable(false);
            orderButton.setClickable(false);
        }
        basket.setTotalAmount();
        displayAmount();
    }


}