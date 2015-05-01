package com.example.inga.mcash.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.activitiy.PayActivity;
import com.example.inga.mcash.adapter.CommodityListViewAdapter;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.activitiy.PaymentActivity;
import com.example.inga.mcash.database.PaymentPositionDataSource;
import com.example.inga.mcash.R;
import com.example.inga.mcash.dialog.BaseDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inga on 20.03.2015.
 */
public class PaymentFragment extends OrderFragment {

    private Button buttonCancel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     return super.onCreateView(inflater,container,savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TextView) getView().findViewById(R.id.textView_empty_details)).setText(getString(R.string.no_payment_selected));

    }

    public void cancelPayment(Payment paymentToCancel){
        Payment payment = new Payment();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        payment.setCalendar(calendar);
        payment.setStatus(Payment.STATUS_CANCELLATION);
        payment.setTotalAmount(-paymentToCancel.getTotalAmount());
        payment.setCashier(LoginActivity.cashier.getId());
        paymentDataSource.open();
        long newId =paymentDataSource.createPayment(payment);
        payment.setId((int)newId);

        PaymentPositionDataSource paymentPositionDataSource = new PaymentPositionDataSource(getActivity());
        paymentPositionDataSource.open();
        ArrayList<Commodity> commodities = paymentToCancel.getCommodities();

        for(Commodity com : commodities){
            com.setAmount(-com.getAmount());
            PaymentPosition pP = new PaymentPosition();
            pP.setPaymentId((int) newId);
            pP.setCommodityId(com.getId());
            pP.setPrice(com.getPrice());
            pP.setNumber(com.getAmount());
            if(com instanceof Discount) {
                pP.setPercentage(((Discount) com).getPercentage());
            }
            paymentPositionDataSource.createPosition(pP);
        }

        paymentPositionDataSource.close();
        paymentToCancel.setStatus(Payment.STATUS_CANCELLED);
        paymentDataSource.updatePayment(paymentToCancel);
        paymentDataSource.close();
        setPayment(payment);
    }

    public void setPayment(Payment payment){
        super.setPayment(payment);

        int status= payment.getStatus();
        if(status==Payment.STATUS_COMPLETED) {
            buttonCancel.setVisibility(View.VISIBLE);
            tVAmount.setTextColor(getResources().getColor(R.color.grey_darkest));
        }
        else if(status==Payment.STATUS_CANCELLED){
            buttonCancel.setVisibility(View.GONE);
            tVAmount.setTextColor(getResources().getColor(R.color.grey_darkest));
        }
        else{
            buttonCancel.setVisibility(View.GONE);
            tVAmount.setTextColor(getResources().getColor(R.color.red));
        }
        }



    protected void initButtons(){
        buttonCancel = (Button) view1.findViewById(R.id.button_cancel);

    }


}
