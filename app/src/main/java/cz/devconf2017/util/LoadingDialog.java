package cz.devconf2017.util;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import cz.devconf2017.R;

public class LoadingDialog extends DialogFragment {

    private static final String ARG_TITLE = "TITLE";
    private String title;

    public static LoadingDialog newInstance(String title) {
        LoadingDialog fragment = new LoadingDialog();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);

        return fragment;
    }

    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }

        setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_loading);

        if (title != null) {
            alertDialog.setTitle(title);
        }

        return alertDialog.create();
    }
}