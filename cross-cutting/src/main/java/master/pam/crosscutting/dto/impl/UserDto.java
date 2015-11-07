package master.pam.crosscutting.dto.impl;

import master.pam.crosscutting.dto.api.IUserDto;
import master.pam.crosscutting.dto.base.IdDto;

public class UserDto extends IdDto implements IUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String country;
    private String dropboxToken;

    private String foursquareToken;

    private String foursquareId;

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String aFirstName) {
        firstName = aFirstName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String aEmail) {
        email = aEmail;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String aLastName) {
        lastName = aLastName;
    }

    @Override
    public String getCountry() {
        return country;
    }

    public void setCountry(String aCountry) {
        country = aCountry;
    }

    @Override
    public String getDropboxToken() {
        return dropboxToken;
    }

    @Override
    public void setDropboxToken(String dropboxToken) {
        this.dropboxToken = dropboxToken;
    }

    @Override
    public String getFoursquareToken() {
        return foursquareToken;
    }

    @Override
    public void setFoursquareToken(String foursquareToken) {
        this.foursquareToken = foursquareToken;
    }

    @Override
    public void setFoursquareId(String aFoursquareId) {
        foursquareId = aFoursquareId;
    }

    @Override
    public String getFoursquareId() {
        return foursquareId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", email=");
        builder.append(email);
        builder.append(", country=");
        builder.append(country);
        builder.append(", id=");
        builder.append(getId());
        builder.append("]");
        return builder.toString();
    }

}
