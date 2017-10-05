package cz.devconf2017.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cz.devconf2017.util.LoadingDialog;

/**
 * Base class for all activities, containing common logic. Child activities should implement
 * onViewCreated in order to configure views.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @NonNull
    private LoadingDialog loadingDialog;

    private boolean visible;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = LoadingDialog.newInstance();
    }

    protected void showLoading() {
        // TODO eliminate this boolean. Check if dialog has been added using supporFragmentManager
        if (!visible) {
            loadingDialog.show(getSupportFragmentManager(), "loading");
            visible = true;
        }
    }

    protected void hideLoading() {
        loadingDialog.dismiss();
        visible = false;
    }

    protected void showToast(int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }
}
