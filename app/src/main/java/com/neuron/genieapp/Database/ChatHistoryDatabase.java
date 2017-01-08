package com.neuron.genieapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.neuron.genieapp.DataModels.Chats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan on 17/12/16.
 */

public class ChatHistoryDatabase extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "chat_history";

    // Login table name
    private static final String TABLE_CHAT = "chat";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_CHAT_SIDE = "chatside";
    private static  final String KEY_TIME = "message_time";


    public ChatHistoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_CHAT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MESSAGE + " TEXT,"
                + KEY_CHAT_SIDE + " INTEGER DEFAULT 0," + KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_CHAT_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addChat(String message, Boolean aBoolean, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message); // message
        values.put(KEY_CHAT_SIDE, aBoolean); // boolean
        values.put(KEY_TIME, time);
        Log.d("CHATKK", ""+values);

        // Inserting Row
        long id = db.insert(TABLE_CHAT, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public List<Chats> getChatHistory() {

         List<Chats> user = new ArrayList<Chats>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHAT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Chats chats = new Chats();
                chats.set_id(Integer.parseInt(cursor.getString(0)));
                chats.set_message(cursor.getString(1));
                Log.d("CHATKK", cursor.getString(1));
                chats.set_boolean(Integer.parseInt(cursor.getString(2)) == 1);
                chats.set_time(cursor.getString(3));

                Log.d("CHATKK", ""+(Integer.parseInt(cursor.getString(2)) == 1));

                //adding to chat list
                user.add(chats);
                Log.d("CHATKKCHATS", ""+chats);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        Log.d("CHATKKUSER", ""+user.size());
        return user;
    }

    /**
     * Re create database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CHAT, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
