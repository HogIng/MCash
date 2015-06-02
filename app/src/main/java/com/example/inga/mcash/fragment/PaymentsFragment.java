package com.example.inga.mcash.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.adapter.PaymentListViewAdapter;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.PaymentActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inga on 24.03.2015.
 */
public class PaymentsFragment extends Fragment {

    private PaymentListViewAdapter adapter;
    private ArrayList<Payment> payments;
    private ArrayList<Payment> paymentsSelected;
    private Calendar calendar;
    private TextView textViewDay;
    protected Intent intent;
    private Button buttonNext;
    private View view1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view1 = inflater.inflate(R.layout.fragment_payments, container, false);
        calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        paymentsSelected = new ArrayList<>();

        buttonNext = (Button)view1.findViewById(R.id.button_next);

        if(todaySelected()){
            buttonNext.setVisibility(View.INVISIBLE);
        }

        textViewDay = (TextView) view1.findViewById(R.id.textViewDay);

        PaymentDataSource paymentDataSource = new PaymentDataSource(view1.getContext());
        paymentDataSource.open();
        payments = paymentDataSource.getPaymentsSortedByTime();
        paymentDataSource.close();
        setPayments();
        setAdapter();
        showSelectedDayText();
        return view1;
    }


    public void selectNextDay(){
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
        if(todaySelected()){
            buttonNext.setVisibility(View.INVISIBLE);
        }
        showPayments();
        showSelectedDayText();
    }

    public void selectDayBefor(){
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
        buttonNext.setVisibility(View.VISIBLE);
        showPayments();
        showSelectedDayText();
    }

    private void showSelectedDayText(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        textViewDay.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public void showPayments(){
        setPayments();
        adapter.notifyDataSetChanged();
    }

    public void selectToday(){
        calendar.setTime(new Date());
    }

    private void setPayments(){
        paymentsSelected.clear();
        for(Payment payment:payments){
            int day = payment.getCalendar().get(Calendar.DAY_OF_MONTH);
            int month = payment.getCalendar().get(Calendar.MONTH);
            int year = payment.getCalendar().get(Calendar.YEAR);

            int daySelected = calendar.get(Calendar.DAY_OF_MONTH);
            int monthSelected = calendar.get(Calendar.MONTH);
            int yearSelected = calendar.get(Calendar.YEAR);

            if(day==daySelected&&month==monthSelected&&year==yearSelected&&paymentHasRightStatus(payment)){
                paymentsSelected.add(payment);
            }
        }
        if(paymentsSelected.size()==0){
            view1.findViewById(R.id.frameLayout3).setVisibility(View.VISIBLE);
        }
        else{
            view1.findViewById(R.id.frameLayout3).setVisibility(View.GONE);
        }
    }

    private void setAdapter(){
            adapter = new PaymentListViewAdapter(getActivity(), R.layout.view_payment_list, paymentsSelected);
            ListView listView = (ListView) view1.findViewById(R.id.listView_payments);
            listView.setAdapter(adapter);
    }

    private boolean todaySelected(){
        Calendar toDayCalendar=Calendar.getInstance();
        toDayCalendar.setTime(new Date());
        if(calendar.get(Calendar.DAY_OF_MONTH)==toDayCalendar.get(Calendar.DAY_OF_MONTH)){
            return true;
        }
        else{
            return false;
        }
    }

    protected boolean paymentHasRightStatus(Payment payment){
        if(payment.getStatus()!=Payment.STATUS_ORDERED){
            return true;
        }
        return false;
    }

    public ArrayList<Payment> getPaymentsSelected(){
        return  paymentsSelected;
    }

    public void updateDataSet(){
        PaymentDataSource paymentDataSource = new PaymentDataSource(view1.getContext());
        paymentDataSource.open();
        payments = paymentDataSource.getPaymentsSortedByTime();
        paymentDataSource.close();
        showPayments();
    }


}
