package master.pam.world.servlet.impl.marker;

import master.pam.crosscutting.dto.impl.MarkerDto;
import master.pam.crosscutting.gson.GsonHelper;
import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "UpdateMarkerServlet", urlPatterns = "/UpdateMarkerServlet")
public class UpdateMarkerServlet extends AbstractServerRequestServlet {
  private static final long serialVersionUID = 7735454047270919067L;

  @Override
  protected ServerActionsEnum getServerAction() {
    return ServerActionsEnum.UPDATE_MARKER;
  }

  @Override
  protected void buildServerRequest(IServerRequest aServerRequest) {
    aServerRequest.addField(RequestConstants.DTO, GsonHelper.fromGson(getHttpParam(RequestConstants.MARKER), MarkerDto.class));
  }

}
