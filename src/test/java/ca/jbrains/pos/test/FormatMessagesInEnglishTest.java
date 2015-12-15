package ca.jbrains.pos.test;

import ca.jbrains.pos.Price;
import org.junit.Assert;
import org.junit.Test;

public class FormatMessagesInEnglishTest {
    @Test
    public void productNotFound() throws Exception {
        final String text = new EnglishLanguageFormat().formatProductNotFoundMessage("99999");
        Assert.assertEquals("Product not found for 99999", text);
    }

    @Test
    public void dontSendMeEmptyBarcodeBecauseTheMessageWillLookBroken() throws Exception {
        final String text = new EnglishLanguageFormat().formatProductNotFoundMessage("");
        Assert.assertEquals("Product not found for ", text);
    }

    @Test
    public void price() throws Exception {
        Assert.assertEquals("EUR 12.00", new EnglishLanguageFormat().formatPrice(Price.cents(1200)));
    }

    public static class EnglishLanguageFormat {
        public String formatProductNotFoundMessage(String barcode) {
            return String.format("Product not found for %s", barcode);
        }

        public String formatPrice(Price price) {
            return String.format("EUR %.2f", price.inEuro());
        }
    }
}
