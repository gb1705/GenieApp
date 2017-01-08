package com.neuron.genieapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static com.neuron.genieapp.Constants.CONTENT_LENGTH_GLOBAL;
import static com.neuron.genieapp.Constants.DIALOG_DOWNLOAD;
import static com.neuron.genieapp.Constants.DOWNLOAD_MANAGER;
import static com.neuron.genieapp.Constants.FILE_PREFIX;
import static com.neuron.genieapp.Constants.FILE_URL;
import static com.neuron.genieapp.Constants.REQUEST_LOAD_WEB_PAGE;
import static com.neuron.genieapp.Constants.REQUEST_SAVE_FILE;
import static com.neuron.genieapp.Constants.REQUEST_SAVE_WEB_PAGE;

public class BrowserActivityViewPager extends Activity implements SimpleGestureFilter.SimpleGestureListener {

    private static FilesTools filesTools = new FilesTools();
    private static ViewTools viewTools = new ViewTools();

    private EditText currentURLFromEditText;
    private WebView browser;
    private Uri uri;
    private String directory;
    String news_url, url_from_start_page;
    private SimpleGestureFilter detector;
    RelativeLayout layout;

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            currentURLFromEditText.setText(url);

            Log.d(DOWNLOAD_MANAGER, " you are just clicked on " + url);
            return true;
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            //Users will be notified in case there's an error (i.e. no internet connection)
            Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
        }

        public void onPageFinished(WebView view, String url) {
            CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_view_pager);

        Toast.makeText(this,"Hi", Toast.LENGTH_SHORT).show();
        detector = new SimpleGestureFilter(this,this);
//        detector = new SimpleGestureFilter(BrowserActivity_ViewPager.this, BrowserActivity_ViewPager.this);
//        Intent intent = getIntent();
//        news_url = intent.getStringExtra("URL");

        Intent url_intent = getIntent();
        url_from_start_page = url_intent.getStringExtra("URL");
        initViews();
    }

    private void initViews() {
        browser = (WebView) findViewById(R.id.WebEngine);
        layout = (RelativeLayout) findViewById(R.id.llayout);
        layout.setOnTouchListener(detector);

        currentURLFromEditText = (EditText) findViewById(R.id.editText);
        setWindows1251Encoding();

        //replace to GOOGLE_START_PAGE_URL
//        if (news_url != ""){
//            currentURLFromEditText.setText(news_url);
//            goToNewsURL(news_url);
//        }
//       if (url_from_start_page != ""){
//           currentURLFromEditText.setText(url_from_start_page);
//           goToURLFromStartPage(url_from_start_page);
//       }

//        browser.loadUrl(news_url);

//        //Handle Go-button
//        goButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (viewTools.isEditTextFieldIsFill(currentURLFromEditText.getText().toString())) {
//                    goToURL();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please, type some URL.", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        //Handle Enter-button
        currentURLFromEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    goToURL();
                    return true;
                }
                return false;
            }
        });


        //Handle WebView touch
        browser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboardAndRequestFocus();
                return false;
            }
        });

        //Handle Download
        browser.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                uri = Uri.parse(url);
                CONTENT_LENGTH_GLOBAL = contentLength;
