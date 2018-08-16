package com.example.baruch.recsearch;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<AudioFile> AudioList;
    ListView AudioListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initAudioList() {

        new AsyncTask<Void, Void, List<AudioFile>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<AudioFile> doInBackground(Void... params) {
                return AudioList; //DB_ManagerFactory.getDb_manager().getBranches();
            }

            @Override
            protected void onPostExecute(List<AudioFile> Audios) {
                AudioList = Audios;
                if (Audios != null) {
                    //setAudiosListView();
                }
                else {
                    Toast.makeText(MainActivity.this, "there are no Audio to show", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}