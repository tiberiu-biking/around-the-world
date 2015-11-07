package master.pam.world.servlet.impl.user;

import master.pam.crosscutting.dto.impl.UserDto;
import master.pam.crosscutting.gson.GsonHelper;
import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "UpdateUserServlet", urlPatterns = "/UpdateUserServlet")
public class UpdateUserServlet extends AbstractServerRequestServlet {
    private static final long serialVersionUID = 7735454047270919067L;

    @Override
    protected ServerActionsEnum getServerAction() {
        return ServerActionsEnum.UPDATE_USER;
    }

    @Override
    protected void buildServerRequest(IServerRequest aServerRequest) {
        UserDto userDto = GsonHelper.fromGson(getHttpParam(RequestConstants.USER), UserDto.class);
        aServerRequest.addField(RequestConstants.DTO, userDto);
        aServerRequest.addField(RequestConstants.PASSWORD, getHttpParam(RequestConstants.PASSWORD));
    }

}
