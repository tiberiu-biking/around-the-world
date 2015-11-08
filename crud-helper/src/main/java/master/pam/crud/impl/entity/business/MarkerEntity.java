package master.pam.crud.impl.entity.business;

import master.pam.crosscutting.dto.api.IMarkerDto;
import master.pam.crud.impl.entity.base.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "MARKERS")
public class MarkerEntity extends IdEntity implements IMarkerDto {

  private BigDecimal latitude;

  private BigDecimal longitude;

  private String name;

  @Temporal(value = TemporalType.DATE)
  private Date date;

  private String note;

  private Long userId;

  private String externalId;

  private Integer rating;

  @Override
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long aUserId) {
    this.userId = aUserId;
  }

  @Override
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Override
  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal aLongitude) {
    this.longitude = aLongitude;
  }

  @Override
  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal aLatitude) {
    this.latitude = aLatitude;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String aName) {
    name = aName;
  }

  @Override
  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String aExternalId) {
    externalId = aExternalId;
  }

  @Override
  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer aRating) {
    rating = aRating;
  }
}
