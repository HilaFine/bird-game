package com.example.birdsGameEx2.Models;



public class Record {
    private String name ="";
    private int score = 0;
    private double latitude = 0.0;
    private double longitude = 0.0;

    public Record(){

    }
    public String getName(){
        return name;
    }
    public Record setName(String name){
        this.name = name;
        return this;
    }
    public void setScore(int score){
        this.score = score;
    }
    public long getScore() {
        return score;
    }


    public double getLatitude() {
        return latitude;
    }

    public Record setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Record setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }


    public String toString() {
        return "Name: " + name + " Score: " + score;
    }


    public int compareTo(Record r) {
        return (int) (r.getScore() - this.getScore());
    }


}
