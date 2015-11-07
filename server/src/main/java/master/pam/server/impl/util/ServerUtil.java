package master.pam.server.impl.util;

import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crosscutting.geo.GeoPoint;

import java.util.List;

public class ServerUtil {

    public static final GeoPoint calculateCenterPoint(List<IMarkerDto> aMarkers) {
        double minLatitude = 9999;
        double maxLatitude = -9999;
        double minLong = 9999;
        double maxLong = -9999;

        for (IMarkerDto marker : aMarkers) {
            if (marker.getLatitude().doubleValue() > maxLatitude)
                maxLatitude = marker.getLatitude().doubleValue();
            if (marker.getLatitude().doubleValue() < minLatitude)
                minLatitude = marker.getLatitude().doubleValue();

            if (marker.getLongitude().doubleValue() > maxLong)
                maxLong = marker.getLongitude().doubleValue();
            if (marker.getLongitude().doubleValue() < minLong)
                minLong = marker.getLongitude().doubleValue();
        }

        return new GeoPoint((maxLatitude + minLatitude) / 2, (maxLong + minLong) / 2);
    }

}
