package master.pam.server.impl.response.impl.marker;

import master.pam.crosscutting.date.DateFormatUtil;
import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crosscutting.dto.api.ITimelineItemDto;
import master.pam.crosscutting.dto.impl.MarkerDto;
import master.pam.crosscutting.dto.timeline.TimelineItemDto;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.crud.api.dao.IMarkerDao;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.server.api.response.ResponseConstants;
import master.pam.server.impl.exceptions.RequestException;
import master.pam.server.impl.response.base.AbstractResponse;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetTimelineResponse extends AbstractResponse {

    private final Logger logger = LoggerFactory.getLogger(GetMarkersResponse.class);
    private List<IMarkerDto> markers;

    private IMarkerDao markerDao = SpringContext.getBean(IMarkerDao.class);
    private List<ITimelineItemDto> timeline;

    public GetTimelineResponse(IServerRequest aRequest) {
        super(aRequest);
    }

    @Override
    public void doRequest() throws RequestException {
        Long userId = getRequest().getLong(RequestConstants.USER_ID);
        logger.debug("get markers for user = " + userId);

        markers = markerDao.getMarkers(userId, null);

        BeanComparator reverseDateComparator = new BeanComparator("date", new ReverseComparator(new ComparableComparator()));

        Collections.sort(markers, reverseDateComparator);

        Integer currentYear = null;
        TimelineItemDto currentTimelineItem = null;

        timeline = new ArrayList<ITimelineItemDto>();

        for (IMarkerDto marker : markers) {
            Integer year = DateFormatUtil.getYear(marker.getDate());

            if (!year.equals(currentYear)) {
                currentYear = year;
                currentTimelineItem = new TimelineItemDto(currentYear);
                timeline.add(currentTimelineItem);
            }
            currentTimelineItem.addMarker(new MarkerDto(marker));
        }
    }

    @Override
    public void buildResponseEnvelope(IResponseEnvelope aEnvelope) {
        aEnvelope.addData(ResponseConstants.TIMELINE, timeline);
    }
}
