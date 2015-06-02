package com.example.inga.mcash.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.inga.mcash.activitiy.OrderActivity;
import com.example.inga.mcash.activitiy.OrdersActivity;
import com.example.inga.mcash.activitiy.PayActivity;
import com.example.inga.mcash.activitiy.PaymentActivity;
import com.example.inga.mcash.activitiy.ProductsActivity;
import com.example.inga.mcash.adapter.CommodityListViewAdapter;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.database.PaymentPositionDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class OrderFragment extends Fragment {

    CommodityListViewAdapter adapter;
    PaymentDataSource paymentDataSource;
    Payment paymentSelected;
    TextView tVAmount;
    View view1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.fragment_paymentdetails, container, false);
        paymentDataSource = new PaymentDataSource(getActivity());
        initButtons();
        return view1;
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

    protected void initButtons(){
        Button buttonPayOrder = (Button) view1.findViewById(R.id.buttonPayOrder);
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
                if(getActivity() instanceof OrderActivity) {
                    intent = new Intent(getActivity(), BasketActivity.class);
                }
                else{
                    intent = new Intent(getActivity(), ProductsActivity.class);
                }
                startActivity(intent);
                getActivity().finish();
            }
        });

        Button buttonDeleteOrder = (Button) view1.findViewById(R.id.button_cancel);
        buttonDeleteOrder.setText(getString(R.string.delete_order));
        buttonDeleteOrder.setBackgroundColor(getResources().getColor(R.color.grey_dark));

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

    public void deleteOrder(){
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

    public Payment getPaymentSelected(){
        return paymentSelected;
    }
}
