package com.neuron.genieapp;

import android.net.Uri;

/**
 * Created by Karan on 3/1/17.
 */
public class ViewTools {
    public boolean isEditTextFieldIsFill(String currentURLFromEditTextToString) {
        return !currentURLFromEditTextToString.equals("");
    }

    public boolean isUriNotNull(Uri uri) {
        return (uri != null);
    }

    public String deleteSaveHereFromDirectoryPath(String directoryPath) {
        return directoryPath.substring(0, directoryPath.length() - 10);
    }
}

