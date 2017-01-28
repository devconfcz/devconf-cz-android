package cz.devconf2017;

/**
 * Created by jridky on 11.1.17.
 */
public class Voting {

    public int votes, numVotes, score;
    public Talk talk;

    public Voting(){

    }

    public Voting(int talk){
        this.talk = MainActivity.TALKS.findTalk(talk);
        this.votes = 0;
        this.numVotes = 0;
        this.score = 0;
    }

    public void add(int count){
        votes = votes + count;
        numVotes++;
    }

    public void addScore(int s){
        score = score + s;
    }

    public String getScore(){
        return String.valueOf(score);
    }

    public float getAvgVotes(){ return Float.intBitsToFloat(votes)/Float.intBitsToFloat(numVotes); }

    public String getTitle(){
        return talk.getTitle();
    }

    public String getSpeaker(){
        return talk.getSpeakerInfo();
    }

    public String getStatistic(){
        return "Total rating: " + votes + "\nNo. Votes: " + numVotes + ", Avg. Score: " + getAvgVotes();
    }
}
