package fr.vinted.shipment.test;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import fr.vinted.shipment.dao.FileShippingPriceDaoTest;

@RunWith(Categories.class)
@IncludeCategory(IntegrationTestCategory.class)
@ExcludeCategory(UnitTestCategory.class)
@SuiteClasses({ FileShippingPriceDaoTest.class })
public class IntegrationTestSuite {

}