//                saveFileInitiateActivity();
            }
        });
    }

    /*Menu creator*/
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        try {
            this.detector.onTouchEvent(me);
        }catch (NullPointerException e){
            Toast.makeText(this, "Select a Learner First to move Forward..", Toast.LENGTH_SHORT).show();
        }

        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                if (browser.canGoBack()) {
                    browser.goBack();
                    currentURLFromEditText.setText(browser.getUrl());
                }
                break;
            case SimpleGestureFilter.SWIPE_LEFT : str = "Swipe Left";
                if (browser.canGoForward()) {
                    browser.goForward();
                    currentURLFromEditText.setText(browser.getUrl());
                }
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
                break;

        }
        Toast.makeText(this, "" + str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {

    }

    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.exit:
//                browser.clearCache(true);
//                this.finish();
//                return true;
//            case R.id.save:
//                saveWebPageInitiateActivity();
//                return true;
//            case R.id.load:
//                loadWebPageFromStorageInitiateActivity();
//                return true;
//            case R.id.player:
//                startPlayerActivity();
//                return true;
//            case R.id.file_manager:
//                startFileManager();
//                return true;
////            case R.id.google_map:
////                startMap();
////                return true;
//        }
//        return false;
//    }

//    private void startMap() {
//        Intent intent = new Intent(this, BrowserMapActivity.class);
//        startActivity(intent);
//
//        Log.d(DOWNLOAD_MANAGER, " map start : " + BrowserMapActivity.class);
//    }

//    private void startFileManager() {
//        Intent intent = new Intent(this, FileExplorerActivity.class);
//        intent.putExtra(START_PATH, ROOT_DIRECTORY);
//        startActivity(intent);
//
//        Log.d(DOWNLOAD_MANAGER, " activity was send to : " + FileExplorerActivity.class);
//    }
    /*end menu creator*/

//    /*Activities start*/
//    private void startPlayerActivity() {
//        Intent intent = new Intent(this, PlayerActivity.class);
//        startActivity(intent);
//
//        Log.d(DOWNLOAD_MANAGER, " player start : " + AlertExplorerActivity.class);
//    }

//    private void saveWebPageInitiateActivity() {
//        Intent intent = new Intent(this, AlertExplorerActivity.class);
//        intent.putExtra(ACTION_NAME, "save");
//        startActivityForResult(intent, REQUEST_SAVE_WEB_PAGE);
//
//        Log.d(DOWNLOAD_MANAGER, " activity was send to : " + AlertExplorerActivity.class);
//    }

    /* e.g: file:///data/data/com.example.issoft.Browser/files/bash.im .html */
//    private void loadWebPageFromStorageInitiateActivity() {
//        Intent intent = new Intent(this, AlertExplorerActivity.class);
//        intent.putExtra(ACTION_NAME, "load");
//        startActivityForResult(intent, REQUEST_LOAD_WEB_PAGE);
//
//        Log.d(DOWNLOAD_MANAGER, " activity was send to : " + AlertExplorerActivity.class);
//    }

//    private void saveFileInitiateActivity() {
//        Intent intent = new Intent(this, AlertExplorerActivity.class);
//        intent.putExtra(ACTION_NAME, "save");
//        startActivityForResult(intent, REQUEST_SAVE_FILE);
//
//        Log.d(DOWNLOAD_MANAGER, " activity was send to : " + AlertExplorerActivity.class);
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        directory = data.getStringExtra(FILE_URL);

        if (requestCode == REQUEST_LOAD_WEB_PAGE) {
            if (resultCode == RESULT_OK) {
                String currentFileURL = data.getStringExtra(FILE_URL);
                browser.loadUrl(FILE_PREFIX + currentFileURL);
                currentURLFromEditText.setText(FILE_PREFIX + currentFileURL);
                hideKeyboardAndRequestFocus();

                Toast.makeText(getApplicationContext(), "Loading complete.", Toast.LENGTH_LONG).show();
                Log.d(DOWNLOAD_MANAGER, " page loading complete: " + currentFileURL);
            }
        } else if (requestCode == REQUEST_SAVE_WEB_PAGE) {
            if (resultCode == RESULT_OK) {

                filesTools.saveWebPageFromURL(
                        viewTools.deleteSaveHereFromDirectoryPath(directory),
                        currentURLFromEditText.getText().toString());

                Toast.makeText(getApplicationContext(), "Page successfully saved.", Toast.LENGTH_LONG).show();
                Log.d(DOWNLOAD_MANAGER, " page successfully stored in this directory: " + directory);
            }
        } else if (requestCode == REQUEST_SAVE_FILE) {
            if (resultCode == RESULT_OK) {
                showDialog(DIALOG_DOWNLOAD);

                Log.d(DOWNLOAD_MANAGER, " page successfully stored in this directory: " + directory);
            }
        }
    }

    private void goToURL() {
        String currentURL = currentURLFromEditText.getText().toString();
////        String currentURL = filesTools.addHTTPPrefixIfNotExist(currentURLFromEditText.getText().toString());
//        if (!currentURL.contains("http://www.")){
//            currentURL = filesTools.addHTTPPrefixIfNotExist(currentURLFromEditText.getText().toString());
//        }
        if (currentURL.isEmpty() || !Patterns.WEB_URL.matcher(currentURL).matches()){
            currentURLFromEditText.setError("Enter a valid URL");
        }else {
            Toast.makeText(this,"Loading....", Toast.LENGTH_SHORT).show();
            browser.setWebViewClient(new MyWebViewClient());
            browser.loadUrl(currentURL);
        }
        hideKeyboardAndRequestFocus();

        playAudio(currentURL);

        Log.d(DOWNLOAD_MANAGER, " go to " + currentURL + " URL");
    }

