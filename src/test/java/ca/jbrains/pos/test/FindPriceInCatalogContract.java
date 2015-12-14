package ca.jbrains.pos.test;

import ca.jbrains.pos.test.SellOneItemControllerTest.Catalog;
import ca.jbrains.pos.test.SellOneItemControllerTest.Price;
import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        final Price priceMatchingBarcode = Price.cents(1290);
        final Catalog catalog = createCatalogWith("91732", priceMatchingBarcode);
        Assert.assertEquals(priceMatchingBarcode, catalog.findPrice("91732"));
    }

    protected abstract Catalog createCatalogWith(String barcode, Price priceMatchingBarcode);

    @Test
    public void productNotFound() throws Exception {
        Assert.assertEquals(null, createCatalogWithout("72624").findPrice("72624"));
    }

    protected abstract Catalog createCatalogWithout(String barcodeToAvoid);
}
