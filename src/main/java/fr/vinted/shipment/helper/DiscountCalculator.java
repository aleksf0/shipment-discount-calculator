package fr.vinted.shipment.helper;

import java.math.BigDecimal;
import java.util.Map;
import fr.vinted.shipment.dao.ShippingPriceDao;
import fr.vinted.shipment.entity.PackageSizeAndCarrierCodeKey;
import fr.vinted.shipment.entity.TransactionRecord;
import fr.vinted.shipment.entity.TransactionRecordsProcessingState;

public class DiscountCalculator {

  public static final BigDecimal DISCOUNT_LIMIT = new BigDecimal("10");

  private static final String SHIPPING_PACKAGE_SMALL = "S";
  private static final String SHIPPING_PACKAGE_LARGE = "L";
  private static final String CARRIER_LA_POSTE = "LP";

  private ShippingPriceDao shippingPriceDao;

  public DiscountCalculator(ShippingPriceDao shippingPriceDao) {
    this.shippingPriceDao = shippingPriceDao;
  }

  public BigDecimal calculate(TransactionRecord transactionRecord, TransactionRecordsProcessingState transactionRecordsProcessingState) {
    resetProcessingStateIfNeeded(transactionRecord, transactionRecordsProcessingState);
    BigDecimal plainDiscount = calculatePlainDiscount(transactionRecord, transactionRecordsProcessingState);
    BigDecimal discount = applyDiscountLimit(transactionRecordsProcessingState, plainDiscount);
    updateProcessingState(transactionRecord, transactionRecordsProcessingState, discount);
    return discount;
  }

  private void resetProcessingStateIfNeeded(TransactionRecord transactionRecord, TransactionRecordsProcessingState transactionRecordsProcessingState) {
    if (transactionRecord.getTransactionDate().getMonth() != transactionRecordsProcessingState.getPreviousMonth()) {
      transactionRecordsProcessingState.reset();
      transactionRecordsProcessingState.setPreviousMonth(transactionRecord.getTransactionDate().getMonth());
    }
  }

  private BigDecimal calculatePlainDiscount(TransactionRecord transactionRecord, TransactionRecordsProcessingState transactionRecordsProcessingState) {

    BigDecimal plainDiscount = BigDecimal.ZERO;

    BigDecimal shippingPrice = shippingPriceDao.getShippingPrice(transactionRecord.getPackageSize(), transactionRecord.getCarrierCode());

    if (transactionRecord.getPackageSize().equals(SHIPPING_PACKAGE_SMALL)) {
      plainDiscount = shippingPrice.subtract(shippingPriceDao.getLowestShippingPrice(transactionRecord.getPackageSize()));
    }

    PackageSizeAndCarrierCodeKey packageSizeAndCarrierCodeKey = new PackageSizeAndCarrierCodeKey(transactionRecord.getPackageSize(),
        transactionRecord.getCarrierCode());
    if (transactionRecord.getPackageSize().equals(SHIPPING_PACKAGE_LARGE) && transactionRecord.getCarrierCode().equals(CARRIER_LA_POSTE)
        && transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap().get(packageSizeAndCarrierCodeKey) != null
        && transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap().get(packageSizeAndCarrierCodeKey) == 2) {
      plainDiscount = shippingPriceDao.getShippingPrice(transactionRecord.getPackageSize(), transactionRecord.getCarrierCode());
    }

    return plainDiscount;
  }

  private BigDecimal applyDiscountLimit(TransactionRecordsProcessingState transactionRecordsProcessingState, BigDecimal plainDiscount) {
    BigDecimal discountExcess = DISCOUNT_LIMIT.subtract(transactionRecordsProcessingState.getDiscountAmountSum()).subtract(plainDiscount);
    return discountExcess.compareTo(BigDecimal.ZERO) < 0 ? plainDiscount.subtract(discountExcess.abs()) : plainDiscount;
  }

  private void updateProcessingState(TransactionRecord transactionRecord, TransactionRecordsProcessingState transactionRecordsProcessingState,
      BigDecimal discount) {

    PackageSizeAndCarrierCodeKey packageSizeAndCarrierCodeKey = new PackageSizeAndCarrierCodeKey(transactionRecord.getPackageSize(),
        transactionRecord.getCarrierCode());

    Map<PackageSizeAndCarrierCodeKey, Integer> shipmentCounterBySizeAndCarrierMap = transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap();
    int packageSizeCounter = shipmentCounterBySizeAndCarrierMap.get(packageSizeAndCarrierCodeKey) == null ? 1
        : shipmentCounterBySizeAndCarrierMap.get(packageSizeAndCarrierCodeKey) + 1;

    shipmentCounterBySizeAndCarrierMap.put(packageSizeAndCarrierCodeKey, packageSizeCounter);
    transactionRecordsProcessingState.setDiscountAmountSum(transactionRecordsProcessingState.getDiscountAmountSum().add(discount));
  }
}
