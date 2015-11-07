package master.pam.foursquare.api;

import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactVenue;
import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crosscutting.dto.impl.MarkerDto;

import java.util.Date;

public class FoursquareUtil {

    public static final IMarkerDto checkinToMarker(Checkin aCheckin, Long aUserId) {
        CompactVenue venue = aCheckin.getVenue();

        MarkerDto markerDto = new MarkerDto(venue.getLocation().getLat(), venue.getLocation().getLng(), new Date(
                aCheckin.getCreatedAt() * 1000), aCheckin.getId());
        markerDto.setName(venue.getName());
        markerDto.setUserId(aUserId);
        markerDto.setNote("Imported from Foursquare");

        return markerDto;
    }

}
