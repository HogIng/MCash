package com.example.inga.mcash.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.R;
import com.example.inga.mcash.adapter.CommodityListViewAdapter;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.database.PaymentPositionDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Inga on 03.07.2015.
 */
public abstract class DetailsFragment extends Fragment {

    private CommodityListViewAdapter adapter;
    protected Payment paymentSelected;
    protected TextView tVAmount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResourceID(), container, false);
    }

    public void setPayment(Payment payment){
        paymentSelected=payment;
        paymentSelected.setCommoditiesList(getCommoditiesOfPayment(paymentSelected));
        EuroFormat euroFormat = new EuroFormat();

        tVAmount = (TextView) getView().findViewById(R.id.textView_totalAmount);
        tVAmount.setText(new EuroFormat().formatPrice(paymentSelected.getTotalAmount()));

        TextView textViewBillNr = (TextView) getView().findViewById(R.id.textView_billnr_details);
        textViewBillNr.setText(String.valueOf(payment.getId()));

        TextView textViewDate = (TextView) getView().findViewById(R.id.textView_date_details);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        textViewDate.setText(sdf.format(payment.getCalendar().getTime()));

        TextView textViewTime = (TextView) getView().findViewById(R.id.textView_time_details);
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        textViewTime.setText(sdf2.format(payment.getCalendar().getTime()));

        TextView textViewCashier = (TextView) getView().findViewById(R.id.textView_cashier_details);
        textViewCashier.setText(String.valueOf(payment.getCashierID()));

        TextView textViewMwst = (TextView) getView().findViewById(R.id.textView_mwst);
        textViewMwst.setText(getString(R.string.mwst)+" "+ euroFormat.formatPrice(((payment.getTotalAmount() * 19) / 100)));

        if (paymentSelected.getCommodities() != null) {
            adapter = new CommodityListViewAdapter(getActivity(), R.layout.view_commodity_list, paymentSelected.getCommodities());
            ListView listView = (ListView) getView().findViewById(R.id.listView3);
            listView.setAdapter(adapter);
        }
    }


    private ArrayList<Commodity> getCommoditiesOfPayment(Payment payment) {
        ArrayList<Commodity> commodities = new ArrayList<Commodity>();
        //get all payment-positions
        PaymentPositionDataSource paymentPositionDataSource = new PaymentPositionDataSource(getActivity());
        paymentPositionDataSource.open();
        ArrayList<PaymentPosition> paymentPositions = paymentPositionDataSource.getPaymentPositionsByPaymentId(payment.getId());
        paymentPositionDataSource.close();

        CommodityDataSource commodityDataSource = new CommodityDataSource(getActivity());
        commodityDataSource.open();
        if (paymentPositions != null) {
            for (PaymentPosition pP : paymentPositions) {
                Commodity com = null;
                if (pP.getCommodityId() == 0) {
                    if (pP.getPercentage() != 0) {
                        com = new Discount();
                        com.setName(getString(R.string.discount));
                        ((Discount) com).setPercentage(pP.getPercentage());
                    } else {
                        com = new Commodity();
                        com.setName(getString(R.string.manual_entry));
                    }
                } else {
                    com = commodityDataSource.getCommodityById(pP.getCommodityId());
                }
                com.setAmount(pP.getNumber());
                com.setPrice(pP.getPrice());
                commodities.add(com);
            }
        }
        return commodities;
    }

    public void showEmptyView(){
        getView().findViewById(R.id.relativeLayout_header_details).setVisibility(View.GONE);
        getView().findViewById(R.id.relativeLayout_body_details).setVisibility(View.GONE);
        getView().findViewById(R.id.textView_empty_details).setVisibility(View.VISIBLE);
    }

    public void hideEmptyView(){
        getView().findViewById(R.id.relativeLayout_header_details).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.relativeLayout_body_details).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.textView_empty_details).setVisibility(View.GONE);
    }


    public Payment getPaymentSelected(){
        return paymentSelected;
    }

    protected abstract int getLayoutResourceID();
 }

