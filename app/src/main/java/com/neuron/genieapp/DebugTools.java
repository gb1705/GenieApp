package com.neuron.genieapp;

import android.util.Log;

import java.net.URL;

import static com.neuron.genieapp.Constants.DOWNLOAD_MANAGER;

/**
 * Created by Karan on 4/1/17.
 */
public class DebugTools {

    public long writeStartDebugInformation(URL url, String fileName) {
        long startTime = System.currentTimeMillis();

        Log.d(DOWNLOAD_MANAGER, " download begining");
        Log.d(DOWNLOAD_MANAGER, " download url: " + url);
        Log.d(DOWNLOAD_MANAGER, " downloaded file name: " + fileName);
        return startTime;
    }

    public void writeEndDebugInformation(long startTime) {
        Log.d(DOWNLOAD_MANAGER, " download ready in " + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
    }
}

