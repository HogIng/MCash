package com.example.inga.mcash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inga.mcash.Payment;

/**
 * Created by Inga on 24.03.2015.
 */
public class PaymentsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }


    protected boolean paymentHasRightStatus(Payment payment){
        if(payment.getStatus()!=Payment.STATUS_ORDERED){
            return true;
        }
        return false;
    }


}
