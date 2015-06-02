package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.BasketActivity;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.adapter.CommodityGridViewAdapter;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.fragment.BasketFragment;
import com.example.inga.mcash.fragment.ProductsFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Inga on 08.03.2015.
 */
public class ProductsActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private Button buttonBasket;
    private BasketFragment basketFragment;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GridView gV = (GridView) findViewById(R.id.gridView_products);
        gV.setOnItemClickListener(this);

        Button buttonScan = (Button) findViewById(R.id.button_scan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBar();
            }
        });

        basketFragment = (BasketFragment) getFragmentManager().findFragmentById(R.id.fragment_basket);
        if(basketFragment==null) {
            buttonBasket = (Button) findViewById(R.id.buttonBasket);
            buttonBasket.setText(new EuroFormat().formatPrice(LoginActivity.basket.getTotalAmount()));
            buttonBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_products;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_products;
    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Commodity com = (Commodity) parent.getItemAtPosition(position);
            if (com.getIsGroup() == Commodity.NOT_GROUP) {
                LoginActivity.basket.addCommodity(com);
                if(buttonBasket!=null) {
                    buttonBasket.setText(new EuroFormat().formatPrice(LoginActivity.basket.getTotalAmount()));
                }

                else{
                    if(basketFragment!=null) {
                        basketFragment.update();
                    }
                }

            } else {
               ProductsFragment productsFragment = (ProductsFragment) getFragmentManager().findFragmentById(R.id.products_fragment);
                if(productsFragment!= null) {
                    productsFragment.showCommoditiesOfGroup(com);
                }

            }



    }

    public void scanBar() {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            System.out.println("exeption");
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                CommodityDataSource commodityDataSource = new CommodityDataSource(getApplicationContext());
                commodityDataSource.open();
                String contents = intent.getStringExtra("SCAN_RESULT");
                List<Commodity> commodities = commodityDataSource.getAllCommodities();
                commodityDataSource.close();
                if(commodities != null) {
                    boolean productFound = false;
                    for (Commodity commo : commodities) {
                        String ean = commo.getEan();
                        if(ean!=null) {
                            if (ean.equalsIgnoreCase(contents)) {
                                LoginActivity.basket.addCommodity(commo);
                                productFound = true;
                                if (basketFragment != null) {
                                    basketFragment.update();
                                }
                                else{
                                    buttonBasket.setText(new EuroFormat().formatPrice(LoginActivity.basket.getTotalAmount()));
                                }
                            }
                        }
                    }
                    if(!productFound){
                        Toast toast = Toast.makeText(this, contents, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        }

    }
}
