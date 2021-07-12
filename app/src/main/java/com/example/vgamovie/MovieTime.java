package com.example.vgamovie;

public class MovieTime {

    private String NameTheater;
    private String Time;
    private String Time2;
    private String Time3;
    private String Time4;
    private String Time5;

    public MovieTime(String nameTheater, String time) {
        NameTheater = nameTheater;
        Time = time;
    }

    public void setNameTheater(String nameTheater) {
        NameTheater = nameTheater;
    }

    public void setTime(String time) {
        Time = time;
    }



    public String getNameTheater() {
        return NameTheater;
    }

    public String getTime() {
        return Time;
    }

}
