package cz.devconf2017.session;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import cz.devconf2017.R;
import cz.devconf2017.Talk;
import cz.devconf2017.base.BaseFragment;
import cz.devconf2017.util.view.EmptyRecyclerView;

public class RoomSessionsFragment extends BaseFragment {

    public static final String ARG_ROOM_NAME = "ARG_ROOM_NAME";
    public static final String ARG_DAY = "ARG_DAY";

    @BindView(R.id.room_sessions_recycler_view)
    protected EmptyRecyclerView recycler;

    @BindView(R.id.empty_view)
    protected View emptyView;

    private String roomName;
    private int day;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room_sessions;
    }

    public static RoomSessionsFragment newInstance(String roomName, int day) {
        RoomSessionsFragment fragment = new RoomSessionsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_ROOM_NAME, roomName);
        args.putInt(ARG_DAY, day);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomName = getArguments().getString(ARG_ROOM_NAME, "");
        day = getArguments().getInt(ARG_DAY, 1);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = FirebaseDatabase.getInstance()
                .getReference("sessions")
                .orderByChild("day_room")
                .equalTo(day + "_" + roomName);

        FirebaseRecyclerOptions<Talk> options = new FirebaseRecyclerOptions.Builder<Talk>()
                .setQuery(query, Talk.class)
                .setLifecycleOwner(this)
                .build();

        RoomSessionsRecyclerViewAdapter adapter = new RoomSessionsRecyclerViewAdapter(options) {
            @Override
            public void onError(DatabaseError error) {
                super.onError(error);
                showToast(R.string.fui_general_error);
            }
        };

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setEmptyView(emptyView);
    }
}
