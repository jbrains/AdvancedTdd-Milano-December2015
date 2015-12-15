package ca.jbrains.pos;

public interface BarcodeScannedListener {
    // CONTRACT
    // barcode is not empty
    void onBarcode(String barcode);
}
