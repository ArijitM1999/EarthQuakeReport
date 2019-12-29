package com.example.thomashelby.earthquakereport;

public class Word {
    private double mmag;

    // Android version number (e.g. 2.3-2.7, 3.0-3.2.6, 4.0-4.0.4)
    private String iplace1;
    private String iplace2;

    // Drawable resource ID
    private String idate;
private String url;
    /*
     * Create a new AndroidFlavor object.
     *
     * @param vName is the name of the Android version (e.g. Gingerbread)
     * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
     * @param image is drawable reference ID that corresponds to the Android version
     * */
    public Word(double m, String p1,String p2, String d,String web)
    {
        mmag = m;
        iplace1 = p1;
        iplace2=p2;
        idate = d;
        url=web;
    }

    /**
     * Get the name of the Android version
     */
    public double getMagnitude() {
        return mmag;
    }

    /**
     * Get the Android version number
     */
    public String getPlace1() {
        return iplace1;
    }

    public String getPlace2(){
        return iplace2;
    }

    /**
     * Get the image resource ID
     */
    public String getDate() {
        return idate;
    }
public String getUrl(){
        return url;
}
}
