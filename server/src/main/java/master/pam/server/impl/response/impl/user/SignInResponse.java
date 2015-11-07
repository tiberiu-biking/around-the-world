package master.pam.server.impl.response.impl.user;

import com.master.pam.encrypt.api.IEncryptApi;
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

public class SignInResponse extends AbstractResponse {

    private final Logger log = LoggerFactory.getLogger(SignInResponse.class);

    private IUserDto user;

    private IUserDao userDao = SpringContext.getBean(IUserDao.class);

    private IEncryptApi encryptApi = SpringContext.getBean(IEncryptApi.class);

    public SignInResponse(IServerRequest aRequest) {
        super(aRequest);
    }

    @Override
    public void doRequest() throws RequestException {
        String username = getRequest().getString(RequestConstants.USERNAME);
        String password = getRequest().getString(RequestConstants.PASSWORD);
        String hashedPass = encryptApi.hash(password);

        log.debug("Try to login user: " + username + "/" + password + "/" + hashedPass);

        user = userDao.getUser(username, hashedPass);

        log.debug("User found: " + user);
    }

    @Override
    public void buildResponseEnvelope(IResponseEnvelope aEnvelope) {
        if (user == null)
            aEnvelope.setErrors("User/password incorrect!");
        else {
            aEnvelope.addData(ResponseConstants.USER_ID, user.getId());
            aEnvelope.addData(ResponseConstants.FIRST_NAME, user.getFirstName());
            aEnvelope.addData(ResponseConstants.DROPBOX_TOKEN, user.getDropboxToken());
            aEnvelope.addData(ResponseConstants.FOURSQUARE_TOKEN, user.getFoursquareToken());
        }
    }

}
