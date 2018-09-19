package fr.vinted.shipment.entity;

import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class TransactionRecordsProcessingState {

  private Map<PackageSizeAndCarrierCodeKey, Integer> shipmentCounterBySizeAndCarrierMap;
  private BigDecimal discountAmountSum;
  private Month previousMonth;

  public TransactionRecordsProcessingState() {
    reset();
  }

  public void reset() {
    shipmentCounterBySizeAndCarrierMap = new HashMap<>();
    discountAmountSum = BigDecimal.ZERO;
    previousMonth = null;
  }

  public Map<PackageSizeAndCarrierCodeKey, Integer> getShipmentCounterBySizeAndCarrierMap() {
    return shipmentCounterBySizeAndCarrierMap;
  }

  public void setShipmentCounterBySizeAndCarrierMap(Map<PackageSizeAndCarrierCodeKey, Integer> shipmentCounterBySizeAndCarrierMap) {
    this.shipmentCounterBySizeAndCarrierMap = shipmentCounterBySizeAndCarrierMap;
  }

  public BigDecimal getDiscountAmountSum() {
    return discountAmountSum;
  }

  public void setDiscountAmountSum(BigDecimal discountAmountSum) {
    this.discountAmountSum = discountAmountSum;
  }

  public Month getPreviousMonth() {
    return previousMonth;
  }

  public void setPreviousMonth(Month previousMonth) {
    this.previousMonth = previousMonth;
  }
}
