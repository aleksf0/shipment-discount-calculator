package fr.vinted.shipment.dao;

import java.math.BigDecimal;

public interface ShippingPriceDao {

  BigDecimal getShippingPrice(String packageSize, String carrierCode);

  BigDecimal getLowestShippingPrice(String packageSize);
}
