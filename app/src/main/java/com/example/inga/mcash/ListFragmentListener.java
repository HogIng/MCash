package com.example.inga.mcash;

import android.view.View;

/**
 * Created by Inga on 04.07.2015.
 */
public interface ListFragmentListener {

    public void onButtonNextClicked();

    public void onButtonBackClicked();

    public void onListItemSelected(Payment payment);
}
