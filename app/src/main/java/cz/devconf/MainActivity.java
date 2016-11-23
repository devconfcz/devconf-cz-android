package cz.devconf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.menu)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private DrawerHeaderViewHolder drawerHeaderViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        View drawerHeader = navigationView.getHeaderView(0);
        drawerHeaderViewHolder = new DrawerHeaderViewHolder(drawerHeader);
        drawerHeaderViewHolder.mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        setupNavigationMenu();

        displayHome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            showUserInfo(currentUser);
        } else {
            hideUserInfo();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            showUserInfo(currentUser);
        }
    }

    private void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
        );
        startActivityForResult(
                AuthUI
                        .getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.mipmap.ic_launcher)
                        .setProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        hideUserInfo();
                    }
                });
    }

    private void showUserInfo(FirebaseUser currentUser) {
        Glide.with(this).load(currentUser.getPhotoUrl())
                .transform(new GlideCircleTransform(getApplicationContext()))
                .into(drawerHeaderViewHolder.mUserAvatar);
        drawerHeaderViewHolder.mUserName.setText(currentUser.getDisplayName());

        drawerHeaderViewHolder.mUserAvatar.setVisibility(View.VISIBLE);
        drawerHeaderViewHolder.mUserName.setVisibility(View.VISIBLE);

        drawerHeaderViewHolder.mSignInButton.setVisibility(View.GONE);
        navigationView.getMenu().findItem(R.id.signout).setVisible(true);
    }

    private void hideUserInfo() {
        drawerHeaderViewHolder.mUserAvatar.setVisibility(View.GONE);
        drawerHeaderViewHolder.mUserName.setVisibility(View.GONE);

        drawerHeaderViewHolder.mSignInButton.setVisibility(View.VISIBLE);
        navigationView.getMenu().findItem(R.id.signout).setVisible(false);
    }

    // -- Navigation ------------------------------------------------------------------------------

    @OnClick(R.id.drawer)
    void openDrawerMenu() {
        drawerLayout.openDrawer(GravityCompat.START);
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

    private void display(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void displayHome() {
        display(new HomeFragment());
    }

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

    /**
     * Class to represent the Drawer Header
     */

    protected static class DrawerHeaderViewHolder {

        @BindView(R.id.signIn)
        SignInButton mSignInButton;

        @BindView(R.id.user_avatar)
        ImageView mUserAvatar;

        @BindView(R.id.user_name)
        TextView mUserName;

        DrawerHeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
