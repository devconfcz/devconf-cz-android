package cz.devconf;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by jridky on 9.12.16.
 */

@IgnoreExtraProperties
public class Speaker {
    public String avatar, bio, country, email, name, twitter;

    public Speaker(){
        // for firebase purposes
    }

    public Speaker(String avatar, String bio, String country, String email, String name, String twitter){
        this.avatar = avatar;
        this.bio = bio;
        this.country = country;
        this.email = email;
        this.name = name;
        this.twitter = twitter;
    }

    public String getAvatar() {
        return avatar;
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
}
