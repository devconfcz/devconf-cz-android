package cz.devconf2017;

import java.util.HashMap;

import cz.devconf2017.MainActivity;
import cz.devconf2017.Talk;
import cz.devconf2017.Vote;

/**
 * Votes corresponding to 1 user.
 */
public class Votes {

    private int votes;
    private int numVotes;
    private int score;
    private Talk talk;

    // Integer is session Id.
    private HashMap<Integer, Vote> voteCollection;

    public Votes() {
        // For Firebase purpose
    }

    public Votes(int sessionId) {
        this.talk = MainActivity.TALKS.findTalk(sessionId);
    }

    public void add(int count) {
        votes = votes + count;
        numVotes++;
    }

    public void addScore(int s) {
        score = score + s;
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public float getAvgVotes() {
        return Float.intBitsToFloat(votes) / Float.intBitsToFloat(numVotes);
    }

    public String getTitle() {
        if (talk == null) {
            return "null";
        }
        return talk.getTitle();
    }

    public String getSpeaker() {
        if (talk == null) {
            return "null";
        }
        return talk.getSpeakerCompleteInfo();
    }

    public String getStatistic() {
        return "Total rating: " + votes + "\nNo. Votes: " + numVotes + ", Avg. Score: " + getAvgVotes();
    }

    public int getVotes() {
        return votes;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public Talk getTalk() {
        return talk;
    }
}
