package master.pam.world.servlet.impl.push;

import fi.foyt.foursquare.api.entities.Checkin;
import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crosscutting.dto.api.IUserDto;
import master.pam.crosscutting.gson.GsonHelper;
import master.pam.crosscutting.notifications.NotificationPool;
import master.pam.foursquare.api.FoursquareUtil;
import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.server.api.response.ResponseConstants;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import master.pam.world.servlet.base.AbstractServerRequestServlet;
import master.pam.world.servlet.exception.WrongRequestException;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "FoursquarePushServlet", urlPatterns = "/FoursquarePushServlet")
public class FoursquarePushServlet extends AbstractServerRequestServlet {

  private static final long serialVersionUID = 4052291386604876551L;

  @Override
  protected ServerActionsEnum getServerAction() {
    return ServerActionsEnum.ADD_MARKERS;
  }

  @Override
  protected void buildServerRequest(IServerRequest aServerRequest) throws WrongRequestException {
    String checkinGson = getHttpParam(RequestConstants.FOURSQUARE_CHECKIN);

    logger.debug("Recieved Foursquare push notification: " + checkinGson);

    Checkin checkin = GsonHelper.fromGson(checkinGson, Checkin.class);

    IServerRequest userRequest = builGetUserInfoRequest(checkin.getUser().getId());
    IResponseEnvelope requestUserResponse = getServer().sendRequest(userRequest);
    IUserDto userDto = requestUserResponse.getData(ResponseConstants.USER, IUserDto.class);

    if (userDto != null) {
      IMarkerDto markerDto = FoursquareUtil.checkinToMarker(checkin, userDto.getId());
      aServerRequest.addField(RequestConstants.DTO, markerDto);
    }
  }

  @Override
  protected String buildResult(IResponseEnvelope aResponseEnvelope) {
    String newMarkers = super.buildResult(aResponseEnvelope);

    logger.debug("Add notification to the pool: " + newMarkers);

    NotificationPool.getPool().addNotification(newMarkers);

    return "";
  }

  private IServerRequest builGetUserInfoRequest(String aFoursquareUserId) {
    IServerRequest userRequest = getServer().createRequest();
    userRequest.setAction(ServerActionsEnum.GET_USER_INFO_BY_FOURSQUAREID);
    userRequest.addField(RequestConstants.USER_FOURSQUARE_ID, aFoursquareUserId);
    return userRequest;
  }


}
