package com.neuron.genieapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.neuron.genieapp.DataModels.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan on 17/5/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //Database Name
    public static final String DATABASE_NAME = "chat_database";

    //Chat table name
    public static final String TABLE_QUESTIONS = "questions";

    //Database Version
    public static final int DATABASE_VERSION = 1;

    //Chat Table Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_REPLY = "reply";
    private static final String KEY_PHONE_NO = "phone_number";
    private static final String KEY_LOCATION = "location";

    //Constructor
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating table with column name and their data types.
        String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_QUESTION + " TEXT," + KEY_REPLY + " TEXT," + KEY_PHONE_NO +
                " TEXT," + KEY_LOCATION+ " TEXT" +")";

        db.execSQL(CREATE_CHAT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);

        //Create tables again
        onCreate(db);
    }

    void addToChatTable(Question question){
        // Opening database
        SQLiteDatabase database = this.getWritableDatabase();

        // adding items in the content values
        ContentValues values = new ContentValues();
            values.put(KEY_NAME, question.get_name());
            values.put(KEY_QUESTION, question.get_question());
            values.put(KEY_REPLY, question.get_reply());
            values.put(KEY_PHONE_NO, question.get_phone());
            values.put(KEY_LOCATION, question.get_location());

        // inserting content value in database
        database.insert(TABLE_QUESTIONS, null, values);

        // Closing database connection
        database.close();
    }

    // Getting All Questions
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();

                question.set_id(Integer.parseInt(cursor.getString(0)));
                question.set_name(cursor.getString(1));
                question.set_question(cursor.getString(2));
                question.set_reply(cursor.getString(3));
                question.set_phone(cursor.getString(4));
                question.set_location(cursor.getString(5));

                // Adding question to list
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return question list
        return questionList;
    }
}
