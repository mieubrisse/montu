package com.strangegrotto.montu.parse.listmarkers;

import com.strangegrotto.montu.view.component.checklistitem.BulletListMarker;
import com.strangegrotto.montu.view.component.checklistitem.ListMarker;

public class BulletListMarkerSupplier implements ListMarkerSupplier {
    private final char bullet;

    public BulletListMarkerSupplier(char bullet) {
        this.bullet = bullet;
    }

    @Override
    public ListMarker get() {
        return new BulletListMarker(this.bullet);
    }
}
