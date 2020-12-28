package com.strangegrotto.montu.secondparse.listmarkers;

public class OrderedListMarkerSupplier implements ListMarkerSupplier {
    private int index;
    private final char delimiter;

    public OrderedListMarkerSupplier(int startIndex, char delimiter) {
        this.index = startIndex;
        this.delimiter = delimiter;
    }

    @Override
    public String get() {
        var result = Integer.toString(this.index) + delimiter;
        this.index++;
        return result;
    }
}
