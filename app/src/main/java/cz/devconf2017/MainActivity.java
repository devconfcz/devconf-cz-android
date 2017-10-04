package cz.devconf2017;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final MainNavigationHelper.Section DEFAULT_HOME_SCREEN_SECTION = MainNavigationHelper.Section.HOME;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.main_nav)
    protected NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    private MainNavigationHelper navigationHelper;

    // Todo refactor props below
    private DrawerHeaderViewHolder drawerHeaderViewHolder;

    static int RC_SIGN_IN = 16;

    static boolean notificationPosted;
    static boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // TODO handle intents from notifications and set initial section
        MainNavigationHelper.Section selectedSection = DEFAULT_HOME_SCREEN_SECTION;

        // todo: refactor ..
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy", Locale.US);
        try {
            TALKS.dayOne = sdf.parse("27/1/2017");
            TALKS.dayTwo = sdf.parse("28/1/2017");
            TALKS.dayThree = sdf.parse("29/1/2017");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // todo: .. until here

        configureDrawer();
        configureNavigation(selectedSection);

        DatabaseReference connectedRef = new FBDB().getDatabase().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d("CONNECTION", "connected");
                    notificationPosted = false;
                } else {
                    Log.d("CONNECTION", "not connected");
                    if (!NetworkUtils.isNetworkAvailable(MainActivity.this) && !notificationPosted) {
                        notificationPosted = true;
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

        String message = getIntent().getStringExtra("message");

        if (message != null && !message.equals("")) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            float dpi = getResources().getDisplayMetrics().density;
            TextView m = new TextView(this);
            m.setText(message);
            m.setPadding((int) (25 * dpi), (int) (19 * dpi), (int) (25 * dpi), (int) (14 * dpi));
            dlgAlert.setView(m);
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getIntent().removeExtra("message");
                }
            });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        }

    }

    private void configureDrawer() {
        // Drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Drawer header
        View drawerHeader = navigationView.getHeaderView(0);
        drawerHeaderViewHolder = new DrawerHeaderViewHolder(drawerHeader);
        drawerHeaderViewHolder.mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        ADMINS.view = navigationView;

        // todo: refactor below
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd ", Locale.US);

        navigationView.getMenu().findItem(R.id.nav_day_1).setTitle(navigationView.getMenu().findItem(R.id.nav_day_1).getTitle() + sdf.format(TALKS.dayOne));
        navigationView.getMenu().findItem(R.id.nav_day_2).setTitle(navigationView.getMenu().findItem(R.id.nav_day_2).getTitle() + sdf.format(TALKS.dayTwo));
        navigationView.getMenu().findItem(R.id.nav_day_3).setTitle(navigationView.getMenu().findItem(R.id.nav_day_3).getTitle() + sdf.format(TALKS.dayThree));
    }

    private void configureNavigation(@NonNull MainNavigationHelper.Section selectedSection) {
        navigationView.setNavigationItemSelectedListener(this);
        navigationHelper = new MainNavigationHelper(getSupportFragmentManager());
        navigationHelper.navigate(selectedSection);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                navigationHelper.navigate(MainNavigationHelper.Section.HOME);
                break;

            case R.id.nav_day_1:
                navigationHelper.navigate(MainNavigationHelper.Section.DAY_1);
                break;

            case R.id.nav_day_2:
                navigationHelper.navigate(MainNavigationHelper.Section.DAY_2);
                break;

            case R.id.nav_day_3:
                navigationHelper.navigate(MainNavigationHelper.Section.DAY_3);
                break;

            case R.id.nav_favorites:
                navigationHelper.navigate(MainNavigationHelper.Section.FAVORITES);
                break;

            case R.id.nav_voting:
                navigationHelper.navigate(MainNavigationHelper.Section.VOTING);
                break;

            case R.id.nav_venue:
                navigationHelper.navigate(MainNavigationHelper.Section.VENUE);
                break;

            case R.id.nav_floor_plan:
                navigationHelper.navigate(MainNavigationHelper.Section.FLOOR_PLAN);
                break;

            case R.id.nav_speakers:
                navigationHelper.navigate(MainNavigationHelper.Section.SPEAKERS);
                break;

            case R.id.nav_social_event:
                navigationHelper.navigate(MainNavigationHelper.Section.SOCIAL_EVENT);
                break;

            case R.id.nav_about:
                navigationHelper.navigate(MainNavigationHelper.Section.ABOUT);
                break;

            case R.id.nav_sign_out:
                signOut();
                return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
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
        Log.d("TOKEN", "Refreshed Token: " + token);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            showUserInfo(currentUser);
            FAVORITES.checkLoad();
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
                        .setLogo(R.drawable.ic_launcher)
                        .setProviders(providers)
                        .build(),
                RC_SIGN_IN);

        // TODO refactor
        // displayLastFragment();
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        hideUserInfo();
                        FAVORITES.favorites.clear();
                    }
                });
    }

    public void showUserInfo(FirebaseUser currentUser) {
        Glide.with(this).load(currentUser.getPhotoUrl())
                .transform(new GlideCircleTransform(getApplicationContext()))
                .into(drawerHeaderViewHolder.mUserAvatar);
        drawerHeaderViewHolder.mUserName.setText(currentUser.getDisplayName());

        drawerHeaderViewHolder.mUserAvatar.setVisibility(View.VISIBLE);
        drawerHeaderViewHolder.mUserName.setVisibility(View.VISIBLE);

        drawerHeaderViewHolder.mSignInButton.setVisibility(View.GONE);
        navigationView.getMenu().findItem(R.id.nav_sign_out).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_favorites).setVisible(true);
        isAdmin(currentUser.getEmail());
    }

    private void hideUserInfo() {
        drawerHeaderViewHolder.mUserAvatar.setVisibility(View.GONE);
        drawerHeaderViewHolder.mUserName.setVisibility(View.GONE);

        drawerHeaderViewHolder.mSignInButton.setVisibility(View.VISIBLE);
        navigationView.getMenu().findItem(R.id.nav_sign_out).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_favorites).setVisible(false);
        // TODO refactor
