package com.example.inga.mcash.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.R;

import java.math.BigDecimal;
import java.security.interfaces.DSAKey;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.zip.DeflaterInputStream;

/**
 * Created by Inga on 20.03.2015.
 */
public class CommodityListViewAdapter extends ArrayAdapter<Commodity> {


    protected final Context context;
    protected final ArrayList<Commodity> commodities;
    protected View viewCommodity;
    protected Commodity com;

    public CommodityListViewAdapter(Context context, int resource, ArrayList<Commodity> commodities) {
        super(context, resource, commodities);
        this.context = context;
        this.commodities = commodities;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewCommodity = inflater.inflate(R.layout.view_commodity_list, parent, false);
        TextView textViewName = (TextView) viewCommodity.findViewById(R.id.textView_commoname);
        TextView textViewId = (TextView) viewCommodity.findViewById(R.id.textView_commoid);
        TextView textViewPrice = (TextView) viewCommodity.findViewById(R.id.textView_commoprice);
        TextView textViewAmount = (TextView) viewCommodity.findViewById(R.id.textView_commototal);
        ImageView imageView = (ImageView) viewCommodity.findViewById(R.id.imageView_commo);

        com = commodities.get(position);

        textViewName.setText(com.getName());


        if(!(com instanceof Discount)){
            textViewPrice.setText(formatPrice(com.getPrice()));

        }
        else{
            Discount discount = (Discount) com;
            textViewPrice.setText(String.valueOf(discount.getPercentage()) + " " + getContext().getString(R.string.sign_discount));
            com.setImage("discount2");
        }

        if(com.getId()==0&& !(com instanceof Discount)){
            com.setImage("manual");
        }


        if(com.getEan()==null || com.getEan().equalsIgnoreCase("")) {
            textViewId.setVisibility(View.GONE);
        }
        else{
            textViewId.setVisibility(View.GONE);
        }

        textViewAmount.setText(formatPrice(com.getPrice()*com.getAmount()));

        if (com.getImage() != null && com.getImage() != "") {
            int resourceID =
                    context.getResources().getIdentifier(com.getImage(), "drawable", context.getPackageName());
            imageView.setImageResource(resourceID);
        }

        TextView textViewCount = (TextView) viewCommodity.findViewById(R.id.textView_count);
        textViewCount.setText(Integer.toString(com.getAmount()));


        return viewCommodity;
    }



    private CharSequence formatPrice(int price) {
        NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        return numberFormat.format(price2);
    }




}

