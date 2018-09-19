package fr.vinted.shipment;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import fr.vinted.shipment.dao.ShippingPriceDaoStub;
import fr.vinted.shipment.entity.DiscountRecord;
import fr.vinted.shipment.helper.FileUtils;
import fr.vinted.shipment.test.UnitTestCategory;

public class DiscountTest {

  private static final String INVALID_TRANSACTION_RECORD_INPUT = "/fr/vinted/shipment/invalidTransactionRecordInput.txt";
  private static final String TRANSACTION_RECORD_APPLICABLE_FOR_DISCOUNT_INPUT = "/fr/vinted/shipment/transactionRecordApplicableForDiscountInput.txt";
  private static final String TRANSACTION_RECORD_NOT_APPLICABLE_FOR_DISCOUNT_INPUT = "/fr/vinted/shipment/transactionRecordNotApplicableForDiscountInput.txt";

  @Test
  @Category(UnitTestCategory.class)
  public void DiscountRecordContainsOriginalTransactionStringIfInvalid() throws IOException {
    Discount discount = new Discount(new ShippingPriceDaoStub());
    List<DiscountRecord> discountRecordList = discount.processTransactionsFileContents(FileUtils.readResourceFile(INVALID_TRANSACTION_RECORD_INPUT));
    assertThat(discountRecordList.get(0).isValid(), is(false));
    assertThat(discountRecordList.get(0).getOriginalRecordString(), is("2015-02-29 CUSPS"));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void FormattedDiscountRecordContainsIgnoredIfInvalid() throws IOException {
    Discount discount = new Discount(new ShippingPriceDaoStub());
    List<DiscountRecord> discountRecordList = discount.processTransactionsFileContents(FileUtils.readResourceFile(INVALID_TRANSACTION_RECORD_INPUT));
    assertThat(discount.formatDiscountRecord(discountRecordList.get(0)), is("2015-02-29 CUSPS Ignored"));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void DiscountRecordContainsDiscountValueWhenApplicable() throws IOException {
    Discount discount = new Discount(new ShippingPriceDaoStub(new BigDecimal("2"), new BigDecimal("1.50")));
    List<DiscountRecord> discountRecordList = discount.processTransactionsFileContents(FileUtils.readResourceFile(TRANSACTION_RECORD_APPLICABLE_FOR_DISCOUNT_INPUT));
    assertThat(discountRecordList.get(0).isValid(), is(true));
    assertThat(discountRecordList.get(0).getDiscount(), is(new BigDecimal("0.50")));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void FormattedDiscountRecordContainsDiscountValueWhenApplicable() throws IOException {
    Discount discount = new Discount(new ShippingPriceDaoStub(new BigDecimal("2"), new BigDecimal("1.50")));
    List<DiscountRecord> discountRecordList = discount.processTransactionsFileContents(FileUtils.readResourceFile(TRANSACTION_RECORD_APPLICABLE_FOR_DISCOUNT_INPUT));
    assertThat(discount.formatDiscountRecord(discountRecordList.get(0)), is("2015-02-01 S MR 1.50 0.50"));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void DiscountRecordDoesntContainDiscountValueWhenNotApplicable() throws IOException {
    Discount discount = new Discount(new ShippingPriceDaoStub(new BigDecimal("3"), new BigDecimal("3")));
    List<DiscountRecord> discountRecordList = discount
        .processTransactionsFileContents(FileUtils.readResourceFile(TRANSACTION_RECORD_NOT_APPLICABLE_FOR_DISCOUNT_INPUT));
    assertThat(discountRecordList.get(0).isValid(), is(true));
    assertThat(discountRecordList.get(0).getDiscount(), is(BigDecimal.ZERO));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void FormattedDiscountRecordContainsHyphenInsteadOfDiscountValueWhenNotApplicable() throws IOException {
    Discount discount = new Discount(new ShippingPriceDaoStub(new BigDecimal("3"), new BigDecimal("3")));
    List<DiscountRecord> discountRecordList = discount
        .processTransactionsFileContents(FileUtils.readResourceFile(TRANSACTION_RECORD_NOT_APPLICABLE_FOR_DISCOUNT_INPUT));
    assertThat(discount.formatDiscountRecord(discountRecordList.get(0)), is("2015-02-08 M MR 3.00 -"));
  }
}
