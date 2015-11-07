package master.pam.world.servlet.impl.marker;

import com.drew.imaging.ImageProcessingException;
import com.master.pam.geo.coding.api.IGeoCodingAPI;
import com.master.pam.geo.tagging.PictureTaggingUtil;
import com.master.pam.geo.tagging.PictureTags;
import master.pam.crosscutting.dto.api.IAddressDto;
import master.pam.crosscutting.dto.impl.MarkerDto;
import master.pam.crosscutting.geo.GeoPoint;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@MultipartConfig
@WebServlet(name = "UploadPictureServlet", urlPatterns = "/UploadPictureServlet")
public class UploadPictureServlet extends AbstractServerRequestServlet {

    private static final long serialVersionUID = -4114394782652326927L;

    private boolean isGPSMissing;

    private IGeoCodingAPI geoCodingAPI = SpringContext.getBean(IGeoCodingAPI.class);

    @Override
    protected ServerActionsEnum getServerAction() {
        return ServerActionsEnum.ADD_MARKERS;
    }

    @Override
    protected void buildServerRequest(IServerRequest aServerRequest) {
        Collection<Part> parts = getHttpRequestParts();
        isGPSMissing = false;
        List<MarkerDto> markers = new ArrayList<MarkerDto>();

        for (Part part : parts) {
            if (part.getContentType() != null) {
                try {
                    InputStream inputStream = part.getInputStream();

                    BufferedInputStream pictureStream = new BufferedInputStream(inputStream);
                    PictureTags pictureTags = PictureTaggingUtil.getGeoLocation(pictureStream);
                    if (pictureTags == null) {
                        isGPSMissing = true;
                        return;
                    }
                    GeoPoint location = pictureTags.getLocation();
                    MarkerDto markerDto = new MarkerDto(location.getLatitude(), location.getLongitude());
                    markerDto.setDate(pictureTags.getDateTaken());

                    markerDto.setUserId(Long.parseLong(getHttpParam(RequestConstants.USER_ID)));
/*          if ( part instanceof ApplicationPart ) {
            ApplicationPart appPart = ( ApplicationPart ) part;
            markerDto.setNote( "Imported from picture: " + StringUtils.upperCase( (appPart).getFilename() ) );
          } else {*/
                    markerDto.setNote("Imported from a picture ");
/*          }*/

                    // geo coding
                    IAddressDto address = geoCodingAPI.getAddress(markerDto.getLatitude(), markerDto.getLongitude());
                    markerDto.setName(address.getShortAddress());
                    markers.add(markerDto);

                } catch (IOException | ImageProcessingException e) {
                    logger.error("FileUploadServlet: cannot get location from picture!", e);
                    isGPSMissing = true;
                }
            }
        }
        aServerRequest.addField(RequestConstants.DTO_LIST, markers);

    }

    @Override
    protected String buildResult(IResponseEnvelope aResponseEnvelope) {
        if (isGPSMissing) {
            aResponseEnvelope.addDataMessage("Picture(s) doesn't contain GPS information.");
        }

        return super.buildResult(aResponseEnvelope);
    }

}