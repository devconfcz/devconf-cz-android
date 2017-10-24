package cz.devconf2017.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.devconf2017.R;
import cz.devconf2017.Talk;
import cz.devconf2017.base.BaseFragment;
import cz.devconf2017.util.view.EmptyRecyclerView;

public class FavoritesFragment extends BaseFragment {

    private static final String TAG = "FavouritesFragment";

    @BindView(R.id.favorites_title)
    protected TextView title;

    @BindView(R.id.favorites_recycler_view)
    protected EmptyRecyclerView recycler;

    @BindView(R.id.favorites_empty_view)
    protected TextView emptyView;

    private final List<Talk> favoriteSessions = new ArrayList<>();
    private FavoritesRecyclerViewAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorites;
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoading();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        @SuppressWarnings("ConstantConditions")
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference favoritesRef = FirebaseDatabase.getInstance()
                .getReference("favorites")
                .child(userId);

        favoritesRef.addListenerForSingleValueEvent(userFavoritesInitialLoadListener);
        favoritesRef.addChildEventListener(userFavoritesValueListener);

        adapter = new FavoritesRecyclerViewAdapter(favoriteSessions);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setEmptyView(emptyView);
    }

    private final ValueEventListener userFavoritesInitialLoadListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            hideLoading();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            showToast(R.string.fui_general_error);
        }
    };

    private final ChildEventListener userFavoritesValueListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            final String favoriteSessionId = dataSnapshot.getValue(String.class);

            FirebaseDatabase.getInstance()
                    .getReference("sessions")
                    .child(favoriteSessionId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            favoriteSessions.add(dataSnapshot.getValue(Talk.class));
                            adapter.notifyItemInserted(favoriteSessions.size() - 1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, databaseError.getMessage());
                        }
                    });
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String removedSessionId = dataSnapshot.getValue(String.class);
            Talk talk = new Talk(removedSessionId);
            int position = favoriteSessions.indexOf(talk);
            favoriteSessions.remove(position);
            adapter.notifyItemRemoved(position);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
}