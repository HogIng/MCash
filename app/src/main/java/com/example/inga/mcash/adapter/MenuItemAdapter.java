package com.example.inga.mcash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.R;

import java.util.ArrayList;

/**
 * Created by Inga on 03.04.2015.
 */
public class MenuItemAdapter extends ArrayAdapter<String> {

    private Context context;
    private  ArrayList<String> items;
    protected View viewItem;

    public MenuItemAdapter(Context context, int resource,ArrayList<String> items) {
        super(context, resource,items);
        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewItem = inflater.inflate(R.layout.menu_item, parent, false);
        TextView textViewName = (TextView) viewItem.findViewById(R.id.textView_item_name);

        String item = items.get(position);
        textViewName.setText(item);

        return viewItem;
    }
}
