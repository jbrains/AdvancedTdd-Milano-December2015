package ca.jbrains.pos.test;

import ca.jbrains.pos.test.SellOneItemControllerTest.Price;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
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
    public static class InMemoryCatalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
