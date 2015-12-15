package ca.jbrains.pos.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

public class HandleScannedBarcodeTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final BarcodeScannedListener barcodeScannedListener
            = context.mock(BarcodeScannedListener.class);
    private final CommandProcessor commandProcessor
            = new CommandProcessor(barcodeScannedListener);

    @Test
    public void oneBarcode() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode(with("12345"));
        }});

        commandProcessor.processCommands(new StringReader("12345"));
    }

    @Test
    public void noBarcodes() throws Exception {
        context.checking(new Expectations() {{
            never(barcodeScannedListener);
        }});

        commandProcessor.processCommands(new StringReader(""));
    }

    @Test
    public void manyBarcodes() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 1::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 2::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 3::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 4::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 5::"));
        }});
        
        commandProcessor.processCommands(new StringReader(
                "::barcode 1::\n"
                        + "::barcode 2::\n"
                        + "::barcode 3::\n"
                        + "::barcode 4::\n"
                        + "::barcode 5::"
        ));
    }

    @Test
    public void someEmptyBarcodes() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 1::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 2::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 3::"));
        }});

        commandProcessor.processCommands(new StringReader(
                "\n\n\n\r\n::barcode 1::\n\r\n\n\n"
                        + "::barcode 2::\n\n\r\n"
                        + "::barcode 3::\n\r\n"
        ));
    }

    public static class CommandProcessor {
        private final BarcodeScannedListener barcodeScannedListener;

        public CommandProcessor(BarcodeScannedListener barcodeScannedListener) {
            this.barcodeScannedListener = barcodeScannedListener;
        }

        private void processCommands(Reader source) {
            final BufferedReader bufferedReader = new BufferedReader(source);
            bufferedReader.lines()
                    .filter((line) -> !line.isEmpty())
                    .forEach(barcodeScannedListener::onBarcode);
        }
    }

    public interface BarcodeScannedListener {
        // CONTRACT
        // barcode is not empty
        void onBarcode(String barcode);
    }
}
