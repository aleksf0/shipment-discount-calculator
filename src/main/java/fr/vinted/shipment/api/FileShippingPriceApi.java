package fr.vinted.shipment.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.vinted.shipment.entity.PackageSizeAndCarrierCodeKey;
import fr.vinted.shipment.entity.ShippingPriceRecord;
import fr.vinted.shipment.helper.FileUtils;

public class FileShippingPriceApi {

  private static FileShippingPriceApi instance;

  private Map<PackageSizeAndCarrierCodeKey, BigDecimal> shippingPriceMap = new HashMap<>();

  private Map<String, BigDecimal> lowestPriceForSizeMap = new HashMap<>();

  private FileShippingPriceApi() {

    List<ShippingPriceRecord> shippingPriceRecordList = new ArrayList<>();

    for (String line : FileUtils.readResourceFile("/fr/vinted/shipment/api/priceMapData.txt")) {
      shippingPriceRecordList.add(new ShippingPriceRecord(line));
    }

    for (ShippingPriceRecord shippingPriceRecord : shippingPriceRecordList) {
      shippingPriceMap.put(new PackageSizeAndCarrierCodeKey(shippingPriceRecord.getPackageSize(), shippingPriceRecord.getCarrierCode()),
          shippingPriceRecord.getShippingPrice());
      if (lowestPriceForSizeMap.get(shippingPriceRecord.getPackageSize()) == null
          || lowestPriceForSizeMap.get(shippingPriceRecord.getPackageSize()).compareTo(shippingPriceRecord.getShippingPrice()) > 0) {
        lowestPriceForSizeMap.put(shippingPriceRecord.getPackageSize(), shippingPriceRecord.getShippingPrice());
      }
    }
  }

  public static FileShippingPriceApi getInstance() {
    if (instance == null) {
      synchronized (FileShippingPriceApi.class) {
        if (instance == null) {
          instance = new FileShippingPriceApi();
        }
      }
    }
    return instance;
  }

  public Map<PackageSizeAndCarrierCodeKey, BigDecimal> getShippingPriceMap() {
    return shippingPriceMap;
  }

  public Map<String, BigDecimal> getLowestPriceForSizeMap() {
    return lowestPriceForSizeMap;
  }
}