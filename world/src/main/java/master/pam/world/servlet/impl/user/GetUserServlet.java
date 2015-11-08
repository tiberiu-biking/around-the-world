package master.pam.world.servlet.impl.user;

import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "GetUserServlet", urlPatterns = "/GetUserServlet")
public class GetUserServlet extends AbstractServerRequestServlet {

  private static final long serialVersionUID = 7735454047270919067L;

  @Override
  protected ServerActionsEnum getServerAction() {
    return ServerActionsEnum.GET_USER_INFO;
  }

  @Override
  protected void buildServerRequest(IServerRequest aServerRequest) {
    aServerRequest.addField(RequestConstants.USER_ID, Long.parseLong(getHttpParam(RequestConstants.USER_ID)));
  }

}
