package com.example.baruch.recsearch.api;

import android.util.Log;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.AddDocumentOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryOptions;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

import org.json.JSONArray;

import java.io.FileNotFoundException;

public class Searcher {

//    public JSONArray search(String query){
//        JSONArray res = new JSONArray();
//        discovery.query(
//                new QueryOptions().naturalLanguageQuery().
//        );
//
//        return res;
//    }


    Discovery discovery;
    void init() {

        discovery = new Discovery(
                "2018-08-01"               ,
                "21b9e719-2b0c-4758-85e4-3b5b2b90cee1",
                "Mqsez5Qb3hUx"
                );

        discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");


    }

    void index(String record){
        discovery.addDocument(new AddDocumentOptions.Builder().fileContentType(record).build());
    }

}
