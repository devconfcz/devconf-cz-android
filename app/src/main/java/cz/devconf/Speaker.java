package cz.devconf;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jridky on 9.12.16.
 */

@IgnoreExtraProperties
public class Speaker {
    public String avatar, bio, country, email, name, twitter, organization, id;
    public List<Talk> talks;

    public Speaker(){
        // for firebase purposes
    }

    public Speaker(String avatar, String bio, String country, String email, String name, String twitter, String organization, String id){
        this.avatar = avatar;
        this.bio = bio;
        this.country = country;
        this.email = email;
        this.name = name;
        this.twitter = twitter;
        this.organization = organization;
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getOrganization() { return organization; }

    public List<Talk> getTalks() { return talks; }

    public void addTalk(Talk t){
        if(talks == null){
            talks = new ArrayList<Talk>();
        }
        if(!find(t)){
            talks.add(t);
        }
    }

    private boolean find (Talk talk){
        for(Talk t: talks){
            if(t.getId() == talk.getId()){
                return true;
            }
        }
        return false;
    }
}
