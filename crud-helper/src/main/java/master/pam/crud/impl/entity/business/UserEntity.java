package master.pam.crud.impl.entity.business;

import master.pam.crosscutting.dto.api.IUserDto;
import master.pam.crud.impl.entity.base.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserEntity extends IdEntity implements IUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String dropboxToken;

    private String foursquareToken;

    private String foursquareId;

    public UserEntity() {
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String aLastName) {
        lastName = aLastName;
    }


    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        builder.append("UserEntity [firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", email=");
        builder.append(email);
        builder.append(", country=");
        builder.append(country);
        builder.append(", dropboxToken=");
        builder.append(dropboxToken);
        builder.append(", foursquareToken=");
        builder.append(foursquareToken);
        builder.append(", foursquareId=");
        builder.append(foursquareId);
        builder.append("]");
        return builder.toString();
    }

}
