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

public class GetUserByFoursquareIdResponse extends AbstractResponse {

    private final Logger logger = LoggerFactory.getLogger(GetUserByFoursquareIdResponse.class);

    private IUserDto user;

    private IUserDao userDao = SpringContext.getBean(IUserDao.class);

    public GetUserByFoursquareIdResponse(IServerRequest aRequest) {
        super(aRequest);
    }

    @Override
    public void doRequest() throws RequestException {
        String userFoursquareId = getRequest().getString(RequestConstants.USER_FOURSQUARE_ID);
        logger.debug("Get infor for user with foursquare id: " + userFoursquareId);

        user = userDao.getUserByFoursquareId(userFoursquareId);
        logger.debug("User found: " + user);
    }

    @Override
    public void buildResponseEnvelope(IResponseEnvelope aEnvelope) {
        aEnvelope.addData(ResponseConstants.USER, user);
    }

}
