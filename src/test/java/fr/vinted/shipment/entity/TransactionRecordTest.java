package fr.vinted.shipment.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import fr.vinted.shipment.dao.ShippingPriceDaoStub;
import fr.vinted.shipment.entity.TransactionRecord;
import fr.vinted.shipment.test.UnitTestCategory;

public class TransactionRecordTest {

  private static final String VALID_TRANSACTION_RECORD = "2015-02-01 S MR";
  private static final String INVALID_TRANSACTION_RECORD = "2015-13-01 S MR";
  private TransactionRecord transactionRecord;

  @Before
  public void setUp() {
    transactionRecord = new TransactionRecord(VALID_TRANSACTION_RECORD, new ShippingPriceDaoStub(new BigDecimal("2"), null));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void TransactionDateExtractedFromValidTransactionRecord() {
    assertThat(transactionRecord.getTransactionDate(), is(LocalDate.of(2015, 2, 1)));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void PackageSizeExtractedFromValidTransactionRecord() {
    assertThat(transactionRecord.getPackageSize(), is("S"));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void CarrierCodeExtractedFromValidTransactionRecord() {
    assertThat(transactionRecord.getCarrierCode(), is("MR"));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void TransactionRecordMarkedValidWhenParsedSuccessfully() {
    assertThat(transactionRecord.isValid(), is(true));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void TransactionRecordMarkedInvalidWhenShippingPriceMissing() {
    transactionRecord = new TransactionRecord(VALID_TRANSACTION_RECORD, new ShippingPriceDaoStub(null, null));
    assertThat(transactionRecord.isValid(), is(false));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void TransactionRecordMarkedInvalidWhenIsMalformed() {
    transactionRecord = new TransactionRecord(INVALID_TRANSACTION_RECORD, new ShippingPriceDaoStub(new BigDecimal("2"), null));
    assertThat(transactionRecord.isValid(), is(false));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void OriginalRecordStringPreservedIfTransactionRecordMarkedInvalid() {
    transactionRecord = new TransactionRecord(INVALID_TRANSACTION_RECORD, new ShippingPriceDaoStub(new BigDecimal("2"), null));
    assertThat(transactionRecord.getOriginalRecordString(), is(INVALID_TRANSACTION_RECORD));
  }
}
