package cz.devconf2017;

import android.content.Context;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import cz.devconf2017.util.DateFormatUtils;

public class TalkBusiness {

    private final Talk talk;

    public TalkBusiness(Talk talk) {
        this.talk = talk;
    }

    public CharSequence printScore() {
        return String.valueOf(talk.getAverageRating());
    }

    public CharSequence printTitle() {
        return talk.getTitle();
    }

    public CharSequence printSpeakers(@Nullable Collection<Speaker> speakers) {
        if (speakers == null || speakers.size() == 0) {
            return "No speaker";
        }

        final StringBuilder sb = new StringBuilder();

        for (Speaker speaker : speakers) {
            sb.append(speaker.getName())
                    .append(", ");
        }
        return sb.delete(sb.length() - 2, sb.length()).toString();
    }

    public CharSequence printStatistics() {
        return "todo";
    }

    public float calculateAverageRatingOfSession() {
        HashMap<String, Feedback> votes = talk.getVotes();

        if (votes == null) {
            return 0;
        }

        float ratingSum = 0;
        for (Feedback feedback : votes.values()) {
            ratingSum += feedback.getRating();
        }

        return ratingSum / votes.size();
    }

    @Nullable
    public Collection<String> getSpeakerIds() {
        return talk.getSpeakers();
    }

    public String printStart() {
        try {
            Date dateStartTime = DateFormatUtils.DATE_FORMAT_TIME_INPUT.parse(talk.getStart());
            return DateFormatUtils.DATE_FORMAT_TIME.format(dateStartTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return talk.getStart();
        }
    }

    public String printDuration() {
        try {
            Date dateDuration = DateFormatUtils.DATE_FORMAT_DURATION_INPUT.parse(talk.getDuration());
            return DateFormatUtils.DATE_FORMAT_DURATION.format(dateDuration) + " h";
        } catch (ParseException e) {
            e.printStackTrace();
            return talk.getDuration();
        }
    }

    public CharSequence printDay(Context context) {
        return context.getString(R.string.day_format, talk.getDay());
    }

    public CharSequence printRoom() {
        return talk.getRoom().toUpperCase();
    }
}
