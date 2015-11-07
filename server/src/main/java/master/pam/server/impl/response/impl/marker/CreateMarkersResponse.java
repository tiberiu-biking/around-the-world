package master.pam.server.impl.response.impl.marker;

import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.crud.api.dao.IMarkerDao;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.server.api.response.ResponseConstants;
import master.pam.server.api.response.ResponseType;
import master.pam.server.impl.exceptions.RequestException;
import master.pam.server.impl.response.base.AbstractResponse;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import master.pam.server.impl.util.ServerUtil;

import java.util.ArrayList;
import java.util.List;

public class CreateMarkersResponse extends AbstractResponse {

    private IMarkerDao markerDao = SpringContext.getBean(IMarkerDao.class);
    private List<IMarkerDto> markersList;
    private int newMarkers;

    public CreateMarkersResponse(IServerRequest aRequest) {
        super(aRequest);
    }

    @Override
    public void doRequest() throws RequestException {

        List<IMarkerDto> dtoList = getRequest().getDtoList(IMarkerDto.class);
        if (dtoList == null) {
            dtoList = new ArrayList<IMarkerDto>();
            IMarkerDto dto = getRequest().getDto(IMarkerDto.class);
            if (dto != null)
                dtoList.add(dto);
        }

        markersList = new ArrayList<IMarkerDto>();

        for (IMarkerDto dto : dtoList) {
            IMarkerDto existingDto = markerDao.getByExternalId(dto.getExternalId(), dto.getUserId());
            if (existingDto == null)
                markersList.add(markerDao.create(dto));
        }

        newMarkers = markersList.size();

        ResponseType responseType = getRequest().get(RequestConstants.RETURN_TYPE, ResponseType.class);
        Long userId = getRequest().get(RequestConstants.USER_ID, Long.class);
        if (responseType == ResponseType.RETURN_ALL)
            markersList = markerDao.getMarkers(userId, null);
    }

    @Override
    public void buildResponseEnvelope(IResponseEnvelope aResponseEnvelope) {
        aResponseEnvelope.addData(ResponseConstants.MARKERS, markersList).addData(ResponseConstants.CENTER_POINT,
                ServerUtil.calculateCenterPoint(markersList))
                .addDataMessage(newMarkers + " new marker(s) added.");
    }
}
