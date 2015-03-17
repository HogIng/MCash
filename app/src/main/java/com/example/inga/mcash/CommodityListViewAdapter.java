package com.example.inga.mcash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inga on 13.03.2015.
 */
public class CommodityListViewAdapter extends ArrayAdapter<Commodity> {

private final Context context;
private final ArrayList<Commodity> commodities;

public CommodityListViewAdapter(Context context, int resource, ArrayList<Commodity> commodities) {
        super(context, resource, commodities);
        this.context = context;
        this.commodities=commodities;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewCommodity = inflater.inflate(R.layout.view_commodity_list, parent, false);
        TextView textViewName = (TextView) viewCommodity.findViewById(R.id.textView_commoname);
        TextView textViewId = (TextView) viewCommodity.findViewById(R.id.textView_commoid);
        TextView textViewPrice = (TextView) viewCommodity.findViewById(R.id.textView_commoprice);
        TextView textViewAmount = (TextView) viewCommodity.findViewById(R.id.textView_commototal);
        ImageView imageView = (ImageView) viewCommodity.findViewById(R.id.imageView_commo);

        Commodity com = commodities.get(position);

        textViewName.setText(com.getName());
        textViewId.setText(String.valueOf(com.getId()));

        NumberFormat numberFormat =
            NumberFormat.getCurrencyInstance();

        int price = com.getPrice();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        textViewPrice.setText(numberFormat.format(price2));

        int amount = com.getPrice()*com.getAmount();
        BigDecimal amount2 = new BigDecimal(amount).movePointLeft(2);
        textViewAmount.setText(numberFormat.format(amount2));

        if(com.getImage()!=null && com.getImage()!= "") {
        int resourceID =
        context.getResources().getIdentifier(com.getImage(), "drawable",context.getPackageName());
        imageView.setImageResource(resourceID);
        }
        return viewCommodity;
        }


        }