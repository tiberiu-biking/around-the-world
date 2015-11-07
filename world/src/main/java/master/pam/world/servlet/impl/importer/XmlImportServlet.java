package master.pam.world.servlet.impl.importer;

import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import java.util.Collection;

@WebServlet(name = "XmlImportServlet", urlPatterns = "/XmlImportServlet")
public class XmlImportServlet extends AbstractServerRequestServlet {

    private static final long serialVersionUID = -7904700628451273567L;

    @Override
    protected ServerActionsEnum getServerAction() {
        return ServerActionsEnum.ADD_MARKERS;
    }

    @Override
    protected void buildServerRequest(IServerRequest aServerRequest) {
        Collection<Part> parts = getHttpRequestParts();
        for (Part part : parts)
            if (part.getContentType() != null)
                // try {
                // InputStream inputStream = part.getInputStream();

                // feed.getValue().getMarkers()

                System.out.print("");

        // MarkerDto markerDto = new MarkerDto( location.getLatitude(),
        // location.getLongitude() );
        // markerDto.setDate( pictureTags.getDateTaken() );

        // markerDto.setUserId( Long.parseLong( getHttpParam(
        // RequestConstants.USER_ID ) ) );
        // if ( part instanceof ApplicationPart ) {
        // ApplicationPart appPart = ( ApplicationPart ) part;
        // markerDto.setNote( "Imported from picture: " +
        // StringUtils.upperCase( (appPart).getFilename() ) );
        // } else
        // markerDto.setNote( "Imported from a picture " );

        // geo coding
        // IAddressDto address = geoCodingAPI.getAddress(
        // markerDto.getLatitude(), markerDto.getLongitude() );
        // markerDto.setName( address.getShortAddress() );

        // aServerRequest.addField( RequestConstants.DTO, markerDto );

        // } catch ( IOException | ImageProcessingException e ) {
        // logger.error( "FileUploadServlet: cannot get location from picture!", e
        // );
        // }
        //
        // long userId = Long.parseLong( getHttpParam( RequestConstants.USER_ID ) );
        //
        // try {
        // aServerRequest.addField( RequestConstants.DTO_LIST,
        // dropboxAPI.getMarkers( code, paths, userId ) );
        // } catch ( DbxException e ) {
        // // TODO
        // e.printStackTrace();
        // }
    }
}
