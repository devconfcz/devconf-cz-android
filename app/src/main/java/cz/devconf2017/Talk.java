package cz.devconf2017;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jridky on 29.12.16.
 */

@IgnoreExtraProperties
public class Talk {
    public String description, difficulty, room, title, track, type;
    public String id, day;
    public String duration, start;
    public List<String> speakers;

    public int numId, numDay;
    public Date numDuration;
    public Date numStart;
    public ArrayList<Speaker> speaker;
    public boolean running, last;

    public Talk(){
        // for firebase purposes

    }

    public Talk(String id, String day, String duration, String start, String description, String room, String title, String track, String type, List<String> speakers){
        this.id = id;//Integer.getInteger(id);
        this.day = day;//Integer.getInteger(day);
        this.speakers = speakers;
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
        this.numId = Integer.parseInt(this.id);
        this.numDay = Integer.parseInt(this.day);
        SimpleDateFormat format = new SimpleDateFormat("H:mm:ss");
        try {
            this.numDuration = format.parse(this.duration);
            format = new SimpleDateFormat("HH:mm");
            this.numStart = format.parse(this.start);
        }catch (Exception e){
            Log.d("SDF", "Error: " + e.getMessage());
        }
        running = false;
        last = false;
    }

    public void connectSpeaker(){
        speaker = new ArrayList<Speaker>();

        if(speakers == null){
            return;
        }

        for (String n: speakers) {
            Speaker s = MainActivity.SPEAKERS.findSpeaker(n);
            if(s != null){
                speaker.add(s);
                s.addTalk(this);
            }
        }
    }

    public void setRunning(){
        this.running = true;
    }

    public void unsetRunning(){
        this.running = false;
    }

    public void setLastOfDay(){
        this.last = true;
    }

    public void unsetLastOfDay(){
        this.last = false;
    }

    public boolean getRunning(){
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

    public Speaker getSpeaker(){
        if(this.speaker == null || this.speaker.size() < 1){
            return null;
        }else{
            return this.speaker.get(0);
        }
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " NumId: " + this.numId + " Day: " + getDay() + " NumDay: " ;
    }

    public String getSpeakerInfo() {

        String result = "";

        if(speaker.size() > 0) {
            result = speaker.get(0).getName();

            for (int i = 1; i < speaker.size(); i++) {
                result = result + ", " + speaker.get(i).getName();
            }
        }else{
            result = "We don't know yet";
        }

        return result;
    }

    public String getSpeakerCompleteInfo(){
        String result = "";
        Speaker s;

        if(speaker.size() > 0) {
            s = speaker.get(0);
            result = s.getName() + " (" + s.getCountry() + ", " + s.getOrganization() + ")";

            for (int i = 1; i < speaker.size(); i++) {
                s = speaker.get(i);
                result = result + ", " + s.getName() + " (" + s.getCountry() + ", " + s.getOrganization() + ")";
            }
        }else{
            result = "We don't know yet";
        }

        return result;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Date getStart() { return numStart; }

    public String getRoom() {
        return room;
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

    public int getId() {
        return numId;
    }

    public int getDay() {
        return numDay;
    }
}
