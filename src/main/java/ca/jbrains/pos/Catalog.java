package ca.jbrains.pos;

public interface Catalog {
    Price findPrice(String barcode);
}
