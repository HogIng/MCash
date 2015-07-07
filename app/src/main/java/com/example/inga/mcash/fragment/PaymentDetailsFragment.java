package com.example.inga.mcash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.database.PaymentPositionDataSource;
import com.example.inga.mcash.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inga on 20.03.2015.
 */
public class PaymentDetailsFragment extends DetailsFragment {

    private Button buttonCancel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = super.onCreateView(inflater,container,savedInstanceState);
        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
       ((TextView) view.findViewById(R.id.textView_empty_details)).setText(getString(R.string.no_payment_selected));
       return view;
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
        PaymentDataSource paymentDataSource = new PaymentDataSource(getActivity());
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

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_paymentdetails;
    }

}
