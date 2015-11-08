package com.master.pam.geo.coding.api;

import master.pam.crosscutting.dto.api.IAddressDto;
import master.pam.crosscutting.geo.GeoPoint;

import java.math.BigDecimal;

public interface IGeoCodingAPI {
  IAddressDto getAddress(BigDecimal aLatitude, BigDecimal aLongitude);

  IAddressDto getAddress(GeoPoint aGeoPoint);
}
