package master.pam.server.impl.response.impl.marker;

import master.pam.crosscutting.spring.SpringContext;
import master.pam.crud.api.dao.IMarkerDao;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.server.api.response.ResponseConstants;
import master.pam.server.impl.exceptions.RequestException;
import master.pam.server.impl.response.base.AbstractResponse;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;

public class DeleteMarkersResponse extends AbstractResponse {

  private IMarkerDao markerDao = SpringContext.getBean(IMarkerDao.class);

  public DeleteMarkersResponse(IServerRequest aRequest) {
    super(aRequest);
  }

  @Override
  public void doRequest() throws RequestException {
    markerDao.delete(getRequest().getLong(RequestConstants.MARKER_ID));
  }

  @Override
  public void buildResponseEnvelope(IResponseEnvelope aResponseEnvelope) {
    aResponseEnvelope.addData(ResponseConstants.MESSAGE, "Delete successful!");
  }

}
