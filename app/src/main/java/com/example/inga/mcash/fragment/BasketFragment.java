package com.example.inga.mcash.fragment;

import android.app.Activity;
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
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.activitiy.PayActivity;
import com.example.inga.mcash.adapter.CommodityBasketListViewAdapter;
import com.example.inga.mcash.dialog.BaseDialog;

import java.util.ArrayList;

/**
 * Created by Inga on 13.03.2015.
 */
public class BasketFragment extends Fragment {

    CommodityBasketListViewAdapter adapter;
    TextView textViewTotal;
    FrameLayout layoutEmptyMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        layoutEmptyMessage = (FrameLayout) activity.findViewById(R.id.frameLayout2);
        textViewTotal = (TextView) activity.findViewById(R.id.textView_totalamount);


        ArrayList<Commodity> commodities;
        commodities = LoginActivity.basket.getCommodities();


        Button buttonPay = (Button) activity.findViewById(R.id.button_payment);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });

        if (commodities.size() == 0) {
            layoutEmptyMessage.setVisibility(View.VISIBLE);
            buttonPay.setClickable(false);
        }

        ListView listView = (ListView) activity.findViewById(R.id.listView2);
        adapter = new CommodityBasketListViewAdapter(activity, R.layout.view_commodity_list, commodities, textViewTotal, layoutEmptyMessage, buttonPay);
        listView.setAdapter(adapter);


        Button clearButton = (Button) activity.findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                showTotal();

            }
        });

        Button buttonDiscount = (Button) activity.findViewById(R.id.button_discount);
        buttonDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscountDialog();
            }
        });

        Button buttonManual = (Button) activity.findViewById(R.id.button_manuel);
        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showManualDialog();
            }
        });

        showTotal();

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
                    adapter.add(discount);
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
                    adapter.add(manuelCommodity);
                } else {
                    dismiss();
                }
            }
        };
        newFragment.show(getFragmentManager(), "manual_dialog");
    }


}
