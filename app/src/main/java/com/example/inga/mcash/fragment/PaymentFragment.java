package com.example.inga.mcash.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.CommodityDataSource;
import com.example.inga.mcash.CommodityListViewAdapter;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.PaymentActivity;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.PaymentPositionDataSource;
import com.example.inga.mcash.R;

import java.util.ArrayList;

/**
 * Created by Inga on 20.03.2015.
 */
public class PaymentFragment extends Fragment {

    CommodityListViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paymentdetails, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Payment payment = PaymentActivity.payment;
        EuroFormat euroFormat = new EuroFormat();

        final TextView tVAmount = (TextView) getActivity().findViewById(R.id.textView_totalAmount);
        tVAmount.setText(euroFormat.formatPrice(payment.getTotalAmount()));

        TextView textViewBillNr = (TextView) getActivity().findViewById(R.id.textView_billnr);
        TextView textViewDate = (TextView) getActivity().findViewById(R.id.textView_date);
        TextView textViewTime = (TextView) getActivity().findViewById(R.id.textView_time);
        TextView textViewCashier = (TextView) getActivity().findViewById(R.id.textView_cashier);
        TextView textViewStatus = (TextView) getActivity().findViewById(R.id.textView_status);
        TextView textViewMwst = (TextView) getActivity().findViewById(R.id.textView_mwst);

        textViewMwst.setText(R.string.mwst+" "+ euroFormat.formatPrice((payment.getTotalAmount() * 19) / 100));
        textViewBillNr.setText(String.valueOf(payment.getId()));
        textViewCashier.setText(String.valueOf(payment.getCashierID()));
        textViewStatus.setText(String.valueOf(payment.getStatus()));

        PaymentPositionDataSource paymentPositionDataSource = new PaymentPositionDataSource(getActivity());
        paymentPositionDataSource.open();
        ArrayList<PaymentPosition> paymentPositions = paymentPositionDataSource.getPaymentPositionsByPaymentId(payment.getId());
        paymentPositionDataSource.close();

        CommodityDataSource commodityDataSource = new CommodityDataSource(getActivity());
        commodityDataSource.open();

        ArrayList<Commodity> commodities = new ArrayList<Commodity>();
        if(paymentPositions!=null) {
            for (int i = 0; i < paymentPositions.size(); i++) {
                PaymentPosition pP = paymentPositions.get(i);
                Commodity com = commodityDataSource.getCommodityById(pP.getCommodityId());
                com.setAmount(pP.getNumber());
                commodities.add(com);
            }
            if (commodities != null) {
                adapter = new CommodityListViewAdapter(getActivity(), R.layout.view_commodity_list, commodities);
                ListView listView = (ListView) getActivity().findViewById(R.id.listView3);
                listView.setAdapter(adapter);
            }
        }


    }
}
