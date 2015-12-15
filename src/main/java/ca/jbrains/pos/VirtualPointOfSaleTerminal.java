package ca.jbrains.pos;

import java.io.InputStreamReader;
import java.util.HashMap;

public class VirtualPointOfSaleTerminal {
    public static class EnglishLanguageConsoleDisplay implements Display {
        @Override
        public void displayPrice(Price price) {
            displayText(formatPrice(price));
        }

        private void displayText(String text) {
            System.out.println(text);
        }

        private String formatPrice(Price price) {
            return String.format("EUR %.2f", price.inEuro());
        }

        @Override
        public void displayProductNotFoundMessage(String barcodeNotFound) {
            displayText(formatProductNotFoundMessage(barcodeNotFound));
        }

        private String formatProductNotFoundMessage(String barcodeNotFound) {
            return String.format("Product not found for %s", barcodeNotFound);
        }
    }

    public static void main(String[] args) {
        new CommandProcessor(
                new SellOneItemController(
                        new InMemoryCatalog(
                                new HashMap<String, Price>() {{
                                    put("4028700035814", Price.cents(301));
                                }}
                        ),
                        new EnglishLanguageConsoleDisplay()
                )
        ).processCommands(new InputStreamReader(System.in));
    }
}
