package master.pam.world.servlet.impl.timeline;

import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "GetTimelineServlet", urlPatterns = "/GetTimelineServlet")
public class GetTimelineServlet extends AbstractServerRequestServlet {

    private static final long serialVersionUID = 3864994239541922490L;

    @Override
    protected ServerActionsEnum getServerAction() {
        return ServerActionsEnum.GET_TIMELINE;
    }

    @Override
    protected void buildServerRequest(IServerRequest aServerRequest) {
        String userId = getHttpParam(RequestConstants.USER_ID);

        aServerRequest.addLong(RequestConstants.USER_ID, Long.parseLong(userId));

    }

}
