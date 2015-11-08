package master.pam.world.servlet.impl.user;

import master.pam.crosscutting.dto.impl.UserDto;
import master.pam.crosscutting.gson.GsonHelper;
import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SignupServlet", urlPatterns = "/SignupServlet")
public class SignupServlet extends AbstractServerRequestServlet {
  private static final long serialVersionUID = 7735454047270919067L;

  @Override
  protected ServerActionsEnum getServerAction() {
    return ServerActionsEnum.CREATE_USER;
  }

  @Override
  protected void buildServerRequest(IServerRequest aServerRequest) {
    aServerRequest.addField(RequestConstants.DTO, GsonHelper.fromGson(getHttpParam(RequestConstants.USER), UserDto.class));
    aServerRequest.addField(RequestConstants.PASSWORD, getHttpParam(RequestConstants.PASSWORD));
  }

}