//    private void goToNewsURL(String news_url) {
//        String currentURL = filesTools.addHTTPPrefixIfNotExist(news_url);
//        browser.setWebViewClient(new MyWebViewClient());
//        browser.loadUrl(currentURL);
//        hideKeyboardAndRequestFocus();
//
//        Log.d(DOWNLOAD_MANAGER, " go to " + currentURL + " URL");
//    }

//    private void goToURLFromStartPage(String urlfromstartpage) {
//        String currentURL = urlfromstartpage;
//        if (!currentURL.contains("http://www.")){
//            Toast.makeText(this,"HTTP is there",Toast.LENGTH_SHORT).show();
//            currentURL = filesTools.addHTTPPrefixIfNotExist(currentURLFromEditText.getText().toString());
//        }
//        browser.setWebViewClient(new MyWebViewClient());
//        browser.loadUrl(currentURL);
//        hideKeyboardAndRequestFocus();
//
//        Log.d(DOWNLOAD_MANAGER, " go to " + currentURL + " URL");
//    }

    private void hideKeyboardAndRequestFocus() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(currentURLFromEditText.getWindowToken(), 0);
        browser.requestFocus();
    }

    private ProgressDialog createProgressDialog() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Download manager");
        dialog.setMessage("Downloading");
        //final Intent intent = new Intent(this, DownloadService.class);

        dialog.setButton(Dialog.BUTTON_NEUTRAL, "HIDE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialog.setButton(Dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filesTools.stopDownloadFileTask();
            }
        });

        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax((int) CONTENT_LENGTH_GLOBAL);
        dialog.setIndeterminate(true);
        dialog.show();

        return dialog;
    }

//    private Notification createNotificationDialog() {
//        final Intent intent = new Intent(this, DownloadFileTask.class);
//
//        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        final Notification notification = new Notification(R.drawable.icon, "Starting download", System
//                .currentTimeMillis());
//
//        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT | Notification.FLAG_AUTO_CANCEL;
//        notification.contentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.download_process);
//        notification.contentIntent = pendingIntent;
//        notification.contentView.setImageViewResource(R.id.status_icon, R.drawable.ic_menu_save);
//        notification.contentView.setTextViewText(R.id.status_text, "Download in progress");
//        notification.contentView.setProgressBar(R.id.status_progress, (int) CONTENT_LENGTH_GLOBAL, 0, false);
//
//        return notification;
//    }

    private NotificationManager createNotificationManager() {
        getApplicationContext();
        return (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
    }

    private void setWindows1251Encoding() {
        WebSettings settings = browser.getSettings();
        settings.setDefaultTextEncodingName("windows-1251");
        settings.getJavaScriptEnabled();

        Log.d(DOWNLOAD_MANAGER, " current encoding was changed to : " + settings.getDefaultTextEncodingName());
    }

//    protected Dialog onCreateDialog(int id) {
//        if (id == DIALOG_DOWNLOAD) {
//            AlertDialog.Builder adb = new AlertDialog.Builder(this);
//            adb.setTitle(R.string.download_title);
//            adb.setMessage(R.string.download_message);
//            adb.setIcon(android.R.drawable.ic_dialog_info);
//            adb.setPositiveButton(R.string.show, downloadDialogClickListener);
//            adb.setNeutralButton(R.string.hide, downloadDialogClickListener);
//            adb.setNegativeButton(R.string.cancel, downloadDialogClickListener);
//            return adb.create();
//        }
//        return super.onCreateDialog(id);
//    }

//    public DialogInterface.OnClickListener downloadDialogClickListener = new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int which) {
//            switch (which) {
//                case Dialog.BUTTON_POSITIVE:
//                    filesTools.saveFileFromURL(viewTools.deleteSaveHereFromDirectoryPath(directory), uri, createProgressDialog());
//                    break;
//                case Dialog.BUTTON_NEUTRAL:
//                    filesTools.saveFileFromURL(viewTools.deleteSaveHereFromDirectoryPath(directory), uri, createNotificationDialog(), createNotificationManager());
//                    break;
//                case Dialog.BUTTON_NEGATIVE:
//                    finish();
//                    break;
//            }
//            Uri outFileName = Uri.parse(viewTools.deleteSaveHereFromDirectoryPath(directory) + uri.getLastPathSegment());
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outFileName));
//        }
//    };

    public void playAudio(String currentURL){
        MediaPlayer player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(currentURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.prepareAsync();
        player.start();
    }
}
