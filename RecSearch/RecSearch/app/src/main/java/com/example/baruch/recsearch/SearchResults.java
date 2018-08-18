package com.example.baruch.recsearch;

import com.example.baruch.recsearch.api.Searcher;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchResults {
    public List<AudioFile> resultList;


    public List<AudioFile> getResultList(String searchWord)
    {
        Searcher searcher = new Searcher();
        return  searcher.perfolrmSearch(searchWord).resultList;
//        resultList= new ArrayList<AudioFile>();
//        List<TimeWord> time = new ArrayList<>();
//        time.add(new TimeWord("00:01:22"));
//        time.add(new TimeWord("00:00:25"));
//        resultList.add(new AudioFile("123",time, "01.01.2018"));
//        for (int i = 0; i < 15; i++) {
//            int year = 2000 + i;
//            int second = 5 + i;
//            String time1 = "00:01:" + second;
//            time = new ArrayList<>();
//            time.add(new TimeWord("00:01:"+second));
//            time.add(new TimeWord("00:00:25"+ (2*second)));
//            resultList.add(new AudioFile("123", time, "01.01."+year));
//        }
//        return resultList;
    }
}
