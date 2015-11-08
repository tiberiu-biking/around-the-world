package master.pam.server.impl.response.impl.user;

import master.pam.crosscutting.dto.api.IUserDto;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.crud.api.dao.IUserDao;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.server.api.response.ResponseConstants;
import master.pam.server.impl.exceptions.RequestException;
import master.pam.server.impl.response.base.AbstractResponse;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetUserResponse extends AbstractResponse {

  private final Logger logger = LoggerFactory.getLogger(GetUserResponse.class);

  private IUserDto user;

  private IUserDao userDao = SpringContext.getBean(IUserDao.class);

  public GetUserResponse(IServerRequest aRequest) {
    super(aRequest);
  }

  @Override
  public void doRequest() throws RequestException {
    long userId = getRequest().getLong(RequestConstants.USER_ID);
    logger.debug("Get infor for user: " + userId);

    user = userDao.getUser(userId);
    logger.debug("User found: " + user);
  }

  @Override
  public void buildResponseEnvelope(IResponseEnvelope aEnvelope) {
    aEnvelope.addData(ResponseConstants.USER, user);
  }

}
