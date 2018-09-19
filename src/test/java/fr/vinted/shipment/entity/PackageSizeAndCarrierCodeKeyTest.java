package fr.vinted.shipment.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import fr.vinted.shipment.entity.PackageSizeAndCarrierCodeKey;
import fr.vinted.shipment.test.UnitTestCategory;

public class PackageSizeAndCarrierCodeKeyTest {

  PackageSizeAndCarrierCodeKey packageSizeAndCarrierCodeKey1;
  PackageSizeAndCarrierCodeKey packageSizeAndCarrierCodeKey2;

  @Before
  public void setUp() {
    packageSizeAndCarrierCodeKey1 = new PackageSizeAndCarrierCodeKey("S", "MR");
    packageSizeAndCarrierCodeKey2 = new PackageSizeAndCarrierCodeKey("S", "MR");
  }

  @Test
  @Category(UnitTestCategory.class)
  public void TwoPackageSizeAndCarrierCodeKeyObjectsOfMatchingArgumentsAreEqual() {
    assertThat(packageSizeAndCarrierCodeKey1.equals(packageSizeAndCarrierCodeKey2), is(true));
  }

  @Test
  @Category(UnitTestCategory.class)
  public void TwoPackageSizeAndCarrierCodeKeyObjectsOfMatchingArgumentsAreNotSame() {
    assertThat(packageSizeAndCarrierCodeKey1 == packageSizeAndCarrierCodeKey2, is(false));
  }
}
