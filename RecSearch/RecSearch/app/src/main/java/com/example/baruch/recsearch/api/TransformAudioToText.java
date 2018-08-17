package com.example.baruch.recsearch.api;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.baruch.recsearch.R;
import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

public class TransformAudioToText {

    private SpeechToText speechService;
    public static final String FILE_NAME_KEY = "audio_file_name";
    public TransformAudioToText(){

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
                    JSONObject jsonObj = new JSONObject(transcript.toString());
                    String transStr = jsonObj.getJSONArray("results").getJSONObject(0)
                            .getJSONArray("alternatives").getJSONObject(0).getString("transcript");
                    JSONObject jsonObjectRes = new JSONObject();
                    jsonObjectRes.put("transcript",transStr);
                    jsonObjectRes.put("file",file.toString());
                    Searcher s = new Searcher();
                    s.init();
                    s.index(jsonObjectRes.toString(), file.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
    private SpeechToText initSpeechToTextService() {
        SpeechToText service = new SpeechToText();;
        return service;
    }
}
