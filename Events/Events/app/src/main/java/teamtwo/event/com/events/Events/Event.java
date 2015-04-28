package teamtwo.event.com.events.Events;

import java.util.ArrayList;

/**
 * Created by Primož Pesjak on 27.3.2015.
 */
public class Event {
    private String name, picture_url, date, city, id, description;

    public Event() {
    }

    public Event(String name, String picture_url, String date , String city, String id, String description) {
        this.name = name;
        this.picture_url = picture_url;
        this.date = date;
        this.city = city;
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id=id;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
