package com.csgradqau.singleactivityassignment.data.model;
import com.csgradqau.singleactivityassignment.data.model.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_users";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(user.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + user.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long registerUser(String email, String pass, String name, String dob, int gender, String hobbies, byte[] profile) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(user.COLUMN_EMAIL, email);
        values.put(user.COLUMN_PASSWORD, pass);
        values.put(user.COLUMN_NAME, name);
        values.put(user.COLUMN_DOB, dob);
        values.put(user.COLUMN_GENDER, gender);
        values.put(user.COLUMN_HOBBIES, hobbies);
        values.put(user.COLUMN_PROFILE, profile);


        // insert row
        long id = db.insert(user.TABLE_NAME, null, values);
        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public boolean checkUserExist(String email){
        String[] columns = {user.COLUMN_EMAIL};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "email=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(DATABASE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    public user getUser(String email) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(user.TABLE_NAME,
                new String[]{user.COLUMN_EMAIL, user.COLUMN_PASSWORD, user.COLUMN_NAME,user.COLUMN_DOB,user.COLUMN_GENDER,user.COLUMN_HOBBIES,user.COLUMN_PROFILE},
                user.COLUMN_EMAIL + "=?",
                new String[]{String.valueOf(email)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        user usr = new user(
                cursor.getString(cursor.getColumnIndex(user.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(user.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(user.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(user.COLUMN_DOB)),
                cursor.getInt(cursor.getColumnIndex(user.COLUMN_GENDER)),
                cursor.getString(cursor.getColumnIndex(user.COLUMN_HOBBIES)),
                cursor.getBlob(cursor.getColumnIndex(user.COLUMN_PROFILE)));

        // close the db connection
        cursor.close();

        return usr;
    }

    public List<user> getAllUsers() {
        List<user> users = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + user.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                user usr = new user();
                usr.setEmail(cursor.getString(cursor.getColumnIndex(user.COLUMN_EMAIL)));
                usr.setPassword(cursor.getString(cursor.getColumnIndex(user.COLUMN_PASSWORD)));
                usr.setName(cursor.getString(cursor.getColumnIndex(user.COLUMN_NAME)));
                usr.setDob(cursor.getString(cursor.getColumnIndex(user.COLUMN_DOB)));
                usr.setGender(cursor.getInt(cursor.getColumnIndex(user.COLUMN_GENDER)));
                usr.setHobbies(cursor.getString(cursor.getColumnIndex(user.COLUMN_HOBBIES)));
                usr.setProfile(cursor.getBlob(cursor.getColumnIndex(user.COLUMN_PROFILE)));

                users.add(usr);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return users;
    }

    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + user.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(user u) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(user.COLUMN_HOBBIES, u.getHobbies());

        // updating row
        return db.update(user.TABLE_NAME, values, user.COLUMN_EMAIL + " = ?",
                new String[]{String.valueOf(u.getEmail())});
    }

    public void deleteNote(user note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(user.TABLE_NAME, user.COLUMN_EMAIL + " = ?",
                new String[]{String.valueOf(note.getEmail())});
        db.close();
    }
}
