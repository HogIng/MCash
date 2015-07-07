package com.example.inga.mcash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;

public class OrdersFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        TextView textViewEmpty = (TextView) view.findViewById(R.id.textViewEmpty);
        textViewEmpty.setText(getString(R.string.no_orders));
        return view;
    }



    protected boolean paymentHasRightStatus(Payment payment){
        if(payment.getStatus()==Payment.STATUS_ORDERED){
            return true;
        }
        return false;
    }

}
