package com.neuron.genieapp.DataModels;

/**
 * Created by Karan on 17/12/16.
 */

public class Chats {

    //private variables
    int _id;
    String _message, _time;
    boolean _boolean;

    // Empty constructor
    public Chats(){}

    // constructor
    public Chats(int id, String message, boolean mboolean, String time){
        this._id = id;
        this._message = message;
        this._boolean = mboolean;
        this._time = time;
    }

    // constructor
    public Chats(String message, boolean mboolean, String time){
        this._message = message;
        this._boolean = mboolean;
        this._time = time;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public boolean is_boolean() {
        return _boolean;
    }

    public void set_boolean(boolean _boolean) {
        this._boolean = _boolean;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }
}
