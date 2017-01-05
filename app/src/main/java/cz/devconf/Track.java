package cz.devconf;

/**
 * Created by jridky on 2.1.17.
 */
public class Track {
    public String name;
    public String color;

    public Track(){
        // for firebase purposes
    }

    public Track(String name, String color){
        this.name = name;
        this.color = color.replace("#","#AA");
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
