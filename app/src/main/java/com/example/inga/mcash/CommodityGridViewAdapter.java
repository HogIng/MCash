package com.example.inga.mcash;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by Inga on 12.03.2015.
 */
public class CommodityGridViewAdapter extends ArrayAdapter<Commodity> {

    private final Context context;
    private final List<Commodity> commodities;

    public CommodityGridViewAdapter(Context context, int resource, List<Commodity> commodities) {
        super(context, resource, commodities);
        this.context = context;
        this.commodities=commodities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewCommodity = inflater.inflate(R.layout.view_commodity, parent, false);
        TextView textViewName = (TextView) viewCommodity.findViewById(R.id.textView_commodityname);
        TextView textViewId = (TextView) viewCommodity.findViewById(R.id.textView_commodityid);
        TextView textViewPrice = (TextView) viewCommodity.findViewById(R.id.textView_commodityprice);
        ImageView imageView = (ImageView) viewCommodity.findViewById(R.id.imageView_commodity);

        Commodity com = commodities.get(position);

        textViewName.setText(com.getName());
        textViewId.setText(String.valueOf(com.getId()));

        int price = com.getPrice();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
        textViewPrice.setText(numberFormat.format(price2));

        if(com.getImage()!=null && com.getImage()!= "") {
            int resourceID =
                    context.getResources().getIdentifier(com.getImage(), "drawable",context.getPackageName());
            imageView.setImageResource(resourceID);
        }

        return viewCommodity;
    }


}
