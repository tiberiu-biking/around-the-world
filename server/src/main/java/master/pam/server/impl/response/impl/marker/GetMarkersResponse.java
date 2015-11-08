package master.pam.server.impl.response.impl.marker;

import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.crud.api.dao.IMarkerDao;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.server.api.response.ResponseConstants;
import master.pam.server.impl.exceptions.RequestException;
import master.pam.server.impl.response.base.AbstractResponse;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import master.pam.server.impl.util.ServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GetMarkersResponse extends AbstractResponse {

  private final Logger logger = LoggerFactory.getLogger(GetMarkersResponse.class);
  private List<IMarkerDto> markers;

  private IMarkerDao markerDao = SpringContext.getBean(IMarkerDao.class);

  public GetMarkersResponse(IServerRequest aRequest) {
    super(aRequest);
  }

  @Override
  public void doRequest() throws RequestException {

    Long userId = getRequest().getLong(RequestConstants.USER_ID);
    Long markerId = getRequest().getLong(RequestConstants.MARKER_ID);

    logger.debug("get markers for user = " + userId + ", marker = " + markerId);

    markers = markerDao.getMarkers(userId, markerId);
  }

  @Override
  public void buildResponseEnvelope(IResponseEnvelope aEnvelope) {
    aEnvelope.addData(ResponseConstants.MARKERS, markers).addData(ResponseConstants.CENTER_POINT,
            ServerUtil.calculateCenterPoint(markers));
  }
}
