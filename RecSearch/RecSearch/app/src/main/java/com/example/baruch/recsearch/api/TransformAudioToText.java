package com.example.baruch.recsearch.api;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.baruch.recsearch.R;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

import java.io.File;
import java.io.FileNotFoundException;

public class TransformAudioToText {
    private Context context;
    private SpeechToText speechService;

    public TransformAudioToText(Context context){
        this.context = context;
        speechService = initSpeechToTextService();
    }

    public void transformAudioToText(final File file){

        Thread thread = new Thread() {
            public void run() {
                speechService.setUsernameAndPassword("6c00ceb7-c1f5-4e1e-8b76-1331c59c9a0c", "Bbs83QqxcNgG");

                RecognizeOptions options = null;
                try {
                    options = new RecognizeOptions.Builder()
                            .audio(file)
                            .contentType(HttpMediaType.AUDIO_FLAC).timestamps(true)
                            .build();
                    SpeechRecognitionResults transcript = speechService.recognize(options).execute();
                    Log.d("TAG",transcript.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
    private SpeechToText initSpeechToTextService() {
        SpeechToText service = new SpeechToText();
        String username = context.getString(R.string.speech_text_username);
        String password = context.getString(R.string.speech_text_password);
        service.setUsernameAndPassword(username, password);
        service.setEndPoint(context.getString(R.string.speech_text_url));
        return service;
    }
}
