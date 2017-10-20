package cz.devconf2017.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cz.devconf2017.util.LoadingDialog;

/**
 * Base class for all activities, containing common logic. Child activities should implement
 * onViewCreated in order to configure views.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static LoadingDialog _loadingDialog;

    private boolean visible;

    private LoadingDialog getLoadingDialog() {
        if (_loadingDialog == null) {
            _loadingDialog = LoadingDialog.newInstance();
        }
        return _loadingDialog;
    }

    protected void showLoading() {
        // TODO eliminate this boolean. Check if dialog has been added using supportFragmentManager
        if (!visible) {
            getLoadingDialog().show(getSupportFragmentManager(), "loading");
            visible = true;
        }
    }

    protected void hideLoading() {
        if (visible) {
            getLoadingDialog().dismiss();
            visible = false;
        }
    }

    protected void showToast(int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }
}
