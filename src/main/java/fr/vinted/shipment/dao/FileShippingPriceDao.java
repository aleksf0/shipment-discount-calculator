package fr.vinted.shipment.dao;

import java.math.BigDecimal;
import fr.vinted.shipment.api.FileShippingPriceApi;
import fr.vinted.shipment.entity.PackageSizeAndCarrierCodeKey;

public class FileShippingPriceDao implements ShippingPriceDao {

  @Override
  public BigDecimal getShippingPrice(String packageSize, String carrierCode) {
    return FileShippingPriceApi.getInstance().getShippingPriceMap().get(new PackageSizeAndCarrierCodeKey(packageSize, carrierCode));
  }

  @Override
  public BigDecimal getLowestShippingPrice(String packageSize) {
    return FileShippingPriceApi.getInstance().getLowestPriceForSizeMap().get(packageSize);
  }
}
