package ca.jbrains.pos;

import java.io.InputStreamReader;
import java.util.HashMap;

public class VirtualPointOfSaleTerminal {
    public static void main(String[] args) {
        new CommandProcessor(
                new SellOneItemController(
                        new InMemoryCatalog(
                                new HashMap<String, Price>() {{
                                    put("4028700035814", Price.cents(301));
                                }}
                        ),
                        new HighlyFlexibleDisplay(
                                new EnglishLanguageFormat(),
                                new UdpPostOffice(
                                        "localhost",
                                        5358,
                                        "UTF-8"
                                )
                        )
                )
        ).processCommands(new InputStreamReader(System.in));
    }

    public static class StandardOutPostOffice implements PostOffice {
        @Override
        public void sendMessage(String text) {
            System.out.println(text);
        }
    }

    public static class HighlyFlexibleDisplay implements Display {
        private final MessageFormat messageFormat;
        private final PostOffice postOffice;

        public HighlyFlexibleDisplay(
                MessageFormat messageFormat,
                PostOffice postOffice) {

            this.messageFormat = messageFormat;
            this.postOffice = postOffice;
        }

        @Override
        public void displayPrice(Price price) {
            sendMessage(messageFormat.formatPrice(price));
        }

        private void sendMessage(String text) {
            postOffice.sendMessage(text);
        }

        @Override
        public void displayProductNotFoundMessage(String barcodeNotFound) {
            sendMessage(messageFormat.formatProductNotFoundMessage(barcodeNotFound));
        }
    }
}
