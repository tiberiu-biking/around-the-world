package com.master.pam.geo.tagging;

import master.pam.crosscutting.geo.GeoPoint;

import java.util.Date;

public class PictureTags {

    private Date dateTaken;

    private GeoPoint location;

    public PictureTags(GeoPoint aGeoPoint, Date aDateTaken) {
        setDateTaken(aDateTaken);
        setLocation(aGeoPoint);
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PictureTags [dateTaken=").append(dateTaken).append(", location=").append(location).append("]");
        return builder.toString();
    }
}
