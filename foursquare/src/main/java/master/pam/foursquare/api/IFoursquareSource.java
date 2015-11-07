package master.pam.foursquare.api;

import fi.foyt.foursquare.api.FoursquareApiException;
import master.pam.crosscutting.dto.api.IMarkerDto;

import java.util.List;

public interface IFoursquareSource {

    List<IMarkerDto> getMarkers(String aCode, long aUserId) throws FoursquareApiException;

    String getUserId(String aCode) throws FoursquareApiException;

}
