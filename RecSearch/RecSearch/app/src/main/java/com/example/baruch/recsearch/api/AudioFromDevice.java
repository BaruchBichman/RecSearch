package com.example.baruch.recsearch.api;

public class AudioFromDevice {
    private long mAudioID;
    private String mAudioTitle;

    public AudioFromDevice(long id, String title){
        mAudioID = id;
        mAudioTitle = title;
    }

    public long getmAudioID(){
        return mAudioID;
    }

    public String getSongTitle(){
        return mAudioTitle;
    }
}
