package cz.devconf2017.speaker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.devconf2017.R;
import cz.devconf2017.Speaker;
import cz.devconf2017.SpeakerDetail;

class SpeakerHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @BindView(R.id.speaker_name)
    protected TextView name;

    @BindView(R.id.speaker_avatar)
    protected ImageView avatar;

    public Speaker speaker;

    SpeakerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    @OnClick(R.id.vote_holder_container)
    void onVoteClick(View v) {
        v.getContext().startActivity(new Intent(v.getContext(), SpeakerDetail.class)
                .putExtra("id", speaker.getId())
                .putExtra("name", speaker.getName())
                .putExtra("bio", speaker.getBio())
                .putExtra("avatar", speaker.getEmail())
                .putExtra("country", speaker.getCountry())
                .putExtra("twitter", speaker.getTwitter())
                .putExtra("organization", speaker.getOrganization())
        );
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
        name.setText(speaker.getName());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference sr = storage.getReferenceFromUrl("gs://" + FirebaseApp.getInstance().getOptions().getStorageBucket());
        Glide.with(context)
                // FIXME extract this URL logic, not working, at is transformed to %40
                .load(sr.child("speakers/" + speaker.getEmail().toLowerCase() + ".jpg"))
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.default_avatar))
                .into(avatar);
    }
}
