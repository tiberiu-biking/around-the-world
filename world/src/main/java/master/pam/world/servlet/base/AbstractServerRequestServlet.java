package master.pam.world.servlet.base;

import master.pam.crosscutting.gson.GsonHelper;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.server.IServer;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import master.pam.world.gson.PasswordExclusionStrategy;
import master.pam.world.gson.RelationsExclusionStrategy;
import master.pam.world.servlet.exception.WrongRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractServerRequestServlet extends HttpServlet {

  private static final long serialVersionUID = 5518741746920834843L;

  abstract protected ServerActionsEnum getServerAction();

  abstract protected void buildServerRequest(IServerRequest aServerRequest) throws WrongRequestException;

  protected final Logger logger = LoggerFactory.getLogger(AbstractServerRequestServlet.class);

  protected final String JSON_RESPONSE = "application/json";

  private IServerRequest serverRequest;

  private HttpServletRequest httpRequest;

  private IServer serverIf;

  @Override
  public void init() throws ServletException {
    super.init();
    serverIf = SpringContext.getBean(IServer.class);
  }

  @Override
  public void destroy() {
    super.destroy();
  }

  @Override
  public void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
    doPost(aRequest, aResponse);
  }

  @Override
  public void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {

    logger.debug("doPost " + getClass().getCanonicalName());

    httpRequest = aRequest;

    doRequest(aResponse);
  }

  protected void doRequest(HttpServletResponse aResponse) throws ServletException, java.io.IOException {

    serverRequest = serverIf.createRequest();

    logger.debug("Server action:  " + getServerAction());

    serverRequest.setAction(getServerAction());

    IResponseEnvelope resultObj;
    try {
      buildServerRequest(serverRequest);
      resultObj = serverIf.sendRequest(serverRequest);
    } catch (WrongRequestException e) {
      resultObj = e.getErrorEnvelope();
    }

    String resultJson = buildResult(resultObj);

    logger.debug("Sending json to server: " + resultJson);
    aResponse.setContentType(JSON_RESPONSE);
    aResponse.setCharacterEncoding("utf-8");
    aResponse.setStatus(HttpServletResponse.SC_OK);

    PrintWriter out = aResponse.getWriter();
    out.println(resultJson);
  }

  protected String getHttpParam(String aParamName) {
    return httpRequest.getParameter(aParamName);
  }

  protected Collection<Part> getHttpRequestParts() {
    try {
      return httpRequest.getParts();
    } catch (IllegalStateException | IOException | ServletException e) {
      // TODO
      e.printStackTrace();
      logger.error("The http request has no parts!", e);
      return new ArrayList<Part>();
    }
  }

  protected String buildResult(IResponseEnvelope aResponseEnvelope) {

    logger.debug("Build result. Server result: " + aResponseEnvelope);

    String gsonString = buildGson(aResponseEnvelope);

    return gsonString;
  }

  protected String buildGson(IResponseEnvelope aResponseEnvelope) {
    return GsonHelper.toGson(aResponseEnvelope, new RelationsExclusionStrategy(), new PasswordExclusionStrategy());
  }

  public IServer getServer() {
    return serverIf;
  }
}
