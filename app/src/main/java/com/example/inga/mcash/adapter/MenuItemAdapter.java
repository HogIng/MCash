package com.example.inga.mcash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.MenuItem;
import com.example.inga.mcash.R;

import java.util.ArrayList;

/**
 * Created by Inga on 03.04.2015.
 */
public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    private Context context;
    private  ArrayList<MenuItem> items;
    protected View viewItem;

    public MenuItemAdapter(Context context, int resource,ArrayList<MenuItem> items) {
        super(context, resource,items);
        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewItem = inflater.inflate(R.layout.menu_item, parent, false);
        MenuItem menuItem = getItem(position);

        TextView textViewName = (TextView) viewItem.findViewById(R.id.textView_item_name);
        String itemName = items.get(position).getName();
        textViewName.setText(itemName);

        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView_menuIcon);

        if(!items.get(position).getImg().equalsIgnoreCase("")) {
            int resourceID =
                    context.getResources().getIdentifier(menuItem.getImg(), "drawable", context.getPackageName());
            imageView.setImageResource(resourceID);
        }

        return viewItem;
    }
}
