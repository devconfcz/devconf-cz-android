package cz.devconf2017.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cz.devconf2017.util.LoadingDialog;

/**
 * Base class for all activities, containing common logic. Child activities should implement
 * onViewCreated in order to configure views.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = LoadingDialog.newInstance();
    }

    protected void showLoading() {
        loadingDialog.show(getSupportFragmentManager(), "loading");
    }

    protected void hideLoading() {
        loadingDialog.dismiss();
    }

    protected void showToast(int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }
}
