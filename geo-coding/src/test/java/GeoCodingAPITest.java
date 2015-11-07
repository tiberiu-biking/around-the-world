import com.master.pam.geo.coding.api.impl.GeoCodingAPI;
import master.pam.crosscutting.geo.GeoPoint;

public class GeoCodingAPITest {

    public static void main(String[] args) {
        GeoCodingAPI api = new GeoCodingAPI();
        System.out.println(api.getAddress(new GeoPoint(52.513329, 13.395451)));

    }

}
