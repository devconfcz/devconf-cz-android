package cz.devconf2017;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@IgnoreExtraProperties
public class Talk {

    private String day;
    private String description;
    private String difficulty;
    private String duration;
    private String id;
    private String room;
    private ArrayList<String> speakers;
    private String startTime;
    private String title;
    private String track;
    private String type;
    private HashMap<String, Feedback> votes;

    private float averageRating;

    public Talk() {
        // For Firebase purposes
    }

    public String getDay() {
        return day;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getDuration() {
        return duration;
    }

    public String getId() {
        return id;
    }

    public String getRoom() {
        return room;
    }

    public ArrayList<String> getSpeakers() {
        return speakers;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }

    public String getTrack() {
        return track;
    }

    public String getType() {
        return type;
    }

    public HashMap<String, Feedback> getVotes() {
        return votes;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    private int numId;
    private int numDay;
    private Date numDuration;
    private Date numStart;
    private boolean running;
    boolean last;

    public Talk(String id, String day, String duration, String start, String description, String room, String title, String track, String type, List<String> speakers) {
        this.id = id;
        this.day = day;
//        this.speakers = speakers;
        this.startTime = start;
        this.duration = duration;
        this.description = description;
        this.room = room;
        this.type = type;
        this.title = title;
        this.track = track;
        setSufficientDataTypes();
        connectSpeaker();
    }

    public void setSufficientDataTypes() {
        this.numId = Integer.parseInt(this.getId());
        this.numDay = Integer.parseInt(this.getDay());
        SimpleDateFormat format = new SimpleDateFormat("H:mm:ss");
        try {
            this.numDuration = format.parse(this.duration);
            format = new SimpleDateFormat("HH:mm");
            this.numStart = format.parse(this.startTime);
        } catch (Exception e) {
            Log.d("SDF", "Error: " + e.getMessage());
        }
        running = false;
        last = false;
    }

    public void connectSpeaker() {
//        speaker = new ArrayList<Speaker>();
//
//        if (speakers == null) {
//            return;
//        }
//
//        for (String n : speakers) {
//            Speaker s = MainActivity.SPEAKERS.findSpeaker(n);
//            if (s != null) {
//                speaker.add(s);
//                s.addTalk(this);
//            }
//        }
    }

    public void setRunning() {
        this.running = true;
    }

    public void unsetRunning() {
        this.running = false;
    }

    public void setLastOfDay() {
        this.last = true;
    }

    public void unsetLastOfDay() {
        this.last = false;
    }

    public boolean getRunning() {
        return this.running;
    }

    public String getFormatedStart() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(this.numStart);
    }

    public String getFormatedDuration() {
        SimpleDateFormat format = new SimpleDateFormat("H:mm");
        return format.format(this.numDuration) + " h";
    }

    public Speaker getSpeaker() {
//        if (this.speakers == null || this.speakers.size() < 1) {
//            return null;
//        } else {
//            return this.speakers.get(0);
//        }
        return null;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " NumId: " + this.numId + " Day: " + getDay() + " NumDay: ";
    }

    public String getSpeakerInfo() {

        String result = "";

        if (speakers != null && speakers.size() > 0) {
            result = speakers.get(0);

            for (int i = 1; i < speakers.size(); i++) {
                result = result + ", " + speakers.get(i);
            }
        } else {
            result = "We don't know yet";
        }

        return result;
    }

    public String getSpeakerCompleteInfo() {
        String result = "";
        Speaker s;

//        if (speakers != null && speakers.size() > 0) {
//            s = speakers.get(0);
//            result = s.getName() + " (" + s.getCountry() + ", " + s.getOrganization() + ")";
//
//            for (int i = 1; i < speakers.size(); i++) {
//                s = speakers.get(i);
//                result = result + ", " + s.getName() + " (" + s.getCountry() + ", " + s.getOrganization() + ")";
//            }
//        } else {
        result = "We don't know yet";
//        }

        return result;
    }

    public boolean isMySpeaker(String s) {
//        for (Speaker sp : speakers) {
//            if (sp.getId().equalsIgnoreCase(s)) {
//                return true;
//            }
//        }
        return false;
    }

    public Date getStart() {
        return numStart;
    }

}
