package com.master.pam.dropbox.api;

import com.dropbox.core.DbxException;
import master.pam.crosscutting.dto.api.IMarkerDto;

import java.util.List;

public interface IDropboxSource {

    List<IMarkerDto> getMarkers(String aToken, List<String> aFiles, long aUserId) throws DbxException;

}
