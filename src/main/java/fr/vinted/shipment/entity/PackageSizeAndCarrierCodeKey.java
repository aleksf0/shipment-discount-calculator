package fr.vinted.shipment.entity;

import java.util.Objects;

public class PackageSizeAndCarrierCodeKey {

  private final String packageSize;
  private final String carrierCode;

  public PackageSizeAndCarrierCodeKey(String packageSize, String carrierCode) {
    this.packageSize = packageSize;
    this.carrierCode = carrierCode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(packageSize, carrierCode);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof PackageSizeAndCarrierCodeKey)) {
      return false;
    }
    PackageSizeAndCarrierCodeKey other = (PackageSizeAndCarrierCodeKey) o;
    return Objects.equals(packageSize, other.packageSize) && Objects.equals(carrierCode, other.carrierCode);
  }
}
