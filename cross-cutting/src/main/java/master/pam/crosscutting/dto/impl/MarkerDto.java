package master.pam.crosscutting.dto.impl;

import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crosscutting.dto.base.IdDto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class MarkerDto extends IdDto implements IMarkerDto {

    private String name;
    private Date date;
    private String note;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private long userId;
    private String externalId;
    private Integer rating;

    public MarkerDto(BigDecimal aLatitude, BigDecimal aLongitude) {
        setLatitude(aLatitude);
        setLongitude(aLongitude);
    }

    public MarkerDto(double aLatitude, double aLongitude) {
        setLatitude(BigDecimal.valueOf(aLatitude));
        setLongitude(BigDecimal.valueOf(aLongitude));
    }

    public MarkerDto(BigDecimal aLatitude, BigDecimal aLongitude, Date aDate, String aExternalId) {
        this(aLatitude, aLongitude);
        setExternalId(aExternalId);
        setDate(aDate);
    }

    public MarkerDto(double aLatitude, double aLongitude, Date aDate, String aExternalId) {
        this(aLatitude, aLongitude);
        setExternalId(aExternalId);
        setDate(aDate);
    }

    public MarkerDto(IMarkerDto aMarkerDto) {
        setName(aMarkerDto.getName());
        setDate(aMarkerDto.getDate());
        setExternalId(aMarkerDto.getExternalId());
        setId(aMarkerDto.getId());
        setLatitude(aMarkerDto.getLatitude());
        setLongitude(aMarkerDto.getLongitude());
        setNote(aMarkerDto.getNote());
        setRating(aMarkerDto.getRating());
        setUserId(aMarkerDto.getUserId());
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    @Override
    public Date getDate() {
        if (date == null)
            return Calendar.getInstance().getTime();
        return date;
    }

    public void setDate(Date aDate) {
        date = aDate;
    }

    @Override
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal aLatitude) {
        latitude = aLatitude;
    }

    @Override
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal aLongitude) {
        longitude = aLongitude;
    }

    @Override
    public String getNote() {
        return note;
    }

    public void setNote(String aNote) {
        note = aNote;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long aUserId) {
        userId = aUserId;
    }

    public void setExternalId(String aExternalId) {
        externalId = aExternalId;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer aRating) {
        rating = aRating;
    }

}
