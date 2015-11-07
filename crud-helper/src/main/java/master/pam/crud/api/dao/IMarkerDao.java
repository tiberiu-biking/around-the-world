package master.pam.crud.api.dao;

import master.pam.crosscutting.dto.api.IMarkerDto;

import java.util.List;

public interface IMarkerDao {

    IMarkerDto update(IMarkerDto aDto);

    void delete(long aId);

    IMarkerDto create(IMarkerDto aDto);

    List<IMarkerDto> getMarkers(Long aUserId, Long aMarkerId);

    IMarkerDto getByExternalId(String aExternalId, Long aUserId);

}
