package com.example.birdsGameEx2.Activities;

import android.app.Application;

import com.example.birdsGameEx2.Utilities.SignalGenerator;
import com.example.birdsGameEx2.Utilities.MySP;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignalGenerator.init(this);
        MySP.getInstance(this);
    }
}
