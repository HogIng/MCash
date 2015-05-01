package com.example.inga.mcash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Inga on 25.03.2015.
 */
public class PaymentListViewAdapter extends ArrayAdapter<Payment> {
    final ArrayList<Payment> payments;
    Payment payment;
    Context context1;
    View viewPayment;
    View firstView;

    public PaymentListViewAdapter(Context context, int resource, ArrayList<Payment> payments) {
        super(context, resource,payments);
        this.payments = payments;
        this.context1=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewPayment = inflater.inflate(R.layout.view_payment_list, parent, false);
        viewPayment.setBackgroundColor(context1.getResources().getColor(R.color.grey_light_background));
        TextView textViewTime = (TextView) viewPayment.findViewById(R.id.textView_time);
        TextView textViewId = (TextView) viewPayment.findViewById(R.id.textView_billnr_details);
        TextView textViewAmount = (TextView) viewPayment.findViewById(R.id.textView_total);

        payment = payments.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        textViewTime.setText(sdf.format(payment.getCalendar().getTime()));
        textViewId.setText(String.valueOf(payment.getId()));
        int total = payment.getTotalAmount();
        if(total <0){
            textViewAmount.setTextColor(context1.getResources().getColor(R.color.red));
        }
        textViewAmount.setText(formatPrice(total));

        return viewPayment;
    }



    private CharSequence formatPrice(int price) {
        NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        return numberFormat.format(price2);
    }

}
