package com.strangegrotto.montu.view.component.checklistitem;

public class OrdenalListMarker implements ListMarker {
    private final int numeral;
    private final char delimiter;

    public OrdenalListMarker(int numeral, char delimiter) {
        this.numeral = numeral;
        this.delimiter = delimiter;
    }

    @Override
    public String getMarker() {
        return Integer.toString(this.numeral) + String.valueOf(this.delimiter);
    }
}
