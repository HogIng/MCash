package com.example.inga.mcash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inga.mcash.MenuItem;
import com.example.inga.mcash.R;

import java.util.ArrayList;

/**
 * Created by Inga on 03.04.2015.
 */
public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    public MenuItemAdapter(Context context, int resource,ArrayList<MenuItem> items) {
        super(context, resource,items);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewItem = inflater.inflate(R.layout.menu_item, parent, false);
        MenuItem menuItem = getItem(position);

        TextView textViewName = (TextView) viewItem.findViewById(R.id.textView_item_name);
        textViewName.setText(menuItem.getName());

        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView_menuIcon);

        if(!menuItem.getImg().equalsIgnoreCase("")) {
            int resourceID =
                    getContext().getResources().getIdentifier(menuItem.getImg(), "drawable", getContext().getPackageName());
            imageView.setImageResource(resourceID);
        }

        return viewItem;
    }
}
