package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.R;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.database.PaymentPositionDataSource;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inga on 17.03.2015.
 */
public class PayActivity extends BaseActivity {

    private EditText editTextCash;
    private TextView textViewChange;
    private TextView textViewAmount;
    private int totalAmount;
    private Button buttonChange;
    private Button buttonPay;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        editTextCash = (EditText) findViewById(R.id.editText_cash);
        totalAmount = LoginActivity.basket.getTotalAmount();
        textViewAmount = (TextView) findViewById(R.id.textView_amount);
        textViewAmount.setText(formatPrice(totalAmount));
        textViewChange = (TextView) findViewById(R.id.textView_cash);

        buttonChange = (Button) findViewById(R.id.button_change);
        buttonPay = (Button) findViewById(R.id.button_pay);

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long newId = savePayment();
                LoginActivity.basket.removeCommodities();
                Intent intent;
                if (screenIsLarge()) {
                    intent = new Intent(getApplicationContext(), PaymentsActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra(PaymentActivity.PAYMENT_ID, (int) newId);
                }

                startActivity(intent);
                finish();
            }
        });

        buttonChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String cashStr = editTextCash.getText().toString();

                if (!cashStr.equals("")) {
                    float cash = Float.parseFloat(cashStr);
                    int cashInt = (int) (cash * 100);

                    if (cashInt >= totalAmount) {
                        EuroFormat euroFormat = new EuroFormat();
                        int change = cashInt - totalAmount;
                        editTextCash.setVisibility(View.INVISIBLE);
                        TextView textViewCash = (TextView) findViewById(R.id.textView_cash);
                        textViewCash.setVisibility(View.VISIBLE);
                        textViewCash.setText(new EuroFormat().formatPrice(cashInt));
                        findViewById(R.id.frameLayout_divider).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout_change).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.textView_change)).setText("- " + euroFormat.formatPrice(change));
                        buttonChange.setVisibility(View.GONE);
                        buttonPay.setVisibility(View.VISIBLE);
                    } else {

                    }
                }
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_pay;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_payment;
    }

    private CharSequence formatPrice(int price) {
        NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        return numberFormat.format(price2);
    }

    private long savePayment() {
        Payment payment = new Payment();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        payment.setCalendar(calendar);
        payment.setStatus(Payment.STATUS_COMPLETED);
        payment.setTotalAmount(totalAmount);
        payment.setCashier(LoginActivity.cashier.getId());
        ArrayList<Commodity> commodities = LoginActivity.basket.getCommodities();
        payment.setCommoditiesList(commodities);

        PaymentDataSource pDS = new PaymentDataSource(getApplicationContext());
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

        PaymentPositionDataSource paymentPositionDataSource = new PaymentPositionDataSource(getApplicationContext());
        paymentPositionDataSource.open();

        if (LoginActivity.basket.getOrderId() != 0) {
            paymentPositionDataSource.deletePaymentPositionsOfPayment(paymentId);
        }

        for (Commodity com : commodities) {
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
        LoginActivity.basket.setOrderId(0);
        return paymentId;
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        if (screenIsLarge()) {
            intent = new Intent(getApplicationContext(), ProductsActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), BasketActivity.class);
        }
        startActivity(intent);
        finish();
    }


}