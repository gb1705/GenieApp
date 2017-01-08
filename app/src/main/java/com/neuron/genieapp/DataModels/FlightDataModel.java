package com.neuron.genieapp.DataModels;

/**
 * Created by Karan on 27/12/16.
 */
public class FlightDataModel {

    int flightImage;
    String flightTime;
    String flightDuration;
    String flightPrice;
    String flightSeatsLeft;

    public FlightDataModel(int _flightImage, String _flightTime,
                           String _flightDuration, String _flightPrice, String _flightSeatsLeft ) {
        this.flightImage=_flightImage;
        this.flightTime=_flightTime;
        this.flightDuration=_flightDuration;
        this.flightPrice=_flightPrice;
        this.flightSeatsLeft=_flightSeatsLeft;

    }

    public int getFlightImage(){
        return flightImage;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public String getFlightPrice() {
        return flightPrice;
    }

    public String getFlightSeatsLeft() {
        return flightSeatsLeft;
    }

}