package com.example.mysqlitedata.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;


/**
 * Generic Confirmation dialog handler
 */

public class GenericConfirmDialog extends DialogFragment {

    //Tag for the class name
    private final String TAG = GenericConfirmDialog.class.getSimpleName();
    private final String DIALOG_TITLE = "Confirmation Dialog";
    private final String PREFIX = "Selected ID: ";
    private long ID;
    private final String POSTFIX = "Please select one of the listed below options:";

    private GenericDialogListener mHost;

    private String deleteBtn, updateBtn, cancelBtn;

    public void setButtons(String positiveBtn, String negativeBtn, String cancelBtn, long id){

        this.deleteBtn = positiveBtn;
        this.updateBtn = negativeBtn;
        this.cancelBtn = cancelBtn;
        this.ID = id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        Log.d(TAG + ": onCreateDialog", " method called");

        //Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Set Builder properties
        builder.setTitle(DIALOG_TITLE);

        //String msg = savedInstanceState.getString("message");
        builder.setMessage(PREFIX + ID + "\n\n" + POSTFIX);

        //Set buttons
        //DELETE
        builder.setPositiveButton(deleteBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.d(TAG + ": onCreateDialog", "deleteBtn button selected");
                mHost.genericPositiveResult(GenericConfirmDialog.this);
            }
        });

        //UPDATE
        builder.setNegativeButton(updateBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.d(TAG + ": onCreateDialog", "updateBtn button selected");
                mHost.genericNegativeResult(GenericConfirmDialog.this);
            }
        });

        //CANCEL
        builder.setNeutralButton(cancelBtn, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {

                Log.d(TAG + ": setNeutralButton", "cancelBtn button selected");
                mHost.genericNeutralResult(GenericConfirmDialog.this);
            }
        });


        //Return the created dialog
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dlg){

        super.onCancel(dlg);
        Log.d(TAG + ": onCreateDialog", DIALOG_TITLE + " is canceled by user");
    }

    //Implements an interface for hosts to get callbacks
    public interface GenericDialogListener {

        void genericPositiveResult(DialogFragment dlg);
        void genericNegativeResult(DialogFragment dlg);
        void genericNeutralResult(DialogFragment dlg);
    }

    //Override onAttach to get Activity instance
    //TODO - Warning: Overrides deprecated method in 'android.app.Fragment'
    @Override
    public void onAttach(Activity activity){

        //TODO - Warning: 'onAttach(android.app.Activity)' is deprecated
        super.onAttach(activity);
        mHost = (GenericDialogListener)activity;
        Log.d(TAG + ": onAttach", " is called");
    }

}
