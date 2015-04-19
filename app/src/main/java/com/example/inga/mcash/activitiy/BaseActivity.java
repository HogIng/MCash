package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inga.mcash.R;
import com.example.inga.mcash.fragment.SlidingMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;

/**
 * Created by Inga on 22.03.2015.
 */
public abstract class BaseActivity extends SlidingFragmentActivity {

    protected  LinearLayout productsButt;
    protected LinearLayout paymentsButt;
    private SlidingMenu slidingMenu ;
    private SlidingMenuFragment mFrag;
    private SlidingMenu sm;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResourceId());

        setBehindContentView(R.layout.slidingmenu);
        if (bundle == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new SlidingMenuFragment();
            t.replace(R.id.slidingmenu, mFrag);
            t.commit();
        } else {
            mFrag = (SlidingMenuFragment)this.getSupportFragmentManager().findFragmentById(R.id.slidingmenu);
        }
// customize the SlidingMenu
        sm = getSlidingMenu();
        sm.setBehindWidthRes(R.dimen.slidingMenu_offset);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        LinearLayout lLMenuButton = (LinearLayout) findViewById(R.id.menu_button);
        lLMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.toggle();
            }
        });


        TextView textViewTitle = (TextView) findViewById(R.id.textView_title);
        textViewTitle.setText(getResources().getString(getTitleResourceId()));

    }

    protected abstract int getLayoutResourceId();

    protected abstract int getTitleResourceId();

    @Override
    public void onBackPressed() {
            sm.toggle();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            sm.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}