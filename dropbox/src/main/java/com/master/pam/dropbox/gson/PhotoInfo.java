package com.master.pam.dropbox.gson;

import java.util.List;

public class PhotoInfo {

    private String time_taken;

    private List<Double> lat_long;

    public List<Double> getLat_long() {
        return lat_long;
    }

    public void setLat_long(List<Double> aLat_long) {
        lat_long = aLat_long;
    }

    public String getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(String time_taken) {
        this.time_taken = time_taken;
    }
}
