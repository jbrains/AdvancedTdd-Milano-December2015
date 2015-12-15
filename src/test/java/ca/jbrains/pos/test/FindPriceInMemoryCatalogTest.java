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
            this.pricesByBarcode = new HashMap(pricesByBarcode);
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }

    @Test
    public void changeAPriceBehindMyBack() throws Exception {
        final Price originalPrice = Price.cents(1);
        final HashMap<String, Price> pricesByBarcode = new HashMap<String, Price>() {{
            put("12345", originalPrice);
        }};

        final InMemoryCatalog catalog = new InMemoryCatalog(pricesByBarcode);

        pricesByBarcode.put("12345", Price.cents(2));
        Assert.assertEquals(originalPrice, catalog.findPrice("12345"));
    }
}
