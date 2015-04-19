package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.BasketActivity;
import com.example.inga.mcash.activitiy.LoginActivity;
import com.example.inga.mcash.adapter.CommodityGridViewAdapter;
import com.example.inga.mcash.fragment.BasketFragment;
import com.example.inga.mcash.fragment.ProductsFragment;


/**
 * Created by Inga on 08.03.2015.
 */
public class ProductsActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private Button buttonBasket;
    private BasketFragment basketFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GridView gV = (GridView) findViewById(R.id.gridView_products);
        gV.setOnItemClickListener(this);

        basketFragment = (BasketFragment) getFragmentManager().findFragmentById(R.id.fragment_basket);
        if(basketFragment==null) {
            buttonBasket = (Button) findViewById(R.id.buttonBasket);
            buttonBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            Button buttonOrder = (Button) findViewById(R.id.buttonOrder);

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
                if(basketFragment==null) {
                    Button basketButton = (Button) findViewById(R.id.buttonBasket);
                    basketButton.setText(new EuroFormat().formatPrice(LoginActivity.basket.getTotalAmount()));
                }

            } else {
               ProductsFragment productsFragment = (ProductsFragment) getFragmentManager().findFragmentById(R.id.products_fragment);
                if(productsFragment!= null) {
                    productsFragment.showCommoditiesOfGroup(com);
                }
            }

            basketFragment.update();

    }
}
