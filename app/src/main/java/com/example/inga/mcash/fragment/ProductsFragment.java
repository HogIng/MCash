package com.example.inga.mcash.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.BasketActivity;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.adapter.CommodityGridViewAdapter;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.dialog.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inga on 11.03.2015.
 */
public class ProductsFragment extends Fragment {

    CommodityDataSource cDS;
    ArrayList<Commodity> commoditiesSelected;
    List<Commodity> commodities;
    TextView title;
    CommodityGridViewAdapter cGVA;
    ImageButton buttonBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        cDS = new CommodityDataSource(getActivity());
        cDS.open();
        commodities = cDS.getAllCommodities();
        commoditiesSelected = cDS.getCommoditiesOfGroup(0);
        cDS.close();

        ImageButton buttonSearch = (ImageButton) view.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });

        buttonBack = (ImageButton) view.findViewById(R.id.button_back);
        buttonBack.setVisibility(View.GONE);
        setBackButtonListenerProductsNotEmpty();

        if (commoditiesSelected != null) {
            cGVA = new CommodityGridViewAdapter(getActivity(), R.layout.view_commodity, commoditiesSelected);
            GridView gridView = (GridView) view.findViewById(R.id.gridView_products);
            gridView.setAdapter(cGVA);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        title = (TextView) getActivity().findViewById(R.id.textView_title);
    }



    private void showSearchDialog() {
        DialogFragment newFragment = new BaseDialog() {
            EditText editTextValue;

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                Dialog dialog = super.onCreateDialog(savedInstanceState);
                return dialog;
            }

            @Override
            protected int getLayoutResourceId() {
                return R.layout.dialog_search;
            }

            @Override
            protected int getTitleResourceId() {
                return R.string.product_search;
            }

            @Override
            protected void doPositiveAction() {

                editTextValue = (EditText) v.findViewById(R.id.editText_Search);
                String valueStr = editTextValue.getText().toString();
                if (!valueStr.equals("")) {
                    buttonBack.setVisibility(View.VISIBLE);
                    String[] charsToReplace = new String[]{",", ".", "€", " "};
                    for (int i = 0; i < charsToReplace.length; i++) {
                        valueStr = valueStr.replace(charsToReplace[i], "");
                    }
                    commoditiesSelected.clear();
                    for (Commodity commodity : commodities) {
                        String idStr = String.valueOf(commodity.getId());
                        String nameStr = commodity.getName();
                        String priceStr = String.valueOf(commodity.getPrice());
                        if (nameStr.equalsIgnoreCase(valueStr) || idStr.equalsIgnoreCase(valueStr) || priceStr.equals(valueStr)) {
                            commoditiesSelected.add(commodity);
                        }
                    }
                    cGVA.notifyDataSetChanged();
                    if (commoditiesSelected.size() <= 0) {
                        showEmptyView();
                        setBackButtonListenerProductsEmpty();
                    } else {
                        hideEmptyView();
                    }
                } else {
                    dismiss();
                }
            }
        };
        newFragment.show(getFragmentManager(), "discount_dialog");
    }

    private void showEmptyView() {
        getActivity().findViewById(R.id.frameLayout_noProducts).setVisibility(View.VISIBLE);
    }

    private void hideEmptyView() {
        getActivity().findViewById(R.id.frameLayout_noProducts).setVisibility(View.GONE);
    }

    private void setBackButtonListenerProductsNotEmpty() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int parentGroupId = 0;
                if (commoditiesSelected != null && commoditiesSelected.size() > 0) {
                    hideEmptyView();
                    int groupId = commoditiesSelected.get(0).getGroupId();
                    for (Commodity commodity : commodities) {
                        if (commodity.getId() == groupId) {
                            parentGroupId = commodity.getGroupId();
                        }
                    }
                    commoditiesSelected.clear();
                    for (Commodity commodity : commodities) {
                        if (commodity.getGroupId() == parentGroupId) {
                            commoditiesSelected.add(commodity);
                        }
                    }
                    cGVA.notifyDataSetChanged();
                    if (parentGroupId == 0) {
                        buttonBack.setVisibility(View.GONE);
                        title.setText(getResources().getString(R.string.title_products));
                    } else {
                        cDS.open();
                        Commodity commodityParent = cDS.getCommodityById(parentGroupId);
                        cDS.close();
                        title.setText(commodityParent.getName());
                    }
                } else {
                    showEmptyView();
                }
            }
        });
    }

    private void setBackButtonListenerProductsEmpty() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Commodity commodity : commodities) {
                    if (commodity.getGroupId() == 0) {
                        commoditiesSelected.add(commodity);
                    }
                }
                cGVA.notifyDataSetChanged();
                setBackButtonListenerProductsNotEmpty();
                buttonBack.setVisibility(View.GONE);
                hideEmptyView();
                title.setText(getResources().getString(R.string.title_products));
            }
        });
    }

    public void showCommoditiesOfGroup(Commodity com) {
        cGVA.clear();
        commoditiesSelected.clear();
        for (Commodity commodity : commodities) {
            if (commodity.getGroupId() == com.getId()) {
                commoditiesSelected.add(commodity);
            }
        }
        title.setText(com.getName());
        buttonBack.setVisibility(View.VISIBLE);
        cGVA.notifyDataSetChanged();
        if (commoditiesSelected.size() <= 0) {
            showEmptyView();
        } else {
            hideEmptyView();
        }
    }




}
