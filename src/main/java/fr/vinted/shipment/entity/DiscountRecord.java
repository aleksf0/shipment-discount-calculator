package fr.vinted.shipment.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DiscountRecord {

  private LocalDate transactionDate;
  private String packageSize;
  private String carrierCode;
  private BigDecimal shippingPrice;
  private BigDecimal discount;
  private boolean isValid;
  private String originalRecordString;

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public String getPackageSize() {
    return packageSize;
  }

  public void setPackageSize(String packageSize) {
    this.packageSize = packageSize;
  }

  public String getCarrierCode() {
    return carrierCode;
  }

  public void setCarrierCode(String carrierCode) {
    this.carrierCode = carrierCode;
  }

  public BigDecimal getShippingPrice() {
    return shippingPrice;
  }

  public void setShippingPrice(BigDecimal shippingPrice) {
    this.shippingPrice = shippingPrice;
  }

  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  public String getOriginalRecordString() {
    return originalRecordString;
  }

  public void setOriginalRecordString(String originalRecordString) {
    this.originalRecordString = originalRecordString;
  }
}
