package master.pam.server.impl.response.impl.user;

import master.pam.crosscutting.dto.api.IUserDto;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.crud.api.dao.IPasswordDao;
import master.pam.crud.api.dao.IUserDao;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.server.api.response.ResponseConstants;
import master.pam.server.impl.exceptions.RequestException;
import master.pam.server.impl.response.base.AbstractResponse;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;

public class UpdateUserResponse extends AbstractResponse {

  private IUserDao userDao = SpringContext.getBean(IUserDao.class);
  private IPasswordDao passwordDao = SpringContext.getBean(IPasswordDao.class);

  private IUserDto updatedUser;

  public UpdateUserResponse(IServerRequest aRequest) {
    super(aRequest);
  }

  @Override
  public void doRequest() throws RequestException {
    try {
      String password = getRequest().getString(RequestConstants.PASSWORD);
      updatedUser = userDao.update(getRequest().getDto(IUserDto.class));
      if (!StringUtils.isEmpty(password))
        passwordDao.update(password, updatedUser.getId());

    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RequestException(e.getLocalizedMessage());
    }
  }

  @Override
  public void buildResponseEnvelope(IResponseEnvelope aResponseEnvelope) {
    aResponseEnvelope.addData(ResponseConstants.MESSAGE, "Update successful");
    aResponseEnvelope.addData(ResponseConstants.FIRST_NAME, updatedUser.getFirstName());
  }

}
