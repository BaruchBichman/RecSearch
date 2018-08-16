package com.example.baruch.recsearch;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baruch.recsearch.api.TransformAudioToText;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<AudioFile> AudioList;
    ListView AudioListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();
        Intent intent = new Intent(this, RecordingService.class);
        intent.putExtra(TransformAudioToText.FILE_NAME_KEY,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "audio-file.flac");
        startService(intent);
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
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

        }
    }
}