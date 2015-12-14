package ca.jbrains.pos.test;

import ca.jbrains.pos.test.SellOneItemControllerTest.Catalog;
import ca.jbrains.pos.test.SellOneItemControllerTest.Price;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        final Price priceMatchingBarcode = Price.cents(1290);
        final InMemoryCatalog catalog = new InMemoryCatalog(
                Collections.singletonMap("91732", priceMatchingBarcode)
        );
        Assert.assertEquals(priceMatchingBarcode, catalog.findPrice("91732"));
    }

    @Test
    public void productNotFound() throws Exception {
        final InMemoryCatalog catalog = new InMemoryCatalog(new HashMap() {{
            put("not 72624", Price.cents(0));
            put("still not 72624", Price.cents(0));
            put("so totally not 72624", Price.cents(0));
        }});
        Assert.assertEquals(null, catalog.findPrice("72624"));
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
