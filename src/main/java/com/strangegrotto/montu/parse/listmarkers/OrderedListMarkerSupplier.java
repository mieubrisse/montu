package com.strangegrotto.montu.parse.listmarkers;

import com.strangegrotto.montu.view.component.checklistitem.ListMarker;
import com.strangegrotto.montu.view.component.checklistitem.OrdenalListMarker;

public class OrderedListMarkerSupplier implements ListMarkerSupplier {
    private int index;
    private final char delimiter;

    public OrderedListMarkerSupplier(int startIndex, char delimiter) {
        this.index = startIndex;
        this.delimiter = delimiter;
    }

    @Override
    public ListMarker get() {
        var result = new OrdenalListMarker(this.index, this.delimiter);
        this.index++;
        return result;
    }
}
