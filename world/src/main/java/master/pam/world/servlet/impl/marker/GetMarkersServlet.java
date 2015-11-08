package master.pam.world.servlet.impl.marker;

import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "GetMarkersServlet", urlPatterns = "/GetMarkersServlet")
public class GetMarkersServlet extends AbstractServerRequestServlet {

  private static final long serialVersionUID = 1606559514981505001L;

  @Override
  protected ServerActionsEnum getServerAction() {
    return ServerActionsEnum.GET_MARKERS;
  }

  @Override
  protected void buildServerRequest(IServerRequest aServerRequest) {
    String userId = getHttpParam(RequestConstants.USER_ID);
    String markerId = getHttpParam(RequestConstants.MARKER_ID);

    aServerRequest.addLong(RequestConstants.USER_ID, Long.parseLong(userId));

    if (markerId != null)
      aServerRequest.addLong(RequestConstants.MARKER_ID, Long.parseLong(markerId));
  }

}
