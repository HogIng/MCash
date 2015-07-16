package com.example.inga.mcash.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inga.mcash.ListFragmentListener;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.adapter.PaymentListViewAdapter;
import com.example.inga.mcash.database.PaymentDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inga on 03.07.2015.
 */
public abstract class ListFragment extends Fragment {

    private PaymentListViewAdapter adapter;
    private ArrayList<Payment> payments;
    private ArrayList<Payment> paymentsSelected;
    private Calendar calendar;
    private TextView textViewDay;
    private Button buttonNext;
    private View view;
    private View selectedListView;
    private ArrayList<ListFragmentListener> listeners;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payments, container, false);
        listeners = new ArrayList<>();
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        paymentsSelected = new ArrayList<>();
        textViewDay = (TextView) view.findViewById(R.id.textViewDay);

        ListView listView = (ListView) view.findViewById(R.id.listView_payments);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter1, View v, int position,
                                    long arg3) {
                Payment payment = (Payment) adapter1.getItemAtPosition(position);
                if (selectedListView == null) {
                    selectedListView = adapter.getFirstView();
                }
                selectedListView.setBackgroundColor(getResources().getColor(R.color.grey_light_background));
                v.setBackgroundColor(getResources().getColor(R.color.grey_background));
                selectedListView = v;
                for (ListFragmentListener listener : listeners) {
                    listener.onListItemSelected(payment);
                }
            }
        });

        buttonNext = (Button) view.findViewById(R.id.button_next);
        if (todaySelected()) {
            buttonNext.setVisibility(View.INVISIBLE);
        }


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ListFragmentListener listener : listeners) {
                    listener.onButtonNextClicked();
                }
            }
        });

        Button buttonBack = (Button) view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ListFragmentListener listener : listeners) {
                    listener.onButtonBackClicked();
                }
            }
        });

        PaymentDataSource paymentDataSource = new PaymentDataSource(view.getContext());
        paymentDataSource.open();
        payments = paymentDataSource.getPaymentsSortedByTime();
        paymentDataSource.close();
        setAdapter();
        setPayments();
        showSelectedDayText();
        return view;
    }


    public void selectNextDay() {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        if (todaySelected()) {
            buttonNext.setVisibility(View.INVISIBLE);
        }
        setPayments();
        showSelectedDayText();
    }

    public void selectDayBefore() {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        buttonNext.setVisibility(View.VISIBLE);
        setPayments();
        showSelectedDayText();
    }

    public void selectToday() {
        calendar.setTime(new Date());
    }


    private void showSelectedDayText() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        textViewDay.setText(simpleDateFormat.format(calendar.getTime()));
    }


    private void setPayments() {
        paymentsSelected.clear();
        for (Payment payment : payments) {
            int day = payment.getCalendar().get(Calendar.DAY_OF_MONTH);
            int month = payment.getCalendar().get(Calendar.MONTH);
            int year = payment.getCalendar().get(Calendar.YEAR);

            int daySelected = calendar.get(Calendar.DAY_OF_MONTH);
            int monthSelected = calendar.get(Calendar.MONTH);
            int yearSelected = calendar.get(Calendar.YEAR);

            if (day == daySelected && month == monthSelected && year == yearSelected && paymentHasRightStatus(payment)) {
                paymentsSelected.add(payment);
            }
        }
        if (paymentsSelected.size() == 0) {
            view.findViewById(R.id.frameLayout3).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.frameLayout3).setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        adapter = new PaymentListViewAdapter(getActivity(), R.layout.view_payment_list, paymentsSelected);
        ListView listView = (ListView) view.findViewById(R.id.listView_payments);
        listView.setAdapter(adapter);
    }

    private boolean todaySelected() {
        Calendar toDayCalendar = Calendar.getInstance();
        toDayCalendar.setTime(new Date());
        if (calendar.get(Calendar.DAY_OF_MONTH) == toDayCalendar.get(Calendar.DAY_OF_MONTH)) {
            return true;
        } else {
            return false;
        }
    }

    protected abstract boolean paymentHasRightStatus(Payment payment);

    public ArrayList<Payment> getPaymentsSelected() {
        return paymentsSelected;
    }

    public void updateDataSet() {
        PaymentDataSource paymentDataSource = new PaymentDataSource(view.getContext());
        paymentDataSource.open();
        payments = paymentDataSource.getPaymentsSortedByTime();
        paymentDataSource.close();
        setPayments();
    }

    public void addListener(ListFragmentListener listener) {
        listeners.add(listener);
    }
}