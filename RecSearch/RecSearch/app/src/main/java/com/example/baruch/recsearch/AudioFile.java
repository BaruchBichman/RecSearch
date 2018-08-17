package com.example.baruch.recsearch;

import java.util.Date;
import java.util.List;

public class AudioFile {
    public String name;
    public List<TimeWord> result;
    public String date;

    public AudioFile(String _name, List<TimeWord> _result, String _date){
        name = _name;
        result = _result;
        date = _date;
    }
    
}
