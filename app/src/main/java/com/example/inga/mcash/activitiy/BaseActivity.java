package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inga.mcash.MenuItem;
import com.example.inga.mcash.R;
import com.example.inga.mcash.fragment.SlidingMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;

/**
 * Created by Inga on 22.03.2015.
 */
public abstract class BaseActivity extends SlidingFragmentActivity {

    public SlidingMenuFragment mFrag;
    public SlidingMenu sm;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResourceId());
        if (screenIsLarge()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        setBehindContentView(R.layout.slidingmenu);
        if (bundle == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new SlidingMenuFragment();
            t.replace(R.id.slidingmenu, mFrag);
            t.commit();
        }
        else {
            mFrag = (SlidingMenuFragment) this.getSupportFragmentManager().findFragmentById(R.id.slidingmenu);
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
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            sm.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean screenIsLarge() {
        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return true;
        }
        return false;
    }

}