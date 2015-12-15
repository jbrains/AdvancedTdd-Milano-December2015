package ca.jbrains.pos;

import ca.jbrains.pos.Price;

public class EnglishLanguageFormat implements MessageFormat {
    @Override
    public String formatProductNotFoundMessage(String barcode) {
        return String.format("Product not found for %s", barcode);
    }

    @Override
    public String formatPrice(Price price) {
        return String.format("EUR %.2f", price.inEuro());
    }
}
