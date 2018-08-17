package com.example.baruch.recsearch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baruch.recsearch.api.TransformAudioToText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText searchEditText;
    private ImageButton searchButton;
    List<AudioFile> audioList;
    ListView AudioListView;
    SearchResults results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioList = new ArrayList<>();
        results = new SearchResults();
        findViews();
        isStoragePermissionGranted();
        Intent intent = new Intent(this, RecordingService.class);
        intent.putExtra(TransformAudioToText.FILE_NAME_KEY,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "audio-file.flac");
        startService(intent);
    }

    private void findViews(){
        searchButton = (ImageButton)findViewById( R.id.searchButton );
        searchEditText = (EditText)findViewById(R.id.searchEditText);
        AudioListView = (ListView) findViewById(R.id.resultListView);

        searchButton.setOnClickListener(this);
    }

    private void initAudioList(final String word) {


        new AsyncTask<Void, Void, List<AudioFile>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<AudioFile> doInBackground(Void... params) {
                List<AudioFile> lst=results.getResultList(word);
                return lst;
            }

            @Override
            protected void onPostExecute(List<AudioFile> audios) {
                audioList = audios;
                if (audios != null) {
                    setAudiosListView();
                }
                else {
                    Toast.makeText(MainActivity.this, "there are no Audio to show", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void setAudiosListView() {
        ArrayAdapter<AudioFile> adapter = new ArrayAdapter<AudioFile>(this, R.layout.audio_item_view, audioList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(MainActivity.this, R.layout.audio_item_view, null);
                }
                TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
                TextView numberOfTimesTextView = (TextView) convertView.findViewById(R.id.numberOfTimestextView);
                TextView fileNameTextView = (TextView) convertView.findViewById(R.id.fileNameTextView);

                dateTextView.setText(audioList.get(position).date);
                numberOfTimesTextView.setText(audioList.get(position).result.get(0).time);
                fileNameTextView.setText(audioList.get(position).name);

                return convertView;
            }
        };
        AudioListView.setAdapter(adapter);
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

    @Override
    public void onClick(View v) {
        if(v == searchButton){
            //String jjj = this.searchEditText.getText().toString();
            //Toast.makeText(this, jjj, Toast.LENGTH_LONG).show();
            if(this.searchEditText.getText().toString() == "" || this.searchEditText.getText().toString() == " ")
                Toast.makeText(this, "please enter a word to search", Toast.LENGTH_LONG).show();
            else
                initAudioList(this.searchEditText.getText().toString());
        }

    }
}