package com.example.inga.mcash.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.adapter.CommodityGridViewAdapter;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.R;

import java.util.List;

/**
 * Created by Inga on 11.03.2015.
 */
public class ProductsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        List<Commodity> commodities;
        CommodityDataSource cDS = new CommodityDataSource(getActivity());
        cDS.open();
        commodities = cDS.getAllCommodities();
        cDS.close();

        if(commodities!=null) {
            CommodityGridViewAdapter cGVA = new CommodityGridViewAdapter(getActivity(), R.layout.view_commodity, commodities);
            GridView gridView = (GridView) getActivity().findViewById(R.id.gridView_products);
            gridView.setAdapter(cGVA);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position,
                                        long arg3) {
                    Commodity com = (Commodity) adapter.getItemAtPosition(position);
                    LoginActivity.basket.addCommodity(com);
                    Button basketButton = (Button) getActivity().findViewById(R.id.buttonBasket);
                    basketButton.setText(new EuroFormat().formatPrice(LoginActivity.basket.getTotalAmount()));
                }
            });
        }
    }



}
