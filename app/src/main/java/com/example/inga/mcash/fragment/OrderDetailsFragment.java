package com.example.inga.mcash.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.BasketActivity;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.activitiy.OrderDetailsActivity;
import com.example.inga.mcash.activitiy.ProductsActivity;
import com.example.inga.mcash.adapter.CommodityListViewAdapter;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.database.PaymentPositionDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class OrderDetailsFragment extends DetailsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);

        //init Buttons
        Button buttonPayOrder = (Button) view.findViewById(R.id.buttonPayOrder);
        buttonPayOrder.setVisibility(View.VISIBLE);
        buttonPayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.basket.removeCommodities();
                ArrayList<Commodity> commoditiesOfSelectedOrder = paymentSelected.getCommodities();
                if(commoditiesOfSelectedOrder!= null) {
                    for (Commodity commodity : commoditiesOfSelectedOrder) {
                        LoginActivity.basket.getCommodities().add(commodity);
                    }
                }
                LoginActivity.basket.setOrderId(paymentSelected.getId());
                LoginActivity.basket.setTotalAmount();
                Intent intent = null;
                if(getActivity() instanceof OrderDetailsActivity) {
                    intent = new Intent(getActivity(), BasketActivity.class);
                }
                else{
                    intent = new Intent(getActivity(), ProductsActivity.class);
                }
                startActivity(intent);
                getActivity().finish();
            }
        });
        Button buttonDeleteOrder = (Button) view.findViewById(R.id.button_cancel);
        buttonDeleteOrder.setText(getString(R.string.delete_order));
        buttonDeleteOrder.setBackgroundColor(getResources().getColor(R.color.grey_dark));

        return view;
    }

    public void deleteOrder(){
        PaymentDataSource paymentDataSource = new PaymentDataSource(getActivity());
        paymentDataSource.open();
        int paymentId = paymentSelected.getId();
        paymentDataSource.deletePayment(paymentId);
        paymentDataSource.close();

        PaymentPositionDataSource paymentPositionDataSource = new PaymentPositionDataSource(getActivity());
        paymentPositionDataSource.open();
        for(Commodity commodity: paymentSelected.getCommodities()) {
            paymentPositionDataSource.deletePaymentPosition(paymentId,commodity.getId());
        }
        paymentPositionDataSource.close();
        if(LoginActivity.basket.getOrderId()==paymentId){
            LoginActivity.basket.setOrderId(0);
            LoginActivity.basket.removeCommodities();
        }

    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_paymentdetails;
    }
}
