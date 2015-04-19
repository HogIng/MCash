package com.example.inga.mcash.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.OrderActivity;
import com.example.inga.mcash.activitiy.PaymentActivity;

public class OrdersFragment extends PaymentsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textViewEmpty = (TextView) getActivity().findViewById(R.id.textViewEmpty);
        textViewEmpty.setText(getString(R.string.no_orders));
    }

    protected boolean paymentHasRightStatus(Payment payment){
        if(payment.getStatus()==Payment.STATUS_ORDERED){
            return true;
        }
        return false;
    }

}
