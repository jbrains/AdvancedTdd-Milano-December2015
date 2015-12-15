package ca.jbrains.pos.test;

import ca.jbrains.pos.BarcodeScannedListener;
import ca.jbrains.pos.CommandProcessor;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

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

}
