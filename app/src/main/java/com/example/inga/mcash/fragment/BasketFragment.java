package com.example.inga.mcash.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.activitiy.OrderDetailsActivity;
import com.example.inga.mcash.activitiy.OrdersActivity;
import com.example.inga.mcash.activitiy.PayActivity;
import com.example.inga.mcash.activitiy.PaymentDetailsActivity;
import com.example.inga.mcash.activitiy.ProductsActivity;
import com.example.inga.mcash.adapter.CommodityBasketListViewAdapter;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.database.PaymentPositionDataSource;
import com.example.inga.mcash.dialog.BaseDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inga on 13.03.2015.
 */
public class BasketFragment extends Fragment {

    private CommodityBasketListViewAdapter adapter;
    private TextView textViewTotal;
    private FrameLayout layoutEmptyMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        layoutEmptyMessage = (FrameLayout) view.findViewById(R.id.frameLayout2);
        textViewTotal = (TextView) view.findViewById(R.id.textView_totalamount);

        Button buttonPay = (Button) view.findViewById(R.id.button_payment);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        Button buttonOrder = (Button) view.findViewById(R.id.buttonOrder);
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long newId = savePayment();
                LoginActivity.basket.removeCommodities();
                Intent intent = null;
                if (getActivity() instanceof ProductsActivity) {
                    intent = new Intent(getActivity(), OrdersActivity.class);
                } else {
                    intent = new Intent(getActivity(), OrderDetailsActivity.class);
                }
                intent.putExtra(PaymentDetailsActivity.PAYMENT_ID, (int) newId);
                startActivity(intent);
                getActivity().finish();
            }
        });


        if (LoginActivity.basket.getCommodities().size() == 0) {
            layoutEmptyMessage.setVisibility(View.VISIBLE);
            buttonPay.setClickable(false);
            buttonOrder.setClickable(false);
        }


        ListView listView = (ListView) view.findViewById(R.id.listView2);
        adapter = new CommodityBasketListViewAdapter(getActivity(), R.layout.view_commodity_list, LoginActivity.basket.getCommodities(), textViewTotal, layoutEmptyMessage, buttonPay, buttonOrder);
        listView.setAdapter(adapter);


        Button clearButton = (Button) view.findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.basket.removeCommodities();
                adapter.notifyDataSetChanged();
                showTotal();

            }
        });

        Button buttonDiscount = (Button) view.findViewById(R.id.button_discount);
        buttonDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscountDialog();
            }
        });

        Button buttonManual = (Button) view.findViewById(R.id.button_manuel);
        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showManualDialog();
            }
        });

        showTotal();

        return view;
    }


    private void showTotal() {
        textViewTotal.setText(new EuroFormat().formatPrice(LoginActivity.basket.getTotalAmount()));
    }

    private void showDiscountDialog() {
        DialogFragment newFragment = new BaseDialog() {

            EditText editTextValue;

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                Dialog dialog = super.onCreateDialog(savedInstanceState);
                editTextValue = (EditText) v.findViewById(R.id.editText_percentage);
                return dialog;
            }

            @Override
            protected int getLayoutResourceId() {
                return R.layout.dialog_discount;
            }

            @Override
            protected int getTitleResourceId() {
                return R.string.discount;
            }

            @Override
            protected void doPositiveAction() {
                Discount discount = new Discount();
                discount.setName(getString(R.string.discount));
                discount.setImage("discount2");
                String valueStr = editTextValue.getText().toString();
                if (!valueStr.equals("")) {
                    int percentage = Integer.parseInt(valueStr);
                    discount.setPercentage(percentage);
                    LoginActivity.basket.addCommodity(discount);
                    adapter.notifyDataSetChanged();
                } else {
                    dismiss();
                }
            }
        };
        newFragment.show(getFragmentManager(), "discount_dialog");
    }

    private void showManualDialog() {
        DialogFragment newFragment = new BaseDialog() {

            EditText editTextValue;

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                Dialog dialog = super.onCreateDialog(savedInstanceState);
                editTextValue = (EditText) v.findViewById(R.id.editText_percentage);
                editTextValue.setHint(getString(R.string.price));
                TextView textViewUnit = (TextView) v.findViewById(R.id.textView_unit);
                textViewUnit.setText(getString(R.string.euro_sign));
                return dialog;
            }

            @Override
            protected int getLayoutResourceId() {
                return R.layout.dialog_discount;
            }

            @Override
            protected int getTitleResourceId() {
                return R.string.manual_entry;
            }

            @Override
            protected void doPositiveAction() {
                Commodity manuelCommodity = new Commodity();
                manuelCommodity.setName(getString(R.string.manual_entry));
                manuelCommodity.setImage("manual");
                String priceStr = editTextValue.getText().toString();
                if (!priceStr.equals("")) {
                    float cash = Float.parseFloat(priceStr);
                    int price = (int) (cash * 100);
                    manuelCommodity.setPrice(price);
                    LoginActivity.basket.addCommodity(manuelCommodity);
                    adapter.notifyDataSetChanged();
                } else {
                    dismiss();
                }
            }
        };
        newFragment.show(getFragmentManager(), "manual_dialog");
    }

    public long savePayment() {
        Payment payment = new Payment();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        payment.setCalendar(calendar);
        payment.setStatus(Payment.STATUS_ORDERED);
        payment.setTotalAmount(LoginActivity.basket.getTotalAmount());
        payment.setCashier(LoginActivity.cashier.getId());
        ArrayList<Commodity> commodities = LoginActivity.basket.getCommodities();
        payment.setCommoditiesList(commodities);
        PaymentDataSource pDS = new PaymentDataSource(getActivity());
        pDS.open();
        int paymentId;
        if (LoginActivity.basket.getOrderId() == 0) {
            paymentId = (int) pDS.createPayment(payment);
        } else {
            paymentId = LoginActivity.basket.getOrderId();
            payment.setId(paymentId);
            pDS.updatePayment(payment);
        }
        pDS.close();
        PaymentPositionDataSource paymentPositionDataSource = new PaymentPositionDataSource(getActivity());
        paymentPositionDataSource.open();
        if (LoginActivity.basket.getOrderId() != 0) {
            paymentPositionDataSource.deletePaymentPositionsOfPayment(paymentId);
        }
        for (Commodity com : commodities) {
            System.out.println("PP: " + paymentId + " " + com.getId() + " " + com.getPrice() + " " + com.getAmount());
            PaymentPosition pP = new PaymentPosition();
            pP.setPaymentId(paymentId);
            pP.setCommodityId(com.getId());
            pP.setPrice(com.getPrice());
            pP.setNumber(com.getAmount());
            if (com instanceof Discount) {
                pP.setPercentage(((Discount) com).getPercentage());
            }
            paymentPositionDataSource.createPosition(pP);
        }
        paymentPositionDataSource.close();
        return paymentId;
    }

    public void update() {
        adapter.notifyDataSetChanged();
        showTotal();
    }


}
