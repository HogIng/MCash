package com.example.inga.mcash.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.inga.mcash.R;

import org.w3c.dom.Text;

/**
 * Created by Inga on 26.03.2015.
 */
public abstract class BaseDialog extends DialogFragment {

    public View v;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        v =inflater.inflate(getLayoutResourceId(), null);
        builder.setView(v)
                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doPositiveAction();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        TextView textViewTitle =(TextView) v.findViewById(R.id.textView_dialogtitle);
        textViewTitle.setText(getTitleResourceId());
        // Create the AlertDialog object and return it
        return builder.create();
    }

    protected abstract int getLayoutResourceId();

    protected abstract int getTitleResourceId();

    protected abstract void doPositiveAction();


}
