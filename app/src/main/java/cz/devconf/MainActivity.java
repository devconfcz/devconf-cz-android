package cz.devconf;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static io.fabric.sdk.android.Fabric.TAG;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.menu)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private DrawerHeaderViewHolder drawerHeaderViewHolder;

    enum LastFragment {HOME,PRESENTATION,VENUE,FLOORPLAN,SPEAKERS,SOCIALEVENT,BUG};
    static LastFragment lastFragment = LastFragment.HOME;

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

        displayLastFragment();

        DatabaseReference connectedRef = new FBDB().getDatabase().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d(TAG,"connected");
                } else {
                    Log.d(TAG,"not connected");
                    if(!isNetworkAvailable()) {
                        PendingIntent notifIntent = PendingIntent.getActivity(getBaseContext(), 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(getBaseContext())
                                .setSmallIcon(R.drawable.logonotify)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                                .setColor(getResources().getColor(R.color.primary))
                                .setContentIntent(notifIntent)
                                .setContentTitle(getResources().getString(R.string.syncTitle))
                                .setContentText(getResources().getString(R.string.syncContext))
                                .setSubText(getResources().getString(R.string.syncSubText))
                                .setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                                .setVibrate(new long[]{200, 100, 100, 100})
                                .setPriority(Notification.PRIORITY_MAX);
                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(0, notification.build());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info != null && info.isConnected()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

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
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token: " + token);

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
                    case R.id.home:
                        lastFragment = LastFragment.HOME;
                        displayHome();
                        break;
                    case R.id.presentation:
                        lastFragment = LastFragment.PRESENTATION;
                        displayPresentation();
                        break;
                    case R.id.venue:
                        lastFragment = LastFragment.VENUE;
                        displayVenue();
                        break;
                    case R.id.floor_plan:
                        lastFragment = LastFragment.FLOORPLAN;
                        displayFloorPlan();
                        break;
                    case R.id.speakers:
                        lastFragment = LastFragment.SPEAKERS;
                        displaySpeakers();
                        break;
                    case R.id.social_event:
                        lastFragment = LastFragment.SOCIALEVENT;
                        displaySocialEvent();
                        break;
                    case R.id.bug:
                        lastFragment = LastFragment.BUG;
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
        Toast.makeText(this, "Presentations are under construction.", Toast.LENGTH_SHORT).show();
    }

    private void displayVenue() { display(new VenueFragment()); }

    private void displayFloorPlan() {
        Toast.makeText(this, "Floor plan is under construction.", Toast.LENGTH_SHORT).show();
    }

    private void displaySpeakers() {
        display(new SpeakersFragment());
    }

    private void displaySocialEvent() {
        Toast.makeText(this, "Social events are under construction.", Toast.LENGTH_SHORT).show();
    }

    private void displayBug() { display(new BugFragment()); }

    private void displayLastFragment(){
        switch(lastFragment){
            case PRESENTATION:
                displayPresentation();
                break;
            case VENUE:
                displayVenue();
                break;
            case BUG:
                displayBug();
                break;
            case FLOORPLAN:
                displayFloorPlan();
                break;
            case SPEAKERS:
                displaySpeakers();
                break;
            case SOCIALEVENT:
                displaySocialEvent();
                break;
            default:
                displayHome();
                break;
        }
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

    /**
     * Class to represent Firebase Database
     */

    public static class FBDB {
        private static FirebaseDatabase db;

        public static FirebaseDatabase getDatabase(){
            if(db == null){
               db = FirebaseDatabase.getInstance();
               db.setPersistenceEnabled(true);
            }

            return db;
        }
    }

}
