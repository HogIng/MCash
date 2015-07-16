package com.example.inga.mcash.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.inga.mcash.MenuItem;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.activitiy.OrdersActivity;
import com.example.inga.mcash.activitiy.PaymentsActivity;
import com.example.inga.mcash.activitiy.ProductsActivity;
import com.example.inga.mcash.adapter.MenuItemAdapter;

import java.util.ArrayList;

/**
 * Created by Inga on 02.04.2015.
 */
    public class SlidingMenuFragment extends android.support.v4.app.Fragment{

    private ArrayList<MenuItem> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.slidingmenu, container, false);

        items = new ArrayList<>();
        items.add(new MenuItem(getString(R.string.title_products),"products"));
        items.add(new MenuItem(getString(R.string.orders),"orders"));
        items.add(new MenuItem(getString(R.string.title_payments),"payments"));
        items.add(new MenuItem(getString(R.string.logout),"logout"));

        MenuItemAdapter adapter = new MenuItemAdapter(getActivity(),R.layout.menu_item,items);
        ListView listView = (ListView) view.findViewById(R.id.listView_menu);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Activity activity = getActivity();
                Intent i = null;

                switch (position){
                    case 0:
                            i = new Intent(activity, ProductsActivity.class);

                        break;
                    case 1:
                            i = new Intent(activity, OrdersActivity.class);

                        break;
                    case 2:
                            i = new Intent(activity, PaymentsActivity.class);

                        break;
                    case 3:
                            i = new Intent(activity, LoginActivity.class);

                        break;
                }
                    startActivity(i);
                    activity.finish();
            }
        });

        return view;
    }











}
