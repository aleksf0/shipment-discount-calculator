package fr.vinted.shipment.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import fr.vinted.shipment.test.IntegrationTestCategory;

public class FileShippingPriceDaoTest {

  private ShippingPriceDao shippingPriceDao;

  @Before
  public void setUp() {
    shippingPriceDao = new FileShippingPriceDao();
  }

  @Test
  @Category(IntegrationTestCategory.class)
  public void ShippingPriceExistsForValidPackageSizeAndCarrierCodeCombination() {
    assertThat(shippingPriceDao.getShippingPrice("S", "LP"), is(new BigDecimal("1.50")));
  }

  @Test
  @Category(IntegrationTestCategory.class)
  public void ShippingPriceMissingForInvalidPackageSizeAndCarrierCodeCombination() {
    assertThat(shippingPriceDao.getShippingPrice("X", "LP"), is(nullValue()));
  }

  @Test
  @Category(IntegrationTestCategory.class)
  public void LowestShippingPriceExistsForValidPackageSize() {
    assertThat(shippingPriceDao.getLowestShippingPrice("M"), is(new BigDecimal("3")));
  }

  @Test
  @Category(IntegrationTestCategory.class)
  public void LowestShippingPriceMissingForInvalidPackageSize() {
    assertThat(shippingPriceDao.getLowestShippingPrice("X"), is(nullValue()));
  }
}
