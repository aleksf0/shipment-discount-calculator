package fr.vinted.shipment.entity;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import fr.vinted.shipment.entity.ShippingPriceRecord;
import fr.vinted.shipment.test.UnitTestCategory;

public class ShippingPriceRecordTest {

  private ShippingPriceRecord shippingPriceRecord;

  @Before
  public void setUp() {
    shippingPriceRecord = new ShippingPriceRecord("LP S 1.50");
  }

  @Test
  @Category(UnitTestCategory.class)
  public void CarrierCodeExtractedFromValidShippingPriceRecord() {
    assertThat(shippingPriceRecord.getCarrierCode(), is("LP"));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void PackageSizeExtractedFromValidShippingPriceRecord() {
    assertThat(shippingPriceRecord.getPackageSize(), is("S"));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void ShippingPriceExtractedFromValidShippingPriceRecord() {
    assertThat(shippingPriceRecord.getShippingPrice(), is(new BigDecimal("1.50")));
  }

  @Test(expected = IllegalStateException.class)
  @Category(UnitTestCategory.class)
  public void ExceptionThrownIfShippingPriceRecordIsMalformed() {
    @SuppressWarnings("unused")
    ShippingPriceRecord malformedShippingPriceRecord = new ShippingPriceRecord("LP S 1.50x");
  }
}
