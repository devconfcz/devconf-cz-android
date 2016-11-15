package cz.devconf;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.menu)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupNavigationMenu();
    }

    private void setupNavigationMenu() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.presentation:
                        displayPresentation();
                        break;
                    case R.id.venue:
                        displayVenue();
                        break;
                    case R.id.floor_plan:
                        displayFloorPlan();
                        break;
                    case R.id.speakers:
                        displaySpeakers();
                        break;
                    case R.id.social_event:
                        displaySocialEvent();
                        break;
                    case R.id.bug:
                        displayBug();
                        break;
                    case R.id.signout:
                        signOut();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @OnClick(R.id.drawer)
    void openDrawerMenu() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    // -- Navigation ------------------------------------------------------------------------------

    private void displayPresentation() {
    }

    private void displayVenue() {
    }

    private void displayFloorPlan() {
    }

    private void displaySpeakers() {
    }

    private void displaySocialEvent() {
    }

    private void displayBug() {
    }

    private void signOut() {
    }

}
