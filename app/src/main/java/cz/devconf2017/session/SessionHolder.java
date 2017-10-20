package cz.devconf2017.session;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class SessionHolder extends RecyclerView.ViewHolder {

    public SessionHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
