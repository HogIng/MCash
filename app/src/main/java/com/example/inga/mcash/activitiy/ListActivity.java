package com.example.inga.mcash.activitiy;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.inga.mcash.ListFragmentListener;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.adapter.PaymentListViewAdapter;
import com.example.inga.mcash.fragment.DetailsFragment;
import com.example.inga.mcash.fragment.ListFragment;

/**
 * Created by Inga on 03.07.2015.
 */
public abstract class ListActivity extends BaseActivity implements ListFragmentListener{

    private View selectedView;
    protected DetailsFragment detailsFragment;
    protected ListFragment listFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFragments();
        listFragment.addListener(this);
        ListView listView = (ListView) findViewById(R.id.listView_payments);
        final PaymentListViewAdapter paymentListViewAdapter =(PaymentListViewAdapter) listView.getAdapter();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Payment payment = (Payment) adapter.getItemAtPosition(position);
                if (detailsFragment != null) {
                    if(selectedView == null){
                        selectedView = paymentListViewAdapter.getFirstView();
                    }
                    selectedView.setBackgroundColor(getResources().getColor(R.color.grey_light_background));
                    v.setBackgroundColor(getResources().getColor(R.color.grey_background));
                    selectedView = v;
                    detailsFragment.setPayment(payment);
                } else {
                    startDetailsActivity(payment);
                }

            }
        });

        if(detailsFragment!=null){
            if(listFragment.getPaymentsSelected().size()==0){
                detailsFragment.showEmptyView();
            }
            else{
                detailsFragment.setPayment(listFragment.getPaymentsSelected().get(0));
            }

        }
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected int getTitleResourceId() {
        return 0;
    }

    protected void updateDetailsFragment(){
        if(listFragment.getPaymentsSelected().size()>0){
            detailsFragment.setPayment(listFragment.getPaymentsSelected().get(0));
            detailsFragment.hideEmptyView();
        }
        else{
            detailsFragment.showEmptyView();
        }
    }

    protected abstract void setFragments();

    protected abstract void startDetailsActivity(Payment payment);

    @Override
    public void onButtonNextClicked(){
        listFragment.selectNextDay();
        if(detailsFragment!=null){
            updateDetailsFragment();
        }
    }

    @Override
    public void onButtonBackClicked(){
        listFragment.selectDayBefore();
        if(detailsFragment!=null){
            updateDetailsFragment();
        }
    }

    @Override
    public void onListItemSelected(Payment payment){
        if (detailsFragment != null) {
            detailsFragment.setPayment(payment);
        } else {
            startDetailsActivity(payment);
        }
    }

}
