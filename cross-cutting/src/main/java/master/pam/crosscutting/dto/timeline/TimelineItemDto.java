package master.pam.crosscutting.dto.timeline;

import master.pam.crosscutting.dto.api.ITimelineItemDto;
import master.pam.crosscutting.dto.impl.MarkerDto;

import java.util.ArrayList;
import java.util.List;

public class TimelineItemDto implements ITimelineItemDto {

    private Integer year;

    private List<MarkerDto> markers = new ArrayList<MarkerDto>();


    public TimelineItemDto(Integer aYear) {
        setYear(aYear);
    }

    public void addMarkers(List<MarkerDto> aMarkers) {
        getMarkers().addAll(aMarkers);
    }

    @Override
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public List<MarkerDto> getMarkers() {
        return markers;
    }

    public void setMarkers(List<MarkerDto> aMarkers) {
        this.markers = aMarkers;
    }

    public void addMarker(MarkerDto aMarkerDto) {
        getMarkers().add(aMarkerDto);
    }

}
