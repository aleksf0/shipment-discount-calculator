package fr.vinted.shipment.entity;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShippingPriceRecord {

  private static final Pattern shippingPriceRecordPattern = Pattern.compile("^([A-Z]+) ([A-Z]+) (\\d+(\\.\\d{1,2})?){1}$");

  private String packageSize;
  private String carrierCode;
  private BigDecimal shippingPrice;

  public ShippingPriceRecord(String priceMapKeyString) {
    parse(priceMapKeyString);
  }

  private void parse(String priceMapKeyString) {
    Matcher m = shippingPriceRecordPattern.matcher(priceMapKeyString);
    m.find();
    carrierCode = m.group(1);
    packageSize = m.group(2);
    shippingPrice = new BigDecimal(m.group(3));
  }

  public String getPackageSize() {
    return packageSize;
  }

  public String getCarrierCode() {
    return carrierCode;
  }

  public BigDecimal getShippingPrice() {
    return shippingPrice;
  }
}
