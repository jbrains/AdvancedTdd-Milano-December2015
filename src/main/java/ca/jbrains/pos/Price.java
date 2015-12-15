package ca.jbrains.pos;

public class Price {
    private final int centsValue;

    public Price(int centsValue) {
        this.centsValue = centsValue;
    }

    public static Price cents(int centsValue) {
        return new Price(centsValue);
    }

    public double inEuro() {
        return centsValue / 100.0d;
    }
}
