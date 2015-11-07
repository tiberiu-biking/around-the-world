package master.pam.crosscutting.dto.api;

import master.pam.crosscutting.dto.base.IIdDto;

public interface IUserDto extends IIdDto {

    String getFirstName();

    String getLastName();

    String getCountry();

    String getEmail();

    String getFoursquareToken();

    String getDropboxToken();

    void setFoursquareToken(String aToken);

    void setDropboxToken(String aToken);

    void setFoursquareId(String aUserId);

    String getFoursquareId();

}
