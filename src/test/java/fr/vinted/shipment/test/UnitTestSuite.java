package fr.vinted.shipment.test;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import fr.vinted.shipment.DiscountTest;
import fr.vinted.shipment.entity.PackageSizeAndCarrierCodeKeyTest;
import fr.vinted.shipment.entity.ShippingPriceRecordTest;
import fr.vinted.shipment.entity.TransactionRecordTest;
import fr.vinted.shipment.helper.DiscountCalculatorTest;

@RunWith(Categories.class)
@IncludeCategory(UnitTestCategory.class)
@ExcludeCategory(IntegrationTestCategory.class)
@SuiteClasses({ DiscountTest.class, PackageSizeAndCarrierCodeKeyTest.class, ShippingPriceRecordTest.class, TransactionRecordTest.class,
    DiscountCalculatorTest.class })
public class UnitTestSuite {

}
