package com.example.inga.mcash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.R;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Inga on 12.03.2015.
 */
public class CommodityGridViewAdapter extends ArrayAdapter<Commodity> {

    private final Context context;
    private final List<Commodity> commodities;

    public CommodityGridViewAdapter(Context context, int resource, List<Commodity> commodities) {
        super(context, resource, commodities);
        this.context = context;
        this.commodities = commodities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Commodity com = commodities.get(position);
        View viewCommodity;
        if (com.getIsGroup() == Commodity.IS_GROUP) {
            viewCommodity = inflater.inflate(R.layout.view_commodity_group, parent, false);
        } else {
            viewCommodity = inflater.inflate(R.layout.view_commodity, parent, false);
            TextView textViewPrice = (TextView) viewCommodity.findViewById(R.id.textView_commodityprice);
            ImageView imageView = (ImageView) viewCommodity.findViewById(R.id.imageView_commodity);
            if (com.getImage() != null && com.getImage() != "") {
                int resourceID =
                        context.getResources().getIdentifier(com.getImage(), "drawable", context.getPackageName());
                imageView.setImageResource(resourceID);
            }

            int price = com.getPrice();
            BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
            NumberFormat numberFormat =
                    NumberFormat.getCurrencyInstance();
            textViewPrice.setText(numberFormat.format(price2));
        }
        TextView textViewName = (TextView) viewCommodity.findViewById(R.id.textView_commodityname);
        textViewName.setText(com.getName());
        return viewCommodity;
    }

}
