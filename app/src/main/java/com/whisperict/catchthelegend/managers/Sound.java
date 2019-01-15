package com.whisperict.catchthelegend.managers;


import android.content.Context;

public class Sound {
    private int id;
    private Context context;

    public Sound(int id, Context context) {
        this.id = id;
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public Context getContext() {
        return context;
    }
}
