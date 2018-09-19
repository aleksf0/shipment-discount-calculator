package fr.vinted.shipment.exception;

public class ShippingPriceNotFoundException extends Exception {

  private static final long serialVersionUID = 1870102083591692519L;

  public ShippingPriceNotFoundException(String packageSize, String carrierCode) {
    super("The shipping price for the package size " + packageSize + " and carrier code " + carrierCode + " doesn't exist.");
  }
}
