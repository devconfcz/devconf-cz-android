package cz.devconf2017;

import android.util.Log;

/**
 * Created by jridky on 11.1.17.
 */
public class Voting {

    public int votes;
    public Talk talk;

    public Voting(){

    }

    public Voting(int talk){
        this.talk = MainActivity.TALKS.findTalk(talk);
        this.votes = 0;
    }

    public void add(int count){
        votes = votes + count;
    }

    public String getVotes(){
        return String.valueOf(votes);
    }

    public String getTitle(){
        return talk.getTitle();
    }

    public String getSpeaker(){
        return talk.getSpeakerInfo();
    }
}
