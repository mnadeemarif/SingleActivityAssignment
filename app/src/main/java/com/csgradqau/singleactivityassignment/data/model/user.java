package com.csgradqau.singleactivityassignment.data.model;

import android.graphics.drawable.Drawable;

public class user {
    public static final String TABLE_NAME = "db_users";

    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_HOBBIES = "hobbies";
    public static final String COLUMN_PROFILE = "profile";

    private String email,password,name,hobbies,dob;
    private int gender;
    private byte[] profile;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DOB + " TEXT,"
                    + COLUMN_GENDER + " INTEGER,"
                    + COLUMN_HOBBIES + " TEXT,"
                    + COLUMN_PROFILE + " BLOB"
                    + ")";

    public user() {
    }

    public user(String email, String password, String name, String dob, int gender, String hobbies ,byte[] profile) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.hobbies = hobbies;
        this.dob = dob;
        this.gender = gender;
        this.profile = profile;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }
}
