package fr.vinted.shipment.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.vinted.shipment.dao.ShippingPriceDao;
import fr.vinted.shipment.exception.ShippingPriceNotFoundException;

public class TransactionRecord {

  private static final Pattern transactionRecordPattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}) (\\S+) (\\S+)$");
  private static final String DATE_FORMAT = "yyyy-MM-dd";

  private ShippingPriceDao shippingPriceDao;

  private LocalDate transactionDate;
  private String packageSize;
  private String carrierCode;
  private boolean isValid;
  private String originalRecordString;

  public TransactionRecord(String transactionString, ShippingPriceDao shippingPriceDao) {
    this.shippingPriceDao = shippingPriceDao;
    parse(transactionString);
  }

  private void parse(String transactionString) {
    Matcher m = transactionRecordPattern.matcher(transactionString);
    m.find();
    try {
      transactionDate = LocalDate.parse(m.group(1), DateTimeFormatter.ofPattern(DATE_FORMAT));
      packageSize = m.group(2);
      carrierCode = m.group(3);
      if (shippingPriceDao.getShippingPrice(packageSize, carrierCode) == null) {
        throw new ShippingPriceNotFoundException(packageSize, carrierCode);
      }
      isValid = true;
    } catch (IllegalStateException | DateTimeParseException | ShippingPriceNotFoundException e) {
      isValid = false;
      originalRecordString = transactionString;
    }
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public String getPackageSize() {
    return packageSize;
  }

  public String getCarrierCode() {
    return carrierCode;
  }

  public boolean isValid() {
    return isValid;
  }

  public String getOriginalRecordString() {
    return originalRecordString;
  }
}
