package fr.vinted.shipment.dao;

import java.math.BigDecimal;

public class ShippingPriceDaoStub implements ShippingPriceDao {

  private BigDecimal shippingPrice;

  private BigDecimal lowestShippingPrice;

  public ShippingPriceDaoStub() {

  }

  public ShippingPriceDaoStub(BigDecimal shippingPrice, BigDecimal lowestShippingPrice) {
    this.shippingPrice = shippingPrice;
    this.lowestShippingPrice = lowestShippingPrice;
  }

  @Override
  public BigDecimal getShippingPrice(String packageSize, String carrierCode) {
    return getShippingPrice();
  }

  @Override
  public BigDecimal getLowestShippingPrice(String packageSize) {
    return getLowestShippingPrice();
  }

  public BigDecimal getShippingPrice() {
    return shippingPrice;
  }

  public void setShippingPrice(BigDecimal shippingPrice) {
    this.shippingPrice = shippingPrice;
  }

  public BigDecimal getLowestShippingPrice() {
    return lowestShippingPrice;
  }

  public void setLowestShippingPrice(BigDecimal lowestShippingPrice) {
    this.lowestShippingPrice = lowestShippingPrice;
  }
}
