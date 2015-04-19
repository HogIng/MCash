package com.example.inga.mcash.dialog;

import com.example.inga.mcash.R;

/**
 * Created by Inga on 27.03.2015.
 */
public class CancellationDialog extends BaseDialog {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_cancellation;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.cancellation;
    }

    @Override
    protected void doPositiveAction() {

    }
}
