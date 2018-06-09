package com.example.visioneh.englishhelper.bean;

/**
 * Created by visionEH on 2017/9/19.
 */

public class Word {

    private String name;
    private String translation;
    private String date;
    private int uid;
    public Word(String name, String translation, String date) {
        this.name = name;
        this.translation = translation;
        this.date = date;
    }
    public Word(String name, String translation) {
        this.name = name;
        this.translation = translation;
    }
    public Word(String name, String translation, String date,int uid) {
        this.name = name;
        this.translation = translation;
        this.date = date;
        this.uid=uid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDate() {
        return date;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
