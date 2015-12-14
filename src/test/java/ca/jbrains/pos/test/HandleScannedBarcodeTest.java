package ca.jbrains.pos.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class HandleScannedBarcodeTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final BarcodeScannedListener barcodeScannedListener
            = context.mock(BarcodeScannedListener.class);

    @Test
    public void oneBarcode() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode(with("12345"));
        }});

        process(new StringReader("12345"));
    }

    private void process(StringReader source) throws IOException {
        final String theOnlyLineOfInput = new BufferedReader(source).readLine();
        barcodeScannedListener.onBarcode(theOnlyLineOfInput);
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
