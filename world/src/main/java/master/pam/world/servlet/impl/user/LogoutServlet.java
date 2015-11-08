package master.pam.world.servlet.impl.user;

import master.pam.crosscutting.notifications.NotificationPool;
import master.pam.server.api.request.RequestConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {

  private static final long serialVersionUID = -3798279328944200873L;

  protected final Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

  @Override
  protected void doPost(HttpServletRequest aReq, HttpServletResponse aResp) throws ServletException, IOException {
    logger.debug("Logout of user " + aReq.getParameter(RequestConstants.USER_ID));

    NotificationPool.getPool().setPoolOpen(false);
  }

}
