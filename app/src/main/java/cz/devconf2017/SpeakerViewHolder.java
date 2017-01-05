package cz.devconf2017;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by jridky on 9.12.16.
 */

public class SpeakerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView name;
    public ImageView avatar;
    public Context context;
    public Speaker speaker;

    public void setName() {
        this.name.setText(speaker.getName());
    }

    public void setAvatar() {
        Glide.with(this.context).load(this.speaker.getAvatar())
                .transform(new GlideCircleTransform(this.context)).placeholder(R.drawable.default_avatar)
                .into(this.avatar);
    }

    public void setSpeaker(Speaker s){
        this.speaker = s;
        this.setAvatar();
        this.setName();
    }

    public SpeakerViewHolder(View itemView) {
        super(itemView);

        //implementing onClickListener
        itemView.setOnClickListener(this);
        context = itemView.getContext();
        avatar = (ImageView)itemView.findViewById(R.id.number);
        name = (TextView)itemView.findViewById(R.id.name);

    }

    @Override
    public void onClick(View view) {
        Intent sInfo = new Intent(view.getContext(), SpeakerDetail.class);
        sInfo.putExtra("name", speaker.getName());
        sInfo.putExtra("bio", speaker.getBio());
        sInfo.putExtra("avatar", speaker.getAvatar());
        sInfo.putExtra("country", speaker.getCountry());
        sInfo.putExtra("twitter", speaker.getTwitter());
        sInfo.putExtra("organization", speaker.getOrganization());
        view.getContext().startActivity(sInfo);
    }
}