package cz.devconf2017;

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
    private String start;
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

    public String getStart() {
        return start;
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

    public Talk(String id) {
        this.id = id;
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
        this.start = start;
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
            this.numStart = format.parse(this.start);
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

    @Override
    public String toString() {
        return "Id: " + getId() + " NumId: " + this.numId + " Day: " + getDay() + " NumDay: ";
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

    public Date getNumStart() {
        return numStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Talk talk = (Talk) o;

        return id != null ? id.equals(talk.id) : talk.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
