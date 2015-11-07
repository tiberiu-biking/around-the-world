package master.pam.crosscutting.dto.api;

import master.pam.crosscutting.dto.impl.MarkerDto;

import java.util.List;

public interface ITimelineItemDto {

    Integer getYear();

    List<MarkerDto> getMarkers();

}
