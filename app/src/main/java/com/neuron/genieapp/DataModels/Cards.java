package com.neuron.genieapp.DataModels;

/**
 * Created by Karan on 10/11/16.
 */

public class Cards {

    private int thumbnail;

    public Cards() {
    }

    public Cards(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}