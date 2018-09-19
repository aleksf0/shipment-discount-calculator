package fr.vinted.shipment.helper;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import fr.vinted.shipment.dao.ShippingPriceDaoStub;
import fr.vinted.shipment.entity.PackageSizeAndCarrierCodeKey;
import fr.vinted.shipment.entity.TransactionRecord;
import fr.vinted.shipment.entity.TransactionRecordsProcessingState;
import fr.vinted.shipment.test.UnitTestCategory;

public class DiscountCalculatorTest {

  @Test
  @Category(UnitTestCategory.class)
  public void TransactionRecordsProcessingStateIsResetWhenTransactionMonthChanges() {

    final BigDecimal discountAmountSum = new BigDecimal("1000");

    ShippingPriceDaoStub shippingPriceDaoStub = new ShippingPriceDaoStub(new BigDecimal("2"), new BigDecimal("1.50"));
    DiscountCalculator discountCalculator = new DiscountCalculator(shippingPriceDaoStub);
    TransactionRecord transactionRecord = new TransactionRecord("2015-02-01 S MR", shippingPriceDaoStub);
    TransactionRecordsProcessingState transactionRecordsProcessingState = new TransactionRecordsProcessingState();
    transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap().put(new PackageSizeAndCarrierCodeKey("S", "MR"), 3);
    transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap().put(new PackageSizeAndCarrierCodeKey("M", "LP"), 5);
    transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap().put(new PackageSizeAndCarrierCodeKey("L", "MR"), 7);
    transactionRecordsProcessingState.setDiscountAmountSum(discountAmountSum);
    transactionRecordsProcessingState.setPreviousMonth(transactionRecord.getTransactionDate().getMonth().minus(1));

    discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState);

    assertThat(transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap().size(), is(1));
    assertThat(transactionRecordsProcessingState.getDiscountAmountSum(), is(lessThan(discountAmountSum)));
    assertThat(transactionRecordsProcessingState.getPreviousMonth(), is(transactionRecord.getTransactionDate().getMonth()));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void TransactionRecordsProcessingStateIsAccumulatedForTheSameTransactionMonth() {

    ShippingPriceDaoStub shippingPriceDaoStub = new ShippingPriceDaoStub(new BigDecimal("2"), new BigDecimal("1.50"));
    DiscountCalculator discountCalculator = new DiscountCalculator(shippingPriceDaoStub);
    TransactionRecord transactionRecord = new TransactionRecord("2015-02-01 S MR", shippingPriceDaoStub);
    TransactionRecordsProcessingState transactionRecordsProcessingState = new TransactionRecordsProcessingState();
    transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap().put(new PackageSizeAndCarrierCodeKey("M", "LP"), 5);
    transactionRecordsProcessingState.setPreviousMonth(transactionRecord.getTransactionDate().getMonth());

    discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState);

    assertThat(transactionRecordsProcessingState.getShipmentCounterBySizeAndCarrierMap().size(), is(2));
    assertThat(transactionRecordsProcessingState.getDiscountAmountSum(), is(greaterThan(BigDecimal.ZERO)));
    assertThat(transactionRecordsProcessingState.getPreviousMonth(), is(transactionRecord.getTransactionDate().getMonth()));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void FullDiscountForSmallestShipmentsIsCalculatedWhenItFitsIntoRemainingDiscountLimit() {

    ShippingPriceDaoStub shippingPriceDaoStub = new ShippingPriceDaoStub(new BigDecimal("2"), new BigDecimal("1.50"));
    DiscountCalculator discountCalculator = new DiscountCalculator(shippingPriceDaoStub);
    TransactionRecord transactionRecord = new TransactionRecord("2015-02-01 S MR", shippingPriceDaoStub);
    TransactionRecordsProcessingState transactionRecordsProcessingState = new TransactionRecordsProcessingState();

    assertThat(discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState), is(new BigDecimal("0.50")));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void PartialDiscountForSmallestShipmentsIsCalculatedWhenItExceedsRemainingDiscountLimit() {

    final BigDecimal remainingDiscountLimit = new BigDecimal("0.30");

    ShippingPriceDaoStub shippingPriceDaoStub = new ShippingPriceDaoStub(new BigDecimal("2"), new BigDecimal("1.50"));
    DiscountCalculator discountCalculator = new DiscountCalculator(shippingPriceDaoStub);
    TransactionRecord transactionRecord = new TransactionRecord("2015-02-01 S MR", shippingPriceDaoStub);
    TransactionRecordsProcessingState transactionRecordsProcessingState = new TransactionRecordsProcessingState();
    transactionRecordsProcessingState.setPreviousMonth(transactionRecord.getTransactionDate().getMonth());
    transactionRecordsProcessingState.setDiscountAmountSum(DiscountCalculator.DISCOUNT_LIMIT.subtract(remainingDiscountLimit));

    assertThat(discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState), is(remainingDiscountLimit));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void FullDiscountForThirdLargeLaPosteShipmentsIsCalculatedWhenItFitsIntoRemainingDiscountLimit() {

    final BigDecimal largeLaPosteShipmentPrice = new BigDecimal("6.90");

    ShippingPriceDaoStub shippingPriceDaoStub = new ShippingPriceDaoStub(largeLaPosteShipmentPrice, new BigDecimal("4"));
    DiscountCalculator discountCalculator = new DiscountCalculator(shippingPriceDaoStub);
    TransactionRecord transactionRecord = new TransactionRecord("2015-02-01 L LP", shippingPriceDaoStub);
    TransactionRecordsProcessingState transactionRecordsProcessingState = new TransactionRecordsProcessingState();

    assertThat(discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState), is(BigDecimal.ZERO));
    assertThat(discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState), is(BigDecimal.ZERO));
    assertThat(discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState), is(largeLaPosteShipmentPrice));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void PartialDiscountForThirdLargeLaPosteShipmentsIsCalculatedWhenItExceedsRemainingDiscountLimit() {

    final BigDecimal remainingDiscountLimit = new BigDecimal("0.30");

    ShippingPriceDaoStub shippingPriceDaoStub = new ShippingPriceDaoStub(new BigDecimal("6.90"), new BigDecimal("4"));
    DiscountCalculator discountCalculator = new DiscountCalculator(shippingPriceDaoStub);
    TransactionRecord transactionRecord = new TransactionRecord("2015-02-01 L LP", shippingPriceDaoStub);
    TransactionRecordsProcessingState transactionRecordsProcessingState = new TransactionRecordsProcessingState();
    transactionRecordsProcessingState.setPreviousMonth(transactionRecord.getTransactionDate().getMonth());
    transactionRecordsProcessingState.setDiscountAmountSum(DiscountCalculator.DISCOUNT_LIMIT.subtract(remainingDiscountLimit));

    assertThat(discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState), is(BigDecimal.ZERO));
    assertThat(discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState), is(BigDecimal.ZERO));
    assertThat(discountCalculator.calculate(transactionRecord, transactionRecordsProcessingState), is(remainingDiscountLimit));
  }
}
