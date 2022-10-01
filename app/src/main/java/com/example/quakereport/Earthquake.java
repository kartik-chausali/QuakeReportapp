package com.example.quakereport;

public class Earthquake {
    double mmagnitude;
    String mplace;
    long mtimeInMilliSeconds;
    String murl;


    public Earthquake(double magnitude, String place , long timeInMilliSeconds, String url){
        mmagnitude = magnitude;
        mplace = place;
        mtimeInMilliSeconds = timeInMilliSeconds;
        murl = url;
    }

    double getMmagnitude(){
        return mmagnitude;
    }

    String getMplaceoffSet(){
        int i = 0;
        int count = 0;
        if(mplace.contains("of")){
            while(i<mplace.length()){
         if(mplace.charAt(i) == 'f'){
             count++;
             break;
         }
         i++;
         count++;
            }
        }else{
            return "Near the";
        }

        return mplace.substring(0, count);
    }

    String getLocation(){
        int i = 0;
        int count = 0;
        if(mplace.contains("of")){
            while(i<mplace.length()){
                if(mplace.charAt(i) == 'f'){
                    count++;
                    break;
                }
                i++;
                count++;
            }
        }
     return mplace.substring(count);
    }

    long getMtimeInMilliSeconds(){
        return mtimeInMilliSeconds;
    }
    String getMurl(){
        return murl;
    }
}
