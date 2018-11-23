package com.clas.testdb2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercise {
    @NonNull
    @PrimaryKey
    private String url;
    @NonNull
    private String name;

    @Ignore
    public Exercise(){};

    public Exercise(String url, String name){
        this.url = url;
        this.name = name;
    }

    @NonNull
    public String getName() {return name;}
    public void setName(@NonNull String name) {this.name = name;}
    @NonNull
    public String getUrl() {return url;}
    public void setUrl(@NonNull String url) {this.url = url;}
}
