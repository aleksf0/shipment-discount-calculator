package fr.vinted.shipment;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import fr.vinted.shipment.dao.FileShippingPriceDao;
import fr.vinted.shipment.dao.ShippingPriceDao;
import fr.vinted.shipment.entity.DiscountRecord;
import fr.vinted.shipment.entity.TransactionRecord;
import fr.vinted.shipment.entity.TransactionRecordsProcessingState;
import fr.vinted.shipment.helper.DiscountCalculator;
import fr.vinted.shipment.helper.FileUtils;

public class Discount {

  private ShippingPriceDao shippingPriceDao;
  private DiscountCalculator discountCalculator;

  public static void main(String[] args) throws IOException {

    Discount discount = new Discount();

    List<DiscountRecord> discountRecordList = discount.processTransactionsFileContents(FileUtils.readFileSystemFile(args[0]));

    for (DiscountRecord discountRecord : discountRecordList) {
      System.out.println(discount.formatDiscountRecord(discountRecord));
    }
  }

  public Discount() {
    this(new FileShippingPriceDao());
  }

  public Discount(ShippingPriceDao shippingPriceDao) {
    this.shippingPriceDao = shippingPriceDao;
    this.discountCalculator = new DiscountCalculator(shippingPriceDao);
  }

  public List<DiscountRecord> processTransactionsFileContents(List<String> fileLineList) throws IOException {

    List<DiscountRecord> discountRecordList = new ArrayList<>();
    TransactionRecordsProcessingState transactionRecordsProcessingState = new TransactionRecordsProcessingState();

    for (String line : fileLineList) {
      TransactionRecord transactionRecord = new TransactionRecord(line, shippingPriceDao);
      discountRecordList.add(processTransactionRecord(transactionRecord, transactionRecordsProcessingState));
    }

    return discountRecordList;
  }

  public String formatDiscountRecord(DiscountRecord discountRecord) {

    String discountRecordString;

    if (discountRecord.isValid()) {
      discountRecordString = String.format("%tF %s %s %s %s", discountRecord.getTransactionDate(), discountRecord.getPackageSize(),
          discountRecord.getCarrierCode(), discountRecord.getShippingPrice().setScale(2),
          discountRecord.getDiscount().compareTo(BigDecimal.ZERO) == 0 ? "-" : discountRecord.getDiscount().setScale(2));
    } else {
      discountRecordString = String.format("%s %s", discountRecord.getOriginalRecordString(), "Ignored");
    }

    return discountRecordString;
  }

  private DiscountRecord processTransactionRecord(TransactionRecord transactionRecord, TransactionRecordsProcessingState transactionRecordsProcessingState) {

    DiscountRecord discountRecord = new DiscountRecord();
    discountRecord.setTransactionDate(transactionRecord.getTransactionDate());
    discountRecord.setPackageSize(transactionRecord.getPackageSize());
    discountRecord.setCarrierCode(transactionRecord.getCarrierCode());

    if (transactionRecord.isValid()) {
      BigDecimal shippingPrice = shippingPriceDao.getShippingPrice(transactionRecord.getPackageSize(), transactionRecord.getCarrierCode());
      BigDecimal discount = discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState);
      discountRecord.setShippingPrice(shippingPrice.subtract(discount));
      discountRecord.setDiscount(discount);
    }

    discountRecord.setValid(transactionRecord.isValid());
    discountRecord.setOriginalRecordString(transactionRecord.getOriginalRecordString());

    return discountRecord;
  }
}
