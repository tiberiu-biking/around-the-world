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

public class CreateUserResponse extends AbstractResponse {

  private IUserDao userDao = SpringContext.getBean(IUserDao.class);
  private IUserDto newUser;

  public CreateUserResponse(IServerRequest aRequest) {
    super(aRequest);
  }

  @Override
  public void doRequest() throws RequestException {
    IUserDto userDto = getRequest().getDto(IUserDto.class);
    IUserDto existingUser = userDao.getUser(userDto.getEmail());
    if (existingUser == null)
      newUser = userDao.insertUser(userDto, getRequest().getString(RequestConstants.PASSWORD));
  }

  @Override
  public void buildResponseEnvelope(IResponseEnvelope aResponseEnvelope) {
    if (newUser != null)
      aResponseEnvelope.addData(ResponseConstants.USER_ID, newUser.getId());
    else
      aResponseEnvelope.setErrors("Someone already has registered with this email");

  }

}
