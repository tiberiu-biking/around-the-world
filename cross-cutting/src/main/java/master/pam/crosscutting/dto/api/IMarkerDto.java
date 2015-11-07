package master.pam.crosscutting.dto.api;

import master.pam.crosscutting.dto.base.IIdDto;

import java.math.BigDecimal;
import java.util.Date;

public interface IMarkerDto extends IIdDto {

    Date getDate();

    String getNote();

    BigDecimal getLongitude();

    BigDecimal getLatitude();

    String getName();

    Long getUserId();

    String getExternalId();

    Integer getRating();

}
