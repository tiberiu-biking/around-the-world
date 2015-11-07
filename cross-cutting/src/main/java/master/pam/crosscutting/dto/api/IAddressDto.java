package master.pam.crosscutting.dto.api;

public interface IAddressDto {

    String getFormattedAddress();

    String getCountry();

    String getCity();

    String getShortAddress();
}
