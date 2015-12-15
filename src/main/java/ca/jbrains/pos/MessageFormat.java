package ca.jbrains.pos;

public interface MessageFormat {
    String formatProductNotFoundMessage(String barcode);

    String formatPrice(Price price);
}
