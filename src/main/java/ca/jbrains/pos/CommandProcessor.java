package ca.jbrains.pos;

import java.io.BufferedReader;
import java.io.Reader;

public class CommandProcessor {
    private final BarcodeScannedListener barcodeScannedListener;

    public CommandProcessor(BarcodeScannedListener barcodeScannedListener) {
        this.barcodeScannedListener = barcodeScannedListener;
    }

    public void processCommands(Reader source) {
        final BufferedReader bufferedReader = new BufferedReader(source);
        bufferedReader.lines()
                .filter((line) -> !line.isEmpty())
                .forEach(barcodeScannedListener::onBarcode);
    }
}
