package ca.jbrains.pos.test;

import ca.jbrains.pos.test.SellOneItemControllerTest.Catalog;
import ca.jbrains.pos.test.SellOneItemControllerTest.Price;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {

    @Override
    protected Catalog createCatalogWith(String barcode, Price priceMatchingBarcode) {
        return new InMemoryCatalog(
                new HashMap() {{
                    put(barcode, priceMatchingBarcode);
                    put("not " + barcode, Price.cents(-128736));
                    put("totally not " + barcode, Price.cents(-12763));
                }}
        );
    }

    @Override
    protected Catalog createCatalogWithout(final String barcodeToAvoid) {
        return new InMemoryCatalog(new HashMap() {{
            put("not " + barcodeToAvoid, Price.cents(0));
            put("still not " + barcodeToAvoid, Price.cents(0));
            put("so totally not " + barcodeToAvoid, Price.cents(0));
        }});
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
