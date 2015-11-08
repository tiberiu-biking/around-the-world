package master.pam.world.servlet.impl.user;

import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SignInServlet", urlPatterns = "/SignInServlet")
public class SignInServlet extends AbstractServerRequestServlet {

  private static final long serialVersionUID = 7735454047270919067L;

  @Override
  protected ServerActionsEnum getServerAction() {
    return ServerActionsEnum.SIGN_IN;
  }

  @Override
  protected void buildServerRequest(IServerRequest aServerRequest) {
    aServerRequest.addField(RequestConstants.USERNAME, getHttpParam(RequestConstants.USERNAME));
    aServerRequest.addField(RequestConstants.PASSWORD, getHttpParam(RequestConstants.PASSWORD));
  }

}
