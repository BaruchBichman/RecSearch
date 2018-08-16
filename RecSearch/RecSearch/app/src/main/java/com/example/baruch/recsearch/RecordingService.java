package com.example.baruch.recsearch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.baruch.recsearch.api.TransformAudioToText;

import java.io.File;

public class RecordingService extends Service {

    private String path;

    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String fileName =  intent.getStringExtra(TransformAudioToText.FILE_NAME_KEY);
        File file = new File(fileName);
        TransformAudioToText transformAudioToText = new TransformAudioToText();
        transformAudioToText.transformAudioToText(file);
        return Service.START_NOT_STICKY;
    }
}
