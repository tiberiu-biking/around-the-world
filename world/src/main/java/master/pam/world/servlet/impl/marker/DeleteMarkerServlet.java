package master.pam.world.servlet.impl.marker;

import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "DeleteMarkerServlet", urlPatterns = "/DeleteMarkerServlet")
public class DeleteMarkerServlet extends AbstractServerRequestServlet {
  private static final long serialVersionUID = 7735454047270919067L;

  @Override
  protected ServerActionsEnum getServerAction() {
    return ServerActionsEnum.DELETE_MARKER;
  }

  @Override
  protected void buildServerRequest(IServerRequest aServerRequest) {
    aServerRequest.addLong(RequestConstants.MARKER_ID, Long.parseLong(getHttpParam(RequestConstants.MARKER_ID)));
  }
}
