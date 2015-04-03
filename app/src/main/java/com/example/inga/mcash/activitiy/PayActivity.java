package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.PaymentActivity;
import com.example.inga.mcash.PaymentDataSource;
import com.example.inga.mcash.PaymentPositionDataSource;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.LoginActivity;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Inga on 17.03.2015.
 */
public class PayActivity extends Activity {

    private EditText editTextCash;
    private TextView textViewChange;
    private TextView textViewAmount;
    private int totalAmount;
    private Button buttonChange;
    private Button buttonPay;
    public static final String PAYMENT_ID = "paymentid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


        editTextCash =(EditText) findViewById(R.id.editText_cash);
        textViewChange = (TextView) findViewById(R.id.textView_to_pay);
        totalAmount =  LoginActivity.basket.getTotalAmount();
        textViewAmount = (TextView) findViewById(R.id.textView_amount);

        textViewAmount.setText(formatPrice(totalAmount));

        buttonChange = (Button) findViewById(R.id.button_change);
        buttonPay = (Button) findViewById(R.id.button_pay);

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment payment = new Payment();
                payment.setDate((int)new Date().getTime());
                payment.setStatus(Payment.STATUS_COMPLETED);
                payment.setTotalAmount(totalAmount);
                payment.setCashier( LoginActivity.cashier.getId());
                ArrayList<Commodity> commodities = LoginActivity.basket.getCommodities();
                payment.setCommoditiesList(commodities);
                PaymentDataSource pDS = new PaymentDataSource(getApplicationContext());
                pDS.open();
                long newId = pDS.createPayment(payment);
                pDS.close();
                PaymentPositionDataSource paymentPositionDataSource = new PaymentPositionDataSource(getApplicationContext());
                paymentPositionDataSource.open();
                for(int i = 0; i<commodities.size();i++){
                    Commodity com = commodities.get(i);
                    paymentPositionDataSource.createPosition((int) newId,com.getId(),com.getAmount());
                }
                paymentPositionDataSource.close();
                Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                intent.putExtra(PAYMENT_ID,(int)newId);
                startActivity(intent);
                finish();
            }
        });

        buttonChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String cashStr = editTextCash.getText().toString();

                if(!cashStr.equals("")){
                    float cash = Float.parseFloat(cashStr);
                    int cashInt = (int) (cash*100);

                    if(cashInt >= totalAmount){
                        textViewChange.setText(R.string.change);
                        int change = cashInt - totalAmount;
                        textViewAmount.setText(formatPrice(change));
                        editTextCash.setVisibility(View.GONE);
                        findViewById(R.id.textView_cash).setVisibility(View.GONE);
                        buttonChange.setVisibility(View.GONE);
                        buttonPay.setVisibility(View.VISIBLE);
                    }

                    else{

                    }
                }
            }
        });

    }

    private CharSequence formatPrice(int price){
        NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        return numberFormat.format(price2);
    }
}