//        if (lastFragment == LastFragment.VOTING || lastFragment == LastFragment.FAVORITES) {
//            lastFragment = LastFragment.HOME;
//            displayHome();
//        }
        isAdmin("");
    }

    public void isAdmin(String email) {
        if (ADMINS.find(email)) {
            navigationView.getMenu().findItem(R.id.nav_voting).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_voting).setVisible(false);
        }
    }
    // -- Navigation ------------------------------------------------------------------------------

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

        public static FirebaseDatabase getDatabase() {
            if (db == null) {
                db = FirebaseDatabase.getInstance();
                db.setPersistenceEnabled(true);
            }
            return db;
        }
    }

    /**
     * Class to represent Room identification
     */
    public static class ROOMDB {

        private static final String TAG = ROOMDB.class.getName();
        private static List<Room> rooms = new ArrayList<Room>();

        public static void load() {

            rooms.clear();

            FirebaseDatabase database = new FBDB().getDatabase();
            DatabaseReference myRef = database.getReference("rooms");
            // Read from the database

            myRef.orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (rooms.size() > 0) {
                        rooms.clear();
                    }
                    for (DataSnapshot room : dataSnapshot.getChildren()) {
                        Room name = room.getValue(Room.class);
                        rooms.add(name);
                    }
                    ALLLOADED.rooms = true;
                    ALLLOADED.loaded();
                    Log.d("LOADING", "ROOMS are done " + rooms.size());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        public static void checkLoad() {
            if (rooms == null || rooms.size() < 1) {
                load();
            }
        }

        public static List<Room> getRooms() {
            return rooms;
        }

        public static String getRoomName(int id) {
            checkLoad();
            if (id < rooms.size()) {
                return rooms.get(id).getName();
            }
            return null;
        }

        public static boolean isLoad(String room) {
            for (Room r : rooms) {
                if (r.getName().equalsIgnoreCase(room)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Class to represent Admins
     */
    public static class ADMINS {

        private static final String TAG = ADMINS.class.getName();
        private static List<String> admins = new ArrayList<String>();
        public static NavigationView view;

        public static void load() {

            admins.clear();

            FirebaseDatabase database = new FBDB().getDatabase();
            DatabaseReference myRef = database.getReference("admins");
            // Read from the database

            myRef.orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (admins.size() > 0) {
                        admins.clear();
                    }
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        String name = a.getValue(String.class);
                        admins.add(name);
                    }
                    Log.d("LOADING", "ADMINS are done " + admins.size());

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null && find(user.getEmail())) {
                        view.getMenu().findItem(R.id.nav_voting).setVisible(true);
                    } else {
                        view.getMenu().findItem(R.id.nav_voting).setVisible(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        public static void checkLoad() {
            if (admins == null || admins.size() < 1) {
                load();
            }
        }

        public static boolean find(String email) {
            for (String a : admins) {
                if (a.equalsIgnoreCase(email)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Class to represent favorites talks
     */
    public static class FAVORITES {

        private static final String TAG = FAVORITES.class.getName();
        private static List<Talk> favorites = new ArrayList<Talk>();
        public static HomeRecycleViewAdapter adapter;

        public static void checkLoad() {
            if (favorites == null || favorites.size() < 1) {
                load();
            }
        }

        public static void load() {
            favorites.clear();
            FirebaseDatabase database = new FBDB().getDatabase();
            DatabaseReference myRef = database.getReference("favorites");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // Read from the database

            if (user != null) {
                myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if (favorites.size() > 0) {
                            favorites.clear();
                        }

                        for (DataSnapshot f : dataSnapshot.getChildren()) {
                            String id = f.getValue(String.class);
                            Talk t = TALKS.findTalk(Integer.parseInt(id));
                            if (t != null) {
                                favorites.add(t);
                            }
                        }
                        if (adapter != null) {
                            adapter.updateData(getTalks());
                        }
                        Log.d("LOADING", "FAVORITES are done " + favorites.size());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        }

        public static void add(int id) {
            Talk t = TALKS.findTalk(id);
            if (t != null) {
                FirebaseDatabase database = new FBDB().getDatabase();
                DatabaseReference myRef = database.getReference("favorites");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // Read from the database
                if (user != null) {
                    myRef.child(user.getUid()).child(String.valueOf(id)).setValue(String.valueOf(id));
                }
            }
        }

        public static void remove(int id) {
            FirebaseDatabase database = new FBDB().getDatabase();
            DatabaseReference myRef = database.getReference("favorites");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                myRef.child(user.getUid()).child(String.valueOf(id)).removeValue();
            }
        }

        public static List<Talk> getTalks() {
            Collections.sort(favorites, TALKS.orderByStart);
            return favorites;
        }

        public static boolean isFavorite(int id) {
            for (Talk t : favorites) {
                if (t.getId() == id) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Class to represent tracks
     */
    public static class TRACKS {

        private static final String TAG = TRACKS.class.getName();
        private static List<Track> tracks = new ArrayList<Track>();

        public static void checkLoad() {
            if (tracks == null || tracks.size() < 1) {
                load();
            }
        }

        public static void load() {
            tracks.clear();
            FirebaseDatabase database = new FBDB().getDatabase();
            DatabaseReference myRef = database.getReference("tracks");
            // Read from the database

            myRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (tracks.size() > 0) {
                        tracks.clear();
                    }
                    for (DataSnapshot track : dataSnapshot.getChildren()) {
                        Track t = track.getValue(Track.class);
                        tracks.add(t);
                    }
                    ALLLOADED.tracks = true;
                    ALLLOADED.loaded();
                    Log.d("LOADING", "TRACKS are done " + tracks.size());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        public static List<Track> getTracks() {
            return tracks;
        }

        public static String findColor(String trackName) {
            String color = "#888888";

            for (Track t : tracks) {
                if (t.getName().equalsIgnoreCase(trackName)) {
                    return t.getColor();
                }
            }

            return color;
        }
    }

    /**
     * Class to represent Schedule information
     */
    public static class TALKS {

        private static final String TAG = TALKS.class.getName();
        private static List<Talk> talksD1 = new ArrayList<Talk>();
        private static List<Talk> talksD2 = new ArrayList<Talk>();
        private static List<Talk> talksD3 = new ArrayList<Talk>();
        public static DatabaseReference myRef;


        private static Date dayOne;
        private static Date dayTwo;
        private static Date dayThree;

        public static void load() {

            talksD1.clear();
            talksD2.clear();
            talksD3.clear();

            FirebaseDatabase database = new FBDB().getDatabase();
            myRef = database.getReference("sessions");
            // Read from the database

            myRef.orderByChild("day").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (talksD1.size() > 0) {
                        talksD1.clear();
                    }
                    if (talksD2.size() > 0) {
                        talksD2.clear();
                    }
                    if (talksD3.size() > 0) {
                        talksD3.clear();
                    }
                    for (DataSnapshot t : dataSnapshot.getChildren()) {
                        Talk talk = t.getValue(Talk.class);
                        if (talk.id == null || talk.day.equalsIgnoreCase("")) {
                            Log.d("SKIPPED", talk.getTitle());
                            continue;
                        } else {
                            talk.setSufficientDataTypes();
                        }

                        switch (talk.getDay()) {
                            case 1:
                                talksD1.add(talk);
                                break;
                            case 2:
                                talksD2.add(talk);
                                break;
                            default:
                                talksD3.add(talk);
                                break;
                        }

                    }
                    ALLLOADED.talks = true;
                    ALLLOADED.loaded();
                    Log.d("LOADING", "TALKS are done " + talksD1.size() + " " + talksD2.size() + " " + talksD3.size());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        public static void checkLoad() {
            if (talksD1 == null || talksD2 == null || talksD3 == null || talksD1.size() < 1 || talksD2.size() < 1 || talksD3.size() < 1) {
                load();
            }
        }

        public static Date getDay(int day) {
            switch (day) {
                case 1:
                    return dayOne;
                case 2:
                    return dayTwo;
                default:
                    return dayThree;
            }
        }

        public static List<Talk> getActualTalks() {
            List<Talk> result = new ArrayList<Talk>();
            Date now = new Date();

            if (now.compareTo(dayOne) <= 0) {
                Collections.sort(talksD1, TALKS.orderByRoom);
                String room = "";
                for (Talk t : talksD1) {
                    t.unsetRunning();
                    t.unsetLastOfDay();
                    if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                        result.add(t);
                    }

                    room = t.getRoom();
                }

            }
            if (now.compareTo(dayOne) > 0 && now.compareTo(dayTwo) <= 0) {
                String time = String.format("%02d:%02d", now.getHours(), now.getMinutes());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                Date numNow = now;

                try {
                    numNow = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Collections.sort(talksD1, TALKS.orderByRoom);
                Talk last = null;
                String room = null;
                for (Talk t : talksD1) {
                    t.unsetRunning();
                    t.unsetLastOfDay();
                    if (t.getStart().compareTo(numNow) <= 0) {
                        last = t;
                    } else {
                        if (last == null) {
                            if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                                result.add(t);
                                room = t.getRoom();
                            }
                        } else {
                            if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                                last.setRunning();
                                result.add(last);
                                result.add(t);
                                room = t.getRoom();
                                last = null;
                            }
                        }
                    }
                }

                if (result.size() < 1) {

                    last = null;
                    for (Talk t : talksD1) {
                        if (last != null) {
                            if (last.getStart().compareTo(t.getStart()) <= 0) {
                                last = t;
                            }
                        } else {
                            last = t;
                        }
                    }

                    if (last != null) {
                        last.setLastOfDay();
                        result.add(last);
                    }

                    room = "";
                    Collections.sort(talksD2, TALKS.orderByRoom);
                    for (Talk t : talksD2) {
                        if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                            result.add(t);
                        }

                        room = t.getRoom();
                    }

                }


            }

            if (now.compareTo(dayTwo) > 0 && now.compareTo(dayThree) <= 0) {
                String time = String.format("%02d:%02d", now.getHours(), now.getMinutes());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                Date numNow = now;

                try {
                    numNow = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Collections.sort(talksD2, TALKS.orderByRoom);
                Talk last = null;
                String room = null;
                for (Talk t : talksD2) {
                    t.unsetRunning();
                    t.unsetLastOfDay();
                    if (t.getStart().compareTo(numNow) <= 0) {
                        last = t;
                    } else {
                        if (last == null) {
                            if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                                result.add(t);
                                room = t.getRoom();
                            }
                        } else {
                            if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                                last.setRunning();
                                result.add(last);
                                result.add(t);
                                room = t.getRoom();
                                last = null;
                            }
                        }
                    }
                }

                if (result.size() < 1) {

                    last = null;
                    for (Talk t : talksD2) {
                        if (last != null) {
                            if (last.getStart().compareTo(t.getStart()) <= 0) {
                                last = t;
                            }
                        } else {
                            last = t;
                        }
                    }
                    if (last != null) {
                        last.setLastOfDay();
                        result.add(last);
                    }

                    room = "";
                    Collections.sort(talksD2, TALKS.orderByRoom);
                    for (Talk t : talksD3) {
                        if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                            result.add(t);
                        }

                        room = t.getRoom();
                    }
                }

            }

            if (now.compareTo(dayThree) > 0) {
                String time = String.format("%02d:%02d", now.getHours(), now.getMinutes());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                Date numNow = now;

                try {
                    numNow = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Collections.sort(talksD3, TALKS.orderByRoom);
                Talk last = null;
                String room = null;
                for (Talk t : talksD3) {
                    t.unsetRunning();
                    t.unsetLastOfDay();
                    if (t.getStart().compareTo(numNow) <= 0) {
                        last = t;
                    } else {
                        if (last == null) {
                            if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                                result.add(t);
                                room = t.getRoom();
                            }
                        } else {
                            if (!t.getRoom().equalsIgnoreCase(room) && ROOMDB.isLoad(t.getRoom())) {
                                last.setRunning();
                                result.add(last);
                                result.add(t);
                                room = t.getRoom();
                                last = null;
                            }
                        }
                    }
                }

                if (result.size() < 1) {

                    last = null;
                    for (Talk t : talksD3) {
                        if (last != null) {
                            if (last.getStart().compareTo(t.getStart()) <= 0) {
                                last = t;
                            }
                        } else {
                            last = t;
                        }
                    }
                    if (last != null) {
                        last.setLastOfDay();
                        result.add(last);
                    }
                }
            }

            Collections.sort(result, TALKS.orderByStart);
            return result;
        }

        public static List<Talk> getTalks(int day, int roomId) {

            String room = ROOMDB.getRoomName(roomId);
            List<Talk> result = new ArrayList<Talk>();

            switch (day) {
                case 1:
                    Collections.sort(talksD1, TALKS.orderByRoom);
                    for (Talk t : talksD1) {
                        if (t.getRoom().equalsIgnoreCase(room)) {
                            result.add(t);
                        }
                    }
                    break;
                case 2:
                    Collections.sort(talksD2, TALKS.orderByRoom);
                    for (Talk t : talksD2) {
                        if (t.getRoom().equalsIgnoreCase(room)) {
                            result.add(t);
                        }
                    }
                    break;
                default:
                    Collections.sort(talksD3, TALKS.orderByRoom);
                    for (Talk t : talksD3) {
                        if (t.getRoom().equalsIgnoreCase(room)) {
                            result.add(t);
                        }
                    }
                    break;
            }
            return result;
        }

        public static Talk findTalk(int day, int id) {
            switch (day) {
                case 1:
                    for (Talk t : talksD1) {
                        if (t.getId() == id) {
                            return t;
                        }
                    }
                    break;
                case 2:
                    for (Talk t : talksD2) {
                        if (t.getId() == id) {
                            return t;
                        }
                    }
                    break;
                default:
                    for (Talk t : talksD3) {
                        if (t.getId() == id) {
                            return t;
                        }
                    }
                    break;
            }

            return null;

        }

        public static Talk findTalk(int id) {

            for (Talk t : talksD1) {
                if (t.getId() == id) {
                    return t;
                }
            }

            for (Talk t : talksD2) {
                if (t.getId() == id) {
                    return t;
                }
            }

            for (Talk t : talksD3) {
                if (t.getId() == id) {
                    return t;
                }
            }

            return null;

        }

        public static void add(Talk t, int day) {
            switch (day) {
                case 1:
                    talksD1.add(t);
                    break;
                case 2:
                    talksD2.add(t);
                    break;
                default:
                    talksD3.add(t);
                    break;
            }
        }

        public static String getSpeakerTalks(String speaker) {

            List<Talk> result = new ArrayList<Talk>();

            for (Talk t : talksD1) {
                if (t.isMySpeaker(speaker)) {
                    result.add(t);
                }
            }

            for (Talk t : talksD2) {
                if (t.isMySpeaker(speaker)) {
                    result.add(t);
                }
            }

            for (Talk t : talksD3) {
                if (t.isMySpeaker(speaker)) {
                    result.add(t);
                }
            }

            Collections.sort(result, TALKS.orderByStart);

            String ret = "";
            for (Talk t : result) {
                if (ret.equals("")) {
                    ret = ret.concat("Day " + t.getDay() + " at " + t.getFormatedStart() + " GMT +01:00 in " + t.getRoom().toUpperCase());
                } else {
                    ret = ret.concat(", Day " + t.getDay() + " at " + t.getFormatedStart() + " GMT +01:00 in " + t.getRoom().toUpperCase());
                }
            }

            return ret;
        }

        public static Comparator<Talk> orderByRoom = new Comparator<Talk>() {
            @Override
            public int compare(Talk talk, Talk t1) {
                String room1 = talk.getRoom();
                String room2 = t1.getRoom();
                int dateComparision = room1.compareTo(room2);
                return dateComparision == 0 ? talk.getStart().compareTo(t1.getStart()) : dateComparision;
            }
        };

        public static Comparator<Talk> orderByStart = new Comparator<Talk>() {
            @Override
            public int compare(Talk talk, Talk t1) {
                Date start1 = talk.getStart();
                Date start2 = t1.getStart();
                int dateComparision = start1.compareTo(start2);

                if (talk.getDay() == t1.getDay()) {
                    if (dateComparision == 0) {
                        return talk.getRoom().compareTo(t1.getRoom());
                    } else {
                        return dateComparision;
                    }
                } else {
                    return (talk.getDay() - t1.getDay());
                }


            }
        };
    }

    /**
     * Class to represent Speakers database
     */
    public static class SPEAKERS {

        private static final String TAG = SPEAKERS.class.getName();
        private static List<Speaker> speakers = new ArrayList<Speaker>();
        public static DatabaseReference myRef;

        public static void checkLoad() {
            if (speakers == null || speakers.size() < 1) {
                load();
            }
        }

        public static void load() {
            speakers.clear();
            FirebaseDatabase database = new FBDB().getDatabase();
            myRef = database.getReference("speakers");
            // Read from the database

            myRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (speakers.size() > 0) {
                        speakers.clear();
                    }
                    for (DataSnapshot speaker : dataSnapshot.getChildren()) {
                        Speaker s = speaker.getValue(Speaker.class);
                        speakers.add(s);
                    }

                    ALLLOADED.speakers = true;
                    ALLLOADED.loaded();

                    Log.d("LOADING", "SPEAKERS are done " + speakers.size());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        public static List<Speaker> getSpeakers() {
            return speakers;
        }

        public static Speaker findSpeaker(String id) {
            if (speakers == null) {
                return null;
            }
            for (Speaker s : speakers) {
                if (id.equalsIgnoreCase(s.getId())) {
                    return s;
                }
            }

            return null;
        }
    }

    /**
     * Class to represent Speakers database
     */
    public static class ALLLOADED {

        public static HomeFragment fragment;
        public static boolean speakers = false, tracks = false, talks = false, rooms = false;

        public static void loaded() {
            if (speakers && tracks && talks && rooms) {
                for (Talk t : TALKS.talksD1) {
                    t.connectSpeaker();
                }
                for (Talk t : TALKS.talksD2) {
                    t.connectSpeaker();
                }
                for (Talk t : TALKS.talksD3) {
                    t.connectSpeaker();
                }
                fragment.mAdapter.updateData(TALKS.getActualTalks());
                fragment.setLoadingBox();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_press), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
