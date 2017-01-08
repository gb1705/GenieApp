package com.neuron.genieapp.DataModels;

/**
 * Created by Karan on 17/5/16.
 */
public class Question {

    int _id;
    String _name;
    String _question;
    String _reply;
    String _phone;
    String _location;

    // Empty Constructor
    public Question(){ }

    // Contructor
    public Question(int id, String name, String question,
                    String reply, String phone_no, String location){
        this._id = id;
        this._name = name;
        this._question = question;
        this._reply = reply;
        this._phone = phone_no;
        this._location = location;
    }

    // Constructor
    public Question(String name, String question, String reply,
                    String phone_no, String location){
        this._name = name;
        this._question = question;
        this._reply = reply;
        this._phone = phone_no;
        this._location = location;
    }

    // Getters and Setters
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_question() {
        return _question;
    }

    public void set_question(String _question) {
        this._question = _question;
    }

    public String get_reply() {
        return _reply;
    }

    public void set_reply(String _reply) {
        this._reply = _reply;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }
}
