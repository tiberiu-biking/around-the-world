package master.pam.server.impl.request;

import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.impl.exceptions.RequestException;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import master.pam.server.impl.response.impl.ErrorServerResponse;
import master.pam.server.impl.response.impl.marker.*;
import master.pam.server.impl.response.impl.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestFactory {
  private final static Logger log = LoggerFactory.getLogger(RequestFactory.class);

  public static IResponseEnvelope createResult(IServerRequest aRequest) {

    try {
      log.debug("Create result from factory for request: " + aRequest.getAction());

      if (aRequest.getAction() == ServerActionsEnum.SIGN_IN)
        return new SignInResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.GET_MARKERS)
        return new GetMarkersResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.GET_TIMELINE)
        return new GetTimelineResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.UPDATE_MARKER)
        return new UpdateMarkerResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.DELETE_MARKER)
        return new DeleteMarkersResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.ADD_MARKERS)
        return new CreateMarkersResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.GET_USER_INFO_BY_FOURSQUAREID)
        return new GetUserByFoursquareIdResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.CREATE_USER)
        return new CreateUserResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.GET_USER_INFO)
        return new GetUserResponse(aRequest).getResponse();

      else if (aRequest.getAction() == ServerActionsEnum.UPDATE_USER)
        return new UpdateUserResponse(aRequest).getResponse();

      else
        throw new RequestException("No such action: " + aRequest.getAction());
    } catch (RequestException e) {

      return new ErrorServerResponse(aRequest, e).getResponse();
    }
  }
}
