package com.master.pam.dropbox.api.impl;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxRequestUtil;
import com.master.pam.dropbox.MetadataRequestHandler;
import com.master.pam.dropbox.api.IDropboxSource;
import com.master.pam.dropbox.gson.MediaInfoMetadata;
import com.master.pam.geo.coding.api.IGeoCodingAPI;
import master.pam.crosscutting.date.DateFormatUtil;
import master.pam.crosscutting.dto.api.IAddressDto;
import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crosscutting.dto.impl.MarkerDto;
import master.pam.crosscutting.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DropboxSource implements IDropboxSource {

    private final static Logger logger = LoggerFactory.getLogger(DropboxSource.class);

    private IGeoCodingAPI geoCodingAPI = SpringContext.getBean(IGeoCodingAPI.class);

    @Override
    public List<IMarkerDto> getMarkers(String aToken, List<String> aFiles, long aUserId) throws DbxException {

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());

        DbxClient client = new DbxClient(config, aToken);

        logger.debug("Linked account: " + client.getAccountInfo().displayName);

        List<IMarkerDto> resultList = new ArrayList<IMarkerDto>();

        for (String dropboxFile : aFiles) {
            MediaInfoMetadata metadata = getMediaMetadata(client, dropboxFile);

            if (metadata.getPhoto_info() != null) {
                List<Double> latLongList = metadata.getPhoto_info().getLat_long();

                Date dropboxDate = DateFormatUtil.parseDropboxDate(metadata.getPhoto_info().getTime_taken());

                MarkerDto markerDto = new MarkerDto(latLongList.get(0), latLongList.get(1), Calendar.getInstance().getTime(), dropboxFile);
                IAddressDto address = geoCodingAPI.getAddress(markerDto.getLatitude(), markerDto.getLongitude());
                markerDto.setName(address.getShortAddress());
                markerDto.setUserId(aUserId);
                markerDto.setNote("Imported from Dropbox picture");
                markerDto.setDate(dropboxDate);
                resultList.add(markerDto);
            }
        }

        return resultList;
    }

    private MediaInfoMetadata getMediaMetadata(DbxClient aClient, String aDropboxFile) throws DbxException {
        String apiPath = "1/metadata/dropbox/" + aDropboxFile;

        String[] params = {"list", "true", "file_limit", "25000", "include_media_info", "true"};
        MetadataRequestHandler handler = new MetadataRequestHandler();
        DbxRequestUtil.doGet(aClient.getRequestConfig(), aClient.getAccessToken(), "api.dropbox.com", apiPath, params, null, handler);
        return handler.getMetadata();
    }
}
