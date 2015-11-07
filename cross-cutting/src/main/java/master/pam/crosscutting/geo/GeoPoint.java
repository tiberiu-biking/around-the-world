package master.pam.crosscutting.geo;

import java.math.BigDecimal;

public class GeoPoint {

    private BigDecimal latitude;
    private BigDecimal longitude;

    public GeoPoint() {
    }

    public GeoPoint(BigDecimal aLatitude, BigDecimal aLongitude) {
        setLatitude(aLatitude);
        setLongitude(aLongitude);
    }

    public GeoPoint(double aLatitude, double aLongitude) {
        setLatitude(BigDecimal.valueOf(aLatitude));
        setLongitude(BigDecimal.valueOf(aLongitude));
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal aLatitude) {
        this.latitude = aLatitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal aLongitude) {
        this.longitude = aLongitude;
    }
}
