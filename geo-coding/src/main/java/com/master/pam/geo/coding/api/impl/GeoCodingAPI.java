package com.master.pam.geo.coding.api.impl;

import com.google.code.geocoder.AdvancedGeoCoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import com.master.pam.geo.coding.api.IGeoCodingAPI;
import master.pam.crosscutting.dto.api.IAddressDto;
import master.pam.crosscutting.dto.impl.AddressDto;
import master.pam.crosscutting.geo.GeoPoint;

import java.math.BigDecimal;
import java.util.List;

public class GeoCodingAPI implements IGeoCodingAPI {

  public IAddressDto getAddress(BigDecimal aLatitude, BigDecimal aLongitude) {
    return getAddress(new GeoPoint(aLatitude, aLongitude));
  }

  public IAddressDto getAddress(GeoPoint aGeoPoint) {

    final AdvancedGeoCoder geocoder = new AdvancedGeoCoder();

    GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLocation(
            new LatLng(aGeoPoint.getLatitude(), aGeoPoint.getLongitude()))
            .setLanguage("en")
            .getGeocoderRequest();

    GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);

    List<GeocoderResult> results = geocoderResponse.getResults();

    if (results.size() > 0) {
      GeocoderResult firstResult = results.get(0);

      AddressDto resultAddressDto = new AddressDto(firstResult.getFormattedAddress());

      List<GeocoderAddressComponent> addressComponents = firstResult.getAddressComponents();

      for (GeocoderAddressComponent comp : addressComponents) {
        List<String> types = comp.getTypes();

        for (String type : types) {
          GeocoderResultType geoCoderType = GeocoderResultType.fromValue(type);
          if (geoCoderType.equals(GeocoderResultType.COUNTRY))
            resultAddressDto.setCountry(comp.getLongName());
          else if (geoCoderType.equals(GeocoderResultType.LOCALITY))
            resultAddressDto.setCity(comp.getLongName());
        }
      }

      return resultAddressDto;
    } else
      return null;
  }
}
