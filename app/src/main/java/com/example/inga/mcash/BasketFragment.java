package com.example.inga.mcash;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Inga on 13.03.2015.
 */
public class BasketFragment extends Fragment {

    CommodityListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();

        final TextView tVAmount = (TextView) getActivity().findViewById(R.id.textView_totalamount);
        int price = LoginActivity.basket.getTotalAmount();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        tVAmount.setText(numberFormat.format(price2));

        ArrayList<Commodity> commodities;
        commodities = LoginActivity.basket.getCommodities();

        if (commodities != null) {
            adapter = new CommodityListViewAdapter(getActivity(), R.layout.view_commodity_list, commodities);
            ListView listView = (ListView) getActivity().findViewById(R.id.listView2);
            listView.setAdapter(adapter);
        }

        Button clearButton = (Button) getActivity().findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.basket.removeCommodities();
                adapter.notifyDataSetChanged();
                int price = LoginActivity.basket.getTotalAmount();
                BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
                tVAmount.setText(numberFormat.format(price2));
            }
        });

    }



}
