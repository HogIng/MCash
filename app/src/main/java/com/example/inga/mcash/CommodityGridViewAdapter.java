package com.example.inga.mcash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        textViewPrice.setText(String.valueOf(com.getPrice()));

    /*    String s = values[position];
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }*/

        return viewCommodity;
    }
}
