//******************************************************************
//
//  Main.java
//  Copyright 2014 PSI AG. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
// ******************************************************************

package com.master.pam.geo.tagging;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import master.pam.crosscutting.geo.GeoPoint;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Date;

public class PictureTaggingUtil {

    public static final PictureTags getGeoLocation(BufferedInputStream aInputStream) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(aInputStream, false);

        GpsDirectory gpsDirectory = metadata.getDirectory(GpsDirectory.class);
        if (gpsDirectory == null) {
            return null;
        }
        GeoLocation location = gpsDirectory.getGeoLocation();
        GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());

        ExifSubIFDDirectory dateDirectory = metadata.getDirectory(ExifSubIFDDirectory.class);
        Date dateTaken = dateDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED);

        PictureTags result = new PictureTags(geoPoint, dateTaken);

        return result;
    }
}
